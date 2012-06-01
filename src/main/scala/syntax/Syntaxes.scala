package com.yuvimasory.puritan
package syntax

trait Syntaxes {
  object handle {
    implicit def AddHandleSyntax(h: Handle): HandleSyntax = new HandleSyntax(h)
    class HandleSyntax(h: Handle)
  } 
}
