package tpScala


abstract class Especie

case object Androide extends Especie

case object Namekusein extends Especie

case object Monstruo extends Especie

case object Humano extends Especie

case class Saiyajin(cola: Boolean = true, transformacion: Transformacion = null) extends Especie



