package puritan

import java.io.File

import scalaz.effect.IO

/** Simply mixes in the `FileSystem` trait for easy access. */
object FileSystem extends FileSystem

/** The computer's file system. */
trait FileSystem {
  def listRoots: IO[List[File]] = IO { (File listRoots()).toList }
}
