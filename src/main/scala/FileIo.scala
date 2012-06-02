package puritan

import java.io.File

import scalaz.effect.IO

/** Simply mixes in the `FileIo` trait for easier access. */
object FileIo extends FileIo

/** High-level operations on files. */
trait FileIo {
  def readFile(file: PuritanFile): IO[String] = TODO
  def readFileCharset(file: PuritanFile, charset: Charset): IO[String] = TODO
  def writeFile(file: PuritanFile, contents: String): IO[Unit] = TODO
  def writeFileCharset(
    file: PuritanFile, contents: String, charset: Charset
  ): IO[Unit] = TODO
}
