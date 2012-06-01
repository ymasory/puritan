package com.yuvimasory.puritan.commonsio

import org.apache.commons.io.FileSystemUtils

import scalaz.effect.IO
import scalaz.effect.IO.io

object CommonsFileSystem {

  /* #### Commons functions #### */
  def freeSpaceKb: IO[Long] = IO { FileSystemUtils freeSpaceKb() }
  def freeSpaceKbTimeout(timeout: Long): IO[Long] = IO {
    FileSystemUtils freeSpaceKb timeout
  }
}
