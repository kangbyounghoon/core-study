package com.example.corestudy.schema.query.lucene;

import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.ko.KoreanAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.hibernate.search.backend.lucene.analysis.LuceneAnalysisConfigurationContext;
import org.hibernate.search.backend.lucene.analysis.LuceneAnalysisConfigurer;
import org.springframework.stereotype.Component;

@Component
public class AnalysisConfigurer implements LuceneAnalysisConfigurer {
//    simple, code, simpleCode, id, whitespace, standard, cjk, korean
    @Override
    public void configure(LuceneAnalysisConfigurationContext context) {
        context.analyzer("standard").instance(new StandardAnalyzer());
        context.analyzer("korean").instance(new KoreanAnalyzer());
        context.analyzer("code").instance(new CodeAnalyzer());
        context.analyzer("cjk").instance(new NoStopCJKAnalyzer());
        context.analyzer("simple").instance(new SimpleCodeAnalyzer());
        context.analyzer("whitespace").instance(new WhitespaceAnalyzer());
        context.analyzer("ids").instance(new CodeAnalyzer());
        context.analyzer("id").instance(new IdAnalyzer());

        context.normalizer("lowercase").custom().tokenFilter(LowerCaseFilterFactory.class);
    }
}
