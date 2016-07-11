package com.sausage.lucene.analyzer;

import com.sausage.lucene.filters.CourtesyTitleFilter;
import com.sausage.lucene.filters.GenderFilter;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LetterTokenizer;
import org.apache.lucene.util.AttributeFactory;

public class GenderAnalyzer extends Analyzer {
    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
        Tokenizer letterTokenizer = new LetterTokenizer(AttributeFactory.DEFAULT_ATTRIBUTE_FACTORY);
        TokenStream filter = new GenderFilter(letterTokenizer);
        return new TokenStreamComponents(letterTokenizer, filter);
    }
}
