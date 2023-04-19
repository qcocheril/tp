import com.github.polomarcus.utils.ClimateService
import com.github.polomarcus.model.CO2Record
import org.scalatest.funsuite.AnyFunSuite

//@See https://www.scalatest.org/scaladoc/3.1.2/org/scalatest/funsuite/AnyFunSuite.html
class ClimateServiceTest extends AnyFunSuite {
  test("containsWordGlobalWarming - non climate related words should return false") {
    assert(ClimateService.isClimateRelated("pizza") == false)
  }


  test("isClimateRelated - climate related words should return true") {
    assert(ClimateService.isClimateRelated("climate change") == true)
    assert(ClimateService.isClimateRelated("IPCC"))
    assert(ClimateService.isClimateRelated("global warming"))
  }

  //@TODO
  test("parseRawData") {
    // our inputs
    val firstRecord = (2003, 1, 355.2)     //help: to acces 2003 of this tuple, you can do firstRecord._1
    val secondRecord = (2004, 1, 375.2)
    val thirdRecord = (2010, 5, -600.3)
    val list1 = List(firstRecord, secondRecord)
    val list2 = List(firstRecord, thirdRecord)


    // our output of our method "parseRawData"
    val co2RecordWithType = CO2Record(firstRecord._1, firstRecord._2, firstRecord._3)
    val co2RecordWithType2 = CO2Record(secondRecord._1, secondRecord._2, secondRecord._3)


    val output = List(Some(co2RecordWithType), Some(co2RecordWithType2))
    val output2 = List(Some(co2RecordWithType), None)

    // we call our function here to test our input and output
    assert(ClimateService.parseRawData(list1) == output)
    assert(ClimateService.parseRawData(list2) == output2)
  }

  //@TODO
  test("filterDecemberData") {
    val testData = List(
      Some(CO2Record(2020, 11, 416.21)),
      Some(CO2Record(2020, 12, 414.66)),
      Some(CO2Record(2021, 1, 415.18)),
      Some(CO2Record(2021, 12, 416.54)),
      None
    )

    val expected = List(
      CO2Record(2020, 11, 416.21),
      CO2Record(2021, 1, 415.18)
    )

    val result = ClimateService.filterDecemberData(testData)

    assert(result == expected, s"Expected $expected but got $result")
  }
  test("getMinMax") {
    val testData = List(
      CO2Record(2022, 1, 415.10),
      CO2Record(2022, 2, 416.08),
      CO2Record(2022, 3, 417.08),
      CO2Record(2022, 4, 419.05),
      CO2Record(2022, 5, 419.49),
      CO2Record(2022, 6, 418.34),
      CO2Record(2022, 7, 416.15),
      CO2Record(2022, 8, 413.64),
      CO2Record(2022, 9, 412.58),
      CO2Record(2022, 10, 414.10),
      CO2Record(2022, 11, 416.44),
      CO2Record(2022, 12, 417.77)
    )

    val expected = (412.58, 419.49)
    val actual = ClimateService.getMinMax(testData)

    assert(actual == expected, s"Expected $expected but got $actual")
  }

  test("getMinMaxByYear") {
    val testData = List(
      CO2Record(2020, 1, 415.4),
      CO2Record(2020, 2, 416.2),
      CO2Record(2020, 3, 417.1),
      CO2Record(2020, 4, 416.4),
      CO2Record(2021, 1, 417.6),
      CO2Record(2021, 2, 416.7),
      CO2Record(2021, 3, 417.9),
      CO2Record(2021, 4, 417.5)
    )
    assert(ClimateService.getMinMaxByYear(testData, 2020) == (415.4, 417.1))
    assert(ClimateService.getMinMaxByYear(testData, 2021) == (416.7, 417.9))
  }
}
