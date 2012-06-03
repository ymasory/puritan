package puritan

import java.io.File

import scalaz.effect.IO

/** Simply mixes in the [[puritan.FileIo]] trait for easier access. */
object FileIo extends FileIo

/** High-level operations on files. */
trait FileIo {
  def readFile(file: FilePath): IO[String] = TODO
  def readFileCharset(
    file: FilePath, charset: PuritanCharset
  ): IO[String] = TODO

  def writeFile(file: FilePath, contents: String): IO[Unit] = TODO
  def writeFileCharset(
    file: FilePath, contents: String, charset: PuritanCharset
  ): IO[Unit] = TODO

  def copyRegularFile(src: FilePath, dest: FilePath) = TODO
  def copyDirectory(src: FilePath, dest: FilePath) = TODO

  def moveDirectory(src: FilePath, dest: FilePath) = TODO
}
