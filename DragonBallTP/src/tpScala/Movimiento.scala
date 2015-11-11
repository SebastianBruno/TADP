package tpScala

import utils.Random



object Movement {

  case class EstadoBatalla(atacante: Guerrero, atacado : Option[Guerrero])

  type Movimiento = EstadoBatalla => EstadoBatalla
  type Accion = (Guerrero, Guerrero) => List[Guerrero]

  case object dejarseFajar extends Movimiento {
    def apply(args: EstadoBatalla) = args
  }

  case object cargarKi extends Movimiento {
    def apply(args: EstadoBatalla) = {
      val guerrero = args.atacante
      guerrero.especie match {
        case Androide => EstadoBatalla(guerrero,args.atacado)
        case SuperSaiyajin(_ , nivel) if nivel != 0 => EstadoBatalla(guerrero.aumentarKi(nivel * 150),args.atacado)
        case _ => EstadoBatalla(guerrero.aumentarKi(100), args.atacado)
      }
    }
  }

  case class usarItem(item: Item) extends Movimiento {

    var elMovimientoConItem: Movimiento = crearMovimientoConItem(_)(item)

    def crearMovimientoConItem(args: EstadoBatalla)(item: Item): EstadoBatalla = {
      val atacante = args.atacante
      val atacado = args.atacado.get
      val arma = item

      if (!atacante.tieneItem(arma)) {
        throw new RuntimeException("El atacante no posee ese arma!")
      }

      (arma, atacado.especie) match {
        case (ArmaRoma, Androide) => EstadoBatalla(atacante, Some(atacado))
        case (ArmaRoma, _) => if (atacado.ki < 300) EstadoBatalla(atacante, Some(atacado.cambiarEstado(Some(Inconsciente)))) else EstadoBatalla(atacante, Some(atacado))
        case (ArmaFilosa, Saiyajin(true, Some(MonoGigante))) => EstadoBatalla(atacante, Some(atacado.cambiarEspecie(Saiyajin(false, None)).cambiarEstado(Some(Inconsciente))))
        case (ArmaFilosa, Saiyajin(true, transformacion)) => EstadoBatalla(atacante, Some(atacado.cambiarEspecie(Saiyajin(false, transformacion)).disminuirKi(atacado.ki - 1)))
        case (ArmaFilosa, _) => EstadoBatalla(atacante, Some(atacado.disminuirKi(atacante.ki / 100)))
        case (ArmaFuego(muni), Humano) => EstadoBatalla(atacante, Some(atacado.disminuirKi(20)))
        case (ArmaFuego(muni), Namekusein) => if (atacado.estado == Inconsciente) EstadoBatalla(atacante, Some(atacado.disminuirKi(10))) else EstadoBatalla(atacante, Some(atacado))
        case (Semilla, Androide) => EstadoBatalla(atacante, Some(atacado))
        case (Semilla, _) => EstadoBatalla(atacante, Some(atacado.copy(ki = atacado.kiMaximo)))
      }
    }

    def apply(args: EstadoBatalla) = {
      elMovimientoConItem(args)
    }
  }

  case object convertirseEnMono extends Movimiento{
    def apply(args: EstadoBatalla) = {
      val guerrero = args.atacante
      guerrero.especie match {
        case Saiyajin(true, _) if guerrero.tieneItem(FotoLuna) => EstadoBatalla(guerrero.convertirseEnMono(),args.atacado)
        case _ => throw new RuntimeException("No puede convertirse en mono!")
      }
    }
  }

  case object convertirseEnSuperSaiyajin extends Movimiento {
    def apply(args: EstadoBatalla) = {
      val guerrero = args.atacante
      guerrero.especie match {
        case Saiyajin(_, _) | SuperSaiyajin(_, _) if guerrero.ki > (guerrero.kiMaximo / 2) => EstadoBatalla(guerrero.copy(especie = guerrero.especie.siguienteNivel), args.atacado)
        case _ => EstadoBatalla(guerrero, args.atacado)
      }

    }
  }

  case class Hechizar(random: Random, estados: List[Estado]) extends Movimiento {
    def apply(luchadores : EstadoBatalla): EstadoBatalla = {

      val aplicoA = (0 to 2)(random.nextInt(2))
      val guerreros = List(luchadores.atacante, luchadores.atacado.get)
      val estadoRandom = estados(random.nextInt(estados.size))

      (luchadores.atacante.especie.puedoHechizar, luchadores.atacante.items.filter(_.equals(EsferaDragon)).size > 7) match {
        case (true, _) | (_, true) => aplicoA match {
          case 0 => EstadoBatalla(guerreros(0).copy(estado = estadoRandom), luchadores.atacado)
          case 1 => EstadoBatalla(luchadores.atacante, Option(guerreros(1).copy(estado = estadoRandom)))
          case _ => {
            def aplicarHechizo(guerreros: List[Guerrero]): List[Guerrero] = guerreros match {
              case head :: tail => aplicarHechizo(tail) ++ List(head.copy(estado = estadoRandom))
              case _ => Nil
            }

            val nuevoEstado = aplicarHechizo(guerreros)
            EstadoBatalla(nuevoEstado(0), Option(nuevoEstado(1)))
          }
        }
        case _ => luchadores
      }
    }
  }

}