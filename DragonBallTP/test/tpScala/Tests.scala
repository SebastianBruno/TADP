package tpScala

import org.junit.Before
import org.junit.Assert._
import org.junit.Test

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
}
