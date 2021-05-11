package org.example.parser;

import org.codehaus.stax2.XMLInputFactory2;
import org.example.errors.NoDataInFileException;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class XmlParser {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd\'T\'HH:mm:ss.SSS");

    public ParsingResult parseXmlFile(InputStream inputStream) throws XMLStreamException {

        ParsingResult parsingResult = new ParsingResult();
        XMLInputFactory2 xmlInputFactory = (XMLInputFactory2) XMLInputFactory2.newInstance();
        XMLEventReader reader = xmlInputFactory.createXMLEventReader(inputStream);
        while (reader.hasNext()) {
            XMLEvent nextEvent = reader.nextEvent();
            if (nextEvent.isStartElement()) {
                StartElement startElement = nextEvent.asStartElement();
                if (startElement.getName().getLocalPart().equalsIgnoreCase("row")) {
                    parsingResult.increasePostNumber();
                    parseElementAttributes(parsingResult, startElement);
                }
            }
        }
        if (parsingResult.getTotalPosts() == 0) {
            throw new NoDataInFileException();
        }
        return parsingResult;
    }

    private void parseElementAttributes(ParsingResult parsingResult, StartElement startElement) {
        Attribute acceptedAnswerId = startElement.getAttributeByName(new QName("AcceptedAnswerId"));
        if (acceptedAnswerId != null) {
            parsingResult.increaseTotalAcceptedPosts();
        }
        Attribute score = startElement.getAttributeByName(new QName("Score"));
        if (score != null) {
            parsingResult.increaseSumOfScores(Integer.parseInt(score.getValue()));
        }
        Attribute commentCount = startElement.getAttributeByName(new QName("CommentCount"));
        if (commentCount != null) {
            parsingResult.increaseSumOfComment(Integer.parseInt(commentCount.getValue()));
        }
        Attribute creationDate = startElement.getAttributeByName(new QName("CreationDate"));
        if (creationDate != null) {
            LocalDateTime dateTime = LocalDateTime.parse(creationDate.getValue(), formatter);
            if (parsingResult.getFirstPost() == null || parsingResult.getFirstPost().isAfter(dateTime)) {
                parsingResult.setFirstPost(dateTime);
            }
            if (parsingResult.getLastPost() == null || parsingResult.getLastPost().isBefore(dateTime)) {
                parsingResult.setLastPost(dateTime);
            }

        }
    }
}
