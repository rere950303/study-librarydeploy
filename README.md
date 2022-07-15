# study-librarydeploy
## Overview
A Spring Boot library that supports common responses related to success / failure / validation

## Version(tag)
- 1.0.0: first deployment
- 1.0.1: gradle dependency error solved
- 1.0.2: delete MainClass
- 1.0.3(latest): handle exception if handler return type is void
- 1.0.4(to be distributed): supports a variety of api responses

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
- Add `@EnableApiResponse` annotation on `@SpringBootApplication`
- Add `@ApiResponse(HttpStatus status)` annotation to class or method (default: `HttpStatus.Ok`)
- Method takes precedence when both classes and methods are applied

### success response
The object type returned by the handler should be `Object` or `void`

### failure response
If you throw an exception, you must throw an exception that extends `ApiResponseException`. When creating an exception object, `HttpStatus` suitable for the situation must be handed over as a parameter

### validation response
Both Bean validation and custom validators are applicable. **`errors.properties` Message Code -> Default Message** returns a validation message

### example of use
```java
@RestController
@ApiResponse(HttpStatus.OK)
public class ExampleController {

    @GetMapping("/example1")
    public Object example1() {
        return object;
    }

    @PostMapping("/example2")
    @ApiResponse(HttpStatus.CREATED)
    public void example2(@RequestBody @Valid ExampleDTO dto) {
        
    }

    @GetMapping("/example3")
    public Object example3() {
        throw new ApiResponseException("Invalid request.", HttpStatus.BAD_REQUEST);
    }
}
```
