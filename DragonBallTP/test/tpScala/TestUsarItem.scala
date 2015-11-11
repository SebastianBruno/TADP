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
    val result = atacante.ejecutarMovimiento(usarItem(ArmaFilosa), Some(enemigo))

    assertEquals(99, result.atacado.get.ki)
  }

  @Test
  def unGuerreroAtacaConUnArmaFilosaAUnSaiyajinConCola() = {
    enemigo = enemigo.copy(especie = Saiyajin(true, None))
    val result = atacante.ejecutarMovimiento(usarItem(ArmaFilosa), Some(enemigo))

    assertEquals(1, result.atacado.get.ki)
    assertEquals(Saiyajin(false, None), result.atacado.get.especie)
  }

  @Test
  def unGuerreroAtacaConUnArmaFilosaAUnSuperSaiyajinConCola() = {
    enemigo = enemigo.copy(especie = Saiyajin(true, None))
    val result = atacante.ejecutarMovimiento(usarItem(ArmaFilosa), Some(enemigo))

    assertEquals(1, result.atacado.get.ki)
    assertEquals(Saiyajin(false,None), result.atacado.get.especie)
  }

  @Test
  def unAtaqueDeArmaRomaDejaInconscienteAUnGuerrer() = {
    atacante = atacante.copy(items =  Array[Item](ArmaRoma))

    val result = atacante.ejecutarMovimiento(usarItem(ArmaRoma), Some(enemigo))

    assertEquals(result.atacado.get.estado, Inconsciente)
  }

  @Test
  def unAtaqueDeArmaRomaNoLeHaceNadaALosAndroides() = {
    atacante = atacante.copy(items =  Array[Item](ArmaRoma))
    enemigo = enemigo.copy(especie = Androide)
    val result = atacante.ejecutarMovimiento(usarItem(ArmaRoma), Some(enemigo))

    assertEquals(result.atacado.get.estado, Consciente)
    assertEquals(result.atacado.get.ki, 100)
  }

  @Test
  def unAtaqueDeArmaRomaNoLeHaceNadaAUnGuerreroConMasDe300DeKi() = {
    atacante = atacante.copy(items =  Array[Item](ArmaRoma))
    enemigo = enemigo.copy(ki = 500)

    val result = atacante.ejecutarMovimiento(usarItem(ArmaRoma), Some(enemigo))

    assertEquals(result.atacado.get.estado, Consciente)
  }

  @Test(expected=classOf[RuntimeException])
  def unGuerreroAtacaConUnArmaQueNoTiene() = {
    val result = atacante.ejecutarMovimiento(usarItem(ArmaRoma), Some(enemigo))
    assertEquals("sfdaf", result.atacado.get.ki)
  }
}
