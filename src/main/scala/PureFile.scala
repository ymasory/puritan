package com.yuvimasory.puritan

import java.io.File
import java.net.URI

import scalaz.{ Equal, Order, Ordering, Show }
import scalaz.Ordering.{ EQ, GT, LT }
import scalaz.effect.IO

case class PureFileOperationException(msg: String) extends RuntimeException(msg)

class PureFile(private val f: File)
  extends Serializable with Comparable[PureFile] {

  /* #### subtyping-related, so no Java File functionality is lost. #### */
  override def compareTo(that: PureFile): Int = f compareTo that.f
  override def hashCode: Int = f.hashCode
  override def toString: String = f.toString

  /* ########## pure Java methods, at least in OpenJDK ########## */
  def getAbsoluteFile: PureFile = f.getAbsoluteFile
  def getName: String = f.getName
  def getParentFile: PureFile = f.getParentFile
  def getPath: String = f.getPath
  def equals(that: PureFile): Boolean = f equals that.f
  def isAbsolute: Boolean = f.isAbsolute
  def toURI: URI = f.toURI

  /* ########## impure Java methods ########## */
  def canExecute: IO[Boolean] = IO { f.canExecute }
  def canRead: IO[Boolean] = IO { f.canRead }
  def canWrite: IO[Boolean] = IO { f.canWrite }
  def createNewFile: IO[Unit] = {
    val msg = "could not create new file %s" format angled(f)
    requireSuccess(msg) { f createNewFile() }
  }
  def delete: IO[Unit] = {
    def msg = "could not delete file %s" format angled(f)
    requireSuccess(msg) { f delete() }
  }
  def exists: IO[Boolean] = IO { f.exists }
  def getCanonicalFile: IO[File] = IO { f.getCanonicalFile }
  def getFreeSpace: IO[Long] = IO { f.getFreeSpace }
  def getTotalSpace: IO[Long] = IO { f.getTotalSpace }
  def getUsableSpace: IO[Long] = IO { f.getUsableSpace }
  def isDirectory: IO[Boolean] = IO { f.isAbsolute }
  def isFile: IO[Boolean] = IO { f.isFile }
  def isHidden: IO[Boolean] = IO { f.isHidden }
  def lastModified: IO[Long] = IO { f.lastModified }
  def length: IO[Long] = IO { f.length }
  def listFiles: IO[List[PureFile]] = IO {
    import PureFile.fileToPureFile
    f.listFiles.toList.map { fileToPureFile(_) }
  }
  def mkdir: IO[Unit] = {
    val msg = "could not create directory %s" format angled(f)
    requireSuccess(msg) { f mkdir() }
  }
  def mkdirs: IO[Unit] = {
    val msg = "could not create directory (tree) %s" format angled(f)
    requireSuccess(msg) { f mkdirs() }
  }
  def renameTo(dest: PureFile): IO[Unit] = {
    val msg = "could not rename %s to %s" format (angled(f), angled(dest))
    requireSuccess(msg) { f renameTo dest.f }
  }
  def setExecutable(executable: Boolean): IO[Unit] = {
    val msg = "could not set %s to executable state %s" format (
      angled(f), quoted(executable)
    )
    requireSuccess(msg) { f setExecutable executable }
  }
  def setExecutableOwnerOnly(executable: Boolean): IO[Unit] = {
    val msg = (
      "could not set %s to executable state %s for owner only" format (
        angled(f), quoted(executable)
      )
    )
    requireSuccess(msg) { f setExecutable (executable, true) }
  }
  def setLastModified(time: Long): IO[Unit] = {
    val msg = "could not set last modified %s on file %s" format (
      time, angled(f)
    )
    requireSuccess(msg) { f setLastModified time }
  }
  def setReadable(readable: Boolean): IO[Unit] = {
    val msg = "could not file %s to readable state %s" format (
      angled(f), quoted(readable)
    )
    requireSuccess(msg) { f setReadable readable }
  }
  def setReadableOwnerOnly(readable: Boolean): IO[Unit] = {
    val msg = (
      "could not file %s to readable state %s for owner only" format (
        angled(f), quoted(readable)
      )
    )
    requireSuccess(msg) { f setReadable (readable, true) }
  }
  def setReadOnly: IO[Unit] = {
    val msg = "could not file %s to read-only" format angled(f)
    requireSuccess(msg) { f setReadOnly() }
  }
  def setWritable(writable: Boolean): IO[Unit] = {
    val msg = "could not file %s to writable state %s" format (
      angled(f), quoted(writable)
    )
    requireSuccess(msg) { f setWritable writable }
  }
  def setWritableOwnerOnly(writable: Boolean): IO[Unit] = {
    val msg = (
      "could not file %s to writable state %s for owner only" format (
        angled(f), quoted(writable)
      )
    )
    requireSuccess(msg) { f setWritable (writable, true) }
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
  private[this] def angled(file: PureFile): String =
    "<%s>" format file.getAbsoluteFile.getPath
  private[this] def quoted(b: Boolean): String = "\"%s\"" format b
  private[this] def requireSuccess(msg: String)(bool: => Boolean): IO[Unit] =
    IO { if (bool == false) throw PureFileOperationException(msg) }
}

object PureFile {

  /* #### conversions #### */
  implicit def fileToPureFile(f: File): PureFile = new PureFile(f)

  /* #### typeclass instances ##### */
  implicit def handleHasOrder: Order[PureFile] = new Order[PureFile] {
    override def order(lhs: PureFile, rhs: PureFile): Ordering = {
      import scalaz.std.anyVal.intInstance
      import scalaz.syntax.equal.ToEqualOps
      val res = lhs compareTo rhs
      if (res < 0) LT else if (res === 0) EQ else GT
    }
    override def equalIsNatural = true
  }
  implicit def handleHasShow: Show[PureFile] = Show.showFromToString[PureFile]
}

