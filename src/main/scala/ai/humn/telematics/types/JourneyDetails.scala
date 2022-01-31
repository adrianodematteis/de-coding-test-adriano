package ai.humn.telematics.types

import ai.humn.telematics.`enum`.{AvgSpeed, Distance, Measure}


case class JourneyDetails(journeyId: String, driverId: String, distance: Option[Double], durationMS: Option[Long],
                          avgSpeed: Option[Double]) {
  def decimalFormat(obj: Option[Double], measure: Measure = Distance): String = obj match {
    case Some(num) =>
      measure match {
        case Distance =>
          f"$num%1.1f"
        case AvgSpeed =>
          f"$num%1.2f"
      }
    case None => "NA"
  }

  override def toString: String =
    s"journeyId: $journeyId $driverId distance ${decimalFormat(distance)} durationMS ${durationMS.getOrElse("NA")}" +
      s" avgSpeed in kph was " + decimalFormat(avgSpeed, AvgSpeed)
}
