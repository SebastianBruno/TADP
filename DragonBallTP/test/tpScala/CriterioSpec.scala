package tpScala

import org.junit.Assert._
import org.junit.{ Test, Before }
import tpScala.Criteria.{MayorKi, DejarFueraDeCombateAlEnemigo, DiferenciaDeKiAFavor}
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

  @Test
  def unRoundDondeAmbosCarganKi = {
    val atacante = new Guerrero("Goku", Array[Item](), Array[Movimiento](cargarKi), 10, 400, Saiyajin(true, None), Consciente)
    val atacado = new Guerrero("Numero 18", Array.empty, Array[Movimiento](cargarKi), 20, 400, Saiyajin(true, None), Consciente)

    val estadoBatalla = atacante.pelearRound(cargarKi)(atacado)

    assertEquals(110, estadoBatalla.atacante.ki)
    assertEquals(120, estadoBatalla.atacado.get.ki)
  }

  @Test
  def unRoundConArmasDeFuego = {
    val atacante = new Guerrero("Goku", Array[Item](ArmaFuego(10)), Array[Movimiento](usarItem(ArmaFuego(10))), 50, 400, Humano, Consciente)
    val atacado = new Guerrero("Numero 18", Array[Item](ArmaFuego(10)), Array[Movimiento](usarItem(ArmaFuego(10))), 30, 400, Humano, Consciente)

    val estadoBatalla = atacante.pelearRound(usarItem(ArmaFuego(10)))(atacado)

    assertEquals(30, estadoBatalla.atacante.ki)
    assertEquals(10, estadoBatalla.atacado.get.ki)
  }

  @Test
  def obtengoElPlanDeAtaqueConCriterioDejarFueraDeCombate = {
    val atacante = new Guerrero("Goku", Array[Item](ArmaFuego(10)), Array[Movimiento](Onda(Onda.KameHameHa), MuchosGolpesNinja), 250, 400, Humano, Consciente)
    val atacado = new Guerrero("Numero 18", Array[Item](ArmaFuego(10)), Array[Movimiento](usarItem(ArmaFuego(10))), 100, 400, Humano, Consciente)
    val planDeAtaque = atacante.planDeAtaqueContra(atacado, 2)(DejarFueraDeCombateAlEnemigo)
    assertEquals(MuchosGolpesNinja, planDeAtaque.get(0))
    assertEquals(MuchosGolpesNinja, planDeAtaque.get(1))
  }

  @Test
  def obtengoElPlanDeAtaqueConCriterioMayorKi = {
    val atacante = new Guerrero("Goku", Array[Item](ArmaFuego(10)), Array[Movimiento](Onda(Onda.KameHameHa), MuchosGolpesNinja, cargarKi), 250, 250, Humano, Consciente)
    val atacado = new Guerrero("Numero 18", Array[Item](ArmaFuego(10)), Array[Movimiento](usarItem(ArmaFuego(10))), 100, 400, Humano, Consciente)
    val planDeAtaque = atacante.planDeAtaqueContra(atacado, 2)(MayorKi)
    assertEquals(cargarKi, planDeAtaque.get(0))
    assertEquals(cargarKi, planDeAtaque.get(1))
  }

  @Test
  def peleaConCriterioDejarFueraDeCombateGanaAtacante = {
    val atacante = new Guerrero("Goku", Array[Item](ArmaFuego(10)), Array[Movimiento](Onda(Onda.KameHameHa), MuchosGolpesNinja), 250, 400, Humano, Consciente)
    val atacado = new Guerrero("Numero 18", Array[Item](ArmaFuego(10)), Array[Movimiento](usarItem(ArmaFuego(10))), 100, 400, Humano, Consciente)
    val planDeAtaque = atacante.planDeAtaqueContra(atacado, 5)(DejarFueraDeCombateAlEnemigo)
    val resultado: ResultadoPelea = atacante.pelearContra(atacado)(planDeAtaque.get)

    resultado match {
      case Ganador(ganador) => assertEquals(ganador.nombre,"Goku")
      case _ => assertEquals(1, 2)
    }
  }

  @Test
  def peleaConCriterioDejarFueraDeCombateGanaAtacado = {
    val atacado = new Guerrero("Goku", Array[Item](ArmaFuego(10)), Array[Movimiento](Onda(Onda.KameHameHa), MuchosGolpesNinja), 250, 400, Humano, Consciente)
    val atacante = new Guerrero("Numero 18", Array[Item](ArmaFuego(10)), Array[Movimiento](usarItem(ArmaFuego(10))), 100, 400, Humano, Consciente)
    val planDeAtaque = atacante.planDeAtaqueContra(atacado, 5)(DejarFueraDeCombateAlEnemigo)
    val resultado: ResultadoPelea = atacante.pelearContra(atacado)(planDeAtaque.get)

    resultado match {
      case Ganador(ganador) => assertEquals(ganador.nombre,"Goku")
      case _ => assertEquals(1, 2)
    }
  }

  @Test
  def peleaConCriterioMayorKiSiguenPeleando = {
    val atacante = new Guerrero("Goku", Array[Item](ArmaFuego(10)), Array[Movimiento]( cargarKi), 250, 250, Humano, Consciente)
    val atacado = new Guerrero("Numero 18", Array[Item](ArmaFuego(10)), Array[Movimiento](cargarKi), 100, 400, Humano, Consciente)
    val planDeAtaque = atacante.planDeAtaqueContra(atacado, 2)(MayorKi)
    val resultado: ResultadoPelea = atacante.pelearContra(atacado)(planDeAtaque.get)

    resultado match {
      case SiguenPeleando(_,peleador) => assertEquals(peleador.ki,300)
      case _ => assertEquals(1, 2)
    }
  }


}