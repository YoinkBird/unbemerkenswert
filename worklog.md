# until now
TODO

# current attempt:
api-gate/
morph to follow: https://spring.io/guides/tutorials/bookmarks/
but without the `LoadDatabase`
but not working, so use simpler implementation from: https://spring.io/guides/gs/rest-service/#_create_a_resource_controller

implementing https://spring.io/guides/tutorials/bookmarks/#_http_is_the_platform
Broken:
```
$ ./gradlew bootRun

> Task :compileJava FAILED
/home/yoinkbird/workspace/search3/knime/unbemerkenswert/api-gate/src/main/java/com/merkmal/apigate/DemoController.java:39: error: incompatible types: Optional<Demo> cannot be converted to Demo
    return repository.findById(id);
```

... ok tutorials are hard, but now at least I know exactly what the error was
summary: not really sure why, but adding the DemoNotFoundException resolved the issue. in the tutorial, I hadn't noticed the extra classfile below (for throwing exception).
However, I had commented out the line calling the exception (see git commits), so I'm not sure how this suddenly worked. 

Now:
back-updating the "demo" class to let lombok do it's thing by removing the explicit setters/getter


## Lombok
doesn't like ArrayList, no worries.
Just use normal array, as per src: https://www.projectlombok.org/features/ToString
