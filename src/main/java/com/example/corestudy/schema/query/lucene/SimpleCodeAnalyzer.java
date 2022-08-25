package com.example.corestudy.schema.query.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;


public class SimpleCodeAnalyzer extends Analyzer {

    public SimpleCodeAnalyzer() {
    }

    @Override
    protected TokenStreamComponents createComponents(final String fieldName) {
        final Tokenizer source = new SimpleCodeTokenizer();
        return new TokenStreamComponents(source, new LowerCaseFilter(source));
    }
}
