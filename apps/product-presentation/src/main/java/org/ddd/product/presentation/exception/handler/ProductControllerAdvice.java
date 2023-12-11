package org.ddd.product.presentation.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.ddd.product.application.exceptions.ProductNotFoundException;
import org.ddd.product.domain.exceptions.ProductDomainException;
import org.ddd.shared.application.ErrorDto;
import org.ddd.shared.infrastructure.exception.handler.GlobalExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class ProductControllerAdvice extends GlobalExceptionHandler {

  @ResponseBody
  @ExceptionHandler(value = {ProductDomainException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorDto handleException(ProductDomainException productDomainException) {
    log.error(productDomainException.getMessage(), productDomainException);
    return ErrorDto.builder()
        .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
        .message(productDomainException.getMessage())
        .build();
  }

  @ResponseBody
  @ExceptionHandler(value = {ProductNotFoundException.class})
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorDto handleException(ProductNotFoundException productNotFoundException) {
    log.error(productNotFoundException.getMessage(), productNotFoundException);
    return ErrorDto.builder()
        .code(HttpStatus.NOT_FOUND.getReasonPhrase())
        .message(productNotFoundException.getMessage())
        .build();
  }
}
