# 스프링부트 + Next JS를 이용한 Q&A 서비스 만들기
## 챕터1, 스프링부트 설치 및 세팅
- [스프링부트 세팅](https://start.spring.io/)
  - IntelliJ 커뮤니티 버전은 별도의 설치가 필요합니다.
  - Ultimate는 위 세팅이 아닌 인텔리제이 자체 세팅을 따르면 됩니다.

- 스프링부트 버전 : 3.3.5
- 프로젝트 : Gradle - Groovy
- 언어 : JAVA
- 자바 버전 : 17
- 의존성 주입
  - Lombok
  - Spring Boot Dev Tools
  - Spring Web
  - Spring Web Services

## 챕터2, NEXT.js 설치 및 세팅
- [NEXT.js 공식문서](https://nextjs.org/)
- [NEXT.js 설치법](https://nextjs.org/docs)

### 설치 및 준비사항
1. Node 18v 필요

2. 설치
```bash
# 폴더 경로(현재 프로젝트 기준)
# /c/work/springboot_projects/qna_service/src/main

npx create-next-app@latest frontend
```

3. 설치시 세팅사항
## 세팅
### 설정 옵션 및 추천 선택

- **Would you like to use TypeScript?**

  **Yes**를 선택합니다. TypeScript는 타입 안전성을 제공하며, 프로젝트의 유지보수성을 높입니다.

- **Would you like to use ESLint?**

  **Yes**를 선택합니다. ESLint는 코드 품질을 유지하는 데 도움이 되며, 프로젝트에서 일관된 코드 스타일을 유지할 수 있습니다.

- **Would you like to use Tailwind CSS?**

  이 옵션은 **선택 사항**입니다. Tailwind CSS는 유틸리티 기반의 CSS 프레임워크로, CSS 스타일을 빠르게 적용할 수 있게 해줍니다. 만약 CSS 스타일링을 간편하게 하고 싶다면 **Yes**를 선택합니다.

- **Would you like your code inside a `src/` directory?**

  **Yes**를 추천합니다. 프로젝트 구조가 깔끔해지며, 코드와 설정 파일을 분리하여 관리할 수 있습니다.

- **Would you like to use App Router? (recommended)**

  **Yes**를 추천합니다. App Router는 Next.js의 최신 기능을 사용하여 더 나은 성능과 구조를 제공합니다.

- **Would you like to use Turbopack for `next dev`?**

  **Yes**를 추천합니다. Turbopack은 Next.js의 새로운 빌드 시스템으로, 개발 환경에서 빠른 빌드 성능을 제공합니다.

- **Would you like to customize the import alias (`@/*` by default)?**

  **No**를 선택해도 무방합니다. 기본 `@/*` 경로 별칭은 사용하기 편리하며, 특별한 이유가 없다면 기본값을 사용하는 것이 좋습니다.


### 최종 추천 설정

요약하면, 다음과 같이 설정하는 것을 추천합니다.

1. **TypeScript**: Yes
2. **ESLint**: Yes
3. **Tailwind CSS**: 필요 시 Yes
4. **src/ 디렉토리 사용**: Yes
5. **App Router**: Yes
6. **Turbopack**: Yes
7. **Import alias**: No

### NEXT.js 프로젝트 실행
- frontend 프로젝트를 Visual Studio Code로 오픈
```bash
npm run dev
```
-  http://localhost:3000 선택하여 실행되면 설치 완료

### 스프링부트와 NEXT.js와 소통하기 위한 세팅
### 1. 스프링부트와 리액트간의 통신 테스트

#### 세팅을 하는 이유(프록시 설정을 통한 CORS 문제해결)
- Next.js가 기본적으로 프론트엔드 역할을 하고, Spring Boot가 백엔드 API를 제공할 때, 두 서버는 서로 다른 포트에서 동작한다.(예: Next.js는 localhost:3000, Spring Boot는 localhost:8080). 이로 인해 CORS(Cross-Origin Resource Sharing) 문제가 발생할 수 있습니다.
- CORS는 브라우저 보안 정책 때문에 발생하는 문제로, 프론트엔드가 다른 출처의 백엔드 API에 접근할 때 브라우저가 이를 제한합니다.

#### 1) **axios** 설치
- Axios는 JavaScript 라이브러리 중 하나인 Fetch api와 같은 비동기 통신 라이브러리 React ⇒ Axios를 더 많이 사용한다.
- 특징 : 요청과 응답 모두 JSON 형식으로 자동 변환시켜준다!
```bash
# npm 설치
npm install axios

# yarn 설치
yarn add axios
```

#### 2) 스프링부트 CORS 정책 세팅
- config/CorsConfig 경로
```java
@Configuration
public class CorsConfig implements WebMvcConfigurer {
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/api/**")
            .allowedOrigins("http://localhost:3000") // 허용할 Origin 설정
            .allowedMethods("*")
            .allowedHeaders("*")
            .allowCredentials(true);
  }
}
```

#### 연결 테스트 코드
#### Frontend 테스트 코드
```ts
"use client";

import React, { useState, useEffect } from "react";
import axios from "axios";

export default function App() {
  const [data, setData] = useState<string>("");
  const [error, setError] = useState<Error | null>(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const resp = await axios.get("/data");
        setData(resp.data);
      } catch (err) {
        console.error(`에러 발생 : ${err}`);
        setError(err as Error);
      }
    };

    fetchData();
  }, []);

  return <div>받아온 데이터 : {data}</div>;
}
```

#### Backend 테스트 코드
```java
@RestController
@RequestMapping("/api")
public class HomeController {

  @GetMapping("/data")
  // @ResponseBody
  public String test() {
    return "안녕";
  }
}
```