package com.yuvimasory.puritan

import java.io.File

import scalaz.effect.IO
import scalaz.effect.IO.io

object FileSystem {
  /* #### Jdk functions #### */
  def listRoots: IO[List[File]] = IO { (File listRoots()).toList }
}
