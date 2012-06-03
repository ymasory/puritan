package puritan

import java.io.File

import scalaz.effect.IO

/** The computer's file system. */
trait FileSystem {
  def listRoots: IO[List[File]] = IO { (File listRoots()).toList }
  def sep: String = TODO
  def sepc: Char = TODO
}

/** Simply mixes in the [[puritan.FileSystem]] trait for easy access. */
object FileSystem extends FileSystem
