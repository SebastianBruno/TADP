package tpScala

import utils.Random



object Movement {

  type Movimiento = ((Guerrero, Option[Guerrero], Option[Item])) => ((Guerrero, Option[Guerrero]))
  type Accion = (Guerrero, Guerrero) => List[Guerrero]

  case object dejarseFajar extends Movimiento {
    def apply(args: (Guerrero, Option[Guerrero], Option[Item])) = {
      (args._1, args._2)
    }
  }

  case object cargarKi extends Movimiento {
    def apply(args: (Guerrero, Option[Guerrero], Option[Item])) = {
      val guerrero = args._1
      guerrero.especie match {
        case Androide => (guerrero,args._2)
        case SuperSaiyajin(_ , nivel) if nivel != 0 => (guerrero.aumentarKi(nivel * 150),args._2)
        case _ => (guerrero.aumentarKi(100), args._2)
      }
    }
  }

  case object usarItem extends Movimiento {
    def apply(args: (Guerrero, Option[Guerrero], Option[Item])) = {
      val atacante = args._1
      val atacado = args._2.get
      val arma = args._3.get

      if (!atacante.tieneItem(arma)) {
        throw new RuntimeException("El atacante no posee ese arma!")
      }

      (arma, atacado.especie) match {
        case (ArmaRoma, Androide) => (atacante,Some(atacado))
        case (ArmaRoma, _) => if (atacado.ki < 300) (atacante, Some(atacado.cambiarEstado(Some(Inconsciente)))) else (atacante,Some(atacado))
        case (ArmaFilosa, Saiyajin(true, Some(MonoGigante))) => (atacante, Some(atacado.cambiarEspecie(Saiyajin(false, None)).cambiarEstado(Some(Inconsciente))))
        case (ArmaFilosa, Saiyajin(true, transformacion)) => (atacante, Some(atacado.cambiarEspecie(Saiyajin(false, transformacion)).disminuirKi(atacado.ki - 1)))
        case (ArmaFilosa, _) => (atacante, Some(atacado.disminuirKi(atacante.ki / 100)))
        case (ArmaFuego(muni), Humano) => (atacante, Some(atacado.disminuirKi(20)))
        case (ArmaFuego(muni), Namekusein) => if (atacado.estado == Inconsciente) (atacante, Some(atacado.disminuirKi(10))) else (atacante, Some(atacado))
        case (Semilla, Androide) => (atacante, Some(atacado))
        case (Semilla, _) => (atacante, Some(atacado.copy(ki = atacado.kiMaximo)))
      }
    }
  }

  case object convertirseEnMono extends Movimiento{
    def apply(args: (Guerrero, Option[Guerrero], Option[Item])) = {
      val guerrero = args._1
      guerrero.especie match {
        case Saiyajin(true, _) if guerrero.tieneItem(FotoLuna) => (guerrero.convertirseEnMono(),args._2)
        case _ => throw new RuntimeException("No puede convertirse en mono!")
      }
    }
  }

  case object convertirseEnSuperSaiyajin extends Movimiento {
    def apply(args: (Guerrero, Option[Guerrero], Option[Item])) = {
      val guerrero = args._1
      guerrero.especie match {
        case Saiyajin(_, _) | SuperSaiyajin(_, _) if guerrero.ki > (guerrero.kiMaximo / 2) => (guerrero.copy(especie = guerrero.especie.siguienteNivel), args._2)
        case _ => (guerrero, args._2)
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