package com.oguztasgin.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;

import static com.oguztasgin.exception.EErrorType.BAD_REQUEST_ERROR;


@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleIllegalArgumentException(IllegalArgumentException exception){
        EErrorType errorType = EErrorType.INTERNAL_ERROR;
        return new ResponseEntity<>(createError(errorType,exception), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(AuthException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleSpringMonoException(AuthException exception){
        return new ResponseEntity<>(createError(exception.getErrorType(),exception),exception.getErrorType().getHttpStatus());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public final ResponseEntity<ErrorMessage> handleMessageNotReadableException(
            HttpMessageNotReadableException exception) {
        EErrorType errorType = BAD_REQUEST_ERROR;
        return new ResponseEntity<>(createError(errorType, exception), errorType.getHttpStatus());
    }

    @ExceptionHandler(InvalidFormatException.class)
    public final ResponseEntity<ErrorMessage> handleInvalidFormatException(
            InvalidFormatException exception) {
        EErrorType errorType = BAD_REQUEST_ERROR;
        return new ResponseEntity<>(createError(errorType, exception), errorType.getHttpStatus());
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public final ResponseEntity<ErrorMessage> handleMethodArgumentMisMatchException(
            MethodArgumentTypeMismatchException exception) {
        EErrorType errorType = BAD_REQUEST_ERROR;
        return new ResponseEntity<>(createError(errorType, exception), errorType.getHttpStatus());
    }

    @ExceptionHandler(MissingPathVariableException.class)
    public final ResponseEntity<ErrorMessage> handleMethodArgumentMisMatchException(
            MissingPathVariableException exception) {
        EErrorType errorType = BAD_REQUEST_ERROR;
        return new ResponseEntity<>(createError(errorType, exception), errorType.getHttpStatus());
    }



    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<ErrorMessage> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception) {
        EErrorType errorType = BAD_REQUEST_ERROR;
        List<String> fields = new ArrayList<>();
        exception
                .getBindingResult()
                .getFieldErrors()
                .forEach(e -> fields.add(e.getField() + ": " + e.getDefaultMessage()));
        ErrorMessage errorMessage = createError(errorType, exception);

        return new ResponseEntity<>(errorMessage, errorType.getHttpStatus());
    }



    /**
     * 1- Hatalar tek String şeklinde dönülmemelidir.
     * 2- Hataları bir JsonObject-Entity şeklinde dönmelisiniz.
     * 3- Dönüş nesnesini bir method ile oluşturmak mantıklıdır. çünkü method içinde
     * loglama yapabilir geribildirtimleri toplayabilirsiniz.
     */
    private ErrorMessage createError(EErrorType errorType, Exception exception){
        System.out.println("HATA OLDU.....: "+ exception.getMessage());
        return ErrorMessage.builder()
                .code(errorType.getCode())
                .message(errorType.getMessage())
                .build();
    }

}
