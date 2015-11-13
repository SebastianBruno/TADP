package tpScala

import tpScala.Movement.{ EstadoBatalla, Movimiento }
import tpScala.Movement.EstadoBatalla

object Criteria {

  type Criterio = EstadoBatalla => Option[Movimiento] // Lo declaro como Option porque puede darse el caso de que no devuelva ningun Movimiento
  
  
  case object movimientoMasDanino extends Criterio {
    def apply(guerreros: EstadoBatalla) = {
      val atacante = guerreros.atacante
      val atacado = guerreros.atacado.get
      var movimientoElegido = atacante.movimientos.head
      var kiMaximo = 0

      atacante.movimientos.foreach { mov =>
        val kiDisminuido = atacante.simularMovimiento(atacado, mov).atacado.get.ki
        if (kiDisminuido > kiMaximo)
          movimientoElegido = mov
        kiMaximo = kiDisminuido
      }
      if (kiMaximo != 0)
        Some(movimientoElegido)
      else
        None
    }
  }
}