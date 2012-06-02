package com.yuvimasory.puritan

import scalaz.effect.IO
import scalaz.effect.IO.io

object Terminal {

  def readChar: IO[Char] = TODO
  def readLine: IO[List[Char]] = TODO
  def readLineString: IO[String] = TODO
}
