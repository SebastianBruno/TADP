package tpScala

import tpScala.Movement.Movimiento


trait Estado {
  def ejecutarMovimiento(guerrero: Guerrero, movimiento: Movimiento)
}
case object Inconsciente extends Estado {
  def ejecutarMovimiento(guerrero: Guerrero, movimiento: Movimiento) = ???
}
