package tpScala


trait Especie {
  def convertimeEnMono(guerrero: Guerrero): Guerrero = guerrero
}

case object Androide extends Especie

case object Namekusein extends Especie

case object Monstruo extends Especie

case object Humano extends Especie

case class Saiyajin(cola: Boolean = true, transformacion: Option[Transformacion] = None) extends Especie {

  override def convertimeEnMono(guerrero: Guerrero): Guerrero = {
    (cola, transformacion) match {
      case (_, Some(MonoGigante)) => guerrero
      case (true, _) => guerrero.copy(especie = Saiyajin(true, Option(MonoGigante)), ki = guerrero.kiMaximo, kiMaximo = guerrero.kiMaximo * 3)
      case _ => throw new RuntimeException("No puede convertirse en mono!")
    }
  }
}

case object Fusion extends Especie 
