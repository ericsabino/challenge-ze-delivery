package br.com.zedelivery.parceiroze.app.configuration.exception.handler;

import br.com.zedelivery.parceiroze.app.configuration.exception.BadRequestException;
import br.com.zedelivery.parceiroze.app.configuration.exception.BusinessException;
import br.com.zedelivery.parceiroze.app.configuration.exception.InternalServerErrorException;
import br.com.zedelivery.parceiroze.app.configuration.exception.model.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalHandlerException extends ResponseEntityExceptionHandler {

    @ExceptionHandler({BadRequestException.class})
    protected ResponseEntity<Object> handleBadRequestException(BadRequestException exception, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        List<ErrorCamposResponse> campos = new ArrayList<>();
        Iterator iterator = exception.getDetalhes().iterator();

        while(iterator.hasNext()) {
            BadRequestDetail detail = (BadRequestDetail) iterator.next();
            campos.add(ErrorCamposResponse.builder().campo(detail.getCampo()).mensagem(detail.getMensagem()).valor(detail.getValor()).build());
        }

        ErrorResponse errorBody = ErrorResponse.builder().codigo(String.valueOf(status.value())).mensagem(exception.getMessage()).campos(campos).build();
        return this.handleException(exception, errorBody, status, request);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception, WebRequest request) {
        List<ErrorCamposResponse> campos = new ArrayList<>();
        ErrorResponse errorBody = ErrorResponse.builder()
                .campos(Arrays.asList(ErrorCamposResponse.builder().campo(exception.getParameter().getParameter().getName()).build()))
                .codigo(String.valueOf(HttpStatus.BAD_REQUEST))
                .mensagem(String.valueOf(exception.getMessage()))
                .build();

        return this.handleException(exception, errorBody, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        List<ErrorCamposResponse> campos = ex.getBindingResult().getFieldErrors().stream().map( (error) -> {
                return ErrorCamposResponse.builder().campo(error.getField()).mensagem(error.getDefaultMessage()).build();
        }).collect(Collectors.toList());

        BadRequestException badRequestException = new BadRequestException();
        ErrorResponse errorBody = ErrorResponse.builder().codigo(String.valueOf(HttpStatus.BAD_REQUEST.value())).mensagem(badRequestException.getMessage()).campos(campos).build();

        return handleExceptionInternal(
                badRequestException, errorBody, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(InternalServerErrorException.class)
    protected ResponseEntity<Object> handleInternalServerErrorException(InternalServerErrorException exception, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorResponse errorBody;
        if(!exception.getCodigo().equals("") && exception.getCodigo() != null) {
            errorBody = ErrorResponse.builder()
                    .codigo(String.valueOf(exception.getCodigo()))
                    .mensagem(String.valueOf(exception.getMessage()))
                    .build();
        } else {
            errorBody = ErrorResponse.builder()
                    .codigo(String.valueOf(status.value()))
                    .mensagem(exception.getMessage())
                    .build();
        }

        return this.handleException(exception, errorBody, status, request);
    }

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<Object> handleInternalServerErrorException(BusinessException exception, WebRequest request) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        List<BusinessDetail> detalhes = new ArrayList<>();
        Iterator iterator = exception.getDetalhes().iterator();

        while(iterator.hasNext()) {
            BusinessDetail detail = (BusinessDetail) iterator.next();
            detalhes.add(BusinessDetail.builder().codigo(detail.getCodigo()).mensagem(detail.getMensagem()).build());
        }
        var errorBody = BusinessErrorResponse.builder().codigo(String.valueOf(status.value())).message(exception.getMessage()).detalhes(detalhes).build();
        return this.handleException(exception, errorBody, status, request);
    }

    private ResponseEntity<Object> handleException(Exception exception, Object body, HttpStatus status, WebRequest request) {
        return this.handleExceptionInternal(exception, body, new HttpHeaders(), status, request);
    }
}
