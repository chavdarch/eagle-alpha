import play.api.{Application, GlobalSettings, Logger}


object Global extends GlobalSettings {

  override def onStart(application: Application) {
    // start actors
    Logger.info("Application is started!!!")
  }


  override def onStop(app : play.api.Application) : scala.Unit = {
    //shutdown actors and twitter connection
    Logger.info("Application is shutting down!!!")
  }
}