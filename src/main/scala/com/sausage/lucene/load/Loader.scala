package com.sausage.lucene.load

import java.io.File
import java.nio.file.Paths

import com.sausage.lucene.writers.Writers
import org.apache.lucene.analysis.standard.StandardAnalyzer
import org.apache.lucene.document._
import org.apache.lucene.index.IndexWriter

import scala.io.Source

object Loader extends App{
  private val lines: Iterator[String] = Source.fromFile(new File("./src/main/resources/merchant-lucene.txt")).getLines()
  val lineExpression = "([^\t]*)\t([^\t]*)\t([^\t]*)\t([^\t]*)\t([^\t]*)\t([^\t]*)\t([^\t]*)".r
  private val writer: IndexWriter = Writers.getIndexWriter(Paths.get("./src/main/resources/search"),
    new StandardAnalyzer())

  for(line <- lines){
    line match {
      case lineExpression(id,name,cuisines,lat,long, city, state) =>{
        val d = new Document()
        d.add(new StringField("id",id,Field.Store.YES))
        d.add(new TextField("name",name,Field.Store.YES))
        d.add(new TextField("cuisines",cuisines,Field.Store.YES))
        d.add(new StringField("latitude",lat,Field.Store.YES))
        d.add(new StringField("longitude",long,Field.Store.YES))
        d.add(new TextField("city",city,Field.Store.YES))
        d.add(new TextField("state",state,Field.Store.YES))
        writer.addDocument(d)
        println(id)
      }
      case _ => println(s"WTF merchant $line")
    }
  }
  writer.close()

}
