package com.sausage.lucene.app

import java.io.StringReader

import com.sausage.lucene.analyzer.GenderAnalyzer
import com.sausage.lucene.attributes.GenderAttribute
import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper
import org.apache.lucene.analysis.{Analyzer, TokenStream}

import scala.collection.JavaConversions._

object GenderApp extends App{
  val defanalyzer = new PerFieldAnalyzerWrapper(new GenderAnalyzer(), Map[String,Analyzer]());
  var ts:TokenStream = null;
  var genderAttr:GenderAttribute = null;
  try {
    ts = defanalyzer.tokenStream("not-myfield", new StringReader("I dig the mr and the mrs at lucene.apache.org AB-978"));
    genderAttr = ts.addAttribute(classOf[GenderAttribute]);
    ts.reset();
    println("== Processing field 'myfield' using WhitespaceAnalyzer (per field) ==");
    while (ts.incrementToken()) {
      println(genderAttr.toString());
    }
    ts.end();

  }
  catch  {
    case e => e.printStackTrace();
  }
  finally {
    ts.close();
  }

}
