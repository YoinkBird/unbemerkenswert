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
  // HACK placeholder
  // private final notebook_id = 1;

  DemoController(DemoRepository repository) {
    this.repository = repository;
  }

  /*
   * Individual Notes
   */
  // Aggregate root

  @GetMapping("/notebooks/{nbid}")
  List<Demo> all() {
    return repository.findAll();
  }

  @PostMapping("/notebooks/{nbid}/create")
  Demo newDemo(@RequestBody Demo newDemo) {
    return repository.save(newDemo);
  }

  // Single item

  @GetMapping("/notebooks/{nbid}/{id}")
  Demo one(@PathVariable Long id) {

    return repository.findById(id)
      .orElseThrow(() -> new DemoNotFoundException(id));
  }

  @PostMapping("/notebooks/{nbid}/{id}/update")
  Demo replaceDemo(@RequestBody Demo newDemo, @PathVariable Long id) {

    return repository.findById(id)
      .map(demo -> {
        demo.update( newDemo );
        return repository.save(demo);
      })
      .orElseGet(() -> {
        newDemo.setId(id);
        return repository.save(newDemo);
      });
  }

  @PostMapping("/notebooks/{nbid}/{id}/delete")
  void deleteDemo(@PathVariable Long id) {
    repository.deleteById(id);
  }
}
