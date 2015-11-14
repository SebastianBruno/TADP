package tpScala

import tpScala.Movement.{ EstadoBatalla, Movimiento }
import tpScala.Movement.EstadoBatalla
import utils.Random

object Criteria {

  type Criterio = EstadoBatalla => Option[Movimiento] // Lo declaro como Option porque puede darse el caso de que no devuelva ningun Movimiento

  case object movimientoMasDanino extends Criterio {
    def apply(guerreros: EstadoBatalla) = {
      val atacante = guerreros.atacante
      val atacado = guerreros.atacado.get
      var movimientoElegido = atacante.movimientos.head
      var kiMaximo = 0

      atacante.movimientos.foreach { mov =>
        val kiDisminuido = atacado.ki - atacante.simularMovimiento(atacado, mov).atacado.get.ki
        if (kiDisminuido > kiMaximo) {
          movimientoElegido = mov
          kiMaximo = kiDisminuido
        }
      }
      if (kiMaximo != 0)
        Some(movimientoElegido)
      else
        None
    }
  }

  case object movimientoMenosDanino extends Criterio {
    def apply(guerreros: EstadoBatalla) = {
      val atacante = guerreros.atacante
      val atacado = guerreros.atacado.get
      var movimientoElegido = atacante.movimientos.head
      var kiMinimo = atacado.ki - atacante.simularMovimiento(atacado, atacante.movimientos.head).atacado.get.ki

      atacante.movimientos.foreach { mov =>
        val kiDisminuido = atacado.ki - atacante.simularMovimiento(atacado, mov).atacado.get.ki
        if (kiDisminuido < kiMinimo) {
          movimientoElegido = mov
          kiMinimo = kiDisminuido
        }
      }
      Some(movimientoElegido)
    }
  }

  case object movimientoQueQuitaMenosItems extends Criterio {
    def apply(guerreros: EstadoBatalla) = {
      val atacante = guerreros.atacante
      val atacado = guerreros.atacado.get
      var movimientoElegido = atacante.movimientos.head
      var itemsMinimos = atacado.items.length - atacante.simularMovimiento(atacado, atacante.movimientos.head).atacado.get.items.length

      atacante.movimientos.foreach { mov =>
        val itemsPerdidos = atacado.items.length - atacante.simularMovimiento(atacado, mov).atacado.get.items.length
        if (itemsPerdidos < itemsMinimos) {
          movimientoElegido = mov
          itemsMinimos = itemsPerdidos
        }
      }
      Some(movimientoElegido)
    }
  }

  case object movimientoKrillin extends Criterio {
    def apply(guerreros: EstadoBatalla) = {
      val random = scala.util.Random
      val atacante = guerreros.atacante
      val aplicoA = random.nextInt(atacante.movimientos.length)
      Some(atacante.movimientos(aplicoA))
    }
  }

}