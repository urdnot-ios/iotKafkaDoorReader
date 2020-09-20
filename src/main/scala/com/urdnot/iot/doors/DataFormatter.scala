package com.urdnot.iot.doors

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object DataFormatter extends DataStructures {
  def prepareInflux(structuredData: DataProcessor.DoorStatus): Future[Option[String]] = Future {
    structuredData.toInfluxString(structuredData.timestamp.get)
  }
}
