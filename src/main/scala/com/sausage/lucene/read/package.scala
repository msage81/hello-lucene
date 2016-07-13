package com.sausage.lucene

package object read {
  def getDistanceFromLatLonInMiles(lat1:Double, lon1:Double, lat2:Double, lon2:Double) = {
//    var R = 6371; // Radius of the earth in km
    var R = 3959; // Radius of the earth in miles
    var dLat = deg2rad(lat2-lat1);  // deg2rad below
    var dLon = deg2rad(lon2-lon1);
    val a =
      Math.sin(dLat/2) * Math.sin(dLat/2) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
          Math.sin(dLon/2) * Math.sin(dLon/2)

    var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
    var d = R * c; // Distance in km
    d;
  }

  def deg2rad(deg:Double) ={
    deg * (Math.PI/180)
  }

}
