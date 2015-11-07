package tpScala

import org.junit.Assert._
import org.junit.{Test, Before}
import tpScala.Movement._
import utils.RandomFaked

class MovimientosSpec {
  var guerrero: Guerrero = null

  @Before
  def setUp() = {
    val items = Array[Item]()
    val movimientos = Array[Movimiento]()
    guerrero = new Guerrero("Numero 18", items, movimientos, 4, 40, Androide, Consciente)
  }

  @Test
  def unGuerreroAumentaSuKi() = {
    guerrero = guerrero.aumentarKi(10)
    assertEquals(14, guerrero.ki)
  }

  @Test
  def unAndroideIntentaDescansarParaCargarSuKiPeroNoCargaNada() = {
    val result = guerrero.ejecutarMovimiento(cargarKi)
    assertEquals(4, result._1.ki)
  }

  @Test
  def unHumanoDescansaParaAumentarSuKiYLoLogra = {
    val items = Array[Item]()
    val movimientos = Array[Movimiento]()
    guerrero = new Guerrero("Krillin", items, movimientos, 4, 40, Humano, Consciente)

    val result = guerrero.ejecutarMovimiento(cargarKi)

    assertEquals(104, result._1.ki)
  }

  @Test
  def unHumanoInconscienteDescansaParaAumentarSuKiYNoLoLogra = {
    val items = Array[Item]()
    val movimientos = Array[Movimiento]()
    guerrero = new Guerrero("Krillin", items, movimientos, 4, 40, Humano, Inconsciente)

    val result = guerrero.ejecutarMovimiento(cargarKi)

    assertEquals(4, result._1.ki)
  }

  @Test
  def unSaiyajinDescansaParaAumentarSuKiYLoLogra = {
    val items = Array[Item]()
    val movimientos = Array[Movimiento]()
    guerrero = new Guerrero("Vegetta", items, movimientos, 4, 40, Saiyajin(cola = true, transformacion = None), Consciente)

    var result = guerrero.ejecutarMovimiento(cargarKi)

    assertEquals(104, result._1.ki)
  }

  @Test
  def unSuperSaiyajinDescansaParaAumentarSuKiYLoLogra = {
    val items = Array[Item]()
    val movimientos = Array[Movimiento]()
    guerrero = new Guerrero("Goku", items, movimientos, 4, 40, SuperSaiyajin(cola = true), Consciente)

    var result = guerrero.ejecutarMovimiento(cargarKi)

    assertEquals(154, result._1.ki)
  }

  @Test
  def unMonstruoDescansaParaAumentarSuKiYLoLogra = {
    val items = Array[Item]()
    val movimientos = Array[Movimiento]()
    guerrero = new Guerrero("Cell", items, movimientos, 4, 40, Monstruo, Consciente)

    var result = guerrero.ejecutarMovimiento(cargarKi)

    assertEquals(104, result._1.ki)
  }

  @Test
  def unNamekuDescansaParaAumentarSuKiYLoLogra = {
    val items = Array[Item]()
    val movimientos = Array[Movimiento]()
    guerrero = new Guerrero("Picollo", items, movimientos, 4, 40, Namekusein, Consciente)

    var result = guerrero.ejecutarMovimiento(cargarKi)

    assertEquals(104, result._1.ki)
  }

  @Test
  def unSaiyajinSeConvierteEnSuperSaiyajin = {
    val items = Array[Item]()
    val movimientos = Array[Movimiento]()
    guerrero = new Guerrero("Picollo", items, movimientos, 21, 40, Saiyajin(), Consciente)

    val result = guerrero.ejecutarMovimiento(convertirseEnSuperSaiyajin)

    assertEquals(SuperSaiyajin(true, nivel = 1), result._1.especie)

  }

  @Test
  def unSuperSaiyajinDebeSolamenteSubirElNivel() = {
    val items = Array[Item]()
    val movimientos = Array[Movimiento]()
    guerrero = new Guerrero("Picollo", items, movimientos, 21, 40, SuperSaiyajin(), Consciente)

    val result = guerrero.ejecutarMovimiento(convertirseEnSuperSaiyajin)

    assertEquals(SuperSaiyajin(true, nivel = 2), result._1.especie)
  }

  @Test
  def siNoEsSaiyajinOSuperSaiyajinNoSeDebeConvertir = {
    val items = Array[Item]()
    val movimientos = Array[Movimiento]()
    guerrero = new Guerrero("Picollo", items, movimientos, 21, 40, Namekusein, Consciente)

    val result = guerrero.ejecutarMovimiento(convertirseEnSuperSaiyajin)

    assertEquals(Namekusein, result._1.especie)
  }

  @Test
  def hechizoAlAtacanteCon7EsferasDelDragon = {
    val atacante = new Guerrero("Picollo", Array(EsferaDragon,EsferaDragon,EsferaDragon,EsferaDragon,EsferaDragon,EsferaDragon,EsferaDragon,EsferaDragon), Array.empty , 21, 40, Humano, Inconsciente)
    val atacado = new Guerrero("Santi", Array.empty, Array.empty, 21, 40, Namekusein, Inconsciente)

    val guerrerosResultado = atacante.hechizar(Hechizar(RandomFaked(0), List(Consciente, Inconsciente)), atacado)

    assertEquals(Consciente, guerrerosResultado(0).estado)
  }

  @Test
  def hechizoDeUnNamekusein = {
    val atacante = new Guerrero("Picollo", Array.empty, Array.empty , 21, 40, Namekusein, Inconsciente)
    val atacado = new Guerrero("Santi", Array.empty, Array.empty, 21, 40, Namekusein, Inconsciente)

    val guerrerosResultado = atacante.hechizar(Hechizar(RandomFaked(0), List(Consciente, Inconsciente)), atacado)

    assertEquals(Consciente, guerrerosResultado(0).estado)
  }

  @Test
  def hechizoDeUnHumanoNoEsValido = {
    val atacante = new Guerrero("Picollo", Array.empty, Array.empty , 21, 40, Humano, Inconsciente)
    val atacado = new Guerrero("Santi", Array.empty, Array.empty, 21, 40, Namekusein, Inconsciente)

    val guerrerosResultado = atacante.hechizar(Hechizar(RandomFaked(0), List(Consciente, Inconsciente)), atacado)

    assertEquals(Inconsciente, guerrerosResultado(0).estado)
  }


}
