# springboot
https://start.spring.io/#!type=gradle-project&language=java&platformVersion=2.2.4.RELEASE&packaging=jar&jvmVersion=1.8&groupId=com.merkmal&artifactId=api-gate&name=apigate&description=API%20gateway%20for%20unbemerkenswert%2C%20the%20app%20with%20nothing%20to%20see%20here!&packageName=com.merkmal.apigate&dependencies=devtools,web,jdbc,data-jdbc,postgresql,flyway,restdocs,cloud-contract-stub-runner

remove a lot to get it working; probably included too much
https://start.spring.io/#!type=gradle-project&language=java&platformVersion=2.2.4.RELEASE&packaging=jar&jvmVersion=1.8&groupId=com.merkmal&artifactId=api-gate&name=apigate&description=API%20gateway%20for%20unbemerkenswert%2C%20the%20app%20with%20nothing%20to%20see%20here!&packageName=com.merkmal.apigate&dependencies=devtools,web

## Spring Data JPA
https://start.spring.io/#!type=gradle-project&language=java&platformVersion=2.2.4.RELEASE&packaging=jar&jvmVersion=1.8&groupId=com.merkmal&artifactId=api-gate&name=apigate&description=API%20gateway%20for%20unbemerkenswert%2C%20the%20app%20with%20nothing%20to%20see%20here!&packageName=com.merkmal.apigate&dependencies=devtools,web,data-jpa

## following rest tutorial
https://spring.io/guides/gs/accessing-data-rest/
https://start.spring.io/#!type=gradle-project&language=java&platformVersion=2.2.4.RELEASE&packaging=jar&jvmVersion=1.8&groupId=com.merkmal&artifactId=api-gate&name=apigate&description=API%20gateway%20for%20unbemerkenswert%2C%20the%20app%20with%20nothing%20to%20see%20here!&packageName=com.merkmal.apigate&dependencies=devtools,web,data-jpa,data-rest,h2

# intellij
intellij uninstall
remember to remove this: 
/usr/local/bin/idea

intellij is evil
why use case in a dirname? so that autocomplete won't find it?
src: https://intellij-support.jetbrains.com/hc/en-us/community/posts/207006735-Reset-IntelliJ-settings-to-default- 

## misc errors
https://stackoverflow.com/questions/31215452/intellij-idea-importing-gradle-project-getting-java-home-not-defined-yet

# live reload / hot swap
https://dzone.com/articles/spring-boot-application-live-reload-hot-swap-with

```
1. Open the Settings --> Build-Execution-Deployment --> Compiler

    and enable the Make Project Automatically.

2. Then press ctrl+shift+A and search for the registry. In the registry, make the following configuration enabled.

compiler.automake.allow.when.app.running
```
/.IdeaIC2019.3/config/options/ide.general.xml

# dataabase in memory
https://stormpath.com/blog/tutorial-crud-spring-boot-20-minutes#add-a-database
2020-02-12 20:11:58.115  INFO 4467 --- [  restartedMain] o.s.b.a.h2.H2ConsoleAutoConfiguration    : H2 console available at '/h2-console'. Database available at 'jdbc:h2:mem:testdb'

# setting up alternative port
adapted from https://stackoverflow.com/questions/21083170/how-to-configure-port-for-a-spring-boot-application

# gradle lock
# src: https://stackoverflow.com/a/32783447
$ find ~/.gradle -type f -name "*.lock" -delete


# Java Misc
src/main/java/com/example/books/Book.java:13: error: class Books is public, should be declared in a file named Books.java
