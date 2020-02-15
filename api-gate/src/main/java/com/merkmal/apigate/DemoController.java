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
   * Notebooks
   */
  // TODO: use separate class for 'Notebook' instead of 'Demo
  // view all notebooks
  @GetMapping("/notebooks")
  List<Demo> allNotebooks() {
    return repository.findAll();
  }

  // create notebook
  @PostMapping("/notebooks/create")
  Demo newNotebook(@RequestBody Demo newNotebook) {
    return repository.save(newNotebook);
  }

  /*
  // view notebook
  @GetMapping("/notebooks/{nbid}")
  Demo singleNotebook(@PathVariable Long id) {

    return repository.findById(id)
      .orElseThrow(() -> new DemoNotFoundException(id));
  }
  */

  // NO need for "notebook update", although timestamp does need to be updated

  // delete notebook
  @PostMapping("/notebooks/{nbid}/delete")
  void deleteNotebook(@PathVariable Long id) {
    repository.deleteById(id);
  }

  /*
   * Individual Notes
   */
  // within notebook, view all notes
  @GetMapping("/notebooks/{nbid}")
  List<Demo> all() {
    return repository.findAll();
  }

  // within notebook, create note
  @PostMapping("/notebooks/{nbid}/create")
  Demo newDemo(@RequestBody Demo newDemo) {
    return repository.save(newDemo);
  }

  // within notebook, view note
  @GetMapping("/notebooks/{nbid}/{id}")
  Demo one(@PathVariable Long id) {

    return repository.findById(id)
      .orElseThrow(() -> new DemoNotFoundException(id));
  }

  // within notebook, update note
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

  // within notebook, delete note
  @GetMapping("/notebooks/{nbid}/{id}/delete")
  void deleteDemo(@PathVariable Long id) {
    repository.deleteById(id);
  }
}
