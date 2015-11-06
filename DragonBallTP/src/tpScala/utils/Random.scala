package tpScala.utils

import scala.util.Random

trait Random {
  def nextInt(number: Int) = Random.nextInt(number)
}

case class RandomFaked(number: Int) extends Random {

  override def nextInt(ignored: Int) = number

}
