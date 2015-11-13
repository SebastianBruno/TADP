package tpScala

import tpScala.Movement.{ EstadoBatalla, Movimiento }
import tpScala.Movement.EstadoBatalla

object Criteria {

  type Criterio = EstadoBatalla => Movimiento

  case object movimientoMasDanino extends Criterio {
    def apply(guerreros: EstadoBatalla) = {
      val atacante = guerreros.atacante
      val atacado = guerreros.atacado.get
      var movimientoElegido = atacante.movimientos.head
      var kiMaximo = atacante.cuantificarResultadoDeCriterio(atacado, atacante.movimientos.head)
      
      atacante.movimientos.foreach { mov =>  
        if(atacante.cuantificarResultadoDeCriterio(atacado, mov) > kiMaximo) 
          movimientoElegido = mov
          kiMaximo = atacante.cuantificarResultadoDeCriterio(atacado, mov)
          }
          
      movimientoElegido
    }
  }
}