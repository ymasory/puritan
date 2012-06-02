package com.yuvimasory.puritan

import java.io.File

import scalaz.effect.IO

trait IOInputStream {
  def next: IO[Byte]
  def hasNext: IO[Boolean]
}
