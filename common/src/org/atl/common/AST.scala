package org.atl.common

/*
* Type := typename | typename -> type
* Statement : let name: Type = Term. | data typename = ctorlist.
* Term: name | Term Term | \ name : Type -> Term | (Term) | let name : Type = Term in Term
* */

sealed trait AST

trait ASTType extends AST{
  def ->(that: ASTType) = ArrowType(this, that)
}

case class TypeName(name: String) extends ASTType

case class ArrowType(left: ASTType, right: ASTType) extends ASTType


trait Statement {
  def |(that: Statement) = Seq(this, that)
}

case class LetStatement(what: NamedTypedDeclaration, term: Term) extends Statement

case class Seq(left: Statement, right: Statement) extends Statement

case class TypeDeclaration(name: String, ctors: List[NamedTypedDeclaration]) extends Statement

trait Term

case class NamedTypedDeclaration(name: String, typed: ASTType) extends AST

case class Application(left: Term, right: Term)

case class Abstraction(by: NamedTypedDeclaration, body: Term)

case class LetTerm(correspond: LetStatement, body: Term)


/*
* data tree = | Nil : tree | Node : num -> tree -> tree -> tree.
*
* let c : int = 42.
* let sum = \t: tree ->
* match
* | Node s l r => (+ (+ (sum l) (sum r)) s)
* | Nil => 0
* end.
*
* let main = \x ->
* let t: tree = Node 1 Nil Nil in
* println (sum t)
* .
*/

object testprogram {
  val t = TypeName("tree")
  val program =
    TypeDeclaration("tree", List(NamedTypedDeclaration("Nil", t), NamedTypedDeclaration("Node", TypeName("num")->t->t)))



}