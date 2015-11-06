package tpScala

import org.junit.Before
import org.junit.Assert._
import org.junit.Test
import tpScala.Movement.{convertirseEnMono, cargarKi, Movimiento}

class GuerreroSpec {
  var guerrero: Guerrero = null

  @Before
  def setUp() = {
    val items = Array[Item]()
    val movimientos = Array[Movimiento](cargarKi)
    guerrero = new Guerrero("Santi", items, movimientos, 4, 40, Androide, Inconsciente)
  }

  @Test
  def unGuerreroAumentaSuKi() = {
    guerrero = guerrero.aumentarKi(10)
    assertEquals(14, guerrero.ki)
  }

  @Test
  def unAndroideIntentaDescansarParaCargarSuKiPeroNoCargaNada() = {
    guerrero = guerrero.ejecutarMovimiento(cargarKi)
    assertEquals(4, guerrero.ki)
  }

  @Test
  def unHumanoDescansaParaAumentarSuKiYLoLogra = {
    /*guerrero = new DGB.Guerrero("Santi", items, movimientos, 4, 40, DGB.Humano, null)
    assertEquals(4, guerrero.ki)*/
  }

  @Test
  def convertirseEnMonoOK(): Unit ={
    val items = Array[Item]()
    val movimientos = Array[Movimiento](convertirseEnMono)

    guerrero = new Guerrero("Santi", items, movimientos, 4, 40, Androide, Inconsciente)


  }

}
