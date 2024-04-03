# 🍃 Spring 서버 만들기

해당 레포지토리는 자바 웹 프로그래밍 Next Step을 참조해 진행합니다.

> html/css는 [해당 레포지토리](https://github.com/Origogi/DreamCoding-FE-Portfolio-Clone)를 참조했습니다.

<br>
<br>
<br>

# Step1. 사용자 정보를 저장한다.

클라이언트로부터 전송된 데이터를 파싱해 사용자 정보를 저장한다.

- [X] 요청에 맞는 정적 페이지를 브라우저에 전달한다.
- [X] Http 메시지 파싱해서 HttpRequest 객체로 만들기
- [X] URI와 HTTP 메소드를 바탕으로 핸들러를 매핑한다.
- [X] 프론트 컨트롤러 패턴을 적용해서 요청 처리
- [X] 요청에 따른 응답을 브라우저에 전달해 정적 페이지를 화면에 띄운다.
- [X] 데이터베이스는 애플리케이션 내부 인메모리 데이터베이스를 사용한다.

<br>
<br>
<br>

## 📦 패키지 구조

```
.
├── app
│   └── core
└── framework
    ├── RequestHandler.java
    ├── annotation
    ├── context
    ├── core
    ├── http
    ├── servlet
    ├── util
    ├── utils
    └── view
```

프로젝트는 크게 app, framework 두 부분으로 나뉘어집니다. app이 framework에 의존하고 framework는 app에 의존하지 않도록 하여 의존성 사이클을 없애고자 했습니다.

<br>
<br>
<br>

## 📝 Http 메시지의 start-line, 표현 헤더(respresentation header), 바디(body)

HTTP 프로토콜에 맞게 클라이언트와 통신하려면 요청의 HTTP 메시지를 해석하고 이에 대한 응답을 HTTP 메시지로 생성하여 클라이언트에게 전송해야 합니다.

이를 위해 공부한 주요 표현 헤더는 다음과 같습니다.

* Content-Length : HTTP 바디의 크기이고, 단위는 byte입니다.
* Content-Type : 리소스의 media type을 나타내기 위해 사용합니다. HTTP 응답에서 Content-Type 헤더는 클라이언트에게 실제 컨텐츠 타입을 제공합니다. 응답 표현 헤더에서 적절한
  Content-Type을 설정하지 않아 화면에 css가 반영되지 않는 이슈를 겪었습니다. 브라우저가 리소스의 MediaType을 해석하고 적절한 화면을 띄워주기 위해서 반드시 설정해야 합니다.
* Location : 리다이렉트(redirect)할 페이지를 나타내는 응답 헤더입니다. HTTP 상태 코드가 300번대 일때만 의미가 있습니다. 이 응답헤더는 회원가입 폼 제출 후 POST요청을 처리하고 리다이렉트
  할 때 사용합니다. 리다이렉트를 하는 이유는 새로고침으로 인한 중복 제출을 방지하기 위함입니다.

<br>
<br>
<br>

## 🎮 프론트 컨트롤러(Front controller)

DispatcherServlet을 이용해 프론트 컨트롤러를 구현했습니다. DispatcherServlet에서는 HTTP 프로토콜로 들어오는 요청에서 공통적으로 처리해야할 작업을 수행하고 있습니다.

지금은 등록한 빈을 다 조회해서 적합한 어노테이션을 갖고 있는 핸들러 메소드를 호출하는 방식으로 핸들러 매핑을 진행하고 있습니다. 추후에 더 빠르게 조회할 수 있도록 핸들러 매핑 로직을 수정할 예정입니다.

<br>
<br>
<br>

## 🍃 스프링 컨테이너

`@Component` 어노테이션 기반 스프링 자동 빈 등록 방식인 Component Scan과 `@Configuration`과 `@Bean`을 통한 수동 빈 등록 방식을 리플렉션(Reflection)을 통해
구현했습니다.

리플렉션은 다음과 같은 문제점이 있습니다.

* 캡슐화 위반 : 리플렉션을 이용하면 클래스의 내부구조에 접근할수 있고, 이는 애플리케이션의 보안에 취약점을 만들 수 있습니다.
* 성능 저하 : 리플렉션을 통한 메소드 호출은 일반적인 메소드 호출에 비해 성능이 저하될 수 있습니다.
* 타입 안정성 문제: 컴파일 시점에 타입 체크가 이루어지지 않고 런타임에 결정됩니다.

그럼에도 불구하고 리플렉션을 사용한 이유는 유연성과 확장성을 높입니다. 객체 간의 의존 관계를 변경할 때, 의존 관계를 설정하는 부분에서만 변경이 일어나고, 다른 부분에는 영향을 미치치 않기 때문입니다. 이는 의존성
주입이 동적으로 일어나기 때문입니다. 프레임워크 패키지에서 애플리케이션 패키지에 대해서 의존하지 않고 객체 생성과 의존성 주입을 진행할 수 있습니다.

리플렉션은 유연성과 확장성을 높일 수 있지만, 캡슐화 위반, 성능 저하, 타입 안정성 문제가 있기 때문에 리플렉션은 꼭 필요한 곳에만 사용해야 됩니다.

# Step2. 로그인 기능을 구현한다.

- [X] 로그인 기능 구현.
- [X] Session 만료시간 관리.
- [X] 회원 정보 상세보기
- [X] 회원 정보 수정

<br>
<br>
<br>

## 쿠키(Cookie)와 세션(Session)

## 쿠키

* 서버에 메모리 부담을 줄일 수 있습니다.
* 요청 시 쿠키 내부에 있는 정보들이 그대로 노출될 수 있어 보안 상의 문제가 있습니다.
* 쿠키의 크기가 클 경우 네트워크 부하가 커질 수 있습니다.

<br>
<br>
<br>

## 세션

* 세션은 사용자 정보를 세션 아이디와 함께 서버에 저장합니다.
* 클라이언트에게 세션 아이디를 Set-Cookie 헤더에 넣어 전송합니다.
* 세션 아이디만 보내기 때문에 네트워크 부하가 커지지 않습니다.
* 서버에 저장되므로 클라이언트의 웹브라우저 호환성 문제가 해결됩니다.
* 서버에 데이터 저장량이 많아져 서버 메모리에 부담이 갈 수 있습니다.

<br>
<br>
<br>

# Step3. 데이터베이스를 교체한다.

애플리케이션 내부 메모리에 저장하던 사용자 정보를 외부 데이터베이스인 MySQL에 저장했습니다.

- [X] 인메모리 에서 외부 데이터베이스 교체(MySQL)
- [X] JDBC Template 구현
- [X] prepared statement 사용해서 SQL 주입 방지

<br>
<br>
<br>

## 📁 데이터베이스에 저장하는 방식 변경

스프링 서버를 직접 구현하며, 데이터를 저장하는 방식을 메모리에 저장하는 방식에서 JDBC API를 이용해서 외부 데이터베이스에 저장하는 방식으로 바꿨습니다. 이를 통해 데이터를 영구적으로 저장할 수 있게
되었습니다.

<br>
<br>
<br>

## 📏 책임 분리 시 템플릿 콜백 패턴 이용

데이터베이스에 접근 시, 변경되는 부분과 변경되지 않는 부분을 분리할 필요가 있었습니다. 이때에 템플릿 콜백 패턴을 적용하여 변하지 않는 부분은 템플릿으로 만들었고, SQL문과 같이 변하는 부분은 콜백으로 만들고
템플릿에 전달하여서, 원하는 시점에 콜백을 실행시킬 수 있게끔 했습니다.
