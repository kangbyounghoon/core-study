package com.example.corestudy.schema.query.lucene;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.CharTokenizer;
import org.apache.lucene.util.AttributeFactory;


public final class CodeTokenizer extends CharTokenizer {

    public CodeTokenizer() {
    }

    public CodeTokenizer(AttributeFactory factory) {
        super(factory);
    }

    public CodeTokenizer(int maxTokenLen) {
        super(TokenStream.DEFAULT_TOKEN_ATTRIBUTE_FACTORY, maxTokenLen);
    }

    public CodeTokenizer(AttributeFactory factory, int maxTokenLen) {
        super(factory, maxTokenLen);
    }

    /** Collects only characters which do not satisfy
     * {@link Character#isWhitespace(int)}.*/
    @Override
    protected boolean isTokenChar(int c) {
        return c != ',';
    }
}

