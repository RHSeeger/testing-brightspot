# Automated Testing in Brightspot

## Overview

Setting up automated testing for a Brightspot project can be tricky at first, especially if you want to test 
how your code interacts with other systems (or any tests that run slow necessitating the ability to run them 
only on demand). That being said, if we take it step by step, we can setup automated testing for a project 
without too much effort.

The overall goals I work towards include:
- The quick/unit tests will be run on every build
- The slower/integration/functional tests can be run on demand.
- There is a clear separation between the quick/unit and slower/integration tests

Some assumptions I'm making include
- Maven is being used for the project configuration/build process
- JUnit is being used for testing
- You have a general idea of what unit testing is and how to do it (outside of the context of Brightspot)

I'm going to break up the configuration into a few different sections; Simple, Database Required, and Integration tests.

## Simple Tests

These are the tests that don't require any interaction with the database or other systems. Specifically, they 
are the simplest of the Unit Tests, interacting purely on data.

### Dependencies

Obviously, you'll need to add the junit dependency to your [pom.xml]. (link to pom and line numbers)

```xml
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.8.1</version>
    <scope>test</scope>
</dependency>
```

### Directory Structure

Once this is done, you can start writing tests. Maven automatically knows how to interact with tests as 
long as the project follows the directory structure it expects (src/text/java). For example, to test a 
class such as 
[```com.sample.Sample```](https://github.com/RHSeeger/testing-brightspot/blob/master/src/main/java/com/sample/Simple.java)
we would put the test file in 
[```src/test/java/com/simple/Sample_Test.java```](https://github.com/RHSeeger/testing-brightspot/blob/master/src/test/java/com/sample/Simple_Test.java)
and add some tests to it.

### Running the Tests

Then we can run our tests with maven and see the results

```
[master](69)>> mvn test
[INFO] Scanning for projects...
... snip ...
-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running com.sample.Simple_Test
Running tests from class [com.sample.Simple_Test]
Running test [max_second]
Running test [max_same]
Running test [max_first]
Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.032 sec

Results :

Tests run: 3, Failures: 0, Errors: 0, Skipped: 0

[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 4.826 s
[INFO] Finished at: 2015-02-23T12:14:22-05:00
[INFO] Final Memory: 33M/320M
[INFO] ------------------------------------------------------------------------
```

## Database Required

These are the types of tests that require the database (ex, running tests on instances of classes that
extend Record or Content).

### Configuration

The first thing to do is create a 
[src/test/resources/settings.properties](https://github.com/RHSeeger/testing-brightspot/blob/master/src/test/resources/settings.properties)
file that tells the system to create in-memory databases for our testing purposes.

```
dari/defaultDatabase=my.local
dari/database/my.local/class=com.psddev.dari.db.SqlDatabase
dari/database/my.local/jdbcUrl=jdbc:h2:mem:db1
dari/database/my.local/jdbcUser=
dari/database/my.local/jdbcPassword=
```

Then, we need to add the items in the 
[pom.xml](https://github.com/RHSeeger/testing-brightspot/blob/master/pom.xml)
it needs to load up the databases in memory

```xml
<dependency>
    <groupId>org.apache.solr</groupId>
    <artifactId>solr-core</artifactId>
    <version>3.5.0</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <version>1.3.166</version>
    <scope>test</scope>
</dependency>
```

### Writing the Tests

Once the configuration is done, we can create and interact Dari objects normally.

*__Note:__ If you're working with Metrics, be aware that (as of the time this was written) they 
do not work correctly with the H2 (in-memory) database.*

For example, see 
[RecordBased.java](https://github.com/RHSeeger/testing-brightspot/blob/master/src/main/java/com/sample/RecordBased.java)
and 
[RecordBased_Test.java](https://github.com/RHSeeger/testing-brightspot/blob/master/src/test/java/com/sample/RecordBased_Test.java)
. When you run the tests, you'll see it load up the database when it realizes it needs access to it.

```
[master](82)>> mvn test
[INFO] Scanning for projects...
...snip...
-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running com.sample.RecordBased_Test
Running tests from class [com.sample.Simple_Test]
Running test [generateDisplayTitle_source]
Running test [generateDisplayTitle_editorial]
Running test [generateDisplayTitle_none]
Running test [beforeSave_title_source]
Feb 23, 2015 12:46:55 PM com.psddev.dari.util.Settings$2$1 update
INFO: Can't read from JNDI! [javax.naming.NoInitialContextException: Need to specify class name in environment or system property, or as an applet parameter, or in an application resource file:  java.naming.factory.initial]
Feb 23, 2015 12:46:55 PM com.psddev.dari.util.TaskExecutor <init>
INFO: Creating [Periodic Caches]
Feb 23, 2015 12:46:56 PM com.psddev.dari.db.SqlDatabase createDataSource
INFO: Automatically creating BoneCP data source:
        url=jdbc:h2:mem:db1
        username=
        poolSize=24
        connectionsPerPartition=8
        partitionCount=3
Feb 23, 2015 12:46:56 PM com.jolbox.bonecp.BoneCPConfig sanitize
WARNING: JDBC username was not set in config!
Feb 23, 2015 12:46:56 PM com.psddev.dari.db.SqlDatabase setDataSource
INFO: Initializing SQL vendor for [null]: [H2] -> [class com.psddev.dari.db.SqlVendor$H2]
Feb 23, 2015 12:46:56 PM com.psddev.dari.db.DatabaseEnvironment refreshGlobals
INFO: Loading globals from [my.local]
Feb 23, 2015 12:46:56 PM com.psddev.dari.db.DatabaseEnvironment refreshTypes
INFO: Loading [0] types from [my.local]
Feb 23, 2015 12:46:58 PM com.psddev.dari.db.DatabaseEnvironment refreshGlobals
INFO: Loading globals from [my.local]
Feb 23, 2015 12:46:58 PM com.psddev.dari.db.DatabaseEnvironment refreshTypes
INFO: Loading [188] types from [my.local]
Running test [beforeSave_title_editorial]
Running test [beforeSave_title_none]
Tests run: 6, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 2.925 sec
Running com.sample.Simple_Test
Running tests from class [com.sample.Simple_Test]
Running test [max_same]
Running test [max_first]
Running test [max_second]
Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0 sec

Results :

Tests run: 9, Failures: 0, Errors: 0, Skipped: 0

[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 7.937 s
[INFO] Finished at: 2015-02-23T12:46:58-05:00
[INFO] Final Memory: 35M/396M
[INFO] ------------------------------------------------------------------------
```

## Integration Tests

These are the tests that need to talk to external systems, or just take too long to run to do so with every 
build. Admittedly, this doesn't really define them as "Integration Tests", but certain accommodations 
are made since

1. We really only have 2 types of test "batches" we can put things into
2. We want to keep the "every build" test runs speedy.

This is also where things get more complicated, since maven doesn't understand multiple types of test phases 
out of the box. In order to have it run these tests, and be able to do so on command, we use the 
[Failsafe Plugin](http://maven.apache.org/surefire/maven-failsafe-plugin/) 
to tie the [verify] phase of the build to running the integration tests.

Bear in mind that most of this is me muddling my way through various sites that discuss the very same topic, 
trying to find what works best for me. Its entirely possible that there are other approaches that may work better.

That being said...

### Configuration

We're going to need to setup a fair number of different pieces to make this process work.

### Directory Structure

Add the directory structure for integration tests, to keep them separate from other tests
We're going to put the test classes and resources in 
[src/integration-test/java](https://github.com/RHSeeger/testing-brightspot/tree/master/src/integration-test/java)
and 
[src/integration-test/resources](https://github.com/RHSeeger/testing-brightspot/tree/master/src/integration-test/resources)
. Other than that, the structure is the same as for normal unit tests (package name path for src, etc).

#### Source Files

Configure the build to copy the test source files during the build We use the build-helper-maven-plugin 
plugin (create link) to copy the java files over.

```xml
    <build>
        <plugins>
            ...
            <plugin>
                <groupId>org.codehaus.mojo</groupId>
                <artifactId>build-helper-maven-plugin</artifactId>
                <version>1.7</version>
                <executions>
                    <execution>
                        <id>add-test-source</id>
                        <phase>process-resources</phase>
                        <goals>
                            <goal>add-test-source</goal>
                        </goals>
                        <configuration>
                            <sources>
                                <source>src/integration-test/java</source>
                            </sources>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
```

#### Resource Files

Configure the build to copy resources required for the integration tests. We use the maven-resources-plugin 
(create link to file in github) to copy the integration test resources over to where the test run can find them

```xml
    <build>
        <plugins>
            ...
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-resources-plugin</artifactId>
                <version>2.5</version>
                <executions>
                    <execution>
                        <id>add-it-resources</id>
                        <phase>pre-integration-test</phase>
                        <goals>
                            <goal>copy-resources</goal>
                        </goals>
                        <configuration>
                            <outputDirectory>${project.build.directory}/integration-test-classes</outputDirectory>
                            <resources>
                                <resource>
                                    <directory>src/integration-test/resources</directory>
                                </resource>
                            </resources>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
```

#### Build Phase Binding

Bind the failsafe plugin actions to the verify phase.

```
<build>
        <plugins>
            ...
            <!-- Failsafe is used to run functional tests via [mvn verify] -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-failsafe-plugin</artifactId>
                <version>2.14.1</version>
                <executions>
                    <execution>
                        <goals>
                            <goal>integration-test</goal>
                            <goal>verify</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
```

**Note:** The order of the plugins in the build section "may" be important. I'm sure I saw something about one of them 
needing to be before the maven-compiler-plugin. Unfortunately, I don't recall which that was, nor why.
Create the Tests

The failsage plugin is configured (by default) to look for classes by the name *_IT. As such, much like 
for normal unit tests where you name your test classes *MyClass_Test* (to test *MyClass*), you'll name your 
integration test classes *MyClass_IT*. It is worth noting that it's likely that you'll have integration test 
classes that don't test a single class from the main source code. For those cases, I tend to use a class name 
in the format of *TypeOfFunctionalityBeingTested_IT*.

A sample test file can be found in 
[Integrated_IT.java](https://github.com/RHSeeger/testing-brightspot/blob/master/src/integration-test/java/com/sample/Integrated_IT.java)

And, from there, you can run the tests with

```
mvn verify
```

I hope this helps...

