package com.sausage.lucene.fields

import java.nio.file.{Path, Paths}

import com.sausage.lucene.writers.Writers
import org.apache.lucene.analysis.standard.StandardAnalyzer
import org.apache.lucene.document.{Document, Field, StringField}

object StringFields extends App{


  var document = new Document();
  document.add(new StringField("telephone_number", "04735264927", Field.Store.YES));
  document.add(new StringField("area_code", "0484", Field.Store.YES));

  val indexWriter = Writers.getIndexWriter(Paths.get("/tmp/foo"),new StandardAnalyzer())

  indexWriter.addDocument(document);
  indexWriter.commit();

}
