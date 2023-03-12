package com.study.deposit.global.common.exception.handler;

import com.study.deposit.global.common.CommonResponse;
import com.study.deposit.global.common.code.Code;
import com.study.deposit.global.common.code.CommonCode;
import com.study.deposit.global.common.exception.DefaultException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DefaultException.class)
    public ResponseEntity<Object> handleDefaultException(DefaultException e) {
        return handleExceptionInternal(e);
    }

    //@Validated 로 발생하는 에러
//    @ExceptionHandler(ConstraintViolationException.class)
//    public ResponseEntity<Object> handleValidatedException(ConstraintViolationException e){
//        Code code = CommonCode.BAD_REQUEST;
//        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
//        StringBuilder message = new StringBuilder();
//        if(constraintViolations != null) {
//            for(ConstraintViolation c : constraintViolations){
//                String[] paths = c.getPropertyPath().toString().split("\\.");
//                String path = paths.length > 0 ? paths[paths.length - 1] : "";
//                message.append(path);
//                message.append(" : ");
//                message.append(c.getMessage());
//                message.append(". ");
//            }
//        }
//        return handleExceptionInternal(code, message.toString());
//    }

    //@Valid 로 발생하는 에러
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        List<ObjectError> errorList = ex.getBindingResult().getAllErrors();

        StringBuilder builder = new StringBuilder();
        errorList.forEach(error -> {
            String field = ((FieldError) error).getField();
            String msg = error.getDefaultMessage();
            builder.append(field).append(" : ").append(msg).append(". ");
        });
        String message = builder.toString();

        Code code = CommonCode.BAD_REQUEST;

        return handleExceptionInternal(HttpStatus.BAD_REQUEST, code, message);
    }

    private ResponseEntity<Object> handleExceptionInternal(DefaultException e) {
        CommonResponse errorResponse = CommonResponse.toErrorResponse(e.getErrorCode());
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(errorResponse);
    }

    private ResponseEntity<Object> handleExceptionInternal(HttpStatus httpStatus, Code code, String message) {
        CommonResponse errorResponse = CommonResponse.toErrorResponse(code, message);
        return ResponseEntity
                .status(httpStatus)
                .body(errorResponse);
    }
}