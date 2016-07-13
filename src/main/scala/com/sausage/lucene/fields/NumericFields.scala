package com.sausage.lucene.fields

import java.nio.file.Paths

import com.sausage.lucene.writers.Writers
import org.apache.lucene.analysis.standard.StandardAnalyzer
import org.apache.lucene.document._


object NumericFields extends App{

  val intField = new IntPoint("int_value", 100);
  val longField = new LongPoint("long_value", 100L);
  val floatField = new FloatPoint("float_value", 100.0F);
  val doubleField = new DoublePoint("double_value", 100.0D);
  val sortedIntField = new FieldType();
  val document = new Document();
  document.add(intField);
  document.add(longField);
  document.add(floatField);
  document.add(doubleField);

  val indexWriter = Writers.getIndexWriter(Paths.get("/tmp/foo"),new StandardAnalyzer())

  indexWriter.addDocument(document);
  indexWriter.commit();

}
