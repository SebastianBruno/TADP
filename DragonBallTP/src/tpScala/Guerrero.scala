package tpScala

import tpScala.Movement.{EstadoBatalla, Movimiento}
import tpScala.Criteria.Criterio

case class Guerrero(nombre: String, items: Array[Item] = Array(),
    movimientos: Array[Movimiento] = Array.empty, ki: Int, kiMaximo: Int,
    especie: Especie, estado: Estado) {

  def ejecutarMovimiento(movimiento: Movimiento, enemigo: Option[Guerrero] = None): EstadoBatalla = {
    if (estado == Consciente) {
      return movimiento.apply(EstadoBatalla(this, enemigo))
    }

    return EstadoBatalla(this, enemigo)
  }

  def aumentarKi(cuanto: Int) = copy(ki = ki + cuanto)

  def disminuirKi(cuanto: Int): Guerrero =
    (cuanto >= ki) match {
      case true => muere
      case false  => this.copy(ki = ki - cuanto)
    }

  def muere(): Guerrero = copy(ki = 0, estado = Muerto)

  def cambiarEstado(estadoNuevo: Option[Estado] = None): Guerrero = {
    estadoNuevo match {
      case Some(_estado) => copy(estado = _estado)
      case None if ki < 300 => copy(estado = Inconsciente)
      case _ => this
    }
  }

  def cambiarNombre(nuevoNombre: String) : Guerrero = {
    copy(nombre = nuevoNombre)
  }

  def cambiarEspecie(nuevaEspecie: Especie): Guerrero = {
    copy(especie = nuevaEspecie)
  }

  def cambiarKiMaximo(nuevoKiMaximo: Int): Guerrero = {
    copy(kiMaximo = nuevoKiMaximo)
  }

  def agregarMovimientos(nuevosMovimientos: Array[Movimiento]): Guerrero = {
    copy(movimientos = movimientos ++ nuevosMovimientos)
  }

  def convertirseEnMono(): Guerrero = {
    especie match {
      case Saiyajin(_, Some(MonoGigante)) => throw new RuntimeException("Ya es mono!")
      case Saiyajin(true, _) => copy(especie = Saiyajin(true, Option(MonoGigante)), ki = kiMaximo, kiMaximo = kiMaximo * 3)
      case _ => throw new RuntimeException("No puede convertirse en mono!")
    }
  }


  def explotar(atacado: Guerrero): (Guerrero, Guerrero) = {
    val atacadoNew = atacado.especie match {
      case Namekusein if (ki * 2 >= atacado.ki) => atacado.copy(ki = 1)
      case Androide(_) => atacado.disminuirKi(ki * 3)
      case _ => atacado.disminuirKi(ki * 2)
    }
    (muere, atacadoNew)
  }

  def subirAKiMaximo() = copy(ki = kiMaximo)

  def tieneItem(item: Item): Boolean = items.contains(item)
  
  def movimientoMasEfectivoContra(enemigo: Guerrero)(criterio: Criterio): Movimiento = {
    criterio.apply(EstadoBatalla(this, Some(enemigo))).get
  }
  
  def simularMovimiento(enemigo: Guerrero, movimiento: Movimiento): EstadoBatalla = {
    val atacanteAux = this
    val enemigoAux = enemigo // La idea de los auxiliares, es no causar efecto colateral en los reales
    val estadoBatalla = atacanteAux.ejecutarMovimiento(movimiento, Some(enemigoAux))
    EstadoBatalla(estadoBatalla.atacante, Some(estadoBatalla.atacado.get))
  } 
}




