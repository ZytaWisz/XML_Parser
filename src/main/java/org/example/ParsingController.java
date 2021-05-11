package org.example;

import org.example.dto.ParseRequest;
import org.example.dto.ParsingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class ParsingController {

    @Autowired
    ParsingService parsingService;

    @PostMapping("/analyze")
    public ParsingResponse analyze(@Valid @RequestBody ParseRequest parseRequest) {
        return parsingService.getParsingResult(parseRequest);
    }


}
