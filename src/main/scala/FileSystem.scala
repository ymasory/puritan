package com.yuvimasory.puritan

import java.io.File

import scalaz.effect.IO

object FileSystem extends FileSystem

trait FileSystem {
  def listRoots: IO[List[File]] = IO { (File listRoots()).toList }
}
