package com.yuvimasory

import java.io.File
import java.net.URI

import scala.{ Ordering => SOrdering }

import scalaz.{ Equal, Order, Ordering, Show }
import scalaz.Ordering.{ EQ, GT, LT }
import scalaz.effect.IO
import scalaz.effect.IO.io

class Handle(private val file: File) extends AnyRef
  with Serializable 
  with Comparable[Handle] {

  /* ########## difficult semantics for now ########## */
  //TODO createTempFile1/2
  //TODO deleteOnExit

  /* ########## pure Java methods, at least in OpenJDK ########## */
  def getAbsoluteFile: File = file.getAbsoluteFile
  def getAbsolutePath: String = file.getAbsolutePath
  def getName: String = file.getName
  def getParent: String = file.getParent
  def getParentFile: File = file.getParentFile
  def getPath: String = file.getPath
  def equals(that: Handle): Boolean = file equals that.file
  def isAbsolute: Boolean = file.isAbsolute
  def toURI: URI = file.toURI
  override def hashCode: Int = file.hashCode
  override def toString: String = file.toString

  /* ########## impure Java methods ########## */
  def canExecute: IO[Boolean] = IO { file.canExecute }
  def canRead: IO[Boolean] = IO { file.canRead }
  def canWrite: IO[Boolean] = IO { file.canWrite }
  def createNewFile: IO[Unit] = {
    val msg = "could not create new file %s" format angled(file)
    requireSuccess(msg) { file createNewFile() }
  }
  override def compareTo(that: Handle): Int = file compareTo that.file
  def delete: IO[Unit] = {
    def msg = "could not delete file %s" format angled(file)
    requireSuccess(msg) { file delete() }
  }
  def exists: IO[Boolean] = IO { file.exists }
  def getCanonicalFile: IO[File] = IO { file.getCanonicalFile }
  def getCanonicalPath: IO[String] = IO { file.getCanonicalPath }
  def getFreeSpace: IO[Long] = IO { file.getFreeSpace }
  def getTotalSpace: IO[Long] = IO { file.getTotalSpace }
  def getUsableSpace: IO[Long] = IO { file.getUsableSpace }
  def isDirectory: IO[Boolean] = IO { file.isAbsolute }
  def isFile: IO[Boolean] = IO { file.isFile }
  def isHidden: IO[Boolean] = IO { file.isHidden }
  def lastModified: IO[Long] = IO { file.lastModified }
  def length: IO[Long] = IO { file.length }
  def list: IO[List[String]] = IO { file.list.toList }
  def listFiles: IO[List[File]] = IO { file.listFiles.toList }
  def mkdir: IO[Unit] = {
    val msg = "could not create directory %s" format angled(file)
    requireSuccess(msg) { file mkdir() }
  }
  def mkdirs: IO[Unit] = {
    val msg = "could not create directory (tree) %s" format angled(file)
    requireSuccess(msg) { file.mkdirs }
  }
  def renameTo(dest: File): IO[Unit] = {
    val msg = "could not rename %s to %s" format (angled(file), angled(dest))
    requireSuccess(msg) { file renameTo dest }
  }
  def setExecutable(executable: Boolean): IO[Unit] = {
    val msg = "could not set %s to executable state %s" format (
      angled(file), quoted(executable)
    )
    requireSuccess(msg) { file setExecutable executable }
  }
  def setExecutableOwnerOnly(executable: Boolean): IO[Unit] = {
    val msg = (
      "could not set %s to executable state %s for owner only" format (
        angled(file), quoted(executable)
      )
    )
    requireSuccess(msg) { file setExecutable (executable, true) }
  }
  def setLastModified(time: Long): IO[Unit] = {
    val msg = "could not set last modified %s on file %s" format (
      time, angled(file)
    )
    requireSuccess(msg) { file setLastModified time }
  }
  def setReadable(readable: Boolean): IO[Unit] = {
    val msg = "could not file %s to readable state %s" format (
      angled(file), quoted(readable)
    )
    requireSuccess(msg) { file setReadable readable }
  }
  def setReadableOwnerOnly(readable: Boolean): IO[Unit] = {
    val msg = (
      "could not file %s to readable state %s for owner only" format (
        angled(file), quoted(readable)
      )
    )
    requireSuccess(msg) { file setReadable (readable, true) }
  }
  def setReadOnly: IO[Unit] = {
    val msg = "could not file %s to read-only" format angled(file)
    requireSuccess(msg) { file setReadOnly() }
  }
  def setWritable(writable: Boolean): IO[Unit] = {
    val msg = "could not file %s to writable state %s" format (
      angled(file), quoted(writable)
    )
    requireSuccess(msg) { file setWritable writable }
  }
  def setWritableOwnerOnly(writable: Boolean): IO[Unit] = {
    val msg = (
      "could not file %s to writable state %s for owner only" format (
        angled(file), quoted(writable)
      )
    )
    requireSuccess(msg) { file setWritable (writable, true) }
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

case class HandleOperationException(msg: String) extends RuntimeException(msg)

object Handle {
  def fromFile(file: File) = new Handle(file)
  def listRoots: IO[List[File]] = IO { (File listRoots()).toList }

  implicit def handleHasOrder: Order[Handle] = new Order[Handle] {
    override def order(lhs: Handle, rhs: Handle): Ordering = {
      val res = lhs compareTo rhs
      if (res < 0) LT else if (res == 0) EQ else GT //FIXME ==
    }
    override def equalIsNatural = true
  }
  implicit def handleHasShow: Show[Handle] = Show.showFromToString[Handle]
}

