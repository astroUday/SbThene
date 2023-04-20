package com.chicmic.sbthene.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler implements handleResponseEntityExceptionHandler {

    // Exception for User not found
    @ExceptionHandler({CustomApiExceptionHandler.class})
    public final ResponseEntity<ErrorDetails> handleApiExceptions(CustomApiExceptionHandler ex, WebRequest request) {

        return new ResponseEntity<ErrorDetails>(
                new ErrorDetails(LocalDateTime.now(),ex.getMessage(), ex.getStatus()), HttpStatus.NOT_FOUND);
    }


    @Override
    public ResponseEntity<Object> handleMethodArggumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        //final List<String> errors = new ArrayList<String>();
        String errors = "";
        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors += error.getDefaultMessage();
        }
//
        ErrorDetails error = new ErrorDetails(LocalDateTime.now(),errors,HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

    }

//    // Exception for Token not Given
//    @ExceptionHandler(TokenNotGivenException.class)
//    public final ResponseEntity<ErrorDetails> handleTokenNotGivenExceptions(Exception ex, WebRequest request) throws Exception {
//
//        ErrorDetails errorDetails=new ErrorDetails(LocalDateTime.now(),
//                new Throwable("Token Id is not Valid, please provide valid 'tokenId' starting with 'Bearer ' !").getMessage(), request.getDescription(false));
//
//        return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.BAD_REQUEST);
//    }
//
//    // Exception for User already present
//    @ExceptionHandler(UserAlreadyPresent.class)
//    public final ResponseEntity<ErrorDetails> handleUserAlreadyPresent(Exception ex, WebRequest request) throws Exception {
//
//        ErrorDetails errorDetails=new ErrorDetails(LocalDateTime.now(),
//                new Throwable("The username is already taken, please choose another one.").getMessage(), request.getDescription(false));
//
//        return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.BAD_REQUEST);
//    }
}
