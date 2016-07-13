package com.sausage.lucene.writers

import java.nio.file.Path

import org.apache.lucene.analysis.Analyzer
import org.apache.lucene.index.{IndexWriter, IndexWriterConfig}
import org.apache.lucene.store.FSDirectory

object Writers {

  def getIndexWriter(indexPath: Path, analyzer: Analyzer):IndexWriter = {

    val directory = FSDirectory.open(indexPath);
    val config = new IndexWriterConfig(analyzer);
    config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
    config.setRAMBufferSizeMB(64);
    config.setMaxBufferedDocs(4000);
    new IndexWriter(directory, config);
  }



}
