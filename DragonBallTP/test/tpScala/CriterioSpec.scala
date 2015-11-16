package tpScala

import org.junit.Assert._
import org.junit.{ Test, Before }
import tpScala.Criteria.{DejarFueraDeCombateAlEnemigo, DiferenciaDeKiAFavor}
import tpScala.Movement._

class CriterioSpec {

    @Test
    def unGuerreroEligeMovimientoSegunCriterioMasDanino() {
      val atacante = new Guerrero("Goku", Array[Item](), Array[Movimiento](Onda(Onda.KameHameHa), MuchosGolpesNinja), 50, 90, Saiyajin(true, None), Consciente)
      val atacado = new Guerrero("Vegeta", Array[Item](), Array[Movimiento](dejarseFajar), 20, 70, Monstruo, Consciente)

      assertEquals(Some(MuchosGolpesNinja), atacante.movimientoMasEfectivoContra(atacado)(DiferenciaDeKiAFavor))
    }

  @Test
  def unGuerreroEligeMovimientoSegunCriterioDejarFueraDeCombate() {
    val atacante = new Guerrero("Goku", Array[Item](), Array[Movimiento](Onda(Onda.Dodonpa), cargarKi), 50, 90, Saiyajin(true, None), Consciente)
    val atacado = new Guerrero("Vegeta", Array[Item](), Array[Movimiento](dejarseFajar), 20, 70, Saiyajin(false, None), Consciente)

    assertEquals(Some(Onda(Onda.Dodonpa)), atacante.movimientoMasEfectivoContra(atacado)(DejarFueraDeCombateAlEnemigo))
  }


}