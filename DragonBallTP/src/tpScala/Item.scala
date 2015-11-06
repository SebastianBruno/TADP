package tpScala

trait Item

case object Semilla extends Item

case object ArmaFilosa extends Item

case class ArmaFuego(municion: Int = 6) extends Item

case object ArmaRoma extends Item

case object FotoLuna extends Item

case class usarItem(item: Item, atacado: Guerrero) {
  def apply(guerrero: Guerrero) = {
    (atacado.especie, item) match {
      case (Androide, ArmaRoma) => guerrero
      case (_, ArmaRoma)  => guerrero.cambiarEstado(None)
      case (Saiyajin(true, _), ArmaFilosa) => guerrero.recibirAtaque(guerrero, ArmaFilosa)
      case (_, ArmaFilosa) => guerrero.disminuirKi(guerrero.ki / 100)
      case (Humano, ArmaFuego(municion)) if municion > 0 => guerrero.disminuirKi(20)
      case (Namekusein, ArmaFuego(municion)) if atacado.estado == Inconsciente
        && municion > 0 => guerrero.disminuirKi(10)
      case (_, semilla) => guerrero.subirAKiMaximo()
    }
  }
}/*
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
  }                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           */