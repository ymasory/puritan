package com.yuvimasory.puritan

import java.io.File

import scalaz.effect.IO

object FileIo extends FileIo

sealed abstract class Charset(name: String)

trait FileIo {
  def readFile(pureFile: PureFile): IO[String] = TODO
  def readFileCharset(pureFile: PureFile, charset: Charset): IO[String] = TODO
  def writeFile(pureFile: PureFile, contents: String): IO[Unit] = TODO
  def writeFileCharset(
    pureFile: PureFile, contents: String, charset: Charset
  ): IO[Unit] = TODO
}
