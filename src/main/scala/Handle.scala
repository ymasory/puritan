package com.yuvimasory.puritan

import java.io.File
import java.net.URI

import scalaz.{ Equal, Order, Ordering, Show }
import scalaz.Ordering.{ EQ, GT, LT }
import scalaz.effect.IO
import scalaz.effect.IO.io

case class HandleOperationException(msg: String) extends RuntimeException(msg)

class Handle(private val f: File) extends Serializable with Comparable[Handle] {

  /* #### subtyping-related, so no Java File functionality is lost. #### */
  override def compareTo(that: Handle): Int = f compareTo that.f
  override def hashCode: Int = f.hashCode
  override def toString: String = f.toString

  /* ########## difficult semantics for now ########## */
  //TODO createTempFile1/2
  //TODO deleteOnExit

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

  /* ########## pure Java methods, at least in OpenJDK ########## */
  def getAbsoluteFile: File = f.getAbsoluteFile
  //not implementing, no String methods when File works
  // def getAbsolutePath: String = f.getAbsolutePath
  def getName: String = f.getName
  //not implementing, no String methods when File works
  // def getParent: String = f.getParent
  def getParentFile: File = f.getParentFile
  def getPath: String = f.getPath
  def equals(that: Handle): Boolean = f equals that.f
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
  //not implementing, no String methods when File works
  // def getCanonicalPath: IO[String] = IO { f.getCanonicalPath }
  def getFreeSpace: IO[Long] = IO { f.getFreeSpace }
  def getTotalSpace: IO[Long] = IO { f.getTotalSpace }
  def getUsableSpace: IO[Long] = IO { f.getUsableSpace }
  def isDirectory: IO[Boolean] = IO { f.isAbsolute }
  def isFile: IO[Boolean] = IO { f.isFile }
  def isHidden: IO[Boolean] = IO { f.isHidden }
  def lastModified: IO[Long] = IO { f.lastModified }
  def length: IO[Long] = IO { f.length }
  //not implementing, no String methods when File works
  // def list: IO[List[String]] = IO { f.list.toList }
  def listFiles: IO[List[File]] = IO { f.listFiles.toList }
  def mkdir: IO[Unit] = {
    val msg = "could not create directory %s" format angled(f)
    requireSuccess(msg) { f mkdir() }
  }
  def mkdirs: IO[Unit] = {
    val msg = "could not create directory (tree) %s" format angled(f)
    requireSuccess(msg) { f mkdirs() }
  }
  def renameTo(dest: File): IO[Unit] = {
    val msg = "could not rename %s to %s" format (angled(f), angled(dest))
    requireSuccess(msg) { f renameTo dest }
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

  /* ########## not implementing ########## */
  //toUrl (deprecated)

  /* ########## internal ########## */
  private[this] def angled(file: File): String =
    "<%s>" format file.getAbsolutePath
  private[this] def quoted(b: Boolean): String = "\"%s\"" format b
  private[this] def requireSuccess(msg: String)(bool: => Boolean): IO[Unit] =
    IO { if (bool == false) throw HandleOperationException(msg) }
}

object Handle {

  /* #### conversions #### */
  implicit def fileToHandle(f: File): Handle = new Handle(f)

  /* #### typeclass instances ##### */
  implicit def handleHasOrder: Order[Handle] = new Order[Handle] {
    override def order(lhs: Handle, rhs: Handle): Ordering = {
      import scalaz.std.anyVal.intInstance
      import scalaz.syntax.equal.ToEqualOps
      val res = lhs compareTo rhs
      if (res < 0) LT else if (res === 0) EQ else GT
    }
    override def equalIsNatural = true
  }
  implicit def handleHasShow: Show[Handle] = Show.showFromToString[Handle]
}

