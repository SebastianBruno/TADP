package tpScala

import tpScala.Movement.{EstadoBatalla, Movimiento}
import tpScala.Criteria._

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

  def subirAKiMaximo() = copy(ki = kiMaximo)

  def tieneItem(item: Item): Boolean = items.contains(item)
  
  def simularMovimiento(enemigo: Guerrero, movimiento: Movimiento): EstadoBatalla = {
    val atacanteAux = this
    val enemigoAux = enemigo // La idea de los auxiliares, es no causar efecto colateral en los reales
    val estadoBatalla = atacanteAux.ejecutarMovimiento(movimiento, Some(enemigoAux))
    EstadoBatalla(estadoBatalla.atacante, Some(estadoBatalla.atacado.get))
  }

  def movimientoMasEfectivoContra(enemigo: Guerrero)(criterio: Criterio) : Option[Movimiento] = {
    var resultadoSimulaciones = for  {
      movimiento <- movimientos
    } yield (movimiento, criterio(ejecutarMovimiento(movimiento, Some(enemigo))))

    var movimiento = resultadoSimulaciones.foldLeft(resultadoSimulaciones.head)((a, b) =>if(a._2 > b._2) a else b)._1

    return Option(movimiento)

  }
  
  def pelearRound(movimiento: Movimiento)(enemigo: Guerrero) :EstadoBatalla = {
    //Primer golpe
    var luegoPrimerGolpe = ejecutarMovimiento(movimiento, Some(enemigo))
    //Movimiento mas efectivo del atacado
    var movimientoAtacado = enemigo.movimientoMasEfectivoContra(luegoPrimerGolpe.atacado.get)(MayorKi)
    var luegoSegundoGolpe = luegoPrimerGolpe.atacado.get.ejecutarMovimiento(movimiento, Some(luegoPrimerGolpe.atacante))
    
    //Devolver con el orden correcto
    return EstadoBatalla(luegoSegundoGolpe.atacado.get,Some(luegoSegundoGolpe.atacante))
  }
}




