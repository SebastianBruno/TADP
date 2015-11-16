package tpScala

import tpScala.Movement._
import utils.Random

object Criteria {

  type Criterio = EstadoBatalla => Int

  case object DiferenciaDeKiAFavor extends Criterio {
    def apply(estadoBatalla: EstadoBatalla): Int = {
      return estadoBatalla.atacante.ki - estadoBatalla.atacado.get.ki
    }
  }

  case object DejarFueraDeCombateAlEnemigo extends Criterio {
    def apply(estadoBatalla: EstadoBatalla): Int = {
      estadoBatalla.atacado.get.estado match {
        case Muerto => 100
        case Inconsciente => 50
        case _ => 0
      }
    }
  }

  case object DejarInconscienteAlEnemigo extends Criterio {
    def apply(estadoBatalla: EstadoBatalla): Int = {
      estadoBatalla.atacado.get.estado match {
        case Inconsciente => 100
        case _ => 0
      }
    }
  }
}