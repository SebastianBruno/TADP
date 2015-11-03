package tpScala

object DGB{

  trait Item
  case object Semilla extends Item
  case object armaFilosa extends Item
  case class armaFuego(municion: Int = 6) extends Item
  case object armaRoma extends Item
  case object fotoLuna extends Item

  abstract class Especie
  case object Humano extends Especie
  case class Saiyajin(cola: Boolean = true, transformacion: Transformacion = null) extends Especie {

  }
  case object Androide extends Especie
  case object Namekusein extends Especie
  case object Monstruo extends Especie

  abstract class Transformacion
  case object MonoGigante extends Transformacion
  case class SuperSaiyajin(nivel: Int = 0) extends Transformacion

  trait Estado {
    def ejecutarMovimiento(guerrero: Guerrero, movimiento: Movimiento)
  }
  case object Inconsciente extends Estado {
    def ejecutarMovimiento(guerrero: Guerrero, movimiento: Movimiento) = {}
  }
  case class Guerrero(nombre: String,
      items: Array[Item] = Array(),
      movimientos: Array[Movimiento] = Array(),
      ki: Int,
      kiMaximo: Int,
      especie: Especie,
      estado: Estado) {
    def ejecutarMovimiento(movimiento: Movimiento) = estado.ejecutarMovimiento(this, movimiento)
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

  type Movimiento = Function1[Guerrero, Guerrero]
  case object dejarseFajar extends Movimiento {
    def apply(guerrero: Guerrero) = { guerrero }
  }
  case object cargarKi extends Movimiento {
    def apply(guerrero: Guerrero) = {
      guerrero.especie match {
        case Androide => guerrero
        case Saiyajin(_, SuperSaiyajin(nivel)) if nivel != 0 => guerrero.aumentarKi(nivel * 150)
        case _ => guerrero.aumentarKi(100)
      }
    }
  }
  case class usarItem(item: Item, atacado: Guerrero) {
    def apply(guerrero: Guerrero) = {
      (atacado.especie, item) match {
        case (Androide, armaRoma) => guerrero
        case (_, armaRoma) if guerrero.ki < 300 => guerrero.cambiarEstado(Inconsciente)
        case (Saiyajin(true, _), armaFilosa) => guerrero.perderCola()
        case (_, armaFilosa) => guerrero.disminuirKi(guerrero.ki / 100)
        case (Humano, armaFuego(municion)) if municion > 0 => guerrero.disminuirKi(20)
        case (Namekusein, armaFuego(municion)) if atacado.estado == Inconsciente
          && municion > 0 => guerrero.disminuirKi(10)
        case (_, semilla) => guerrero.subirAKiMaximo()
      }
    }
  }
  case class convertirseEnMono() {
    def apply(guerrero: Guerrero) = {
      guerrero.especie match {
        case Saiyajin(true, _) if guerrero.tieneItem(fotoLuna) => guerrero.convertirseEnMono()
        case _ => throw new RuntimeException("No puede convertirse en mono!")
      }
    }
  }
}