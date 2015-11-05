package tpScala

object Movement {

  type Movimiento = Function1[Guerrero, Guerrero]


  case object dejarseFajar extends Movimiento {
    def apply(guerrero: Guerrero) = {
      guerrero
    }
  }

  case object cargarKi extends Movimiento {
    def apply(guerrero: Guerrero) = {
      guerrero.especie match {
        case Androide => guerrero
        case Saiyajin(_, SuperSaiyajin(nivel)) if nivel != 0 => guerrero.aumentarKi(nivel * 150)
        case _ => guerrero.aumentarKi(100)
      }
    }
  }

  case class convertirseEnMono() {
    def apply(guerrero: Guerrero) = {
      guerrero.especie match {
        case Saiyajin(true, _) if guerrero.tieneItem(fotoLuna) => guerrero.convertirseEnMono()
        case _ => throw new RuntimeException("No puede convertirse en mono!")
      }
    }
  }
}