package tpScala

import org.junit.Before
import org.junit.Assert._
import org.junit.Test
import tpScala.Movement.{ cargarKi, Movimiento }
import tpScala.Movement.dejarseFajar
import tpScala.Movement.convertirseEnMono

class GuerreroSpec {
  var santiElSaiyajin: Guerrero = null
  var diegoElHumanoInservible: Guerrero = null
  var matiElAndroide: Guerrero = null
  
  var santiFusionado: Guerrero = null

  @Before
  def setUp() = {
    val items = Array[Item]()
    var movimientosSanti = Array[Movimiento]()
    movimientosSanti = movimientosSanti ++ Array(cargarKi)
    
    var movimientosDiego = Array[Movimiento]()
    movimientosDiego = movimientosDiego ++ Array(dejarseFajar)

    santiElSaiyajin = new Guerrero("Santi", items, movimientosSanti, 4, 40, Saiyajin(true, None), Inconsciente)
    diegoElHumanoInservible = new Guerrero("Diego", items, movimientosDiego, 0, 5, Humano, Inconsciente)
    matiElAndroide = new Guerrero("Mati", items, movimientosSanti, 10, 30, Androide, Inconsciente)
  }

  @Test
  def unGuerreroAumentaSuKi() = {
    santiElSaiyajin = santiElSaiyajin.aumentarKi(10)
    assertEquals(14, santiElSaiyajin.ki)
  }

  @Test
  def unAndroideIntentaDescansarParaCargarSuKiPeroNoCargaNada() = {
    santiElSaiyajin = santiElSaiyajin.ejecutarMovimiento(cargarKi)
    assertEquals(4, santiElSaiyajin.ki)
  }

  @Test
  def unHumanoDescansaParaAumentarSuKiYLoLogra = {
    /*guerrero = new DGB.Guerrero("Santi", items, movimientos, 4, 40, DGB.Humano, null)
    assertEquals(4, guerrero.ki)*/
  }

  @Test
  def unSaiyajinSeFusionaConUnHumanoConExito = {
    santiFusionado = santiElSaiyajin.fusionarseCon(diegoElHumanoInservible)
    assertEquals("SantiDiego", santiFusionado.nombre)
    assertTrue(santiFusionado.movimientos contains dejarseFajar)
    assertTrue(santiFusionado.movimientos contains cargarKi)
  }
  
  @Test (expected = classOf[RuntimeException])
  def unSaiyajinNoLograFusionarseConUnAndroide {
      santiFusionado = santiElSaiyajin.fusionarseCon(matiElAndroide)
  } 
}
