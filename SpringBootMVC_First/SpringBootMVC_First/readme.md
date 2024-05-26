# 스프링 MVC 1편 - 백엔드 웹 개발 핵심 기술
## 웹 애플리케이션 이해
### 웹서버
- HTTP기반으로 동작
- 정적 리소스 제공, 기타 부가기능
- 정적 파일 HTML,CSS, JS, 이미지, 영상
### 웹 애플리케이션 서버
- HTTP 기반으로 동작
- 웹 서버 기능 포함 + 정적 리소스 제공 가능
- 프로그램 코드를 실행해서 애플리케이션 로직 수행
  - 동적 HTML, HTTP API
  - 서블릿, JSP, Spring MVC
### 웹 서버, 웹 애플리케이션 서버 차이
- 웹 서버는 정적 리소스, WAS는 애플리에키션 로직
- 사실 둘의 경계는 모호하다.
- 자바는 서블릿 컨테이너 기능을 제공하면 WAS
- WAS는 에플리케이션 코드를 실행하는데 특화되어 있다.
### WAS의 문제점
- WAS가 너무 많은 역활을 담당한다. 서버 과부화 우려
- 가방 비싼 애플리케이션 로직이 정적 리소스 때문에 수행이 어려울 수 있음
- WAS 장애시 오류 화면도 노출 불가능 할 수도 있다
###  WEB, WAS, DB
- 따라서 WAS만 단일로 사용하는 것 보다는 WEB server와 Was를 동시에 사용한다.
- 정적 파일들은 web server가 담당하고 애플리케이션 로직은 WAS가 담당하는 것으로 한다
- 따라서 효율적인 리소스 관리가 가능하다.
  - 정적 리소스가 많이하면 web server증설
  - 로직 리소스가 많아지만 WAS 증설
- 정적 리소스를 제공하는 web server는 잘 안죽음
- 반면에 was나 DB는 잘 죽기 때문에 죽었을때 web이 오류 페이지를 보여줄 수 있음
### servlet
- urlPatterns의 url이 호출되면 서블릿 코드가 실행
  - HTTP 요청 정보를 편리하게 사용할 수 있는 HttpServletRequest
  - HTTP 응답 정보를 편리하게 만들 수 있는 HttpServletResponse
  - 개발자는 HTTP 스팩을 매우 편리하게 사용한다.
### servlet 동작과정
<img src="image/servlet_running.png">

### 서블릿 컨테이너
- 톰캣처럼 서블릿을 지우너하는 WAS를 서블릿 컨테이너라고 한다.
- 서블릿 컨테이너는 서블릿 객체를 생성, 초기화, 호출, 종료하는 생명주기 관리한다.
- 서블릿 객체는 싱글톤으로 관리
  - 고객의 요청이 올 때 마다 계속 객체를 생성하는 것을 비효율적이다.
  - 최초 로딩 시점에 서블릿 객체를 미리 만들어두고 재활용
  - 모든 요청은 동일한 서블릿 객체 인스턴스에 접근
  - 공유 변수 사용 주의
  - 서블릿 컨에티너 종료시 함께 종료
- JSP도 서블릿으로 변환되어 사용
- 동시 용청을 위한 멀티 쓰레드 지원
### servlet single thread 동작 과정
<img src="image/servlet_single_thread1.png">
<img src="image/servlet_single_thread2.png">
<img src="image/servlet_single_thread3.png">

### servlet multi thread 동작 과정
<img src="image/servlet_multi_thread1.png">
<img src="image/servlet_multi_thread2.png">
<img src="image/servlet_multi_thread3.png">

### 요청마다 쓰레드 생성 장단점
- 장점
  - 동시 요청을 처리할 수 있다.
  - 리소스가 허용할 때 까지 처리 가능
  - 하나의 스레드가 지연되어도, 나머지 쓰레드는 정상 동작 한다.
- 단점 
  - 쓰레드는 생성 비용은 매우 비싸다.
    - 고객의 요청이 올 때마 쓰레드를 생성하면 응답속도가 늦어질 가능성이 크다.
  - 쓰레드는 컨텍스트 스위칭 비용이 발생한다.
  - 쓰렏 생성 제한이 없어 리소스 임계점을 넘어 버리면 서버가 죽는다.
- 단점을 해결하기 위해서 쓰레드 풀을 이용하는 방법을 채택한다.

### 쓰레드 풀
- 특징
  - 쓰레드를 미리 만들어두 두고 관리한다.
- 사용
  - 쓰레드가 필요하면, 이미 생성되어 있는 쓰레드를 쓰레드 풀에서 꺼내서 사용한다.
  - 사용을 종료하면 쓰레드 풀에 해당 쓰레드를 반납한다.
  - 최대 쓰레드가 상용중이면 반납 될때까지 기다리거나 거절한다.
- 장점
  - 쓰레드가 미리 생성되어 있음므로, 쓰레드를 생성하고 종료하는 비용이 줄어든다.
  - 생성 가능한 쓰레드의 최대치가 있음으로, 너무 많은 요청이 들어와도 기존 요청은 안전하게 처리할 수 있다.
- 실무 팁
  - WAS의 주요 튜닝 포인트는 최대 쓰레드 수이다.
  - 너무 낮게 설정하면 로직을 처리할 스레드가 부족함으로 클라이언트 응답이 지연
  - 너무 높게 설정하면 서버 리소스 임계점 초과로 서버 다운
- 장애 발생시?
  - 클라우드면 일단 서버 늘리고, 이후에 튜닝
  - 클라우드가 아니면 열심히 튜닝?

### 서버사이드 렌더링, 클라이언트 사이드 렌더링
- SSR-서버 사이드 렌더링
  - HTML 최종 결과를 서버에서 만들어서 웹 브라우저에 전달
  - 주로 정적인 화면에 사용
  - 관련기술: JPS, 타임리프 
- CSR-클라이언트 사이드 렌더링
  - HTML 결과를 자바스트립트를 사용해 웹 브라우저에서 동적으로 생성해서 적용
  - 주로 동적인 화면에 사용, 웹 환경을 마치 앱 처럼 필요한 부분을 변경할 수 있음

<img src="image/CSR.png">

### @ServletComponentScan
- spring boot에서 servlet을 지원하는데 servlet을 사용하기 위해서는 @ServletComponentScan을 넣어야 한다.
- Scan범위는 해당 위치 하위 패치키를 스캔한다.

### 서블릿 컨테이너 동작 방식
<img src="image/servlet_container_running_process.png">
<img src="image/servlet_contatiner_response.png">

### from에서 넘어오는 값을 받는 다양한 방법
- HttpServletRequest.getInputStream()를 사용하면 파싱 이전의 값을 받을 수 있다.
- 이후에 값을 파싱 하는데 그 기준은 어떤 형태로 넝어 오는지에 따라 달라진다.
  - JSON
    - ObjectMaper.class의 readvValue method를 사용하면 json형태로 넘어오는 값을 DTO class에 넣을 수 있다.
    - 여기서 DTO에 없는 필드값이 넘어오면 UnrecognizedPropertyException가 발생하고
    - DTO에 필드값은 있는데 데이터가 넘어 오지 않는 경우에는 초기화된 값이 된다.

### 서블릿과 JSP의 한계
- 서블릿만으로 개발하는 경우
  - servlet에서 html를 만들어서 보여주기때문에 html유지보수가 힘들어 진다.
- jsp를 추가하는 경우 
  - 동적으로 보여져야 하는 부분은 동적으로 보여질 수 있다.
  - 하지만 JSP가 많은 역활을 담당하고 있어. 유지보수가 힘들어 질 수 있다.
  - 유지보수가 힘든 이유는 비즈니스 로직의 라이프사이클과 UI의 라이프 사이클이 다르다는 점이 문제다.
  - 따라서 두 개발자가 같은 부분을 수정할 가능성이 있다.
- 따라서 나온것이 MVC 패턴이다.

### MVC 패턴 이전과 이후의 차이.
<img src="image/before_MVC2.png">
<img src="image/after_MVC.png">

### VMC 패턴의 한계
- 화면을 이동할때 servlet을 통해서 이동해야 하는 규칙이 있어서 중복코드가 많이 있음
  - ```java
        String viewPath = "/WEB-INF/views/new-form.jsp";
        RequestDispatcher requestDispatcher = req.getRequestDispatcher(viewPath);
        requestDispatcher.forward(req,resp);
- 공통처리가 어렵다.
  - 로그 출력과 같은 공통 처리를 각 servlet에 넣어야 한다.

### 프론트 컨트롤러의 특징
1. 프론트 컨트롤러 서블릿 하나로 클라이언트의 요청을 받음
2. 프론트 컨트롤러가 요청에 맞는 컨트롤러를 찾아서 호출
3. 공통 처리 가능
4. 프론트 컨트롤러를 제외한 나머지 컨트롤러는 서블릿을 사용하지 않아도 됨

<img src="image/before_front_controller.png">
<img src="image/after_front_controller.png">

### 프론트 컨트롤러 도입 (V1)
<img src="image/introdcing_front_controller.png">

### view 분리 (V2)
<img src="image/separation_view.png">

### model 분리 (V3)
<img src="image/separation_model.png">

### Controller에서 view를 반환 (V4)
<img src="image/retrun-view-on-controller.png">

### adepter pattern (V5)
- generic으로 선언되어 있기 때문에 v4와 v3를 같이 사용할 수 없다.
- 하지만 상황에 따라서 선택하여 사용할 수 있도록 할 수 있다.

<img src="image/adepter-pattern-in-controller.png">

