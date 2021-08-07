//package module1
//
//import org.scalatest.flatspec.AnyFlatSpec
//import org.scalatest.matchers.should.Matchers
//
///**
// * @author Alexey_Bodyak
// */
//class OptionTest extends AnyFlatSpec with Matchers {
//
//  "get method from option with value" should "return this value" in {
//    val opt = Option.some("Hello World")
//
//    val result: String = opt.get
//
//    result shouldBe "Hello World"
//  }
//
//  "isEmpty method from option without value" should "return true value" in {
//    val opt = Option.none
//
//    val result = opt.isEmpty
//
//    result shouldBe true
//  }
//
//  "isEmpty method from option with value" should "return false value" in {
//    val opt = Option.some("Hello")
//
//    val result = opt.isEmpty
//
//    result shouldBe false
//  }
//
//  "getOrElse method from option with value" should "return this value" in {
//    val opt = Option.some("Hello")
//
//    val result = opt.getOrElse("Default")
//
//    result shouldBe "Hello"
//  }
//
//  "getOrElse method from option without value" should "return default value" in {
//    val opt = Option.none
//
//    val result = opt.getOrElse("Default")
//
//    result shouldBe "Default"
//  }
//
//  "map method from option with value" should "return value with applied function" in {
//    val opt = Option.some("1")
//
//    val result = opt.map(it => it.toInt)
//
//    result shouldBe Option.some(1)
//  }
//
//  "flatMap method from option without value" should "return default value" in {
//    val opt = Option.some("1")
//
//    val result = opt.flatMap(it => Option.some(it.toInt))
//
//    result shouldBe Option.some(1)
//  }
//
//  "orElse method from option with value" should "return this option" in {
//    val opt = Option.some("Hello")
//
//    val result = opt.orElse("Another")
//
//    result shouldBe Option.some("Hello")
//  }
//
//  "orElse method from option without value" should "return another option" in {
//    val opt = Option.none
//
//    val result = opt.orElse("Another")
//
//    result shouldBe Option.some("Another")
//  }
//
//  "filter method from option with a value satisfying the filter" should "return this option" in {
//    val opt = Option.some("Hello")
//
//    val result = opt.filter(it => it == "Hello")
//
//    result shouldBe Option.some("Hello")
//  }
//
//  "filter method from option without a value satisfying the filter" should "return none" in {
//    val opt = Option.none
//
//    val result = opt.filter(it => it == "!Hello")
//
//    result shouldBe Option.none
//  }
//
//  "zip method from option with a value" should "return this option with two values" in {
//    val opt = Option.some("Hello")
//
//    val result = opt.zip(Option.some("World!"))
//
//    result shouldBe Option.some(("Hello", "World!"))
//  }
//
//  "zip method from option without a value" should "return none" in {
//    val opt = Option.none
//
//    val result = opt.zip(Option.some(" World!"))
//
//    result shouldBe Option.none
//  }
//}
