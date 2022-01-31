package ai.humn.telematics

import ai.humn.telematics.utils.Utils._

import scala.io.Source


object ProcessDataFile extends App {
  // Read lines from the input file (passed as first parameter)
  val buffSource = Source.fromFile(args(0))
  val rightJourneys = buffSource.getLines.drop(1).toSet.map(parseJourney).toList
    .filter(removeIncorrect)
    .sortWith(_.journeyId < _.journeyId)

  if (rightJourneys.nonEmpty) {
    println("Journeys of 90 minutes or more.")
    rightJourneys.filter(isDurationGreaterThan(89.0)).foreach(println)

    println("\nAverage speeds in Kph")
    rightJourneys.foreach(println)

    //Calculating Mileage By Driver
    val mileageByDriver = rightJourneys.getMileageByDriver

    println("\nMileage By Driver")
    mileageByDriver.sortWith(_.driverId < _.driverId).foreach(println)

    mileageByDriver.getMostActiveDrivers
    print(s"\nMost active drivers are ${mileageByDriver.getMostActiveDrivers.mkString(" ")}")

  } else {
    println("Empty File")
  }

  buffSource.close
}