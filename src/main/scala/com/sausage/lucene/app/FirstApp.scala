package com.sausage.lucene.app

import org.apache.lucene.analysis.core.WhitespaceAnalyzer
import org.apache.lucene.document.{Document, Field, TextField}
import org.apache.lucene.index.{DirectoryReader, IndexWriter, IndexWriterConfig, Term}
import org.apache.lucene.queryparser.classic.QueryParser
import org.apache.lucene.search.{IndexSearcher, ScoreDoc, TopDocs}
import org.apache.lucene.store.RAMDirectory

object FirstApp extends App{

  val analyzer = new WhitespaceAnalyzer()
  val directory = new RAMDirectory()
  val config = new IndexWriterConfig( analyzer)
  val indexWriter = new IndexWriter(directory, config)


  val doc = new Document()
  val text = "Lucene is an Information Retrieval library written in Java."
  doc.add(new TextField("fieldname", text, Field.Store.YES))

  indexWriter.addDocument(doc)
  indexWriter.close()

  search

  def search: Unit = {
    val indexReader = DirectoryReader.open(directory)
    val indexSearcher = new IndexSearcher(indexReader)

    val parser = new QueryParser("fieldname", analyzer)
    val query = parser.parse("Lucene")

    val hitsPerPage = 10
    val docs = indexSearcher.search(query, hitsPerPage)
    val hits = docs.scoreDocs
    val end = Math.min(docs.totalHits, hitsPerPage)
    println("Total Hits: " + docs.totalHits)
    println("Results: ")
    for (i <- (0 until end)) {
      val d = indexSearcher.doc(hits(i).doc)
      println("Content: " + d.get("fieldname"))
    }
    indexReader.close()
  }
  delete
  def delete: Unit ={
    val analyzer = new WhitespaceAnalyzer()
    val config = new IndexWriterConfig( analyzer)
    val indexWriter = new IndexWriter(directory,config)
    indexWriter.deleteAll()
  }
  search

//  indexWriter.deleteDocuments(new Term("id", "1"))
//  indexWriter.close()
}
