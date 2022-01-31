package ai.humn.telematics.utils

import ai.humn.telematics.types.{JourneyDetails, MileageByDriver}

object Utils {
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
      if(timeDiff > 0) {
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

  def isDurationGreaterThan(minutes: Double)(journey: JourneyDetails): Boolean =
    journey.durationMS.fold(false)(_ > minutes * 60 * 1000)

  def removeIncorrect(journey: JourneyDetails): Boolean = {
    journey.distance.isDefined && journey.durationMS.isDefined && journey.avgSpeed.isDefined
  }

  implicit class JourneyDetailsUtils(journeys: List[JourneyDetails]) {
    def getMileageByDriver: List[MileageByDriver] = {
      journeys.groupBy(_.driverId).mapValues(_.map(_.distance.get).sum.toInt).toList.map(MileageByDriver.tupled)
    }
  }

  implicit class MileageByDriverUtils(milByDriver: List[MileageByDriver]) {
    def getMostActiveDrivers: List[String] = {
      val longestJourneys = milByDriver.sortWith(_.mileage > _.mileage).head.mileage
      milByDriver.filter(_.mileage == longestJourneys).map(_.driverId)
    }
  }
}
