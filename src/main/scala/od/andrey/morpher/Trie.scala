package od.andrey.morpher

import scala.collection.mutable

/**
 * Created by andrey on 13.06.2015.
 */

class Trie[T] {
  val root: Node[T] = new Node()

  def += (key: String, value: T) = {
    key.toCharArray.foldLeft(root)((current: Node[T], ch: Char) => {
      current.child(ch)
    }).value = Option.apply(value)
  }

  def apply(key: String): Option[T] = {
    val node = key.toCharArray.foldLeft(root)((current: Node[T], ch: Char) => {
      if (current == null) {
        return null
      }

      if (current.children.contains(ch)) {
        current.children(ch)
      } else {
        null
      }
    })

    if (node == null) {
      Option.empty
    } else {
      node.value
    }
  }

  def findVariants(key: String) = {
    val result = new mutable.HashMap[String, T]()

    result += (("", root.value.get))

    val builder = new mutable.StringBuilder()
    val last = key.toCharArray.foldLeft[Node[T]](root)((current: Node[T], ch: Char) => {
      builder += ch
      if (current != null) {
        if (current.value.nonEmpty) {
          result += ((builder.substring(0, builder.length - 1), current.value.get))
        }

        if (current.children.contains(ch)) {
          current.children(ch)
        } else {
          null
        }
      } else {
        null
      }
    })

    if (last != null && last.value.nonEmpty) {
      result += ((builder.toString, last.value.get))
    }

    result
  }

  class Node[T] {
    var value: Option[T] = Option.empty
    val children: mutable.Map[Char, Node[T]] = new mutable.HashMap[Char, Node[T]]()

    def this(v: T) = {
      this()
      value = Option.apply(v)
    }

    def child(ch: Char): Node[T] =
      if (children.contains(ch)) {
        children(ch)
      } else {
        val node = new Node[T]
        children += ((ch, node))
        node
      }
  }
}
