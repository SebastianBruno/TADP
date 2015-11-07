package tpScala

trait Item

case object Semilla extends Item

case object ArmaFilosa extends Item

case class ArmaFuego(municion: Int = 6) extends Item

case object ArmaRoma extends Item

case object FotoLuna extends Item

case object EsferaDragon extends Item