package com.sausage.lucene.models

case class Merchant(id:String, name:String, cusines:String, latitude:Double, longitude:Double,city:String, state:String,
                    distanceInMiles:Double)

object Merchant extends Ordering[Merchant] {
  override def compare(x: Merchant, y: Merchant): Int = x.distanceInMiles compare  y.distanceInMiles
}
