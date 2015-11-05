package tpScala

import tpScala.Movement.Movimiento


case class Guerrero(nombre: String, items: Array[Item] = Array(),
                    movimientos: Array[Movimiento] = Array.empty, ki: Int, kiMaximo: Int,
                    especie: Especie, estado: Estado) {

  def ejecutarMovimiento(movimiento: Movimiento): Guerrero = movimiento(this)

  def aumentarKi(cuanto: Int) = copy(ki = ki + cuanto)

  def disminuirKi(cuanto: Int) = {
    if (cuanto > ki) copy(ki = 0)
    else copy(ki = ki - cuanto)
  }

  def cambiarEstado(estadoNuevo: Estado) = copy(estado = estadoNuevo)

  def perderCola() {
    especie match {
      case Saiyajin(true, MonoGigante) => copy(especie = Saiyajin(false, null), /*estado = Inconsciente,*/ ki = 1, kiMaximo = kiMaximo / 3)
      case Saiyajin(true, transformacion) => copy(especie = Saiyajin(false, transformacion), ki = 1)
      case _ => throw new RuntimeException("Esta especie no tiene cola")
    }
  }

  def convertirseEnMono() {
    especie match {
      case Saiyajin(_, MonoGigante) => throw new RuntimeException("Ya es mono!")
      case Saiyajin(true, _) => copy(especie = Saiyajin(true, MonoGigante), ki = kiMaximo, kiMaximo = kiMaximo * 3)
      case _ => throw new RuntimeException("No puede convertirse en mono!")
    }
  }

  def subirAKiMaximo() = copy(ki = kiMaximo)

  def tieneItem(item: Item): Boolean = items.contains(item)
}




