# study-librarydeploy
## Overview
A Spring Boot library that supports common responses related to success / failure / validation

## Version
- 1.0.0: first deployment
- 1.0.1(latest): gradle dependency error solved

## Response spec
### success
```json
{
  "result": "success",
  "code": 200,
  "msg": null,
  "data": [
    {
      "field1": data1,
      "field2": data2,
      "field3": data3
    },
    {
      "field1": data1,
      "field2": data2,
      "field3": data3
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
  "msg": msg,
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
    "field1": validationMsg1,
    "field2": validationMsg2,
    "obejct": validationMsg3
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
```

## Getting started
- Add `@EnableApiResponse` annotation on `@SpringBootApplication`
- Add `@ApiResponse(HttpStatus status)` annotation to class or method (default: `HttpStatus.Ok`)
- Method takes precedence when both classes and methods are applied

### success response
The object type returned by the handler should be `Object`

### failure response
If you throw an exception, you must throw an exception that extends `ApiResponseException`. When creating an exception object, `HttpStatus` suitable for the situation must be handed over as a parameter

### validation response
Both Bean validation and custom validators are applicable. **`errors.properties` Message Code -> Default Message** returns a validation message

### example of use
```java
@RestController
@ApiResponse(HttpStatus.OK)
static class ExampleController {

    @GetMapping("/example1")
    public Object example1() {
        return object;
    }

    @PostMapping("/example2")
    @ApiResponse(HttpStatus.CREATED)
    public Object example2() {
        return object;
    }

    @GetMapping("/example3")
    public Object example3() {
        throw new ApiResponseException("Invalid request.", HttpStatus.BAD_REQUEST);
    }
}
```
