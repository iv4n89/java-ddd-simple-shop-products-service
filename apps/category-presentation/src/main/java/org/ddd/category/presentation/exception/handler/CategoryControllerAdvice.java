package org.ddd.category.presentation.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.ddd.category.application.exceptions.CategoryNotFoundException;
import org.ddd.category.domain.exceptions.CategoryDomainException;
import org.ddd.shared.application.ErrorDto;
import org.ddd.shared.infrastructure.handler.GlobalExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class CategoryControllerAdvice extends GlobalExceptionHandler {

  @ResponseBody
  @ExceptionHandler(value = {CategoryDomainException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorDto handleException(CategoryDomainException categoryDomainException) {
    log.error(categoryDomainException.getMessage(), categoryDomainException);
    return ErrorDto.builder()
        .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
        .message(categoryDomainException.getMessage())
        .build();
  }

  @ResponseBody
  @ExceptionHandler(value = {CategoryNotFoundException.class})
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorDto handleException(CategoryNotFoundException categoryNotFoundException) {
    log.error(categoryNotFoundException.getMessage(), categoryNotFoundException);
    return ErrorDto.builder()
        .code(HttpStatus.NOT_FOUND.getReasonPhrase())
        .message(categoryNotFoundException.getMessage())
        .build();
  }
}
