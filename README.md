# study-librarydeploy
## Overview
A Spring Boot library that supports common responses related to success / failure / validation

## Version(tag)
- 1.0.0: first deployment
- 1.0.1: gradle dependency error solved
- 1.0.2: delete MainClass
- 1.0.3: handle exception if handler return type is void
- 1.0.4(latest): supports a variety of API responses

## Development environment
- [Spring Boot 2.7.1](https://spring.io/projects/spring-boot)
- [Gradle 7.4.1](https://docs.gradle.org/7.4.1/release-notes.html)
- [Java 11](https://docs.oracle.com/en/java/javase/11/docs/api/)

## Response spec
### success
```json
{
  "result": "success",
  "code": 200,
  "msg": null,
  "data": [
    {
      "field1": "data1",
      "field2": "data2",
      "field3": "data3"
    },
    {
      "field1": "data1",
      "field2": "data2",
      "field3": "data3"
    }
  ],
  "validationMsg": null
}
```

### failure
```json
{
  "result": "fail",
  "code": 400,
  "msg": "Invalid request",
  "data": null,
  "validationMsg": null
}
```

### validation
```json
{
  "result": "fail",
  "code": 400,
  "msg": null,
  "data": null,
  "validationMsg": {
    "field1": "validationMsg1",
    "field2": "validationMsg2",
    "obejct": "validationMsg3"
  }
}
```

## Add dependencies
### gradle
```groovy
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}

dependencies {
	implementation 'com.github.rere950303:study-librarydeploy:Tag'
	implementation 'org.springframework.boot:spring-boot-starter-validation'
	implementation 'org.springframework.boot:spring-boot-starter-aop'
	implementation 'org.springframework.boot:spring-boot-starter-web'
}
```

### maven
```xml
<repositories>
	<repository>
		<id>jitpack.io</id>
		<url>https://jitpack.io</url>
	</repository>
</repositories>

<dependency>
	<groupId>com.github.rere950303</groupId>
	<artifactId>study-librarydeploy</artifactId>
	<version>Tag</version>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
    <version></version>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
    <version></version>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <version></version>
</dependency>
```

## Getting started
- Add `@EnableAPIResponse` annotation on `@SpringBootApplication`
- Add `@APIResponse(HttpStatus status)` annotation to class or method (default: `HttpStatus.Ok`)
- Method takes precedence when both classes and methods are applied

### success response
The object type returned by the handler should be `Object` or `void`. If the return type is void and you do not want to return any value, set `wantReturn` of `@APIResponse` to `false` 

### failure response
If you throw an exception, you must throw an exception that extends `APIResponseException`. When creating an exception object, `HttpStatus` suitable for the situation must be handed over as a parameter

### validation response
Both Bean validation and custom validators are applicable. **`errors.properties` Message Code -> Default Message** returns a validation message

### API response custom
- If you want to customize the API Response, inherit the class `AbstractCommonResult` and set the `returnType` of `@APIResponse`
- The `APIResponseFactory` interface, which creates inherited classes, must be implemented and registered as bean

### example of use
```java
@RestController
@APIResponse(HttpStatus.OK)
public class ExampleController {

    @GetMapping("/example1")
    public Object example1() {
        return object;
    }

    @PostMapping("/example2")
    @APIResponse(HttpStatus.CREATED)
    public void example2(@RequestBody @Valid ExampleDTO dto) {
        
    }

    @GetMapping("/example3")
    public Object example3() {
        throw new APIResponseException("Invalid request.", HttpStatus.BAD_REQUEST);
    }
}
```
