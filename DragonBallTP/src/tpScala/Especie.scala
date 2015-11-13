package tpScala


trait Especie {
  def convertimeEnMono(guerrero: Guerrero): Guerrero = guerrero

  def siguienteNivel(): Especie = ???

  def puedoHechizar() = false
}

case class Androide(bateria: Int = 100) extends Especie {
  def disminuirBateria(cantidad: Int): Androide = {
    cantidad > bateria match {
      case true => copy(0)
      case false => copy(bateria = bateria - cantidad)

    }
  }
}

case object Namekusein extends Especie {
  override def puedoHechizar() = true
}

case object Monstruo extends Especie {

  override def puedoHechizar() = true

}


case object Humano extends Especie

case class Saiyajin(cola: Boolean = true, transformacion: Option[Transformacion] = None) extends Especie {

  override def convertimeEnMono(guerrero: Guerrero): Guerrero = {
    (cola, transformacion) match {
      case (_, Some(MonoGigante)) => guerrero
      case (true, _) => guerrero.copy(especie = Saiyajin(true, Option(MonoGigante)), ki = guerrero.kiMaximo, kiMaximo = guerrero.kiMaximo * 3)
      case _ => throw new RuntimeException("No puede convertirse en mono!")
    }
  }

  override def siguienteNivel() = SuperSaiyajin()

}

case class SuperSaiyajin(cola :Boolean = true, nivel: Int = 1) extends Especie {

  override def siguienteNivel() = SuperSaiyajin(nivel = nivel + 1)

}

case object Fusion extends Especie 
