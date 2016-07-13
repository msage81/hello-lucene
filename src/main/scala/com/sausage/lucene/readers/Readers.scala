package com.sausage.lucene.readers

import java.nio.file.Path

import org.apache.lucene.index.{DirectoryReader, IndexReader}
import org.apache.lucene.store.FSDirectory

object Readers {
  def getIndexReader(indexPath: Path):IndexReader = {

    val directory = FSDirectory.open(indexPath);
    DirectoryReader.open(directory)
  }
}
