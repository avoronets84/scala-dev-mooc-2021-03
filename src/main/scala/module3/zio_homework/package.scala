package module3

import java.util.concurrent.TimeUnit

import module3.zio_homework.SimpleBenchmarkService.SimpleBenchmark
import zio._
import zio.clock.Clock
import zio.console.{Console, putStrLn}
import zio.duration.durationInt

import scala.io.StdIn
import scala.language.postfixOps
import scala.util.{Random, Try}

package object zio_homework {

  /**
   * 1.
   * Используя сервисы Random и Console, напишите консольную ZIO программу которая будет предлагать пользователю угадать число от 1 до 3
   * и печатать в когнсоль угадал или нет.
   */
  lazy val guessProgram: ZIO[Console, Throwable, Unit] = ZIO.effect(println("Угадай число, которое я задумал от 1 до 3"))
    .flatMap(_ => UIO(Try(StdIn.readLine().toInt)))
    .flatMap(guessAndPrintResult())

  def guessAndPrintResult(): Try[Int] => ZIO[Console, Throwable, Unit] = it =>
    ZIO.fromTry(it)
      .flatMap(checkAndPrint())
      .orElseSucceed(println("Вы ввели не число! Введите число."))


  def checkAndPrint(): Int => ZIO[Console, Throwable, Unit] = it => {
    val number = Random.nextInt(3)
    if (number == it) {
      ZIO.succeed(println("Угадал!"))
    } else {
      ZIO.succeed(println(s"Не угадал, я задумал число $number"))
    }
  }

  /**
   * 2. реализовать функцию doWhile, которая будет выполнять эффект до тех пор, пока его значение в условии не даст true
   */
  def doWhile[R, E, A](body: ZIO[R, E, A])(condition: A => Boolean): ZIO[R, E, A] =
    body.flatMap(it => if (condition(it)) ZIO.succeed(it) else doWhile(body)(condition))


  /**
   * 3. Реализовать метод, который безопасно прочитает конфиг из файла, а в случае ошибки вернет дефолтный конфиг
   * и выведет его в консоль
   * Используйте эффект "load" из пакета config
   */
  def loadConfigOrDefault: URIO[Any, config.AppConfig] = config.load.orElseSucceed(config.AppConfig("default.conf", "someUrl"))


  /**
   * 4. Следуйте инструкциям ниже для написания 2-х ZIO программ,
   * обратите внимание на сигнатуры эффектов, которые будут у вас получаться,
   * на изменение этих сигнатур
   */


  /**
   * 4.1 Создайте эффект, который будет возвращать случайеым образом выбранное число от 0 до 10 спустя 1 секунду
   * Используйте сервис zio Random
   */
  val eff: ZIO[Clock, Nothing, Int] = ZIO.sleep(1 seconds) *> ZIO.succeed(Random.nextInt(10))

  /**
   * 4.2 Создайте коллукцию из 10 выше описанных эффектов (eff)
   */
  val effects: List[ZIO[Clock, Nothing, Int]] = List.fill(10)(eff)

  /**
   * 4.3 Напишите программу которая вычислит сумму элементов коллекци "effects",
   * напечатает ее в консоль и вернет результат, а также залогирует затраченное время на выполнение,
   * можно использовать ф-цию printEffectRunningTime, которую мы разработали на занятиях
   */

  val app: ZIO[Console with Clock, Nothing, Int] = zioConcurrency.printEffectRunningTime(
    for {
      list <- ZIO.collectAll(effects)
      sum <- ZIO.succeed(list.sum)
      _ <- putStrLn(s"Result: $sum")
    } yield sum
  )

  /**
   * 4.4 Усовершенствуйте программу 4.3 так, чтобы минимизировать время ее выполнения
   */

  val appSpeedUp: ZIO[Console with Clock, Nothing, Int] = zioConcurrency.printEffectRunningTime(
    for {
      list <- ZIO.collectAllPar(effects)
      sum <- ZIO.succeed(list.sum)
      _ <- putStrLn(s"Result: $sum")
    } yield sum
  )

  /**
   * 5. Оформите ф-цию printEffectRunningTime разработанную на занятиях в отдельный сервис, так чтобы ее
   * молжно было использовать аналогично zio.console.putStrLn например
   */

  type SimpleBenchmarkService = Has[SimpleBenchmark]

  object SimpleBenchmarkService {
    val currentTime: URIO[Clock, Long] = clock.currentTime(TimeUnit.SECONDS)

    trait SimpleBenchmark {
      def printEffectRunningTime[R, E, A](zio: ZIO[R, E, A]): ZIO[Console with Clock with R, E, A]
    }

    val live: ULayer[Has[SimpleBenchmark]] = ZLayer.succeed(new SimpleBenchmark {
      override def printEffectRunningTime[R, E, A](zio: ZIO[R, E, A]): ZIO[Console with Clock with R, E, A] = for {
        start <- currentTime
        r <- zio
        finish <- currentTime
        _ <- putStrLn(s"Running time ${finish - start}")
      } yield r
    })
  }

  def printEffectRunningTime[R, E, A](zio: ZIO[R, E, A]): ZIO[SimpleBenchmarkService with Console with Clock with R, E, A] =
    ZIO.accessM[SimpleBenchmarkService with Console with Clock with R](_.get.printEffectRunningTime(zio))
}
