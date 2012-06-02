package com.yuvimasory.puritan

import java.io.{ Console => JConsole }

import scalaz.effect.IO
import scalaz.effect.IO.io

object Terminal {

  def readChar: IO[Char] = TODO
  def readLine: IO[String] = TODO
  def readLineChars: IO[List[Char]] = TODO
}
