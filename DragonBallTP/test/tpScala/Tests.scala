package tpScala

import org.junit.Before
import org.junit.Assert._
import org.junit.Test
import tpScala.Movement.Movimiento

class Tests {
  var guerrero: Guerrero = null

  @Before
  def setUp() = {
    val items = Array[Item]()
    val movimientos = Array[Movimiento]()
    guerrero = new Guerrero("Santi", items, movimientos, 4, 40, Androide, null)
  }

  @Test
  def unGuerreroAumentaSuKi() = {
    guerrero = guerrero.aumentarKi(10)
    assertEquals(14, guerrero.ki)
  }


}
