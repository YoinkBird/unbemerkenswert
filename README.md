# unbemerkenswert
nothing of note here!

# USAGE
Runs `docker-compose --abort-on-container-exit` and prints the return code
```
./run.sh
```

# About

(partially) Implements [requirements.md](requirements.md).

Miscellaneous notes are under [notes.md](notes.md) and continued under [worklog.md](worklog.md) .

# Structure

The [docker-compose.yaml](docker-compose.yaml) corresponds to targets within the [Dockerfile](Dockerfile), namely `build`, `prod`, and `test`.

[api-gate](api-gate) contains all java code.

[utils](utils) contains scripts that helped manage IDEs and frameworks

## Documentation

There are not as many comments or javadoc as I would like; this is due to time constraints.

My hope is that this is self-documenting enough via the simple [run.sh](run.sh) entrypoint and the resulting test output.

## Testing
Testing is implemented as a bash e2e test using `jq` and `test` within [api-gate/curl_8080_demo-temp.sh](api-gate/curl_8080_demo-temp.sh)


Unit testing was not implemented; see [#unit-testing](#unit-testing) for details.


## Program Structure

Simple layout with all code being within [api-gate/build/classes/java/main/com/merkmal/apigate](api-gate/build/classes/java/main/com/merkmal/apigate)

gradle was used as the build tool as it is easy to hand-edit.

## Infrastructure: Dev Environment

For containerization, Docker was chosen due to it's widespread usage.

For orchestration, docker-compose was chosen due to its simple integration with Docker and easy local setup.

To overcome limitations of docker-compose `depends_on`, [dockerize](https://github.com/jwilder/dockerize) was used.

# COMPROMISES

## UNIT TESTING

There was not enough time to set up unit tests.

Unfortunately, the choice of framework dictates the structure of unit tests.

Since SpringBoot tutorials all use a very tight coupling, I could not figure out how to split up the java files into meaningful units.

For this, I apologise.

## SPECIFICATION

There was not enough time to fully implement the spec.

Unfortunately, I chose to use the SpringBoot framework, which required a lot of research to understand.

Therefore, only a basic "create note" functionality exists, and not "create notebooks full of notes".

This is because I could not figure out how to retrieve or search through the stored objects (repository).

Therefore I could neither find a way to retreive a "Note" from a NoteBook" nor could I search for "tags" within a "Note".

## RESTful

This is not a RESTful API as it does not semantically expose its resources.

This is because more pressing issues needed to be solved, and then there wasn't enough time.

# CHALLENGES

## IDE

Intellij basically never worked as it should. 

Short non-exhaustive list of issues:

* wouldn't start until I deleted my preferences
* wouldn't run my included gradle files
* completely refused to recognise valid imports, e.g. Lombok, which were defined within the build.gradle
* very tricky to install correctly on \*nix, and has no uninstaller. See [utils/install_intellij.sh](utils/install_intellij.sh)

I have used java IDEs before, and usually they save a lot of time with advanced refactoring, managing imports, gradle, etc.
Unfortunately that was not meant to be.

So I used vim and ran `gradlew bootRun` from my terminal and simply edited everything by hand.

This took a long time.

## Framework

From experience, it is usually a good idea to use frameworks for setting up web APIs.

### SpringBoot

Bad choice for this assignment.

Initially, I chose SpringBoot, which comes with all kinds of neat features.

However, the abstractions are so intense that it requires a lot of time to figure out.

Ultimately I failed to figure out how to retrieve anything from the "repository" back into a semblance of the original object.
I am certain that this is possible, but there is simply not enough time.


#### Setup
Additionally, it is odd to have a "framework" that gets updated by downloading zip files and extracting them.

I had to use an incremental approach to setting it up.

When I downloaded the "fully loaded" zip file, there many compile errors due to missing configurations.

So I slimmed it down, got it running, then downloaded a zip file with slighly more features, saw what failed, etc.

not great.

#### Documentation and Tutorials

There are a lot of tutorials and documentation, but they make the classic mistake of showing one very opinionated implementation.

Once this path has been followed, it is difficult to adapt the code to do anything else, thus rendering it good only for a demo.

Useful Links:

https://spring.io/guides/tutorials/bookmarks/#_http_is_the_platform

=> simply returns repository representations, no actual interaction. Still, the documentation on being RESTful is nice.

### JavaSpark

Would have been a better choice, although again the documentation is a bit lacking.

It is also very unfortunate that Apache Spark shares the same name, thus rendering it difficult to research online.

### Setup

Setup is not very well documented, but it was as simple as adding the dependencies to the build.gradle.

This is logical, since this framework does only a small fraction of what SpringBoot does.

By default it is less featured than SpringBoot, e.g. uses the `jetty` server which has no hot-reloading (or if it does, I didn't see it :-) )

However, it is a much simpler setup and learning experience and I would recommend this for future reference.

#### Documentation and Tutorials

Best documentation: https://github.com/perwendel/spark

The spark documentation itself assumes quite a lot of familiarity with other ... somethings; it doesn't even mention how to start/stop the server.

http://sparkjava.com/documentation#getting-started


A few other secondary sources which were helpful:


http://zetcode.com/java/spark/


https://www.twilio.com/blog/2015/09/getting-started-with-gradle-and-the-spark-framework-3.html

More advanced:

https://www.boxuk.com/insight/creating-a-rest-api-quickly-using-pure-java/

Once set up, this tutorial would have been all that was needed:

https://github.com/perwendel/spark/blob/master/README.md#examples

Unfortunately, I was able to get past a few SpringBoot issues and went down that path instead.


## CONTAINERIZATION

It was interesting to learn how to set up gradle within a container, e.g. how to run it in non-daemon mode and exactly where the jar files live

This link was particularly useful:

https://codefresh.io/docs/docs/learn-by-example/java/gradle/



