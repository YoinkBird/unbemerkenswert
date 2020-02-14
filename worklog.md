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

## Testing
enhancing quick-and-dirty E2E test in bash to serve purposes as this evolves


## timestamps
src: https://stackoverflow.com/a/23068748
=> good enough
found imports at:
https://docs.oracle.com/javase/7/docs/api/java/util/Date.html
https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html

CANNOT use SimpleDateFormat to do ISO 8601 src: https://stackoverflow.com/a/2202300

notes from attempts:
```
          // attempt2 - ISO 8601-ish. oh noes: src https://stackoverflow.com/a/2202300
          //String dateFormat = "yyyy.MM.ddTHH.mm.ss";
          // java.lang.IllegalArgumentException: Illegal pattern character 'T'
          // attempt3: didn't RTFM well enough, the  T needed single quotes
```
          // attempt2 - ISO 8601-ish. oh noes: src https://stackoverflow.com/a/2202300

### Constructors
not sure how to get Lombok to put the timestamp stuff in each generated constructor; for now doing it the super wrong way by calling it on creation/update/etc :-(
further reading src: https://www.baeldung.com/spring-injection-lombok


### testing
refactored to create json in better fashion, be more strict about timestamps

### Constructors again
finally making progress; maybe was just doing it all wrong earlier?


## tags
not sure why tags don't update, but a bit of testing has shown that the actual newDemo doesn't have them
```
        demo.update( newDemo );
```
```

import java.util.Arrays;
//...
        public void update(Demo updateDemo){
          this.setBody( updateDemo.getBody() );
          String[] testarr =  { "no1","no2"} ;
          this.setTags( testarr );
          System.out.println( Arrays.toString( updateDemo.getTags() ) );
          this.setTags( updateDemo.getTags() );
          this.setUpdateDate( updateDemo.getLastModified());
        }
```
