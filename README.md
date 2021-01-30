# Examples of Aspect Oriented Programming (AOP) with SpringBoot

Most people know object oriented programming and functional programming. But
what about **aspect oriented programming** (AOP)? This repository contains 4 examples
of AOP, ranging from built-in annotations to custom Aspects.

## Dependencies

* Java JDK 15
* SpringBoot
* Maven

## Compile and run the web service

* Use Maven for the build process:
```sh
mvn clean install
mvn spring-boot:run
```

## Example REST calls

* Compute a Fibonacci number:
```json
GET http://localhost:8080/api/fibonacci/40
```
* Store a String (simulate shaky database)
```json
POST http://localhost:8080/api/storeData?data=hello-world
```

## Aspects for the Fibonacci computation

The computation of a Fibonacci number creates the following log:

```raw
Method [fibonacci] gets called with parameters [40]
Execution took [3379ms]
------------
Method [fibonacci] gets called with parameters [40]
Execution took [3ms]
```

The following aspects are in play:
* `@Cacheable`: This standard-Spring annotation allows to cache previous results. As you can see above, the first REST call
is pretty slow, but the second call is super fast.
* `@MonitorTime`: We use a custom aspect which measure the total execution time of our REST call and prints the result
to the console.
* `@LogMethodName`: We use a second custom aspect which prints the name and arguments of our REST controller method.

## Aspects for storing data

Storing a String in our shaky database creates the following log:

```raw
Method [storeData] gets called with parameters [hello-world]
------------
Method [storeData] gets called with parameters [hello-world]
------------
Method [storeData] gets called with parameters [hello-world]
```

The following aspects are in play:
* `doUnstableOperation`: This Aspect makes sure that the (unstable and idempotent) method is retried until it succeeds.
In the log above, you can see that it took 3 tries to store the data. 