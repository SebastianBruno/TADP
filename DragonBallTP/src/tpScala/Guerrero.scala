package tpScala

import tpScala.Movement.{ EstadoBatalla, ResultadoPelea,Ganador,SiguenPeleando, Movimiento }
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
      case false => this.copy(ki = ki - cuanto)
    }

  def muere(): Guerrero = copy(ki = 0, estado = Muerto)

  def cambiarEstado(estadoNuevo: Option[Estado] = None): Guerrero = {
    estadoNuevo match {
      case Some(_estado) => copy(estado = _estado)
      case None if ki < 300 => copy(estado = Inconsciente)
      case _ => this
    }
  }

  def cambiarNombre(nuevoNombre: String): Guerrero = {
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

  def movimientoMasEfectivoContra(enemigo: Guerrero)(criterio: Criterio): Option[Movimiento] = {
    var resultadoSimulaciones = for {
      movimiento <- movimientos
    } yield (movimiento, criterio(ejecutarMovimiento(movimiento, Some(enemigo))))

    var movimiento = resultadoSimulaciones.foldLeft(resultadoSimulaciones.head)((a, b) => if (a._2 > b._2) a else b)._1

    return Option(movimiento)

  }

  def pelearRound(movimiento: Movimiento)(enemigo: Guerrero): EstadoBatalla = {
    //Primer golpe
    var luegoPrimerGolpe = ejecutarMovimiento(movimiento, Some(enemigo))
    //Movimiento mas efectivo del atacado
    var movimientoAtacado = enemigo.movimientoMasEfectivoContra(luegoPrimerGolpe.atacado.get)(MayorKi)
    var luegoSegundoGolpe = luegoPrimerGolpe.atacado.get.ejecutarMovimiento(movimiento, Some(luegoPrimerGolpe.atacante))

    //Devolver con el orden correcto
    return EstadoBatalla(luegoSegundoGolpe.atacado.get, Some(luegoSegundoGolpe.atacante))
  }

  def planDeAtaqueContra(enemigo: Guerrero, cantidadDeRounds: Int)(unCriterio: Criterio): Option[Array[Movimiento]] = {
    var planDeAtaque: Array[Movimiento] = Array()
    var movimiento = movimientoMasEfectivoContra(enemigo)(unCriterio)
    if (movimiento == null) return None
    planDeAtaque = planDeAtaque ++ movimiento
    var estadoBatalla = pelearRound(movimiento.get)(enemigo)
    for (i <- 1 to (cantidadDeRounds - 1)) {
      movimiento = estadoBatalla.atacante.movimientoMasEfectivoContra(estadoBatalla.atacado.get)(unCriterio)
      if (movimiento == null) return None
      estadoBatalla = estadoBatalla.atacante.pelearRound(movimiento.get)(estadoBatalla.atacado.get)
      planDeAtaque = planDeAtaque ++ movimiento
    }
    return Option(planDeAtaque)
  }

  def pelearContra(enemigo :Guerrero)(plan :Array[Movimiento]) :ResultadoPelea = {
    //Chequear que el plan no esta vacio
    if (plan.size == 0) return SiguenPeleando(this,enemigo) 
    
    //Ejecutar round
    val resultadoRound :EstadoBatalla = this.pelearRound(plan.head)(enemigo)
    
    //Si alguno murio, devolver el ganador
    (resultadoRound.atacante.estado,resultadoRound.atacado.get.estado) match{
      case (_,Muerto) => return Ganador(this)
      case (Muerto,_) => return Ganador(enemigo)
    //Sino, seguir
      case _ => return resultadoRound.atacante.pelearContra(resultadoRound.atacado.get)(plan.tail)
    }   
  }
}




