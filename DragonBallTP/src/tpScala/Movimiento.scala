package tpScala

import utils.Random



object Movement {

  type Movimiento = (Guerrero =>  Guerrero)
  type Accion = (Guerrero, Guerrero) => List[Guerrero]


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

  case class Hechizar(random: Random, estados: List[Estado]) extends Accion {
    //TODO : devuelve una lista con los guerreros que fueron modificados hay que adaptarlo a un movimiento
    def apply(atacante: Guerrero, atacado: Guerrero): List[Guerrero] = {

      val aplicoA = (0 to 2)(random.nextInt(2))
      val guerreros = List(atacante, atacado)
      val estadoRandom = estados(random.nextInt(estados.size))

      (atacante.especie.puedoHechizar, atacante.items.filter(_.equals(EsferaDragon)).size > 7) match {
        case (true, _) | (_, true) => aplicoA match {
          case 0 | 1 => List(guerreros(aplicoA).copy(estado = estadoRandom))
          case _ => {
            def aplicarHechizo(guerreros: List[Guerrero]): List[Guerrero] = guerreros match {
              case head :: tail => aplicarHechizo(tail) ++ List(head.copy(estado = estadoRandom))
              case _ => Nil
            }

            aplicarHechizo(guerreros)
          }
        }
        case _ => List(atacante, atacado)
      }
    }
  }

}