package tpScala

import tpScala.Movement.Movimiento


trait Estado {
  def ejecutarMovimiento(guerrero: Guerrero, movimiento: Movimiento): Guerrero
}

case object Inconsciente extends Estado {
  def ejecutarMovimiento(guerrero: Guerrero, movimiento: Movimiento) = {
    movimiento match {
      case cargarKi => guerrero
    }
  }
}

case object Consciente extends Estado {
  def ejecutarMovimiento(guerrero: Guerrero, movimiento: Movimiento) = {
      movimiento(guerrero)
  }
}

object Estado {

  val estados = List(Consciente, Inconsciente)

}