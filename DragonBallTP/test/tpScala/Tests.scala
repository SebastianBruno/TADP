package tpScala

import org.junit.Before
import org.junit.Assert._
import org.junit.Test
import tpScala.Movement.{cargarKi, Movimiento}

class Tests {
  var guerrero: Guerrero = null

  @Before
  def setUp() = {
    val items = Array[Item]()
    val movimientos = Array[Movimiento]()
    guerrero = new Guerrero("Numero 18", items, movimientos, 4, 40, Androide, Inconsciente)
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
    val items = Array[Item]()
    val movimientos = Array[Movimiento]()
    guerrero = new Guerrero("Krillin", items, movimientos, 4, 40, Humano, null)

    guerrero = guerrero.ejecutarMovimiento(cargarKi)

    assertEquals(104, guerrero.ki)
  }

  @Test
  def unSaiyajinDescansaParaAumentarSuKiYLoLogra = {
    val items = Array[Item]()
    val movimientos = Array[Movimiento]()
    guerrero = new Guerrero("Vegetta", items, movimientos, 4, 40, Saiyajin(cola = true, transformacion = None), null)

    guerrero = guerrero.ejecutarMovimiento(cargarKi)

    assertEquals(104, guerrero.ki)
  }

  @Test
  def unSuperSaiyajinDescansaParaAumentarSuKiYLoLogra = {
    val items = Array[Item]()
    val movimientos = Array[Movimiento]()
    guerrero = new Guerrero("Goku", items, movimientos, 4, 40, Saiyajin(cola = true, transformacion = Some(SuperSaiyajin(nivel = 1))), null)

    guerrero = guerrero.ejecutarMovimiento(cargarKi)

    assertEquals(154, guerrero.ki)
  }

}
