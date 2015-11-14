package tpScala

import org.junit.Assert._
import org.junit.{Before, Test}
import tpScala.Movement.{Movimiento, cargarKi, dejarseFajar}
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

    santiElSaiyajin = new Guerrero("Santi", items, movimientosSanti, 15, 40, Saiyajin(true, None), Inconsciente)
    diegoElHumanoInservible = new Guerrero("Diego", items, movimientosDiego, 5, 10, Humano, Inconsciente)
    matiElAndroide = new Guerrero("Mati", items, movimientosSanti, 10, 30, Androide(), Inconsciente)
  }

  @Test
  def unGuerreroAumentaSuKi() = {
    santiElSaiyajin = santiElSaiyajin.aumentarKi(10)
    assertEquals(25, santiElSaiyajin.ki)
  }

  @Test
  def unAndroideIntentaDescansarParaCargarSuKiPeroNoCargaNada() = {
    var result = matiElAndroide.ejecutarMovimiento(cargarKi)
    assertEquals(10, result.atacante.ki)
  }

  @Test
  def unHumanoDescansaParaAumentarSuKiYLoLogra = {
    /*guerrero = new DGB.Guerrero("Santi", items, movimientos, 4, 40, DGB.Humano, null)
    assertEquals(4, guerrero.ki)*/
  }

}