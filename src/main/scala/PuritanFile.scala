package puritan

import java.io.File
import java.net.URI

import scalaz.{ Equal, Order, Ordering, Show }
import scalaz.Ordering.{ EQ, GT, LT }
import scalaz.effect.IO

/** Pure wrapper for exceptions thrown by `java.io.File` methods that return
  * a boolean to indicate success or failure. */
case class PuritanFileOperationException(msg: String)
  extends RuntimeException(msg)

/** Pure wrapper for `java.io.File`. */
sealed trait PuritanFile {
  import PuritanFile.AddFileOps

  val javaFile: File

  /* #### subtyping-related, so no Java File functionality is lost. #### */
  override def hashCode: Int = javaFile.hashCode
  override def toString: String = javaFile.toString
  override def equals(that: Any): Boolean = {
    import scalaz.std.string.stringInstance
    import scalaz.syntax.equal.ToEqualOps
    that match {
      case p: PuritanFile => path === p.path
      case _ => false
    }
  }

  /* ########## pure Java methods, at least in OpenJDK ########## */
  def absolute: PuritanFile = javaFile.getAbsoluteFile.puritan
  def name: String = javaFile.getName
  def parent: PuritanFile = javaFile.getParentFile.puritan
  def path: String = javaFile.getPath
  def isAbsolute: Boolean = javaFile.isAbsolute
  def toUri: URI = javaFile.toURI

  /* ########## impure Java methods ########## */
  def canExecute: IO[Boolean] = IO { javaFile.canExecute }
  def canRead: IO[Boolean] = IO { javaFile.canRead }
  def canWrite: IO[Boolean] = IO { javaFile.canWrite }
  def exists: IO[Boolean] = IO { javaFile.exists }
  def isDirectory: IO[Boolean] = IO { javaFile.isAbsolute }
  def isRegularFile: IO[Boolean] = IO { javaFile.isFile }
  def isHidden: IO[Boolean] = IO { javaFile.isHidden }

  def canonical: IO[PuritanFile] = IO { javaFile.getCanonicalFile.puritan }
  def listFiles: IO[List[PuritanFile]] = IO {
    javaFile.listFiles.toList.map { _.puritan }
  }


  def freeSpace: IO[Long] = IO { javaFile.getFreeSpace }
  def totalSpace: IO[Long] = IO { javaFile.getTotalSpace }
  def usableSpace: IO[Long] = IO { javaFile.getUsableSpace }
  def lastModified: IO[Long] = IO { javaFile.lastModified }
  def length: IO[Long] = IO { javaFile.length }


  def changeLastModified(time: Long): IO[Unit] = {
    val msg = "could not set last modified %s on file %s" format (
      time, angled
    )
    require(msg) { javaFile setLastModified time }
  }
  def createRegularFile: IO[Unit] = {
    val msg = "could not create new file %s" format angled
    require(msg) { javaFile createNewFile() }
  }
  def delete: IO[Unit] = {
    def msg = "could not delete file %s" format angled
    require(msg) { javaFile delete() }
  }
  def makeExecutable(executable: Boolean): IO[Unit] = {
    val msg = "could not set %s to executable state %s" format (
      angled, executable.quoted
    )
    require(msg) { javaFile setExecutable executable }
  }
  def makeReadable(readable: Boolean): IO[Unit] = {
    val msg = "could not file %s to readable state %s" format (
      angled, readable.quoted
    )
    require(msg) { javaFile setReadable readable }
  }
  def makeReadableByOwnerOnly(readable: Boolean): IO[Unit] = {
    val msg = (
      "could not file %s to readable state %s for owner only" format (
        angled, readable.quoted
      )
    )
    require(msg) { javaFile setReadable (readable, true) }
  }
  def makeReadOnly: IO[Unit] = {
    val msg = "could not file %s to read-only" format angled
    require(msg) { javaFile setReadOnly() }
  }
  def makeWritable(writable: Boolean): IO[Unit] = {
    val msg = "could not file %s to writable state %s" format (
      angled, writable.quoted
    )
    require(msg) { javaFile setWritable writable }
  }
  def makeWritableByOwnerOnly(writable: Boolean): IO[Unit] = {
    val msg = (
      "could not file %s to writable state %s for owner only" format (
        angled, writable.quoted
      )
    )
    require(msg) { javaFile setWritable (writable, true) }
  }
  def makeExecutableByOwnerOnly(executable: Boolean): IO[Unit] = {
    val msg = (
      "could not set %s to executable state %s for owner only" format (
        angled, executable.quoted
      )
    )
    require(msg) { javaFile setExecutable (executable, true) }
  }
  def mkdir: IO[Unit] = {
    val msg = "could not create directory %s" format angled
    require(msg) { javaFile mkdir() }
  }
  def mkdirs: IO[Unit] = {
    val msg = "could not create directory (tree) %s" format angled
    require(msg) { javaFile mkdirs() }
  }
  def renameTo(dest: PuritanFile): IO[Unit] = {
    val msg = "could not rename %s to %s" format (angled, dest.angled)
    require(msg) { javaFile renameTo dest.javaFile }
  }

  /* ########## difficult semantics for now ########## */
  //TODO createTempFile1/2
  //TODO deleteOnExit

  /* ########## not implementing ########## */
  //(deprecated)
  //- toUrl
  //(String methods where File methods are available)
  // - list
  // - getAbsolutePath
  // - getCanonicalPath
  // - getParent

  /* ########## custom ########## */
  /*
  def extension: Option[String] = ""
  def sansExtension: String = ""
  def basename: String = ""
  */
  /*
  def find: IO[List[File]] = Nil.pure[IO]
  def findFiltered(filter: File => Boolean): IO[List[File]] = Nil.pure[IO]
  def findByGlob(glob: String): IO[List[File]] = Nil.pure[IO]
  def findByGlobFiltered(
    glob: String, filter: File => Boolean
  ): IO[List[File]] = Nil.pure[IO]
  def findByRegex(regex: String): IO[List[File]] = Nil.pure[IO]
  def findByRegexFiltered(
    regex: String, filter: File => Boolean
  ): IO[List[File]] = Nil.pure[IO]
  */

  /* ########## internal ########## */
  private[puritan] def angled: String = "<%s>" format absolute.path

  private[puritan] class BooleanOps(bool: Boolean) {
    def quoted = "\"%s\"" format bool
  }
  private[puritan] implicit def AddBooleanOps(bool: Boolean): BooleanOps =
    new BooleanOps(bool)

  private[puritan] def require(msg: String)(bool: => Boolean): IO[Unit] = TODO
}

/** Conversions and typeclass instances for `PuritanFile`. */
object PuritanFile {

  /* #### conversions ##### */
  /** Pimps a `java.io.File` with the `FileOps` functions. */
  implicit def AddFileOps(file: File): FileOps = new FileOps {
    val f = file
  }

  /* #### typeclass instances ##### */
  /* `scalaz.Order` instance for `PuritanFile`. */
  implicit def PuritanFileHasOrder: Order[PuritanFile] =
    new Order[PuritanFile] {
      override def order(lhs: PuritanFile, rhs: PuritanFile): Ordering = {
        import scalaz.std.anyVal.intInstance
        import scalaz.syntax.equal.ToEqualOps
        val res = lhs.javaFile compareTo rhs.javaFile
        if (res < 0) LT else if (res === 0) EQ else GT
      }
      override def equalIsNatural = true
    }

  /* `scalaz.Show` instance for `PuritanFile`. */
  implicit def PuritanFileHasShow: Show[PuritanFile] =
    Show.showFromToString[PuritanFile]
}

sealed trait FileOps {
  val f: File

  /* Converts a `java.io.File` to a `PuritanFile`. */
  def puritan: PuritanFile = new PuritanFile { val javaFile = f }
}
