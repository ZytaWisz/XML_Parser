package org.example;

import org.example.dto.ParseRequest;
import org.example.dto.ParsingDetails;
import org.example.dto.ParsingResponse;
import org.example.errors.GeneralParsingException;
import org.example.errors.MalformedUrlException;
import org.example.parser.ParsingResult;
import org.example.parser.XmlParser;
import org.springframework.stereotype.Service;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ParsingService {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd\'T\'HH:mm:ss.SSS");

    private XmlParser xmlParser = new XmlParser();

    public ParsingResponse getParsingResult(ParseRequest parseRequest) {

        if (!parseRequest.getUrl().endsWith(".xml")) {
            throw new MalformedUrlException("Incorrect file extension, should be .xml");
        }

        try {
            URL url = new URL(parseRequest.getUrl());
            return new ParsingResponse(LocalDateTime.parse(LocalDateTime.now().format(formatter)),
                    parsingResultToDetailsMapper(xmlParser.parseXmlFile(url.openStream())));
        } catch (java.net.MalformedURLException e) {
            throw new MalformedUrlException(e.getMessage());
        } catch (IOException | XMLStreamException e) {
            throw new GeneralParsingException();
        }
    }

    private ParsingDetails parsingResultToDetailsMapper(ParsingResult parsingResult) {
        return new ParsingDetails(parsingResult.getFirstPost(),
                parsingResult.getLastPost(),
                parsingResult.getTotalPosts(),
                parsingResult.getTotalAcceptedPosts(),
                parsingResult.getAvgScore(),
                parsingResult.getAvgComment());
    }


}
