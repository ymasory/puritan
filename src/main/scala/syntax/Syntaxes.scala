package com.yuvimasory.puritan
package syntax

trait Syntaxes {

  object pureFile {

    implicit def AddPureFileSyntax(h: PureFile): PureFileSyntax =
      new PureFileSyntax(h)

    class PureFileSyntax(h: PureFile)
  } 
}
