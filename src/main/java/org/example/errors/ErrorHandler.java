package org.example.errors;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MalformedUrlException.class)
    public ResponseEntity<ErrorResponse> handleMalformedUrl(MalformedUrlException ex) {
        ErrorResponse response = new ErrorResponse("Sorry, you entered wrong URL", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoDataInFileException.class)
    public ResponseEntity<ErrorResponse> handleNoDataInFileException(NoDataInFileException ex) {
        ErrorResponse response = new ErrorResponse("Sorry, no data found in the file ", "");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(GeneralParsingException.class)
    public ResponseEntity<ErrorResponse> handleGeneralParsingException(GeneralParsingException ex) {
        ErrorResponse response = new ErrorResponse("Could not parse the file", "");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        String details = ex.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(","));

        ErrorResponse response = new ErrorResponse("Sorry, you entered wrong URL", details);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
