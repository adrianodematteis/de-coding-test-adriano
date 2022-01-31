package ai.humn.telematics

object Util {
  def parseJourney(journey: String) = {
    val Array(journeyId, driverId, startTime, endTime, startLat, startLon, endLat, endLon, startOdometer, endOdometer) =
      journey.split(",").map(_.trim)

    val distance = {
      val diff = endOdometer.toInt - startOdometer.toInt
      if (diff > 0) {
        Some(diff.toDouble)
      } else {
        None
      }
    }

    val durationMS = {
      val timeDiff = endTime.toLong - startTime.toLong
      if (timeDiff > 0) {
        Some(timeDiff)
      } else {
        None
      }
    }

    val avgSpeed = (distance, durationMS) match {
      case (Some(dist), Some(durMs)) => Some(dist / (durMs.toDouble / 1000 / 3600))
      case _ => None
    }

    JourneyDetails(journeyId, driverId, distance, durationMS, avgSpeed)
  }

}
