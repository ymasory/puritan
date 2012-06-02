package com.yuvimasory.puritan

import java.io.File
import java.net.URI

import scalaz.{ Equal, Order, Ordering, Show }
import scalaz.Ordering.{ EQ, GT, LT }
import scalaz.effect.IO

/** Pure wrapper for exceptions thrown by `java.io.File` methods that return
  * a boolean to indicate success or failure. */
case class PureFileOperationException(msg: String) extends RuntimeException(msg)

/** Pure wrapper for `java.io.File`. */
sealed trait PureFile {

  import PureFile.pureFile

  val javaFile: File

  /* #### subtyping-related, so no Java File functionality is lost. #### */
  override def hashCode: Int = javaFile.hashCode
  override def toString: String = javaFile.toString
  override def equals(that: AnyRef): Boolean = TODO //canEqual

  /* ########## pure Java methods, at least in OpenJDK ########## */
  def absolute: PureFile = javaFile.getAbsoluteFile
  def name: String = javaFile.getName
  def parent: PureFile = javaFile.getParentFile
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

  def canonical: IO[PureFile] = IO { javaFile.getCanonicalFile }
  def listFiles: IO[List[PureFile]] = IO {
    javaFile.listFiles.toList.map { pureFile(_) }
  }


  def freeSpace: IO[Long] = IO { javaFile.getFreeSpace }
  def totalSpace: IO[Long] = IO { javaFile.getTotalSpace }
  def usableSpace: IO[Long] = IO { javaFile.getUsableSpace }
  def lastModified: IO[Long] = IO { javaFile.lastModified }
  def length: IO[Long] = IO { javaFile.length }


  def changeLastModified(time: Long): IO[Unit] = {
    val msg = "could not set last modified %s on file %s" format (
      time, angled(javaFile)
    )
    requireSuccess(msg) { javaFile setLastModified time }
  }
  def createRegularFile: IO[Unit] = {
    val msg = "could not create new file %s" format angled(javaFile)
    requireSuccess(msg) { javaFile createNewFile() }
  }
  def delete: IO[Unit] = {
    def msg = "could not delete file %s" format angled(javaFile)
    requireSuccess(msg) { javaFile delete() }
  }
  def makeExecutable(executable: Boolean): IO[Unit] = {
    val msg = "could not set %s to executable state %s" format (
      angled(javaFile), quoted(executable)
    )
    requireSuccess(msg) { javaFile setExecutable executable }
  }
  def makeReadable(readable: Boolean): IO[Unit] = {
    val msg = "could not file %s to readable state %s" format (
      angled(javaFile), quoted(readable)
    )
    requireSuccess(msg) { javaFile setReadable readable }
  }
  def makeReadableByOwnerOnly(readable: Boolean): IO[Unit] = {
    val msg = (
      "could not file %s to readable state %s for owner only" format (
        angled(javaFile), quoted(readable)
      )
    )
    requireSuccess(msg) { javaFile setReadable (readable, true) }
  }
  def makeReadOnly: IO[Unit] = {
    val msg = "could not file %s to read-only" format angled(javaFile)
    requireSuccess(msg) { javaFile setReadOnly() }
  }
  def makeWritable(writable: Boolean): IO[Unit] = {
    val msg = "could not file %s to writable state %s" format (
      angled(javaFile), quoted(writable)
    )
    requireSuccess(msg) { javaFile setWritable writable }
  }
  def makeWritableByOwnerOnly(writable: Boolean): IO[Unit] = {
    val msg = (
      "could not file %s to writable state %s for owner only" format (
        angled(javaFile), quoted(writable)
      )
    )
    requireSuccess(msg) { javaFile setWritable (writable, true) }
  }
  def makeExecutableByOwnerOnly(executable: Boolean): IO[Unit] = {
    val msg = (
      "could not set %s to executable state %s for owner only" format (
        angled(javaFile), quoted(executable)
      )
    )
    requireSuccess(msg) { javaFile setExecutable (executable, true) }
  }
  def mkdir: IO[Unit] = {
    val msg = "could not create directory %s" format angled(javaFile)
    requireSuccess(msg) { javaFile mkdir() }
  }
  def mkdirs: IO[Unit] = {
    val msg = "could not create directory (tree) %s" format angled(javaFile)
    requireSuccess(msg) { javaFile mkdirs() }
  }
  def renameTo(dest: PureFile): IO[Unit] = {
    val msg = "could not rename %s to %s" format (angled(javaFile), angled(dest))
    requireSuccess(msg) { javaFile renameTo dest.javaFile }
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

  /* ########## internal ########## */
  private[this] def angled(file: PureFile): String =
    "<%s>" format file.absolute.path
  private[this] def quoted(b: Boolean): String = "\"%s\"" format b
  private[this] def requireSuccess(msg: String)(bool: => Boolean): IO[Unit] =
    IO { if (bool == false) throw PureFileOperationException(msg) }
}

/** Conversions and typeclass instances for `PureFile`. */
object PureFile {

  /* #### conversions #### */
  /* Converts a `java.io.File` to a `PureFile`. */
  implicit def pureFile(file: File): PureFile = new PureFile {
    val javaFile = file
  }

  /* #### typeclass instances ##### */
  /* `scalaz.Order` instance for `PureFile`. */
  implicit def handleHasOrder: Order[PureFile] = new Order[PureFile] {
    override def order(lhs: PureFile, rhs: PureFile): Ordering = {
      import scalaz.std.anyVal.intInstance
      import scalaz.syntax.equal.ToEqualOps
      val res = lhs.javaFile compareTo rhs.javaFile
      if (res < 0) LT else if (res === 0) EQ else GT
    }
    override def equalIsNatural = true
  }

  /* `scalaz.Show` instance for `PureFile`. */
  implicit def handleHasShow: Show[PureFile] = Show.showFromToString[PureFile]
}

