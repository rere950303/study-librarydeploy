# study-librarydeploy
## Overview
성공 / 실패 / 검증과 관련된 공통된 api response를 지원하는 Spring Boot 라이브러리

## Version
- 1.0.0: 첫 배포
- 1.0.1(latest): gradle dependency error solved

## api response spec
### 성공
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

### 실패
```json
{
  "result": "fail",
  "code": 400,
  "msg": msg,
  "data": null,
  "validationMsg": null
}
```

### 검증
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

## 의존성 추가
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

## 사용법
- `@SpringBootApplication` 위에 `@EnableApiResponse` 어노테이션 추가
- 클래스 또는 메서드에 `@ApiResponse(HttpStatus)` 어노테이션 추가 (디폴트: `HttpStatus.Ok`)
- 클래스와 메서드 모두 적용된 경우 메서드가 우선권을 가짐

### 성공 응답
핸들러에서 반환하는 객체 타입을 `Object`로 해주어야 한다.

### 실패 응답
예외를 던질 경우 `ApiResponseException`을 상속한 예외를 던져야 한다. 예외 객체 생성시 상황에 맞는 `HttpStatus`를 매개변수로 넘겨주어야 한다.

### 검증 응답
Bean validation 또는 커스텀한 validator 모두 적용 가능하다. **`errors.properties` 메시지 코드 -> 디폴트 메시지** 순서로 검증 메시지를 반환하다.

### 사용 예시
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
        throw new ApiResponseException("잘못된 요청입니다.", HttpStatus.BAD_REQUEST);
    }
}
```
