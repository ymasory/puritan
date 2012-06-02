package com.yuvimasory.puritan

import scalaz.effect.IO

object Terminal extends Terminal

trait Terminal {
  def readChar: IO[Char] = TODO
  def readLine: IO[String] = TODO
}
