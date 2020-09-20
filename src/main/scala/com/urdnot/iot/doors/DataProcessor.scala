package com.urdnot.iot.doors

import com.typesafe.scalalogging.Logger
import io.circe.parser.parse
import io.circe.{Json, ParsingFailure}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object DataProcessor extends DataStructures {
  val log: Logger = Logger("DoorSensors")
  def parseRecord(record: Array[Byte], log: Logger): Future[Either[String, DoorStatus]] = Future {
    val recordString = record.map(_.toChar).mkString
    val genericParse: Either[ParsingFailure, Json] = parse(recordString)
    import io.circe.optics.JsonPath._
    genericParse match {
      case Right(x) => x match {
        case x: Json => try {
          Right(DoorStatus(
            status = root.status.string.getOption(x),
            timestamp = root.timestamp.long.getOption(x),
            door = root.door.string.getOption(x)))
        } catch {
          case e: Exception => Left("Unable to extract JSON: " + e.getMessage)
        }
        case _ => Left("I dunno what this is, but it's not a door message: " + x)
      }
      case Left(x) => Left(x.getMessage)
    }
//    implicit val decoder: Decoder[DoorStatus] = deriveDecoder[DoorStatus]
//    decode[DoorStatus](record.map(_.toChar).mkString) match {
//      case Right(x) => Right(x)
//      case Left(x) => val errorMessage = "couldn't parse: " + record.map(_.toChar).mkString + " -- " + x
//        log.error(errorMessage)
//        Left(errorMessage)
//    }
  }

}
