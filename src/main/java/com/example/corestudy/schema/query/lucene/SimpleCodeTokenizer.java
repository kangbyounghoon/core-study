package com.example.corestudy.schema.query.lucene;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.util.CharTokenizer;
import org.apache.lucene.util.AttributeFactory;

public class SimpleCodeTokenizer extends CharTokenizer {

    public SimpleCodeTokenizer() {
    }

    /**
     * Construct a new WhitespaceTokenizer using a given
     * {@link AttributeFactory}.
     *
     * @param factory
     *          the attribute factory to use for this {@link Tokenizer}
     */
    public SimpleCodeTokenizer(AttributeFactory factory) {
        super(factory);
    }

    /** Collects only characters which do not satisfy
     * {@link Character#isWhitespace(int)}.*/
    @Override
    protected boolean isTokenChar(int c) {
        return ! (c == ',') ;
    }
}

