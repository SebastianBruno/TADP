package tpScala

import org.junit.Assert._
import org.junit.{ Test, Before }
import tpScala.Movement._
import tpScala.Criteria.movimientoMasDanino
import tpScala.Criteria.movimientoMenosDanino
import tpScala.Criteria.movimientoQueQuitaMenosItems
import tpScala.Criteria.movimientoKrillin

class CriterioSpec {

  @Test
  def unGuerreroEligeMovimientoSegunCriterioMasDanino() {
    val atacante = new Guerrero("Goku", Array[Item](), Array[Movimiento](Onda(Onda.KameHameHa), MuchosGolpesNinja), 50, 90, Saiyajin(true, None), Consciente)
    val atacado = new Guerrero("Vegeta", Array[Item](), Array[Movimiento](dejarseFajar), 20, 70, Monstruo, Consciente)

    assertEquals(MuchosGolpesNinja, atacante.movimientoMasEfectivoContra(atacado)(movimientoMasDanino))
  }

  @Test
  def unGuerreroEligeMovimientoSegunCriterioMenosDanino() {
    val atacante = new Guerrero("Goku", Array[Item](), Array[Movimiento](Onda(Onda.KameHameHa), MuchosGolpesNinja), 50, 90, Saiyajin(true, None), Consciente)
    val atacado = new Guerrero("Vegeta", Array[Item](), Array[Movimiento](dejarseFajar), 20, 70, Monstruo, Consciente)

    assertEquals(Onda(Onda.KameHameHa), atacante.movimientoMasEfectivoContra(atacado)(movimientoMenosDanino))
  }

  // Segun este criterio, los items se pierden. Habria que cambiar lo anterior?

  /* @Test
  def unGuerreroEligeMovimientoSegunCriterioQueQuiteMenosItems() {
    val atacante = new Guerrero("Goku", Array[Item](FotoLuna), Array[Movimiento](convertirseEnMono, MuchosGolpesNinja), 50, 90, Saiyajin(true, None), Consciente)
    val atacado = new Guerrero("Vegeta", Array[Item](), Array[Movimiento](dejarseFajar), 20, 70, Monstruo, Consciente)

    assertEquals(MuchosGolpesNinja, atacante.movimientoMasEfectivoContra(atacado)(movimientoQueQuitaMenosItems))
  }*/

  @Test
  def KrillinEligeACualquiera() {
    val krillin = new Guerrero("Krillin", Array[Item](FotoLuna), Array[Movimiento](convertirseEnMono, MuchosGolpesNinja), 50, 90, Saiyajin(true, None), Consciente)
    val atacado = new Guerrero("Vegeta", Array[Item](), Array[Movimiento](dejarseFajar), 20, 70, Monstruo, Consciente)

    assertTrue(krillin.movimientos contains krillin.movimientoMasEfectivoContra(atacado)(movimientoKrillin))
  }
}