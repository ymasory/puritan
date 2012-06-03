package puritan

import java.io.File

import scalaz.effect.IO

/** Simply mixes in the [[puritan.FileIo]] trait for easier access. */
object FileIo extends FileIo

/** High-level operations on files. */
trait FileIo {
  def readFile(file: PuritanFile): IO[String] = TODO
  def readFileCharset(
    file: PuritanFile, charset: PuritanCharset
  ): IO[String] = TODO

  def writeFile(file: PuritanFile, contents: String): IO[Unit] = TODO
  def writeFileCharset(
    file: PuritanFile, contents: String, charset: PuritanCharset
  ): IO[Unit] = TODO

  def copyRegularFile(src: PuritanFile, dest: PuritanFile) = TODO
  def copyDirectory(src: PuritanFile, dest: PuritanFile) = TODO

  def moveDirectory(src: PuritanFile, dest: PuritanFile) = TODO
}
