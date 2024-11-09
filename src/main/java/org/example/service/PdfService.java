package org.example.service;

import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PdfService {
    private final ChatClient client;
    private final VectorStore vectorStore;

    public PdfService(ChatClient client, VectorStore vectorStore) {
        this.client = client;
        this.vectorStore = vectorStore;
    }

    public String getAnswer(String query){
        List<Document> similarDocuments = vectorStore.similaritySearch(query);
        String information = similarDocuments.stream()
                .map(Document::getContent)
                .collect(Collectors.joining(System.lineSeparator()));
        var systemPromptTemplate = new SystemPromptTemplate(
                """
                You are an expert assistant trained to help users with specific information from the given context.
                Use *only* the information provided below to answer the user's question.
                If the information is insufficient or the answer cannot be inferred, respond politely by saying: 
                "Sorry, I do not have enough information to answer this question."
        
                Always keep your answers concise and to the point, while being as helpful as possible.
        
                Here is the relevant information you should use:
        
                {information}
                """);
        var systemMessage = systemPromptTemplate.createMessage(Map.of("information", information));
        var userPromptTemplate = new PromptTemplate("{query}");
        var userMessage = userPromptTemplate.createMessage(Map.of("query", query));
        var prompt = new Prompt(List.of(systemMessage, userMessage));
        return client.call(prompt).getResult().getOutput().getContent();
    }
}
