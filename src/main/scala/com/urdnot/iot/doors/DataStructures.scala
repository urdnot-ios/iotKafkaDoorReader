package com.urdnot.iot.doors

trait DataStructures  {
  // ns:
  // 1600639700745000000
  // ms:
  // 1600639700745
  // sec:
  // 1600639700
  val msZeros: String = "000"
  val nsZeros: String = "000000000"
  final case class DoorStatus(
                               status: Option[String],
                               timestamp: Option[Long],
                               door: Option[String]
                             ){
    def toInfluxString(timestamp: Long): Option[String] = {

      val measurement = s"""${DoorStatus.this.getClass.getSimpleName},"""
      val tags: String = DoorStatus.this.door match {
        case Some(i) => "door=" + i
        case None => ""
      }
      val fields: String = DoorStatus.this.status match {
            case Some(i) => "status=\"" + i + "\""
            case None => ""
          }
      val timestamp: String = DoorStatus.this.timestamp.get.toString
      Some(measurement + tags + " " + fields + " " + timestamp)
    }
  }
}