package com.sausage.lucene.app

import java.io.StringReader
import java.util

import org.apache.lucene.analysis.{Analyzer, TokenStream}
import org.apache.lucene.analysis.core.WhitespaceAnalyzer
import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper
import org.apache.lucene.analysis.standard.StandardAnalyzer
import org.apache.lucene.analysis.tokenattributes.{CharTermAttribute, OffsetAttribute}

object PerFieldAnalyzer extends App{
  val analyzerPerField = new util.HashMap[String,Analyzer]();
  analyzerPerField.put("myfield", new WhitespaceAnalyzer());
  val defanalyzer = new PerFieldAnalyzerWrapper(new StandardAnalyzer(), analyzerPerField);
  var ts:TokenStream = null;
  var offsetAtt:OffsetAttribute = null;
  var charAtt:CharTermAttribute = null;
  try {
    ts = defanalyzer.tokenStream("myfield", new StringReader("lucene.apache.org AB-978"));
    offsetAtt = ts.addAttribute(classOf[OffsetAttribute]);
    charAtt = ts.addAttribute(classOf[CharTermAttribute]);
    ts.reset();
    System.out.println("== Processing field 'myfield' using WhitespaceAnalyzer (per field) ==");
    while (ts.incrementToken()) {
      System.out.println(charAtt.toString());
      System.out.println("token start offset: " + offsetAtt.startOffset());
      System.out.println("  token end offset: " + offsetAtt.endOffset());
    }
    ts.end();

    ts = defanalyzer.tokenStream("content", new StringReader("lucene.apache.org AB-978"));
    offsetAtt = ts.addAttribute(classOf[OffsetAttribute]);
    charAtt = ts.addAttribute(classOf[CharTermAttribute]);
    ts.reset();
    System.out.println("== Processing field 'content' using StandardAnalyzer ==");
    while (ts.incrementToken()) {
      System.out.println(charAtt.toString());
      System.out.println("token start offset: " + offsetAtt.startOffset());
      System.out.println("  token end offset: " + offsetAtt.endOffset());
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
