package ai.humn.telematics.types

case class MileageByDriver(driverId: String, mileage: Int) {
  override def toString: String = s"$driverId drove $mileage kilometers"
}
