package tpScala

object Movement {

  type Movimiento = (Guerrero =>  Guerrero)


  case object dejarseFajar extends Movimiento {
    def apply(guerrero: Guerrero) = {
      guerrero
    }
  }

  case object cargarKi extends Movimiento {
    def apply(guerrero: Guerrero) = {
      guerrero.especie match {
        case Androide => guerrero
        case SuperSaiyajin(_ , nivel) if nivel != 0 => guerrero.aumentarKi(nivel * 150)
        case _ => guerrero.aumentarKi(100)
      }
    }
  }

  case object convertirseEnMono extends Movimiento{
    def apply(guerrero: Guerrero) = {
      guerrero.especie match {
        case Saiyajin(true, _) if guerrero.tieneItem(FotoLuna) => guerrero.convertirseEnMono()
        case _ => throw new RuntimeException("No puede convertirse en mono!")
      }
    }
  }

  case object convertirseEnSuperSaiyajin extends Movimiento {
    def apply(guerrero: Guerrero) = {
      guerrero.especie match {
        case Saiyajin(_, _) | SuperSaiyajin(_, _) if guerrero.ki > (guerrero.kiMaximo / 2) => guerrero.copy(especie = guerrero.especie.siguienteNivel)
        case _ => guerrero
      }

    }
  }
}