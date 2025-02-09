/*
 * Copyright (c) 2015 Typelevel
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package cats

import simulacrum.typeclass

/**
 * `UnorderedTraverse` is like a `Traverse` for unordered containers.
 */
@typeclass trait UnorderedTraverse[F[_]] extends UnorderedFoldable[F] {
  def unorderedTraverse[G[_]: CommutativeApplicative, A, B](sa: F[A])(f: A => G[B]): G[F[B]]

  def unorderedSequence[G[_]: CommutativeApplicative, A](fga: F[G[A]]): G[F[A]] =
    unorderedTraverse(fga)(identity)
}

object UnorderedTraverse {

  /* ======================================================================== */
  /* THE FOLLOWING CODE IS MANAGED BY SIMULACRUM; PLEASE DO NOT EDIT!!!!      */
  /* ======================================================================== */

  /**
   * Summon an instance of [[UnorderedTraverse]] for `F`.
   */
  @inline def apply[F[_]](implicit instance: UnorderedTraverse[F]): UnorderedTraverse[F] = instance

  @deprecated("Use cats.syntax object imports", "2.2.0")
  object ops {
    implicit def toAllUnorderedTraverseOps[F[_], A](target: F[A])(implicit tc: UnorderedTraverse[F]): AllOps[F, A] {
      type TypeClassType = UnorderedTraverse[F]
    } =
      new AllOps[F, A] {
        type TypeClassType = UnorderedTraverse[F]
        val self: F[A] = target
        val typeClassInstance: TypeClassType = tc
      }
  }
  trait Ops[F[_], A] extends Serializable {
    type TypeClassType <: UnorderedTraverse[F]
    def self: F[A]
    val typeClassInstance: TypeClassType
    def unorderedTraverse[G[_], B](f: A => G[B])(implicit ev$1: CommutativeApplicative[G]): G[F[B]] =
      typeClassInstance.unorderedTraverse[G, A, B](self)(f)
    def unorderedSequence[G[_], B](implicit ev$1: A <:< G[B], ev$2: CommutativeApplicative[G]): G[F[B]] =
      typeClassInstance.unorderedSequence[G, B](self.asInstanceOf[F[G[B]]])
  }
  trait AllOps[F[_], A] extends Ops[F, A] with UnorderedFoldable.AllOps[F, A] {
    type TypeClassType <: UnorderedTraverse[F]
  }
  trait ToUnorderedTraverseOps extends Serializable {
    implicit def toUnorderedTraverseOps[F[_], A](target: F[A])(implicit tc: UnorderedTraverse[F]): Ops[F, A] {
      type TypeClassType = UnorderedTraverse[F]
    } =
      new Ops[F, A] {
        type TypeClassType = UnorderedTraverse[F]
        val self: F[A] = target
        val typeClassInstance: TypeClassType = tc
      }
  }
  @deprecated("Use cats.syntax object imports", "2.2.0")
  object nonInheritedOps extends ToUnorderedTraverseOps

  /* ======================================================================== */
  /* END OF SIMULACRUM-MANAGED CODE                                           */
  /* ======================================================================== */

}
