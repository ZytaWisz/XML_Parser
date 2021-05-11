package org.example.parser;

import org.example.errors.NoDataInFileException;
import org.junit.jupiter.api.Test;

import javax.xml.stream.XMLStreamException;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class XmlParserTest {

    @Test
    void shouldReturnValidResponseWhenValidXmlProvided() throws XMLStreamException {
        // given
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<posts>\n" +
                "  <row Id=\"1\" PostTypeId=\"1\" AcceptedAnswerId=\"5\" CreationDate=\"2015-07-14T18:39:27.757\" Score=\"4\" AnswerCount=\"5\" CommentCount=\"4\"/>\n" +
                "  <row Id=\"2\" PostTypeId=\"2\" ParentId=\"1\" CreationDate=\"2015-07-14T18:42:42.553\" Score=\"0\"  CommentCount=\"0\"/>\n" +
                "  </posts>";

        // when
        XmlParser xmlParser = new XmlParser();
        ParsingResult result = xmlParser.parseXmlFile(new ByteArrayInputStream(xml.getBytes()));

        //then
        assertEquals(2, result.getTotalPosts());
        assertEquals(2, result.getAvgComment());
        assertEquals(1, result.getTotalAcceptedPosts());
        assertEquals(LocalDateTime.parse("2015-07-14T18:39:27.757"), result.getFirstPost());
        assertEquals(LocalDateTime.parse("2015-07-14T18:42:42.553"), result.getLastPost());
        assertEquals(2, result.getAvgScore());
    }

    @Test
    void shouldThrowXmlStreamExceptionWhenUnexpectedProcessingErrors() {
        // given
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<posts>\n" +
                "  <row Id=\"1/>\n" +
                "  </posts>";

        // when, then
        XmlParser xmlParser = new XmlParser();
        assertThrows(XMLStreamException.class, () -> xmlParser.parseXmlFile(new ByteArrayInputStream(xml.getBytes())));

    }

    @Test
    void shouldReturnNoDataInFileExceptionWhenXmlHaveNoData() {

        //given
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<posts></posts>";

        //when, then
        XmlParser xmlParser = new XmlParser();
        assertThrows(NoDataInFileException.class, () -> {
            xmlParser.parseXmlFile(new ByteArrayInputStream(xml.getBytes()));
        });
    }
}