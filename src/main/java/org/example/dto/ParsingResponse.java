package org.example.dto;

import java.time.LocalDateTime;

public class ParsingResponse {

    LocalDateTime analyseDate;
    ParsingDetails details;

    public ParsingResponse() {
    }

    public ParsingResponse(LocalDateTime analyseDate, ParsingDetails details) {
        this.analyseDate = analyseDate;
        this.details = details;
    }

    public LocalDateTime getAnalyseDate() {
        return analyseDate;
    }

    public void setAnalyseDate(LocalDateTime analyseDate) {
        this.analyseDate = analyseDate;
    }

    public ParsingDetails getDetails() {
        return details;
    }

    public void setDetails(ParsingDetails details) {
        this.details = details;
    }
}
