package tpScala

import org.junit.Assert._
import org.junit.{ Test, Before }
import tpScala.Movement._
import tpScala.Criteria.movimientoMasDanino

class CriterioSpec {

  @Test
  def unGuerreroEligeMovimientoSegunCriterioMasDanino() {
    val atacante = new Guerrero("Goku", Array[Item](), Array[Movimiento](Onda(Onda.KameHameHa), MuchosGolpesNinja), 15, 40, Saiyajin(true, None), Inconsciente)
    val atacado = new Guerrero("Krillin", Array[Item](), Array[Movimiento](dejarseFajar), 5, 10, Humano, Inconsciente)
    
    assertEquals(Onda(Onda.KameHameHa), atacante.movimientoMasEfectivoContra(atacado)(movimientoMasDanino))
  }

}