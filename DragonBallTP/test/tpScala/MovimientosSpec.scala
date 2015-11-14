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
    guerrero = new Guerrero("Numero 18", items, movimientos, 4, 40, Androide(), Consciente)
  }

  @Test
  def unGuerreroAumentaSuKi() = {
    guerrero = guerrero.aumentarKi(10)
    assertEquals(14, guerrero.ki)
  }

  @Test
  def unAndroideIntentaDescansarParaCargarSuKiPeroNoCargaNada() = {
    val result = guerrero.ejecutarMovimiento(cargarKi)
    assertEquals(4, result.atacante.ki)
  }

  @Test
  def unHumanoDescansaParaAumentarSuKiYLoLogra = {
    val items = Array[Item]()
    val movimientos = Array[Movimiento]()
    guerrero = new Guerrero("Krillin", items, movimientos, 4, 40, Humano, Consciente)

    val result = guerrero.ejecutarMovimiento(cargarKi)

    assertEquals(104, result.atacante.ki)
  }

  @Test
  def unHumanoInconscienteDescansaParaAumentarSuKiYNoLoLogra = {
    val items = Array[Item]()
    val movimientos = Array[Movimiento]()
    guerrero = new Guerrero("Krillin", items, movimientos, 4, 40, Humano, Inconsciente)

    val result = guerrero.ejecutarMovimiento(cargarKi)

    assertEquals(4, result.atacante.ki)
  }

  @Test
  def unSaiyajinDescansaParaAumentarSuKiYLoLogra = {
    val items = Array[Item]()
    val movimientos = Array[Movimiento]()
    guerrero = new Guerrero("Vegetta", items, movimientos, 4, 40, Saiyajin(cola = true, transformacion = None), Consciente)

    var result = guerrero.ejecutarMovimiento(cargarKi)

    assertEquals(104, result.atacante.ki)
  }

  @Test
  def unSuperSaiyajinDescansaParaAumentarSuKiYLoLogra = {
    val items = Array[Item]()
    val movimientos = Array[Movimiento]()
    guerrero = new Guerrero("Goku", items, movimientos, 4, 40, SuperSaiyajin(cola = true), Consciente)

    var result = guerrero.ejecutarMovimiento(cargarKi)

    assertEquals(154, result.atacante.ki)
  }

  @Test
  def unMonstruoDescansaParaAumentarSuKiYLoLogra = {
    val items = Array[Item]()
    val movimientos = Array[Movimiento]()
    guerrero = new Guerrero("Cell", items, movimientos, 4, 40, Monstruo, Consciente)

    var result = guerrero.ejecutarMovimiento(cargarKi)

    assertEquals(104, result.atacante.ki)
  }

  @Test
  def unNamekuDescansaParaAumentarSuKiYLoLogra = {
    val items = Array[Item]()
    val movimientos = Array[Movimiento]()
    guerrero = new Guerrero("Picollo", items, movimientos, 4, 40, Namekusein, Consciente)

    var result = guerrero.ejecutarMovimiento(cargarKi)

    assertEquals(104, result.atacante.ki)
  }

  @Test
  def unSaiyajinSeConvierteEnSuperSaiyajin = {
    val items = Array[Item]()
    val movimientos = Array[Movimiento]()
    guerrero = new Guerrero("Picollo", items, movimientos, 21, 40, Saiyajin(), Consciente)

    val result = guerrero.ejecutarMovimiento(convertirseEnSuperSaiyajin)

    assertEquals(SuperSaiyajin(true, nivel = 1), result.atacante.especie)

  }

  @Test
  def unSuperSaiyajinDebeSolamenteSubirElNivel() = {
    val items = Array[Item]()
    val movimientos = Array[Movimiento]()
    guerrero = new Guerrero("Picollo", items, movimientos, 21, 40, SuperSaiyajin(), Consciente)

    val result = guerrero.ejecutarMovimiento(convertirseEnSuperSaiyajin)

    assertEquals(SuperSaiyajin(true, nivel = 2), result.atacante.especie)
  }

  @Test
  def siNoEsSaiyajinOSuperSaiyajinNoSeDebeConvertir = {
    val items = Array[Item]()
    val movimientos = Array[Movimiento]()
    guerrero = new Guerrero("Picollo", items, movimientos, 21, 40, Namekusein, Consciente)

    val result = guerrero.ejecutarMovimiento(convertirseEnSuperSaiyajin)

    assertEquals(Namekusein, result.atacante.especie)
  }

  @Test
  def hechizoAlAtacanteCon7EsferasDelDragon = {
    val atacante = new Guerrero("Picollo", Array(EsferaDragon,EsferaDragon,EsferaDragon,EsferaDragon,EsferaDragon,EsferaDragon,EsferaDragon,EsferaDragon), Array.empty , 21, 40, Humano, Consciente)
    val atacado = new Guerrero("Santi", Array.empty, Array.empty, 21, 40, Namekusein, Inconsciente)

    val guerrerosResultado = atacante.ejecutarMovimiento(Hechizar(RandomFaked(0), List(Consciente, Inconsciente)), Some(atacado))

    assertEquals(Consciente, guerrerosResultado.atacante.estado)
  }

  @Test
  def hechizoDeUnNamekusein = {
    val atacante = new Guerrero("Picollo", Array.empty, Array.empty , 21, 40, Namekusein, Consciente)
    val atacado = new Guerrero("Santi", Array.empty, Array.empty, 21, 40, Namekusein, Inconsciente)

    val guerrerosResultado = atacante.ejecutarMovimiento(Hechizar(RandomFaked(0), List(Consciente, Inconsciente)), Some(atacado))

    assertEquals(Consciente, guerrerosResultado.atacante.estado)
  }

  @Test
  def hechizoDeUnHumanoNoEsValido = {
    val atacante = new Guerrero("Picollo", Array.empty, Array.empty , 21, 40, Humano, Consciente)
    val atacado = new Guerrero("Santi", Array.empty, Array.empty, 21, 40, Namekusein, Inconsciente)

    val guerrerosResultado = atacante.ejecutarMovimiento(Hechizar(RandomFaked(0), List(Consciente, Inconsciente)), Some(atacado))

    assertEquals(Consciente, guerrerosResultado.atacante.estado)
  }

  @Test
  def unSaiyajinInconscienteSeFusionaConUnHumanoYNoPuede = {
    val items = Array[Item]()

    val atacante = new Guerrero("Goku", items, Array[Movimiento](cargarKi), 15, 40, Saiyajin(true, None), Inconsciente)
    val atacado = new Guerrero("Krillin", items, Array[Movimiento](dejarseFajar), 5, 10, Humano, Inconsciente)

    val estadoBatalla = atacante.ejecutarMovimiento(FusionarseCon, Some(atacado))

    assertEquals("Goku", estadoBatalla.atacante.nombre)
    assertFalse(estadoBatalla.atacante.movimientos contains dejarseFajar)
    assertTrue(estadoBatalla.atacante.movimientos contains cargarKi)
    assertEquals(estadoBatalla.atacante.especie, Saiyajin(true, None))
  }

  @Test
  def unSaiyajinSeFusionaConUnHumanoYLoLogra = {
    val items = Array[Item]()

    val atacante = new Guerrero("Goku", items, Array[Movimiento](cargarKi), 15, 40, Saiyajin(true, None), Consciente)
    val atacado = new Guerrero("Krillin", items, Array[Movimiento](dejarseFajar), 5, 10, Humano, Consciente)

    val estadoBatalla = atacante.ejecutarMovimiento(FusionarseCon, Some(atacado))

    assertEquals("GokuKrillin", estadoBatalla.atacante.nombre)
    assertTrue(estadoBatalla.atacante.movimientos contains dejarseFajar)
    assertTrue(estadoBatalla.atacante.movimientos contains cargarKi)
    assertEquals(estadoBatalla.atacante.especie, Fusion)
  }

  @Test(expected = classOf[RuntimeException])
  def unSaiyajinNoLograFusionarseConUnAndroide {
    val atacante = new Guerrero("Goku", Array[Item](), Array[Movimiento](cargarKi), 15, 40, Saiyajin(true, None), Consciente)
    val atacado = new Guerrero("Numero 18", Array[Item](), Array[Movimiento](dejarseFajar), 5, 10, Androide(), Consciente)

    val estadoBatalla = atacante.ejecutarMovimiento(FusionarseCon, Some(atacado))
  }

  @Test
  def unHumanoRealizaUnAtaqueDeMuchosGolpesNinjaAUnAndroideYMuere = {
    val atacante = new Guerrero("Krillin", Array[Item](), Array[Movimiento](cargarKi), 10, 15, Humano, Consciente)
    val atacado = new Guerrero("Numero 18", Array[Item](), Array[Movimiento](dejarseFajar), 5, 10, Androide(), Consciente)

    val estadoBatalla = atacante.ejecutarMovimiento(MuchosGolpesNinja, Some(atacado))
    assertEquals(Muerto, estadoBatalla.atacante.estado)
    assertEquals(0, estadoBatalla.atacante.ki)
    assertEquals(5, estadoBatalla.atacado.get.ki)
  }

  @Test
  def aplicarOndaDisminuyeKiDeAmbosGuerreros = {
    val atacante = new Guerrero("Picollo", Array.empty, Array.empty , 20, 40, Humano, Consciente)
    val atacado = new Guerrero("Santi", Array.empty, Array.empty, 21, 40, Namekusein, Inconsciente)

    val nuevoEstadoBatalla = atacante.ejecutarMovimiento(Onda(Onda.KameHameHa), Some(atacado))

    assertEquals(nuevoEstadoBatalla.atacante.ki, 10)
    assertEquals(nuevoEstadoBatalla.atacado.get.ki, 1)
  }

  @Test
  def siEsMonstruoSoloLeSacaLaMitadDeLoNormal = {
    val atacante = new Guerrero("Picollo", Array.empty, Array.empty , 20, 40, Humano, Consciente)
    val atacado = new Guerrero("Santi", Array.empty, Array.empty, 21, 40, Monstruo, Inconsciente)

    val nuevoEstadoBatalla = atacante.ejecutarMovimiento(Onda(Onda.KameHameHa), Some(atacado))

    assertEquals(nuevoEstadoBatalla.atacante.ki, 10)
    assertEquals(nuevoEstadoBatalla.atacado.get.ki, 16)
  }

  @Test
  def atacanteNoPoseeSuficienteKi = {
    val atacante = new Guerrero("Picollo", Array.empty, Array.empty , 10, 40, Humano, Consciente)
    val atacado = new Guerrero("Santi", Array.empty, Array.empty, 21, 40, Monstruo, Inconsciente)

    val nuevoEstadoBatalla = atacante.ejecutarMovimiento(Onda(Onda.Dodonpa), Some(atacado))

    assertEquals(nuevoEstadoBatalla.atacante.ki, 10)
    assertEquals(nuevoEstadoBatalla.atacado.get.ki, 21)
  }

  @Test
  def siEsAndroideBajaSolamenteSuBateria = {
    val atacante = new Guerrero("Picollo", Array.empty, Array.empty , 59, 40, Humano, Consciente)
    val atacado = new Guerrero("Santi", Array.empty, Array.empty, 21, 40, Androide(29), Inconsciente)

    val nuevoEstadoBatalla = atacante.ejecutarMovimiento(Onda(Onda.Dodonpa), Some(atacado))

    assertEquals(nuevoEstadoBatalla.atacante.ki, 40)

    assertEquals(nuevoEstadoBatalla.atacado.get.ki, 21)
    assertEquals(nuevoEstadoBatalla.atacado.get.especie.asInstanceOf[Androide].bateria, 10)
  }

  @Test
  def convertirseEnMonoOK(): Unit = {
    val atacante = new Guerrero("Goku", Array[Item](FotoLuna), Array[Movimiento](cargarKi), 15, 40, Saiyajin(true, None), Consciente)

    val estadoBatalla = atacante.ejecutarMovimiento(ConvertirseEnMono, None)

    assertEquals(estadoBatalla.atacante.especie, Saiyajin(true,Some(MonoGigante)))
  }

  @Test
  def testNoSeConvierteEnMonoSinFotoDeLaLuna(): Unit = {
    val atacante = new Guerrero("Goku", Array[Item](), Array[Movimiento](cargarKi), 15, 40, Saiyajin(true, None), Consciente)

    val estadoBatalla = atacante.ejecutarMovimiento(ConvertirseEnMono, None)

    assertEquals(estadoBatalla.atacante.especie, Saiyajin(true, None))
  }

  @Test
  def unGuerreroExplotaMuereYLeSacaElDobleDeKiAUnNoAndroide = {
    val atacante = new Guerrero("Goku", Array[Item](), Array[Movimiento](Explotar), 5, 40, Saiyajin(true, None), Consciente)
    val atacado = new Guerrero("Numero 18", Array.empty, Array.empty, 20, 40, Androide(35), Inconsciente)

    val estadoBatalla = atacante.ejecutarMovimiento(Explotar, Some(atacado))

    assertEquals(Muerto, estadoBatalla.atacante.estado)
    assertEquals(5, estadoBatalla.atacado.get.ki)
  }

}
