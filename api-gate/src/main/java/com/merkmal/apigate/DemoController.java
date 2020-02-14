package com.merkmal.apigate;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class DemoController {

  private final DemoRepository repository;

  DemoController(DemoRepository repository) {
    this.repository = repository;
  }

  // Aggregate root

  @GetMapping("/demos")
  List<Demo> all() {
    return repository.findAll();
  }

  @PostMapping("/demos")
  Demo newDemo(@RequestBody Demo newDemo) {
    return repository.save(newDemo);
  }

  // Single item

  @GetMapping("/demos/{id}")
  Demo one(@PathVariable Long id) {

    return repository.findById(id)
      .orElseThrow(() -> new DemoNotFoundException(id));
  }

  @PutMapping("/demos/{id}")
  Demo replaceDemo(@RequestBody Demo newDemo, @PathVariable Long id) {

    return repository.findById(id)
      .map(demo -> {
        demo.setBody(newDemo.getBody());
        //demo.setRole(newDemo.getRole());
        return repository.save(demo);
      })
      .orElseGet(() -> {
        newDemo.setId(id);
        return repository.save(newDemo);
      });
  }

  @DeleteMapping("/demos/{id}")
  void deleteDemo(@PathVariable Long id) {
    repository.deleteById(id);
  }
}
