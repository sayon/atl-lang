package org.atl.types

abstract sealed class TypeBase(val name: String)

sealed class AtomicType(name: String) extends TypeBase(name)

case object TNum extends AtomicType("num")

case object TReal extends AtomicType("real")

case object TString extends AtomicType("string")

class Constructor(val name: String, types: => List[TypeBase]) {
  lazy val product = types

  override def toString: String = s"$name ${product map (_.name)}"
}

object Constructor {
  def apply(name: String, types: => List[TypeBase]) = new Constructor(name, types)
}

case class TypeDeclaration(override val name: String, ctors: List[Constructor]) extends TypeBase(name)


object c extends App {
  val myt: TypeDeclaration = TypeDeclaration("nattree", new Constructor("Empty", Nil) :: new Constructor("Node", TNum :: myt :: myt :: Nil) :: Nil)

  println(myt)
}

/*
data t := 
  | Empty 
  | Node nat t t .

sealed case class Product(left: TypeBase, right: TypeBase) extends TypeBase

sealed case class Sum(left: TypeBase, right: TypeBase) extends TypeBase

sealed case class Arrow(left: TypeBase, right: TypeBase) extends TypeBase


  def ->(other: TypeBase) = Arrow(this, other)
  def *(other: TypeBase) = Product(this, other)
  def |(other: TypeBase) = Sum(this, other)

* */