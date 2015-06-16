package od.andrey.morpher

import scala.collection.mutable

/**
 * Created by andrey on 15.06.2015.
 */
class EndsInfo {
  val info = new mutable.HashMap[Char, Weights[Int]]

  def += (base: String, flexionsId: Int) = {
    val last = base.last.toLower
    if (info contains last) {
      info(last) += flexionsId
    } else {
      val weight = new Weights[Int]
      weight += flexionsId
      info += ((last, weight))
    }
  }

  def apply(base: String): Option[Int] = {
    val last = base.last.toLower
    if (info contains last) {
      Option.apply(info(last).max)
    } else if (info.size == 1) {
      Option.apply(info.head._2.max)
    } else {
      Option.empty
    }
  }
}
