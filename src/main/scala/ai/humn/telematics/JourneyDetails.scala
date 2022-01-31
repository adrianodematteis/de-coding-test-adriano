package ai.humn.telematics

import ai.humn.telematics.`enum`.DistOrSpeed.{AvgSpeed, DistOrSpeed, Distance}

case class JourneyDetails(journeyId: String, driverId: String, distance: Option[Double], durationMS: Option[Long],
                          avgSpeed: Option[Double]) {
  def getNDecStr(obj: Option[Double], measure: DistOrSpeed = Distance): String = obj match {
    case Some(num) =>
      if(measure.equals(Distance)) {
        f"$num%1.1f"
      } else {
        f"$num%1.2f"
      }
    case None => "NA"
  }

  override def toString: String =
    s"journeyId: $journeyId $driverId distance ${getNDecStr(distance)} durationMS ${durationMS.getOrElse("NA")}" +
      s" avgSpeed in kph was " + getNDecStr(avgSpeed, AvgSpeed)
}