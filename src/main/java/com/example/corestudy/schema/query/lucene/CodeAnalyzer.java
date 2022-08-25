package com.example.corestudy.schema.query.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;


public class CodeAnalyzer extends Analyzer {

    /**
     * Creates a new {@link WhitespaceAnalyzer}
     */
    public CodeAnalyzer() {
    }

    @Override
    protected TokenStreamComponents createComponents(final String fieldName) {
        final Tokenizer source = new CodeTokenizer();
        return new TokenStreamComponents(source, new LowerCaseFilter(source));
    }
}
