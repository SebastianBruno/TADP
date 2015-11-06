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

  @Test
  def unGuerreroAtacaConUnArmaFilosaAUnSaiyajinConCola() = {
    enemigo = enemigo.copy(especie = Saiyajin(true, None))
    enemigo = enemigo.recibirAtaque(atacante, ArmaFilosa)

    assertEquals(1, enemigo.ki)
    assertEquals(Saiyajin(false, None), enemigo.especie)
  }

  @Test
  def unGuerreroAtacaConUnArmaFilosaAUnSuperSaiyajinConCola() = {
    enemigo = enemigo.copy(especie = Saiyajin(true, None))
    enemigo = enemigo.recibirAtaque(atacante, ArmaFilosa)

    assertEquals(1, enemigo.ki)
    assertEquals(Saiyajin(false,None), enemigo.especie)
  }

  @Test
  def unAtaqueDeArmaRomaDejaInconscienteAUnGuerrer() = {
    atacante = atacante.copy(items =  Array[Item](ArmaRoma))

    enemigo = enemigo.recibirAtaque(atacante, ArmaRoma)

    assertEquals(enemigo.estado, Inconsciente)
  }

  @Test
  def unAtaqueDeArmaRomaNoLeHaceNadaALosAndroides() = {
    atacante = atacante.copy(items =  Array[Item](ArmaRoma))
    enemigo = enemigo.copy(especie = Androide)
    enemigo = enemigo.recibirAtaque(atacante, ArmaRoma)

    assertEquals(enemigo.estado, Consciente)
    assertEquals(enemigo.ki, 100)
  }

  @Test
  def unAtaqueDeArmaRomaNoLeHaceNadaAUnGuerreroConMasDe300DeKi() = {
    atacante = atacante.copy(items =  Array[Item](ArmaRoma))
    enemigo = enemigo.copy(ki = 500)

    enemigo = enemigo.recibirAtaque(atacante, ArmaRoma)

    assertEquals(enemigo.estado, Consciente)
  }

  @Test(expected=classOf[RuntimeException])
  def unGuerreroAtacaConUnArmaQueNoTiene() = {
    enemigo.recibirAtaque(atacante, ArmaRoma)
    assertEquals("sfdaf", enemigo.ki)
  }
}
