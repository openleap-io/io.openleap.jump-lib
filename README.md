# jump-lib
[![Build status](https://github.com/openleapio/jump-lib/actions/workflows/main-build.yml/badge.svg)](https://github.com/openleapio/jump-lib/actions/workflows/main-build.yml)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](LICENSE)
[![Maven central](https://img.shields.io/maven-central/v/io.openleap/jump-lib)](https://search.maven.org/search?q=a:jump-lib)

With the Jump Lib you can make bigger "leaps": It is a collection of helpers used across projects. All dependencies are defined
in Maven `provided` scope to cut transitive dependencies.

## Usage
**Notice: The latest release and the current development branch are tested against Spring Boot 3.3.2**

Add as Maven dependency
```
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>io.openleap</groupId>
                <artifactId>jump-lib</artifactId>
                <version>1.0-SNAPSHOT</version>
            </dependency>
            <dependency>
                <groupId>io.openleap</groupId>
                <artifactId>jump-lib</artifactId>
                <version>1.0-SNAPSHOT</version>
                <type>test-jar</type>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <dependencies>
        <dependency>
            <groupId>io.openleap</groupId>
            <artifactId>jump-lib</artifactId>
        </dependency>
        <dependency>
            <groupId>io.openleap</groupId>
            <artifactId>jump-lib</artifactId>
            <type>test-jar</type>
            <scope>test</scope>
        </dependency>
    </dependencies>

```

To benefit from some abstract test classes use the `test-jar` for all other stuff go with 
the `jar`.

## Core Features

- Useful AOP aspects
- Common exception classes
- Mapper abstraction
- Logging extensions (Logback & Logstash)

### Useful AOP aspects 

Four aspects are implemented to track method calls around application layers. Three of them measure and trace the method execution time at
a defined logging category. The pointcut definition where the aspect actually takes place is pre-defined in `io.openleap.aop.Pointcuts`. This
definition can be overridden by putting a customized `io.openleap.aop.Pointcuts` class on the classpath. In addition to method tracing some
aspects although care about exception translation and offer an extension point to translate custom exceptions.

Jump AOP support is enabled by including the package `io.openleap.annotation` in component-scan **or** by using the `@EnableAspects`
annotation on a custom `@Configuration` class. Furthermore `org.springframework:spring-aspects` needs to be at the classpath at runtime.

| Aspect (classname)           | Method Tracing Logging Category | Exception Translation  | Exception Logging Category   |
| ---------------------------- |:------------------------------- |:----------------------:|:---------------------------- |
| PresentationLayerAspect      | --                              | --                     | PRESENTATION_LAYER_EXCEPTION |
| ServiceLayerAspect           | SERVICE_LAYER_ACCESS            | X                      | SERVICE_LAYER_EXCEPTION      |
| IntegrationLayerAspect       | INTEGRATION_LAYER_ACCESS        | X                      | INTEGRATION_LAYER_EXCEPTION  |
| MeasuredAspect               | MEASURED                        | --                     | --                           |

For method tracing the SLF4J loglevel has to be configured to `INFO`, exception logging need to be configured to level `ERROR` instead.
Since 2.2 logging of exception stack traces can be turned off for specific exception types that are marked with `io.openleap.annotation.NotLogged`.

### Common exception classes 

 Exception classes we have used over and over again in projects were re-implemented in jump-lib. All of them encapsulate a message key that
 can be used to translate the actual message text. Some kind of exceptions are of technical nature, whereas other exceptions express a
 behavior.

![Exception hierarchy][2]

### Mapper abstraction 

In first place we use [Dozer](http://dozer.sourceforge.net) as mapping library. But this dependency is optional, and other mapper libraries can be used as well. A common
interface is defined were client applications can rely on. This is a Java 5 generic interface that makes it easy to map at compile time.
Why we need to map at all in Java is a different discussion. A `io.openleap.mapping.BeanMapper` interface is provided to
applications that need to map object structures. `io.openleap.mapping.DozerMapperImpl` and several Dozer converters
exist. 


### Logging extensions 

At first a `ThreadIdProvider` is used to identify each thread in a concurrent
test run. By default logback does only provide a meaningless thread name. But a thread counter can now be configured to display the current value in the
log message. To get the full power of Jump log extensions just include the `logback-appenders.xml` and `logback-loggers.xml` into your logback.xml:

 ````
 <configuration>
 
     <property name="MODULE_NAME" value="stamplets"/>
 
     <include resource="logback-appenders.xml" />
     <include resource="logback-loggers.xml" />
 
     <logger name="org.foo" level="ERROR"/>
 
     <root level="DEBUG">
         <appender-ref ref="STDOUT"/>
         <appender-ref ref="LOGFILE"/>
     </root>
 
 </configuration>
 ```` 

The following appender names are defined: 

| Appender name | Outputs to                              | Description                              |
| ------------- |:--------------------------------------- |:---------------------------------------- |
| STDOUT        | stdout                                  | Print sto stdout. Useful for test output |
| LOGFILE       | java.io.tmpdir/BOOT-<MODULE_NAME>.log   | Standard program or trace logging        |
| EXCFILE       | java.io.tmpdir/BOOT-<MODULE_NAME>.exlog | Exception logs                           |
| TSL           | java.io.tmpdir/BOOT-<MODULE_NAME>.tslog | Technical service logging. Logs method execution consumption |

The module name can be configured as logback property:

 ```` 
     <property name="MODULE_NAME" value="my-module"/>
 ```` 

`BOOT` is the default tenant name. The logging is basically multitenant-aware. If no active tenant is set, the default is used as prefix.
A tenant can be set by adding a property called `Tenant` to the `org.slf4j.MDC`. See `io.openleap.http.SLF4JMappedDiagnosticContextFilter`.

The logfile path can be configured by setting the logback property:

 ```` 
     <property name="LOG_PATH" value="/tmp"/>
 ```` 

By default Jump logging first tries to find the configured $LOG_PATH property. If this property does not exist, it looks up $CATALINA_BASE
to check if the application is running inside Tomcat. If even this does not exist it tries to find a logback property named $LOG_TEMP, and if
that does not exist either, it will log to java.io.tmpdir.

Notice: The output pattern is defined to be aligned to the Grok pattern that is used in combination with Logstash ([logstash.conf][logstashconf]).


## Development process
 Contribution welcome. The development process is kept lean, without the need to apply any IDE formatter templates. Just a few rules to
 follow:

  - All Java files must have the Apache License header on top
  - All Java types must provide a meaningful Javadoc comment with the initial author (`@author`). The first sentence in Javadoc is used as headline and needs to
    be a short but meaningful description of the type class.

 ````
 /**
  * A Dummy class is a sample implementation to showcase the Javadoc comment requirements.
  *
  * @author Max Mustermann
  */
 ````

  - Public API methods have to be documented

### How to release
 A release is built from the `main` branch. At first all required feature branches need to be merged into the `main` branch. Only if
 the `main` branch builds successfully the release can be done. We follow the simple [Feature branch principle](https://de.atlassian.com/git/tutorials/comparing-workflows#feature-branch-workflow) 

 Checkout the main branch and build it locally with Javadocs and sources. Then use the release plugin to setup versions, git tags and
 upload to artifact repository.

 ````
 $ mvn clean package -Drelease
 $ mvn release:prepare
 $ mvn release:perform
 ````

 Follow these naming conventions:

  - A release version should follow the MM(P)(E) pattern: `<Mayor>.<Minor>(.<Patch>)(-<Extension>)`

  Mayor, Minor and Patch are increasing numbers and follow the rules of [Apache ARP release strategy][1]. The patch version number is only
  applied if the release is a bug fix release of a feature version. An Extension may be applied for release candidates or milestone
  releases, e.g. `-RC1` or `-M1`


 [1]: https://apr.apache.org/versioning.html#strategy
 [2]: src/site/resources/exceptions.png


[logstashconf]: src/main/resources/logstash.conf
