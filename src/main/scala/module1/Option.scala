package module1

/**
 * Option class.
 *
 * @author Alexey_Bodyak
 */
sealed trait Option[+A] {
  def isEmpty: Boolean = this match {
    case Some(_) => false
    case None => true
  }

  def get: A = this match {
    case Some(v) => v
    case None => throw new Exception("Get on empty option")
  }

  def getOrElse[B >: A](b: B): B = this match {
    case Some(v) => v
    case None => b
  }

  def map[B](f: A => B): Option[B] = this match {
    case Some(v) => Some(f(v))
    case None => None
  }

  def flatMap[B](f: A => Option[B]): Option[B] = this match {
    case Some(v) => f(v)
    case None => None
  }

  def orElse[B >: A](b: B): Option[B] = this match {
    case Some(v) => Some(v)
    case None => Some(b)
  }

  def filter(f: A => Boolean): Option[A] = this match {
    case Some(v) if f(v) => Some(v)
    case None => None
    case _ => None
  }

  def zip[B](value: Option[B]): Option[(A, B)] = (this, value) match {
    case (Some(a), Some(b)) => Some((a, b))
    case _ => None
  }

  def printIfAny() = this match {
    case Some(v) => println(v)
    case _ => ()
  }
}

object Option {
  def some[A](a: A): Option[A] = Some(a)

  def none[A]: Option[A] = None
}

case class Some[A](v: A) extends Option[A]

case object None extends Option[Nothing]
