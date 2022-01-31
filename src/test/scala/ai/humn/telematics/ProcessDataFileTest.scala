package ai.humn.telematics

import org.junit.runner.RunWith
import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ProcessDataFileTest extends FlatSpec with Matchers {

  val testFilePath = this.getClass.getClassLoader.getResource("empty.csv")
  val testFilePath2 = this.getClass.getClassLoader.getResource("empty.csv")

  it should "not crash while reading an empty file" in {
    ProcessDataFile.main(Array(testFilePath.getPath))
  }

  it should "not crash while reading a file that contains only the header" in {
    ProcessDataFile.main(Array(testFilePath2.getPath))
  }

  it should "result in a JourneyDetails instance with no distance, no duration and no avg speed" in {
    val res =
      Util.parseJourney("000008,driver_e,1633436902000,1633436902000,0.125,0.458,0.129,0.558,123360,123360")

    assert(JourneyDetails("000008","driver_e",None, None, None) == res)
  }

  it should "result in a JourneyDetails instance with no duration and no avg speed" in {
    val res =
      Util.parseJourney("000008,driver_e,1633437902000,1633436902000,0.125,0.458,0.129,0.558,123360,123361")

    assert(JourneyDetails("000008","driver_e",Some(1.0), None, None) == res)
  }

  it should "result in a JourneyDetails instance with no distance and no avg speed" in {
    val res =
      Util.parseJourney("000008,driver_e,1633436902000,1633446902000,0.125,0.458,0.129,0.558,123360,123351")

    assert(JourneyDetails("000008","driver_e",None, Some(10000000L), None) == res)
  }

  it should "result in a JourneyDetails instance with distance, duration and avg speed" in {
    val res =
      Util.parseJourney("000001,driver_a,1633429702000,1633430302000,0.123,0.456,0.124,0.457,123456,123459")

    assert(JourneyDetails("000001","driver_a",Some(3.0), Some(600000L), Some(18.0)) == res)
  }

}
