package module3

import module3.zio_homework.{SimpleBenchmarkService, effects}
import zio.ZIO
import zio.clock.Clock
import zio.console.{Console, putStrLn}

object zioServices {


  //  val app: ZIO[UserService with EmailService with Console, Throwable, Unit] = for{
  //    _ <- UserService.notifyUser(UserID(1))
  //  } yield ()
  //
  //  val appEnv: ZLayer[Any, Throwable, UserService with EmailService] =
  //    UserDAO.live >>> UserService.live ++ EmailService.live
  //
  //
  //
  //  zio.Runtime.default.unsafeRun(app.provideSomeLayer[Console](appEnv))

  val effect: ZIO[Console with Clock, Nothing, Int] = for {
    list <- ZIO.collectAllPar(effects)
    sum <- ZIO.succeed(list.sum)
    _ <- putStrLn(s"Result: $sum")
  } yield sum


  val app: ZIO[SimpleBenchmarkService with Console with Clock, Nothing, Int] =
    zio_homework.printEffectRunningTime(effect)


  def main(args: Array[String]): Unit = {
    zio.Runtime.default.unsafeRun(app.provideSomeLayer[Console with Clock](SimpleBenchmarkService.live))
  }
}