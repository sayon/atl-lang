package org.atl.common

abstract sealed class TypeBase(val name: String){
  def ->(other: TypeBase) = Arrow(this, other)
}

sealed class AtomicType(name: String) extends TypeBase(name)

sealed case class Arrow(left: TypeBase, right: TypeBase) extends TypeBase(s"$left -> $right")

case object TNum extends AtomicType("num")

case object TReal extends AtomicType("real")

case object TString extends AtomicType("string")

class Constructor(val name: String, mytype: => TypeBase) {
  lazy val product = mytype
  override def toString: String = name
}

//object Constructor {
//  def apply(name: String, types: => List[TypeBase]) = new Constructor(name, types)
//}

case class TypeDescription(override val name: String, ctors: List[Constructor]) extends TypeBase(name)

object c extends App {
  val myt: TypeDescription = TypeDescription("nattree", new Constructor("Empty", myt) :: new Constructor("Node", TNum -> myt -> myt -> myt) :: Nil)

  println(myt)
}



/*
data t := 
  | Empty 
  | Node nat t t .

sealed case class Product(left: TypeBase, right: TypeBase) extends TypeBase

sealed case class Sum(left: TypeBase, right: TypeBase) extends TypeBase


* */