package com.merkmal.apigate;
class DemoNotFoundException extends RuntimeException {

  DemoNotFoundException(Long id) {
    super("Could not find employee " + id);
  }
}
