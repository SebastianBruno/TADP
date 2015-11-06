package tpScala

import tpScala.Movement.Movimiento

case class Guerrero(nombre: String, items: Array[Item] = Array(),
                    movimientos: Array[Movimiento] = Array.empty, ki: Int, kiMaximo: Int,
                    especie: Especie, estado: Estado) {

  def ejecutarMovimiento(movimiento: Movimiento): Guerrero = {
    estado.ejecutarMovimiento(this, movimiento)
  }

  def aumentarKi(cuanto: Int) = copy(ki = ki + cuanto)

  def recibirAtaque(atacante: Guerrero, arma: Item): Guerrero = {
    if(!atacante.tieneItem(arma)) {
      throw new RuntimeException("El atacante no posee ese arma!")
    }

    (arma, especie) match {
      case (ArmaRoma, Androide) => this
      case (ArmaRoma, _) => if(ki < 300) copy(estado = Inconsciente) else this
      case (ArmaFilosa, Saiyajin(true, Some(MonoGigante))) => copy(especie = Saiyajin(false, None), estado = Inconsciente)
      case (ArmaFilosa, Saiyajin(true, transformacion)) => copy(especie = Saiyajin(false, transformacion), ki = 1)
      case (ArmaFilosa, _) => copy(ki = ki - (atacante.ki / 100))
      case (ArmaFuego(muni), Humano) => copy(ki = ki - 20)
      case (ArmaFuego(muni), Namekusein) => if(estado == Inconsciente) copy(ki = ki - 10) else this
      case (Semilla, Androide) => this
      case (Semilla, _) => copy(ki = kiMaximo)
    }
  }


  def disminuirKi(cuanto: Int) = {
    if (cuanto > ki) copy(ki = 0)
    else copy(ki = ki - cuanto)
  }

  def cambiarEstado(estadoNuevo: Option[Estado] = None) = {
    estadoNuevo match {
      case Some(_estado) => copy(estado = _estado)
      case None if ki < 300 => copy(estado = Inconsciente)
      case _ => Unit
    }
  }

  /*def perderCola() {
    especie match {
      case Saiyajin(true, MonoGigante) => copy(especie = Saiyajin(false, None), /*estado = Inconsciente,*/ ki = 1, kiMaximo = kiMaximo / 3)
      case Saiyajin(true, transformacion) => copy(especie = Saiyajin(false, transformacion), ki = 1)
      case _ => throw new RuntimeException("Esta especie no tiene cola")
    }
  }*/

  def convertirseEnMono() {
    especie match {
      case Saiyajin(_, Some(MonoGigante)) => throw new RuntimeException("Ya es mono!")
      case Saiyajin(true, _) => copy(especie = Saiyajin(true, Option(MonoGigante)), ki = kiMaximo, kiMaximo = kiMaximo * 3)
      case _ => throw new RuntimeException("No puede convertirse en mono!")
    }
  }

  def subirAKiMaximo() = copy(ki = kiMaximo)

  def tieneItem(item: Item): Boolean = items.contains(item)
}




