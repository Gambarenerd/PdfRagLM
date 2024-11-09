package org.example.configuration;

import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

@Configuration
class PdfRagLMConfig {

    @Value("classpath:Pizza.pdf")
    private Resource pdfResource;

    @Bean
    VectorStore vectorStore(EmbeddingClient client) {
        SimpleVectorStore vectorStore = new SimpleVectorStore(client);
        initVectorStore(vectorStore);

        return vectorStore;
    }

    private void initVectorStore(VectorStore vectorStore) {
        var config = PdfDocumentReaderConfig.builder()
                .withPageExtractedTextFormatter(
                        new ExtractedTextFormatter.Builder().build()
                )
                .build();

        var pdfReader = new PagePdfDocumentReader(pdfResource, config);
        var textSplitter = new TokenTextSplitter();

        vectorStore.accept(textSplitter.apply(pdfReader.get()));
    }
}