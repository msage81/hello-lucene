package com.sausage.lucene.read

import java.io.{File, FileOutputStream, PrintWriter}
import java.nio.file.Paths

import com.sausage.lucene.models.Merchant
import com.sausage.lucene.readers.Readers
import jdk.nashorn.api.scripting.JSObject
import org.apache.lucene.analysis.standard.StandardAnalyzer
import org.apache.lucene.document.Document
import org.apache.lucene.queryparser.classic.{MultiFieldQueryParser, QueryParser}
import org.apache.lucene.search.IndexSearcher
import play.api.libs.json.JsValue
import play.mvc.BodyParser.Json

object Reader extends App{
  val start = System.currentTimeMillis()
  val (latitudeOfChicago, longitudeOfChicago) = (41.882634,-87.63676)
  val reader = Readers.getIndexReader(Paths.get("./src/main/resources/search"))

  var parser: QueryParser = new MultiFieldQueryParser(Array("cuisines","name", "city"), new StandardAnalyzer())
  val searchAll : String = "*:*"
  val specificSearch = "Fort Lauderdale~"
  var query = parser.parse(specificSearch)

  val indexSearcher:IndexSearcher = new IndexSearcher(reader)

  val hitsPerPage = 20000
  val docs = indexSearcher.search(query, hitsPerPage)
  val hits = docs.scoreDocs
  val end = Math.min(docs.totalHits, hitsPerPage)
  println("Total Hits: " + docs.totalHits)
  println("Results: ")
  var merchants = Array[Merchant]()
  for (i <- (0 until end)) {
    val d = indexSearcher.doc(hits(i).doc)
    val merchant: Merchant = getMerchant(d)
    println(merchant)
    merchants = merchants :+ merchant
  }
  reader.close()
  merchants = merchants.filter(m => m.distanceInMiles <= 30).sortBy(m => m.distanceInMiles)
  var cnt = 0
  var json = ""
  for(merchant <- merchants){
    cnt = cnt + 1
    json += ReadMerchants.getMerchant(merchant.id) + ","
  }

  val fos = new PrintWriter(new FileOutputStream(new File("./src/main/resources/search-result.json")))

  import play.api.libs.json.Json
  val json2: String = "{ \"merchants\":[" + json.take(json.length - 1) + "] }"

  fos.write(Json.prettyPrint(Json.parse(json2)))

  fos.flush()
  fos.close()
  println(s"${System.currentTimeMillis() - start} milliseconds")


//  for(merchant <- merchants){
//    val field: String = s"merchant_${merchant.id}"
//    parser = new QueryParser(field,new StandardAnalyzer())
//    query = parser.parse("*:*")
//    val doc = indexSearcher.search(query,1)
//    println(indexSearcher.doc(0).get(field))
//  }




  def getMerchant(d: Document): Merchant = {
    val cuisines = getCuisines(d)
    val latitude: Double = d.get("latitude").toDouble
    val longitude: Double = d.get("longitude").toDouble
    val key = s"merchant_${d.get("id")}"
    Merchant(d.get("id"), d.get("name"), cuisines, latitude, longitude,
        d.get("city"), d.get("state"), getDistanceFromLatLonInMiles(latitude, longitude, latitudeOfChicago, longitudeOfChicago))
  }


  def getCuisines(d: Document): String = {
    val cuisineMatcher = "([^,]*),([^,]*),([^,]*)".r
    d.get("cuisines") match {
      case cuisineMatcher(one, two, three) => one + (if (!two.trim.isEmpty) ", " + two else "") + (if (!three.trim.isEmpty) ", " + three else "")
      case _ => d.get("cuisines")
    }
  }


}
