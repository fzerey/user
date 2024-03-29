package com.fzerey.user.host.middleware;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fzerey.user.shared.exceptions.AlreadyExistsException;
import com.fzerey.user.shared.exceptions.NotFoundException;
import com.fzerey.user.shared.exceptions.UnauthorizedAccessException;
import com.fzerey.user.shared.exceptions.constants.ExceptionCodes;
import com.fzerey.user.shared.exceptions.constants.ExceptionMessages;

@ControllerAdvice
public class ExceptionMiddleware {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundException(NotFoundException e) {
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage(), e.getCode()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleAlreadyExistsException(AlreadyExistsException e) {
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage(), e.getCode()),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<ExceptionResponse> handleUnauthorizedAccessException(UnauthorizedAccessException e) {
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage(), e.getCode()),
                HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception e) {

        return new ResponseEntity<>(
                new ExceptionResponse(ExceptionMessages.UNEXPECTED_EXCEPTION, ExceptionCodes.UNEXPECTED_EXCEPTION),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
