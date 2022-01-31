package ai.humn.telematics

import `enum`.DistOrSpeed._
import ai.humn.telematics.Util.parseJourney
import scala.io.Source


object ProcessDataFile extends App {
  // Read lines from the input file (passed as first parameter)
  val buffSource = Source.fromFile(args(0))
  val rightJourneys = buffSource.getLines.drop(1).toSet.map(parseJourney).toList
    .filter(j => j.distance.isDefined && j.durationMS.isDefined && j.avgSpeed.isDefined)
    .sortWith(_.journeyId < _.journeyId)

  if (rightJourneys.nonEmpty) {
    println("Journeys of 90 minutes or more.")
    rightJourneys.filter(_.distance.get > 89.0).foreach(println)

    println("\nAverage speeds in Kph")
    rightJourneys.foreach(println)

    //Calculating Mileage By Driver
    val mileageByDriver = rightJourneys.groupBy(_.driverId).mapValues(_.map(_.distance.get).sum).toList

    println("\nMileage By Driver")
    mileageByDriver.sortWith(_._1 < _._1).map(elem => s"${elem._1} drove ${elem._2} kilometers").foreach(println)

    val mostActiveDriver = mileageByDriver.sortWith(_._2 > _._2).head._1

    val longestJourneys = mileageByDriver.sortWith(_._2 > _._2).head._2
    val mostActiveDrivers = mileageByDriver.filter(_._2 == longestJourneys).map(_._1).mkString(" ")
    print(s"\nMost active drivers are $mostActiveDrivers")

  } else {
    println("Empty File")
  }

  buffSource.close

}