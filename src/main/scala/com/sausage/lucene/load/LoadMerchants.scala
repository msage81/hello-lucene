package com.sausage.lucene.load

import java.nio.file.Paths

import com.sausage.lucene.writers.Writers
import org.apache.lucene.analysis.standard.StandardAnalyzer
import org.apache.lucene.document.{Document, Field, StringField}
import org.apache.lucene.index.IndexWriter

import scala.io.Source

object LoadMerchants extends App{
  val lines: Iterator[String] = Source.fromFile("./src/main/resources/merchants-to-search.txt").getLines()


  private val writer: IndexWriter = Writers.getIndexWriter(Paths.get("./src/main/resources/merchants"),
    new StandardAnalyzer())


  while(lines.hasNext){
    val next: String = lines.next()
    println(s"processing $next")
    val url = s"http://staging-inws.idine.com/merchants/${next}/IDTO"
    val d = new Document
    try{
      val json: String = Source.fromURL(url).mkString
      println(json)
      d.add(new StringField("id",next,Field.Store.YES))
      d.add(new StringField(s"merchant",json,Field.Store.YES))
      writer.addDocument(d)
      Thread.sleep(200)

    }catch{
      case e => {
        println(s"error with $next")
        e.printStackTrace()
      }
    }
  }

  writer.close()

}
