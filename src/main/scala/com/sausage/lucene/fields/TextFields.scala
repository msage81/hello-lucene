package com.sausage.lucene.fields

import java.nio.file.Paths

import com.sausage.lucene.writers.Writers
import org.apache.lucene.analysis.standard.StandardAnalyzer
import org.apache.lucene.document.{Document, Field, StringField, TextField}
/*
Don't be confused between a StringField and TextField. Although both the fields contain textual data, there are major differences between these two fields. A StringField is not tokenized and it's a good tool for exact match and sorting. A TextField is tokenized and it's useful for storing any unstructured text for indexing. When you pass the text into an Analyzer for indexing, a TextField is what's used to store the text content.
 */
object TextFields extends App {
  var document = new Document();
  document.add(new StringField("telephone_number", "04735264927", Field.Store.YES));
  val text = "Lucene is an Information Retrieval library written in Java.";
  document.add(new TextField("text", text, Field.Store.YES));

  val indexWriter = Writers.getIndexWriter(Paths.get("/tmp/foo"), new StandardAnalyzer())

  indexWriter.addDocument(document);
  indexWriter.commit();
}