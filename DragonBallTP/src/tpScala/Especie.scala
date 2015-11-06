package tpScala


trait Especie {
  def convertimeEnMono(guerrero: Guerrero): Unit
}

case object Androide extends Especie {
  override def convertimeEnMono(guerrero: Guerrero): Unit = ???
}

case object Namekusein extends Especie {
  override def convertimeEnMono(guerrero: Guerrero): Unit = ???
}

case object Monstruo extends Especie {
  override def convertimeEnMono(guerrero: Guerrero): Unit = ???
}

case object Humano extends Especie {
  override def convertimeEnMono(guerrero: Guerrero): Unit = ???
}

case class Saiyajin(cola: Boolean = true, transformacion: Option[Transformacion] = None) extends Especie {

  def convertimeEnMono(guerrero: Guerrero): Unit = {
    (cola, transformacion) match {
      case (_, Some(MonoGigante)) => guerrero
      case (true, _) => guerrero.copy(especie = Saiyajin(true, Option(MonoGigante)), ki = guerrero.kiMaximo, kiMaximo = guerrero.kiMaximo * 3)
      case _ => throw new RuntimeException("No puede convertirse en mono!")
    }
  }
}

case object Fusion extends Especie {
  override def convertimeEnMono(guerrero: Guerrero): Unit = ???
}
