/** The Puritan top-level package. */
package object puritan {

  /** Throws an exception.
    * Allows a standard placeholder for unimplemented functions, regardless
    * of the function's type. */
  private[puritan] def TODO: Nothing = sys error "unimplemented method called"
}
