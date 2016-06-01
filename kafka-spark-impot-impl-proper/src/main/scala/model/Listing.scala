package model

import util.option._

import scala.beans.BeanInfo

@BeanInfo
case class Listing(
    classifiedGuid: String,
    articleId: Option[Int] = None,
    classifiedTypeId: Option[String] = None,
    makeId: Option[Int] = None,
    modelLineId: Option[Int] = None,

    modelId: Option[Int] = None,
    version: Option[String] = None,
    currencyId: Option[String] = None,
    price: Option[Double] = None,
    vatDeductible: Option[Boolean] = None,

    mileage: Option[Double] = None,
    power: Option[Double] = None,
    firstRegistration: Option[String] = None, // ISO-8601
    nextInspection: Option[String] = None, // ISO-8601
    lastBeltService: Option[String] = None, // ISO-8601

    offerTypeId: Option[String] = None,
    bodyColorId: Option[Int] = None,
    firstImageMd5: Option[String] = None,
    previousOwners: Option[Int] = None,
    bodyTypeId: Option[Int] = None,

    transmissionId: Option[String] = None,
    vendorId: Option[Int] = None,
    addressId: Option[Int] = None,
    numberOfDoors: Option[Int] = None,
    emissionStickerId: Option[Int] = None,

    emissionPollutionClassId: Option[String] = None,
    efficiencyClassId: Option[Int] = None,
    equipmentIds: Option[Seq[Int]] = None,
    usageStateId: Option[String] = None,
    countryCode: Option[String] = None,

    zip: Option[String] = None,
    city: Option[String] = None,
    images: Option[Int] = None,
    customerTypeId: Option[String] = None,
    fuelId: Option[String] = None,

    primaryFuelTypeId: Option[Int] = None,
    allFuelTypesIds: Option[Seq[Int]] = None,
    fuelConsumptionMixed: Option[Double] = None,
    fuelConsumptionCountry: Option[Double] = None,
    fuelConsumptionCity: Option[Double] = None,

    co2Emissions: Option[Int] = None,
    electricConsumptionMixed: Option[Double] = None,
    gears: Option[Int] = None,
    displacement: Option[Int] = None,
    cylinders: Option[Int] = None,

    hsn: Option[String] = None,
    tsn: Option[String] = None,
    weight: Option[Int] = None,
    interiorColorId: Option[Int] = None,
    upholsteryId: Option[String] = None,

    paintTypeId: Option[String] = None,
    bodyColorOriginal: Option[String] = None,
    seats: Option[Int] = None,
    warranty: Option[Int] = None,
    changedDate: Option[String] = None, // ISO-8601

    availableFrom: Option[String] = None, // ISO-8601
    askingPrice: Option[Boolean] = None,
    description: Option[String] = None
) {

  override def toString = {
    val d = ""
    s"""${makeId.getOrZero}\t${modelId.getOrZero}\t${version.getOrEmptyStr.replace("[\n\r]", "")}\t${numberOfDoors.getOrZero}\t${bodyTypeId.getOrZero}\t${classifiedTypeId.getOrEmptyStr}\t${power.getOrZero}\t${seats.getOrZero}\t${fuelId.getOrEmptyStr}"""
  }

}

object Listing {
  lazy val empty = Listing("")
}
