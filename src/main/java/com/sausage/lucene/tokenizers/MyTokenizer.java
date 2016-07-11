package com.sausage.lucene.tokenizers;

import org.apache.lucene.analysis.util.CharTokenizer;
import org.apache.lucene.util.AttributeFactory;

public class MyTokenizer extends CharTokenizer {

    public MyTokenizer() {
        super();
    }

    public MyTokenizer(AttributeFactory factory) {
        super(factory);
    }

    @Override
    protected boolean isTokenChar(int c) {
        return !Character.isSpaceChar(c);
    }
}