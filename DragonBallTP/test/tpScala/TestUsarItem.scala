package tpScala

import org.junit.{Before, Test}
import org.junit.Assert._
import tpScala.Movement._

class TestUsarItem {
  var atacante: Guerrero = null
  var enemigo: Guerrero = null

  @Before
  def setUp() = {
    atacante = new Guerrero("Numero 18", Array[Item](ArmaFilosa), Array[Movimiento](), 100, 200, Androide, Consciente)
    enemigo = new Guerrero("Picollo", Array[Item](), Array[Movimiento](), 100, 200, Namekusein, Consciente)

  }
  @Test
  def unGuerreroAtacaConUnArmaFilosa() = {
    enemigo = enemigo.recibirAtaque(atacante, ArmaFilosa)

    assertEquals(99, enemigo.ki)
  }

  @Test(expected=classOf[RuntimeException])
  def unGuerreroAtacaConUnArmaQueNoTiene() = {
    enemigo.recibirAtaque(atacante, ArmaRoma)
    assertEquals("sfdaf", enemigo.ki)
  }
}
