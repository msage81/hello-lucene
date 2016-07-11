package com.sausage.lucene.app

import java.io.{IOException, StringReader}

import org.apache.lucene.analysis.TokenStream
import org.apache.lucene.analysis.standard.StandardAnalyzer
import org.apache.lucene.analysis.tokenattributes.{CharTermAttribute, OffsetAttribute}

object TokenStreamApp extends App{
  val reader = new StringReader("Lucene is mainly used for information retrieval and you can " +
    "read more about it at lucene.apache.org.");
  val wa = new StandardAnalyzer();
  var ts:TokenStream  = null;

  try {

    ts = wa.tokenStream("field", reader);

    val offsetAtt = ts.addAttribute(classOf[OffsetAttribute]);
    val termAtt = ts.addAttribute(classOf[CharTermAttribute]);

    ts.reset();

    while (ts.incrementToken()) {
      val token = termAtt.toString();
      System.out.println("[" + token + "]");
      System.out.println("Token starting offset: " + offsetAtt.startOffset());
      System.out.println(" Token ending offset: " + offsetAtt.endOffset());
      System.out.println("");
    }

    ts.end();
  } catch  {
    case e => e.printStackTrace();
  } finally {
    ts.close();
    wa.close();
  }

}
