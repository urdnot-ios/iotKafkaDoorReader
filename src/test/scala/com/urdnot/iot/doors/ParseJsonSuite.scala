package com.urdnot.iot.doors

import org.scalatest.flatspec.AsyncFlatSpec
import DataProcessor._

class ParseJsonSuite extends AsyncFlatSpec with DataStructures {
  val validJsonDoor: Array[Byte] = """{"status": "open", "timestamp": 1600626499001, "door": "garage jeffrey"}""".getBytes
  val validJsonReply: DoorStatus = DoorStatus(status = Some("open"), timestamp = Some(1600626499001L), door = Some("garage jeffrey"))
  val validInfluxReply: String = """DoorStatus,status=open door="garage jeffrey" 1600626499001000"""

  behavior of "DataParser"
  it should "Correctly extract an object from the JSON " in {
    parseRecord(validJsonDoor, log).map { x =>
      assert(x == Right(validJsonReply))
    }
  }
  behavior of "DataFormatter"
  it should "prepare the influxdb update body " in {
    DataFormatter.prepareInflux(validJsonReply.asInstanceOf[DataProcessor.DoorStatus]).map { x =>
      assert(x.get == validInfluxReply)
    }
  }
}
