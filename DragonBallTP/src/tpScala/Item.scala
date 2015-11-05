package tpScala

trait Item

case object Semilla extends Item

case object armaFilosa extends Item

case class armaFuego(municion: Int = 6) extends Item

case object armaRoma extends Item

case object fotoLuna extends Item

case class usarItem(item: Item, atacado: Guerrero) {
  def apply(guerrero: Guerrero) = {
    (atacado.especie, item) match {
      case (Androide, armaRoma) => guerrero
      case (_, armaRoma) if guerrero.ki < 300 => guerrero.cambiarEstado(Inconsciente)
      case (Saiyajin(true, _), armaFilosa) => guerrero.perderCola()
      case (_, armaFilosa) => guerrero.disminuirKi(guerrero.ki / 100)
      case (Humano, armaFuego(municion)) if municion > 0 => guerrero.disminuirKi(20)
      case (Namekusein, armaFuego(municion)) if atacado.estado == Inconsciente
        && municion > 0 => guerrero.disminuirKi(10)
      case (_, semilla) => guerrero.subirAKiMaximo()
    }
  }
}