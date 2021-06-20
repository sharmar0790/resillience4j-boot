# Resillience 4J

## This sample project will show the basic example to use **Resillience 4j**

Resilience4j is a lightweight fault tolerance library designed for Java 8 and functional programming. Lightweight, because the library only uses Vavr, which does not have any other external library dependencies.

Resilience4j provides higher-order functions (decorators) to enhance any functional interface, lambda expression or method reference with a Circuit Breaker, Rate Limiter, Retry or Bulkhead. You can stack more than one decorator on any functional interface, lambda expression or method reference. The advantage is that you have the choice to select the decorators you need and nothing else.


### Scaffolding the project
```
$ ./mvnw clean build
```

### Run the app
```
$ java -jar target/demo-0.0.1-SNAPSHOT.jar
```

### yaml config 
```yaml
resilience4j.circuitbreaker:
  configs:
    default:
      registerHealthIndicator: true
      slidingWindowSize: 2
      minimumNumberOfCalls: 5
      permittedNumberOfCallsInHalfOpenState: 2
      automaticTransitionFromOpenToHalfOpenEnabled: true
      waitDurationInOpenState: 5s
      failureRateThreshold: 50
      eventConsumerBufferSize: 10
      recordExceptions:
        - org.springframework.web.client.HttpServerErrorException
        - java.util.concurrent.TimeoutException
        - java.io.IOException
        - org.springframework.web.client.ResourceAccessException
    shared:
      slidingWindowSize: 100
      permittedNumberOfCallsInHalfOpenState: 30
      waitDurationInOpenState: 1s
      failureRateThreshold: 50
      eventConsumerBufferSize: 10
  instances:
    service_1:
      baseConfig: default

resilience4j.bulkhead:
  configs:
    default:
      maxConcurrentCalls: 100
  instances:
    service_1:
      maxConcurrentCalls: 10

resilience4j.thread-pool-bulkhead:
  configs:
    default:
      maxThreadPoolSize: 4
      coreThreadPoolSize: 2
      queueCapacity: 2
  instances:
#   service_2:
#      baseConfig: default
    service_1:
      maxThreadPoolSize: 2
      coreThreadPoolSize: 1
      queueCapacity: 1
```


### Guides
The following guides illustrate how to use some features concretely:

* [Resillience 4j getting started](https://resilience4j.readme.io/docs/getting-started-3)
* [Resillinece 4j Spring boot example](https://github.com/resilience4j/resilience4j-spring-boot2-demo)

