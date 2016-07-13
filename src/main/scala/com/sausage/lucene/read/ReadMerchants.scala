package com.sausage.lucene.read

import java.nio.file.Paths

import com.sausage.lucene.readers.Readers
import org.apache.lucene.analysis.standard.StandardAnalyzer
import org.apache.lucene.document.Document
import org.apache.lucene.index.IndexReader
import org.apache.lucene.queryparser.classic.QueryParser
import org.apache.lucene.search.{IndexSearcher, TopDocs}
import org.apache.lucene.util.BytesRef

object ReadMerchants extends App{

  def getMerchant(id:String): String ={
    val reader: IndexReader = Readers.getIndexReader(Paths.get("./src/main/resources/merchants"))
    val queryParser:QueryParser = new QueryParser("id",new StandardAnalyzer())
    val indexSearcher:IndexSearcher = new IndexSearcher(reader)
    val query = queryParser.parse(id)
    val docs: TopDocs = indexSearcher.search(query,1)
    val hits = docs.scoreDocs
    val end = Math.min(docs.totalHits, 1)
    try{
      if(end == 0) "" else {
        val doc: Document = indexSearcher.doc(hits(0).doc)
        doc.get("merchant")
      }
    }finally{
      reader.close()
    }

  }

}
