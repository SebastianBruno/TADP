package tpScala

import tpScala.Movement.Movimiento

case class Guerrero(nombre: String, items: Array[Item] = Array(),
    movimientos: Array[Movimiento] = Array.empty, ki: Int, kiMaximo: Int,
    especie: Especie, estado: Estado) {

  def ejecutarMovimiento(movimiento: Movimiento): Guerrero = {
    movimiento.apply(this);
  }

  def aumentarKi(cuanto: Int) = copy(ki = ki + cuanto)

  def recibirAtaque(atacante: Guerrero, arma: Item): Guerrero = {
    if (!atacante.tieneItem(arma)) {
      throw new RuntimeException("El atacante no posee ese arma!")
    }

    (arma, especie) match {
      case (ArmaRoma, Androide) => this
      case (ArmaRoma, _) => if (ki < 300) copy(estado = Inconsciente) else this
      case (ArmaFilosa, Saiyajin(true, Some(MonoGigante))) => copy(especie = Saiyajin(false, None), estado = Inconsciente)
      case (ArmaFilosa, Saiyajin(true, transformacion)) => copy(especie = Saiyajin(false, transformacion), ki = 1)
      case (ArmaFilosa, _) => copy(ki = ki - (atacante.ki / 100))
      case (ArmaFuego(muni), Humano) => copy(ki = ki - 20)
      case (ArmaFuego(muni), Namekusein) => if (estado == Inconsciente) copy(ki = ki - 10) else this
      case (Semilla, Androide) => this
      case (Semilla, _) => copy(ki = kiMaximo)
    }
  }

  def disminuirKi(cuanto: Int): Guerrero = {
    val nuevo = (cuanto > ki) match {
      case true => muere
      case false => this.copy(ki = ki - cuanto)
    }
    println(nuevo.ki)
    nuevo
  }

  def muere(): Guerrero = {
    copy(ki = 0, estado = Muerto)
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

  def convertirseEnMono(): Guerrero = {
    especie match {
      case Saiyajin(_, Some(MonoGigante)) => throw new RuntimeException("Ya es mono!")
      case Saiyajin(true, _) => copy(especie = Saiyajin(true, Option(MonoGigante)), ki = kiMaximo, kiMaximo = kiMaximo * 3)
      case _ => throw new RuntimeException("No puede convertirse en mono!")
    }
  }

  def fusionarseCon(otroGuerrero: Guerrero): Guerrero = {
    (especie, otroGuerrero.especie) match {
      case (Humano | Saiyajin(_, _) | Namekusein, Humano | Saiyajin(_, _) | Namekusein) => copy(especie = Fusion,
        nombre = nombre ++ otroGuerrero.nombre, ki = ki + otroGuerrero.ki, kiMaximo = kiMaximo + otroGuerrero.kiMaximo,
        movimientos = movimientos ++ otroGuerrero.movimientos, items = items ++ otroGuerrero.items) // Agrego los items porque quedaba medio cualquiera sino
      case _ => throw new RuntimeException("No pueden fusionarse!")
    }
  }

  def muchosGolpesNinjaA(atacado: Guerrero): Guerrero = {
    (especie, atacado.especie) match {
      case (Humano, Androide) => disminuirKi(10)
      case _ if this.ki < atacado.ki => disminuirKi(20)
      case _ if this.ki > atacado.ki => atacado.disminuirKi(20)
    }
  }

  def explotar(atacado: Guerrero): (Guerrero, Guerrero) = {
    val atacadoNew = atacado.especie match {
      case Namekusein if (ki * 2 >= atacado.ki) => atacado.copy(ki = 1)
      case Androide => atacado.disminuirKi(ki * 3)
      case _ => atacado.disminuirKi(ki * 2)
    }
    (muere, atacadoNew)
  }

  def subirAKiMaximo() = copy(ki = kiMaximo)

  def tieneItem(item: Item): Boolean = items.contains(item)
}




