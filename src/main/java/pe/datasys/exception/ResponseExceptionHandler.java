package pe.datasys.exception;

import org.springframework.beans.TypeMismatchException;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomErrorResponse> handleAllException(Exception ex, WebRequest request) {
        CustomErrorResponse err = new CustomErrorResponse(LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false), 500);
        return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ModelNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleModelNotFoundException(ModelNotFoundException ex,
            WebRequest request) {
        CustomErrorResponse err = new CustomErrorResponse(LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false), 404);
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }

    @Override
     protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
         String msg = ex.getBindingResult().getFieldErrors().stream().map(
                 e -> e.getField().concat(":").concat(e.getDefaultMessage().concat(" "))
         ).collect(Collectors.joining());
 
         CustomErrorResponse err = new CustomErrorResponse(LocalDateTime.now(), msg, request.getDescription(false), 400);
         return  new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
     }
 
     @Override
     protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
         CustomErrorResponse err = new CustomErrorResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false), 400);
         return  new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
     }
}
