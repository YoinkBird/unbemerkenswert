package com.merkmal.apigate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class DemoNotFoundAdvice {

  @ResponseBody
  @ExceptionHandler(DemoNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String employeeNotFoundHandler(DemoNotFoundException ex) {
    return ex.getMessage();
  }
}
