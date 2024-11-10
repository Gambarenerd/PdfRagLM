# PdfRagLM

**PdfRagLM** is a Java and Spring Boot-based application that extracts and indexes PDF document content using neural networks and allows querying based on indexed content.

## Key Features
- **PDF Text Extraction**: Extracts text from PDF documents using the `spring-ai-pdf-document-reader` module.
- **Vector Store**: Saves and manages vector representations of the extracted content for similarity search.
- **Query Answering**: Uses OpenAI to answer questions based on the indexed content.
- **RESTful Interface**: Provides a controller to query the indexed PDF content.

## System Requirements
- **Java** 17 or higher
- **Maven** 3.6+
- **Spring Boot** 2.7.0+
- **OpenAI API Key**

## Configure OpenAI Key
- **Important** You need an OpenAI API key for the application to work properly.
- Add your private key in the src/main/resources/application.yml file:
```yaml
spring:
  ai:
    openai:
      api-key: ${OPENAI_KEY}
