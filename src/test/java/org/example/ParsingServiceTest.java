package org.example;

import org.example.dto.ParseRequest;
import org.example.dto.ParsingResponse;
import org.example.errors.GeneralParsingException;
import org.example.errors.MalformedUrlException;
import org.example.parser.ParsingResult;
import org.example.parser.XmlParser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.xml.stream.XMLStreamException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ParsingServiceTest {

    @Mock
    XmlParser parser;

    @InjectMocks
    ParsingService service;

    @Test
    void shouldThrowMalformedURLException(){
        //given
        ParseRequest request = new ParseRequest();
        request.setUrl("htttttttp://www.merapar.com");

        //when, then
        assertThrows(MalformedUrlException.class, ()-> service.getParsingResult(request));
    }

    @Test
    void shouldThrowGeneralParsingException() throws XMLStreamException {
        //given
        ParseRequest request = new ParseRequest();
        request.setUrl("http://www.merapar.com/a.xml");
        when(parser.parseXmlFile(any())).thenThrow(GeneralParsingException.class);

        // when, then
        assertThrows(GeneralParsingException.class,()->service.getParsingResult(request));

    }

    @Test
    void shouldReturnProperParsingResult() throws XMLStreamException {
        //given
        ParseRequest request =  new ParseRequest();
        request.setUrl("http://www.merapar.com/a.xml");
        ParsingResult result = new ParsingResult(LocalDateTime.now(), LocalDateTime.now(), 40, 5, 300, 200);
        when(parser.parseXmlFile(any())).thenReturn(result);

        //when
        ParsingResponse response = service.getParsingResult(request);

        //then
        assertEquals(40, response.getDetails().getTotalPosts());
        assertEquals(8, response.getDetails().getAvgScore());

    }
}