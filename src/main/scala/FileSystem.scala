package com.yuvimasory.puritan

import org.apache.commons.io.FileSystemUtils

import java.io.File

import scalaz.effect.IO
import scalaz.effect.IO.io

object FileSystem {
  /* #### Jdk functions #### */
  def listRoots: IO[List[File]] = IO { (File listRoots()).toList }

  /* #### Commons functions #### */
  def freeSpaceKb: IO[Long] = IO { FileSystemUtils freeSpaceKb() }
  def freeSpaceKbTimeout(timeout: Long): IO[Long] = IO {
    FileSystemUtils freeSpaceKb timeout
  }
}
