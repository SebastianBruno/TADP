package tpScala

import org.junit.Before
import org.junit.Assert._
import org.junit.Test
import tpScala.DGB.cargarKi

class Tests {
  var guerrero: DGB.Guerrero = null

  @Before
  def setUp() = {
    val items = Array[DGB.Item]()
    val movimientos = Array[DGB.Movimiento]()
    guerrero = new DGB.Guerrero("Santi", items, movimientos, 4, 40, DGB.Androide, null)
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
}
