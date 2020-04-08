# Daikon Eureka

![Daikon](./logo.svg)

Daikon Eureka is a library that add to Daikon the ability to publish on an Eureka Server and discover other services.

The main goals are:
* Use Daikon applications ina a Spring Cloud Netflix system
* Make Eureka integration easiest as possible

## How to add Daikon Eureka to your project
[![](https://jitpack.io/v/DaikonWeb/daikon-eureka.svg)](https://jitpack.io/#DaikonWeb/daikon-eureka)

### Gradle
- Add JitPack in your root build.gradle at the end of repositories:
```
repositories {
    ...
    maven { url 'https://jitpack.io' }
}
```
- Add the dependency
```
implementation('com.github.DaikonWeb:daikon-eureka:1.2.7')
```

### Maven
- Add the JitPack repository to your build file 
```
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```
- Add the dependency
```
<dependency>
    <groupId>com.github.DaikonWeb</groupId>
    <artifactId>daikon-eureka</artifactId>
    <version>1.2.7</version>
</dependency>
```

## How to use

### To publish your service:
```
HttpServer()
    .initDiscoveryClient()
    .start()
```

### To discover a service:
```
HttpServer()
    .initDiscoveryClient()
    .get("/") { _, _, ctx ->
        val service = ctx.discoveryClient().getNextServerFromEureka("onion", false)       
    }
    .start()
```

## Resources
* Documentation: https://daikonweb.github.io
* Examples: https://github.com/DaikonWeb/daikon-examples
* Eureka project: https://github.com/Netflix/eureka
* Spring Cloud Netflix: https://spring.io/projects/spring-cloud-netflix

## Authors

* **[Marco Fracassi](https://github.com/fracassi-marco)**

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details
