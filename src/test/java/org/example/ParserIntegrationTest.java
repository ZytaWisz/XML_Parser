package org.example;

import org.example.dto.ParseRequest;
import org.example.dto.ParsingResponse;
import org.example.errors.ErrorResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.XmlBody.xml;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ParserIntegrationTest {

    @LocalServerPort
    private int port;

    private ClientAndServer mockServer;

    TestRestTemplate restTemplate = new TestRestTemplate();
    HttpHeaders headers = new HttpHeaders();

    @BeforeEach
    void setUp() {
        headers.setContentType(MediaType.APPLICATION_JSON);
        mockServer = ClientAndServer.startClientAndServer(2001);
    }

    @AfterEach
    public void tearDownServer() {
        mockServer.stop();
    }

    @Test
    void shouldReturnNoDataInFileErrorResponse() throws IOException {

        var path = "/files/no-data.xml";
        createExpectation(path, org.mockserver.model.MediaType.XML_UTF_8, "src/test/resources/no-data.xml");

        ParseRequest request = new ParseRequest();
        request.setUrl(createMockServerURLWithPort(path));
        HttpEntity<ParseRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<ErrorResponse> response = restTemplate.exchange(
                createURLWithPort("/analyze"), HttpMethod.POST, entity, ErrorResponse.class);

        assertEquals("Sorry, no data found in the file ", response.getBody().getMessage());
    }

    @Test
    void shouldReturnValidResponse() throws IOException {

        var path = "/files/valid-data.xml";
        createExpectation(path, org.mockserver.model.MediaType.TEXT_PLAIN, "src/test/resources/arabic-posts.xml");

        ParseRequest request = new ParseRequest();
        request.setUrl(createMockServerURLWithPort(path));
        HttpEntity<ParseRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<ParsingResponse> response = restTemplate.exchange(
                createURLWithPort("/analyze"), HttpMethod.POST, entity, ParsingResponse.class);

        assertEquals(80, response.getBody().getDetails().getTotalPosts());
    }

    @Test
    void shouldReturnMalformedExceptionErrorResponse() {

        ParseRequest request = new ParseRequest();
        request.setUrl("htsdfdsfsftp://localhost/files/valid-data.xml");
        HttpEntity<ParseRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<ErrorResponse> response = restTemplate.exchange(
                createURLWithPort("/analyze"), HttpMethod.POST, entity, ErrorResponse.class);

        assertEquals("Sorry, you entered wrong URL", response.getBody().getMessage());
        assertEquals("unknown protocol: htsdfdsfsftp", response.getBody().getDetails());
    }

    @Test
    void shouldReturnWrongURLErrorResponse() {

        ParseRequest request = new ParseRequest();
        HttpEntity<ParseRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<ErrorResponse> response = restTemplate.exchange(
                createURLWithPort("/analyze"), HttpMethod.POST, entity, ErrorResponse.class);

        assertEquals("Sorry, you entered wrong URL", response.getBody().getMessage());
        assertTrue(response.getBody().getDetails().contains("Filed url can't be empty"));
        assertTrue(response.getBody().getDetails().contains("Field url can't be null"));
    }

    @Test
    void shouldReturnGeneralParsingErrorResponse() throws IOException {

        var path = "/files/parsing-exception.xml";
        createExpectation(path, org.mockserver.model.MediaType.TEXT_PLAIN, "src/test/resources/parsing-exception.xml");

        ParseRequest request = new ParseRequest();
        request.setUrl(createMockServerURLWithPort(path));
        HttpEntity<ParseRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<ErrorResponse> response = restTemplate.exchange(
                createURLWithPort("/analyze"), HttpMethod.POST, entity, ErrorResponse.class);

        assertEquals("Could not parse the file", response.getBody().getMessage());
    }

    private void createExpectation(String requestPath, org.mockserver.model.MediaType textPlain, String filePath) throws IOException {
        mockServer.when(
                request()
                        .withMethod(HttpMethod.GET.name())
                        .withPath(requestPath)
        ).respond(
                response()
                        .withStatusCode(HttpStatus.OK.value())
                        .withContentType(textPlain)
                        .withBody(xml(Files.readString(Path.of(filePath))))
        );
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    private String createMockServerURLWithPort(String uri) {
        return "http://localhost:" + mockServer.getLocalPort() + uri;
    }

}
