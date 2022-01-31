package ai.humn.telematics.`enum`

sealed trait Measure
case object Distance extends Measure
case object AvgSpeed extends Measure