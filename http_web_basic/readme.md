# 모든 개발자를 위한 HTTP 웹 기본 지식

## 인터넷 네트워크

<ul>
    <li>클라이언트와 서버가 통신할때 인터넷을 통해 통신해야 한다면 어떻게 해야할까?</li>
    <li>그러면 어떤 규칙과 어떤 방식을 통해 데이터를 주고 받을까?</li>
</ul>

### IP(Internet protocol)

<ul>
    <li>지정한 IP 주소에 데이터 전달</li>
    <li>패킷 이라는 통신 단위로 데이터 전달</li>
</ul>
패킷을 전달하는 과정(ip 패킷) 
<ol>
    <li>출발지 ip와 목적지 ip 패킷에 입력</li>
    <li>패당 패킷에 전달할 데이터 입력</li>
    <li>ip protocol에 의해서 데이터 전달</li>
        <ul>
            <li>이때 노드간의 데이터 전송이 지속적으로 이루어 진다</li>
            <li>request할때의 데이터를 전송받는 노드들과 response할때 데이터를 전달받는 노드들은 다를 수 있다.</li>
        </ul>
</ol>
<img src="img/client_sending_packet.png" alt="">
<b>ip protocol의 문제점</b>
<ul>
    <li>비 연결성 : 패킷을 받을 대상이 없어도 데이터는 전송한다.</li>
    <li>비 신뢰성 : 패킷이 중간에 사라질 가능성, 패킷이 보내진 순서대로 오지 않을 가능성이 있다.</li>
    <li>프로그램 구분 : 같은 ip를 사용하는 서버에서 통신하는 애플리케이션이 둘 이상이면?
    ->음악을 들으며 게임을 하는 상황에 어떤 패킷이 음악에 필요한 패킷인지</li>
</ul>

### TCP,UDP

#### 프로토콜 계층

<img src="img/Protocol.png">

#### TCP/IP 패킷 정보

<img src="img/TCP_IP_Packet.png">

#### TCP 특징

<ul>
    <li>
        <div>
            연결지향 : 3 way hand handshake
            <img src="img/3WayHandShake.png">
        </div>
    </li>
    <li>
        <div>
            데이터 전달 보증
            <img src="img/DataDeliveryGuarantee.png">
        </div>
    </li>
    <li>
        <div>
            순서 보장
            <img src="img/OrderGuarantee.png">
        </div>
    </li>
<li>신뢰할 수 있음</li>
<li>조금은 느림</li>
</ul>

#### UDP

<ul>
    <li>3 way handshake를 하지 않기 때문에 빠르지만 연결지향적이지 않다.</li>
    <li>IP와 거의 같다 + port + 체크섬</li>
    <li>애플리케이션에서 추가 작업 필요</li>
</ul>

### Port

<pre>
한번에 둘 이상 연결하려면 어떻게 해야 할까?
하나의 클라이언트가 두개의 서버에 연결한다면 서버는 하나의 IP에 데이터를 보내기만 하면된다.
하지만 클라이언트는 어떤 데이터가 어떤 어플리케이션에 사용하는 데이터인지 구분해야 한다.
이때 이용하는 것이 port를 사용하는 것이다.
</pre>
<img src="img/port.png">

### DNS

<pre>
naver에 접근하려면 naver의 ip를 알아야 한다.
근데 매번 ip를 찾기도 힘들고 외우기도 힘들다.
DNS는 naver.com과 naver의 서버 IP를 mapping 되어 있는 시스템으로서,
우리가 naver에 접근할때 ip를 찾아주는 시스템이다.</pre>
<img src="img/DNS.png">

## URI와 웹 브라우저 요청 흐름

### URI

<img src="img/URI_URL_URN.png">
<ul>
    <li>URI : Resource Identifier : 자원의 위치 + 자원의 이름</li>
    <li>URL : Resource Locator : 자원의 위치를 지정 </li>
    <li>URN : Resource Name : 자원의 이름를 부여 </li>
    <ul>
        <li>URN 이름만으로 실제 리소스를 찾을 수 있는 방법이 보편화 되지 않음. </li>
    </ul>
</ul>

#### URL 전체 문법

<pre>
scheme://[userinfo@]host[:port][/path][?query][#fragment]
https://www.google.com:443/search?q=hello&hl=ko
</pre>

<ul>
    <li>scheme</li>
        <ul>
            <li>주로 프로토콜을 사용</li>
        </ul>
    <li>userinfo@</li>
        <ul>
            <li>URL에 사용자 정보를 포함해서 인증</li>
            <li>거의 사용하지 않음</li>
        </ul>
    <li>Host</li>
        <ul>
            <li>호스트명</li>
            <li>도메인명 또는 IP 주소를 직접 사용가능</li>
        </ul>
    <li>Port</li>
        <ul>
            <li>접속 포트</li>
        </ul>
    <li>Path</li>
        <ul>
            <li>리소스 경로, 계층적 구조로 이뤄져 있음</li>
        </ul>
    <li>쿼리스트링</li>
        <ul>
            <li>key value 의 형태로 데이터를 전송</li>
        </ul>
    <li>fragment</li>
        <ul>
            <li>html의 내부 북마크 같은 곳에 사용함</li>
            <li>서버에 데이터를 전송하지는 않음</li>
        </ul>
</ul>

### 브라우저 요청 흐름
<ol>
    <li>DNS를 이용해서 IP,Port 찾기</li>
    <li>http 요청 메시지 생성</li>
    <li>socket 라이브러리로 3way handshake 진행</li>
    <li>TCP/IP계층에서 패킷으로 인코딩</li>
    <li>서버로 데이터 전송</li>
    <li>도착한 요청 패킷 디코딩</li>
    <li>요청한 데이터를 찾아서 http 응답 메시지 생성</li>
    <li>패킷으로 인코딩</li>
    <li>클라이언트에게 전송</li>
</ol>

## http의 기본
### 클라이언트 서버 구조 특징
<ul>
    <li>* 각각 독립적으로 구조화 되어 있기 때문에 클라이언트, 서버 내부적으로 변경에 용이함</li>
    <li>Request Response 구조</li>
    <li>클라이언트는 서버에 요청을 보내고, 응답을 대기</li>
    <li>서버가 요청애 대한 결과를 만들어서 응답</li>
</ul>

### stateful , stateless
<ul>
    <li>stateful : 서버가 클라이언트의 상태를 보존하는 것</li>    
    <li>stateless : 서버가 클라이언트의 상태를 보존하지 않는것</li>    
        <ul>
            <li>무상태는 응답 서버를 쉽게 바꿀수 있다 == 무한한 서버 증설이 가능하다 </li>
            <li>서버가 분리 되어 있는 경우 서버 1이 장애가 나는 경우 다른 서버로 클라이언트의 요청을 전달 할 수 있다.</li>
        </ul>
</ul>

#### 실무의 한계
<ul>
    <li>모든것을 무상태로 설계할 수 있는 경우도 있고 아닌경우도 있다.</li>
        <ul>
            <li>무상태 : 정적페이지, 로그인이 필요없는 단순한 서비스 소개 화면</li>
            <li>상태 유지 : 로그인 이후 화면 </li>
        </ul>
    <li>로그인 한 사용자의 경우 로그인 정보를 서버에 유지</li>
    <li>상태 유지는 일반적으로 쿠키와 세션을 사용해서 상태유지한다</li>
    <li>상태 유지 최소한만 사용</li>
</ul>

### 비연결성
<img src="img/persistent_connections.png">
<pre>
연결을 유지하게 되면 하나의 서버가 많은 클라이언트와 연결되어 있음으로 서버에 부하가 걸린다.
이를 방지하기 위해서 http는 연결을 유지하지 않는다.
이는 양날의 검으로 작용하는데 장점으로는 서버의 자원을 효율적으로 사용할 수 있지만,
서버와 다시 연결한다고 하면 3 way handshake를 다시 진행해야 하며, 추가적으로 많은 파일을 다시 받아야 한다.
이를 해결하기 위해서 persistent connections로 문제를 해결한다.
</pre>

### HTTP Message
#### HTTP 메시지 구조
<img src="img/http_message.png">
<ul>
    <li>request</li>
        <ul>
            start-line
            <li>http method : 서버가 수행해야 할 동작 지정<BR>GET, POST, PUT, DELETE</li>
            header            
            <li>요청 대상 : 절대경로= "/"로 시작하는 경로</li>
            <li>HTTP VERSION</li>
        </ul>
    <li>response</li>
        <ul>
            start-line
            <li>http 버전</li>
            <li>http 상태코드 : 성공여부 <br> 200, 400, 500</li>
            header
            <li>인코딩 방식</li>
            <li>필요시 임의의 header 추가 가능</li>
            body
            <li>실제 전송할 테이더 </li>
            <li>html, json, 영상 ,image등등 전부 가능</li>
        </ul>
</ul>

## HTTP Method
<ul>
    <li>GET : 리소스 조회</li>
    <li>POST : 요청 데이터 처리, 등록</li>
    <li>PUT : 리소스를 대체, 리소스가 없으면 생성</li>
    <li>PATCH : 리소스를 부분 변경</li>
    <li>DELETE : 삭제</li>
</ul>

### HTTP API를 만들어 보자.
#### 리소스와 행위를 분리
<ul>
    <li>URI는 리소스만 식별</li>
    <li>리소스와 해당 리로스를 대상으로 하는 행위를 분리</li>
    <ul>
        <li> 리소스 : 회원</li>
        <li> 행위 : 등록,수정,삭제,조회</li>
    </ul>
</ul>

### GET,POST
#### GET
<ul>
    <li>리소스 조회</li>
    <li>서버에 전달하고 싶은 데이터를 query string을 통해서 전달</li>
    <li>메시지 바디를 사용해서 전달할 수 있지만 지원하지 않는 곳이 많아서 권장하지 않는다.</li>
</ul>

##### GET 과정
<ol>
    <li><img src="img/GET_procedure_1.png"></li>
    <li><img src="img/GET_procedure_2.png"></li>
    <li><img src="img/GET_procedure_3.png"></li>
</ol>

#### POST

<ul>
    <li>요청 데이터 처리</li>
    <li>메시지 바디를 통해 서버로 요청 데이터 전달</li>
    <li>서버는 요청 데이터를 처리</li>
    <ul>
        <li>메시지 바디를 통해 들어온 데이터를 처리하는 모든 기능을 수행</li>
    </ul>
    <li>주로 전달된 데이터로 신규 리소스 등록, 프로세스 처리에 사용</li>
</ul>

##### POST의 과정
<ol>
    <li><img src="img/POST_procedure_1.png"></li>
    <li><img src="img/POST_procedure_2.png"></li>
    <li><img src="img/POST_procedure_3.png"></li>
</ol>

##### POST의 역활
<ul>
    <li>새 리소스 등록</li>
    <li>요청 데이터 처리</li>
    <ul>
        <li>프로세스 로직의 동작</li>
        <li>결과로 데이터가 생성되지 않는 경우가 있을 수 있다</li>
    </ul>
    <li>다른 메서드로 처리하기 어려운 경우</li>
    <ul>
        <li>JSON으로 조회 해야 하는데 GET을 사용하기 어려울 때</li>
        <li>애매하면 POST</li>
    </ul>
</ul>

#### PUT
<ul>
    <li>리소스를 대체, 덮어 씌워짐. 즉, 기존 리소스를 삭제하고 새로운 리소스를 넣는다.</li>
    <ul>
        <li>리소스가 있으면 대체</li>
        <li>리소스가 없으면 생성</li>
    </ul>
    <li>클라이언트가 리소스의 위치를 알고 지정</li>
</ul>

##### PUT의 역활
###### 리소스가 있는 경우
<ol>
    <li><img src="img/PUT_procedure_1.png"></li>
    <li><img src="img/PUT_procedure_2.png"></li>
</ol>

###### 리소스가 없는 경우
<ol>
    <li><img src="img/PUT_procedure_3.png"></li>
    <li><img src="img/PUT_procedure_4.png"></li>
</ol>

###### 리소스가 있는데 일부 데이터만 보내는 경우
<ol>
    <li><img src="img/PUT_procedure_5.png"></li>
    <li><img src="img/PUT_procedure_6.png"></li>
</ol>

#### PATCH
<ul>
    <li>리소스를 부분 변경</li>
</ul>

#### DELETE
<ul>
    <li>리소스를 삭제</li>
</ul>

### HTTP 메서드의 속성
<img src="img/http_idempotent.png">

#### 안전
<ul>
    <li>호출해도 리소스를 변경하지 않는다.</li>
</ul>

#### 멱등
<ul>
    <li>여러번 호출하든 결과는 같아야 한다.</li>
    <li>멱등 메서드</li>
    <ul>
        <li>GET : 여러번 조회해도 같은 결과가 조회된다.</li>
        <li>PUT : 결과를 대체한다.따라서 같은 요청을 여러번 해도 최종 결과는 같다.</li>
        <li>DELETE : 결과를 삭제한다. 따라서 같은 요청을 여러번 하면 삭제된 결과로 같다.</li>
        <li style="color: red">POST : 두번 호출하면 같은 결제가 중복해서 발생할 수 있다. 즉, 멱등하지 않다. </li>
    </ul>
</ul>

##### 멱등이 필요한 이유
<ul>
    <li>자동 복구 메커니즘</li>
    <li>서버가 정상적으로 동작하지 않았을때, 동일한 요청을 보내도 되는가?</li>
</ul>

#### 캐시가능
<ul>
    <li>응답 결과 리소스를 캐시해서 사용해도 되는가?</li>
    <li>GET,POST,HEAD,PATCH 캐시가능</li>
    <li>실제로는 GET,HEAD만 캐시로 사용한다</li>
</ul>

## HTTP 매서드의 활용
### 클라이언트에서 서버로 데이터 전송
<ol>
    <li>정적 데이터를 조회 : ex) /static/star.jpg</li>
    <li>동적 데이터를 조회 : ex) /search?id=100&p=3&lang=ko</li>
    <li>HTML Form 데이터 전송 : <br></li>
<ul>
    <li>application/x-www-form-urlencoded : form data 한번에 전송.<br><img src="img/POST_save.png"></li>
    <li>Multipart/form-date : 파일과 같은 바이너리 데이터를 전송시 사용</li>
</ul>
</ol>

### HTTP API 설계 예시

[//]: # (#### 회원관리 API 설계)

[//]: # (<ul>)

[//]: # (    <li>회원 목록 : /members -> GET</li>)

[//]: # (    <li>회원 등록 : /members -> POST</li>)

[//]: # (    <li>회원 조회 : /members/{id} -> GET</li>)

[//]: # (    <li>회원 수정 : /members/{id} -> PATCH,PUT,POST</li>)

[//]: # (    <li>회원 삭제 : /members/{id} -> DELETE</li>)

[//]: # (</ul>)

#### POST - 신규 자원 등록의 특징
<ul>
    <li>클라이언트는 등록될 리소스의 URI를 모른다</li>
    <li>새로운 리소스가 등록되면 URI를 생성해준다.</li>
    <li>location을 통해 새로 등록된 URI를 확인할 수 있다.<br><img src="img/POST_procedure_3.png"></li>
    <li>컬렉션</li>
    <ul>
        <li>서버가 관리하는 리소스 디렉토리</li>
        <li>서버가 리소스의 URI를 생성하고 관리</li>
        <li>컬렉션은 /members가 된단</li>
    </ul>
</ul>

#### PUT - 신규 자원 등록의 특징
<ul>
    <li>클라이언트가 리소스의 URI를 알고 있어야 한다.</li>
    <ul>
        <li>파일 등록 /files/{filename} -> put</li>
        <li>PUT /files/stars.jpg </li>
    </ul>
    <li>클라이언트가 직접 리소스의 URI를 지정한다.</li>
    <li>스토어</li>
    <ul>
        <li>클라이언트가 관리하는 리소스 저장소</li>
        <li>클라이언트가 리소스의 URI를 알고 관리</li>
        <li>스토어는 /files가 된다</li>
    </ul>
</ul>

#### HTML FORM 사용
<ul>
    <li>HTML FORM은 GET과 POST만 사용할 수 있다.</li>
    <li>따라서 DELETE를 할때에는 controll URI를 사용해야 한다.</li>
    <ul>
        <li>/new,/delete,/edit</li>
    </ul>
</ul>

## HTTP 상태코드
### 상태코드
<ul>
    <li>1xx(Informational) : 요청이 수신되어 처리중</li>
    <li>2xx(Successful) : 요청 정상 처리</li>
    <li>3xx(Redirection) : 요청을 완료하려면 추가적인 행동이 필요함</li>
    <li>4xx(Client Error) : 클라이언트 오류, 잘못된 방법으로 서버가 요청을 처리할 수 없음</li>
    <li>5xx(Server Error) : 서버 오류, 서버가 정상적으로 요청을 처리 하시 못함</li>
</ul>

#### 2xx - success
<ul>
    <li>200 OK</li>
        <ul>
            <li><img src="img/200_ok.png"></li>
        </ul>
    <li>201 Created</li>
        <ul>
            <li><img src="img/201_Created.png"></li>
        </ul>
    <li>202 Accepted</li>
        <ul>
            <li>요청이 접수되었으나 처리가 완료되지 않았음.</li>
            <li>배치 처리 같은 곳에소 사용.</li>
            <li>에) 요청 접수 후 1시간 뒤에 배치 프로세스가 요청을 처리함.</li>
        </ul>
    <li>204 No Content</li>
        <ul>
            <li>서버가 성공적으로 동작했지만 응답 페이로드 본문에 보낼 데이터가 없음.</li>
            <li>save 버튼의 결과로 아무 내용도 없어도 될때.</li>
            <li>save 버튼을 눌러도 같은 화면을 유지해야 한다.</li>
        </ul>
</ul>

#### 3xx - redirection
300번 응답에 location이 있으면 자동으로 redirect 된다
<li><img src="img/301_Redirection.png"></li>
<ul>
    <li>영구 리다이렉션: 특정 리소스의 URI가 영구적으로 이동 -> 301, 308</li>
    <li>일시 리다이렉션: 일시적인 변경 -> 302, 307, 303</li>
    <li>특수 리다이렉션: 결과 대신 캐시를 사용</li>
</ul>
<ul>
    <li>300 Multiple Choices</li>
    <li>301 Moved Permanently</li>
    <ul>
        <li><img src="img/302_PRG_Parrent.png "></li>
        <li>본문이 제거 될 수 있음 == 뒤로가기를 하면 redirect되기 때문에 get을 계속해서 불러옴</li>    
    </ul>    
    <li>302 Found</li>
    <ul>
        <li>리다이렉트시 요청 메서드가 GET으로 변하고, 본문이 제거될 수 있음</li>
    </ul>
    <li>303 See Other</li>
    <ul>
        <li>302와 기능은 같음</li>
        <li>리다이렉트시 요청 메서드와 본문유지(요청 메서드는 절대 변경하면 안된다.)</li>
    </ul>
    <li>304 Not Modified</li>
    <ul>
        <li>캐시를 목적으로 사용</li>
        <li>클라이언트에게 리소스가 수정되지 않았음을 알려준다.따라서 클라이언트는 로컬 PC에 저장된 캐시를 재사용한다.</li>
        <li>304 응답은 응답에 메시지 바디를 포함하면 안된다.(로컬 캐시 하용하니까)</li>
        <li>조건부 GET,HEAD 요청시 사용</li>
    </ul>
    <li>307 Temporary Redirect</li>
    <ul>
        <li>302와 기능은 같음</li>
        <li>리다이렉트시 요청 메서드가 GET으로 변경</li>
    </ul>
    <li>308 Permanent Redirect</li>
    <ul>
        <li><img src="img/308_.png "></li>
        <li>본문이 제거되지 않음 == 새로고침을 하면 같은 본문이 중복적으로 날라감</li>
    </ul>       
</ul>

##### PRG pattern
<img src="img/PRG_Pattern.png">
<ul>
    <li>POST로 주문후에 새로 고침으로 인한 중복 주문 방지</li>
    <li>POST로 주문후에 주문 결과를 GET으로 메서드로 리다이렉트</li>
    <li>새로고침해도 결과 화면을 GET으로 조회</li>
    <li>중복 주문 대신에 결과 화면만 GET으로 다시 요청</li>
</ul>

### 4xx - 클라이언트 오류, 5xx - 서버 오류
#### 4xx
<ul>
    <li>클라이언트의 요청에 잘못된 문법등으로 서버가 요청을 수행할 수 없음</li>
    <li>오류의 원인이 클라이언트에 있음</li>
    <li>클라이언트가 이미 잘못된 요청, 데이터를 보내고 있기 때문에, 똑같은 재시도가 실패함</li>
</ul>
<ul>
    <li>400 Bad Request</li>
    <ul>
        <li>요청구문, 메시지 등등 오류</li>
        <li>클라이언트는 요청을 내용을 다시 검토하고 보낸다</li>
    </ul>
    <li>401 Unauthorized</li>
    <ul>
        <li>인증되지 않음</li>
        <li>401 오류 발생시 응답에 WWW-Authenticate 헤더와 함께 인증 방법을 설명</li>
    </ul>
    <li>403 Forbidden</li>
    <ul>
        <li>서버가 요청을 이해했지만 승인 거부</li>
        <li>주로 인증은 되지만 인가가 안될때 사용</li>
    </ul>
    <li>404 Not Found</li>
    <ul>
        <li>요청 리소스를 찾을 수 없음</li>
        <li>클라이언트가 권한이 부족한 리소스에 접근했을때 403 대신 사용</li>
    </ul>
</ul>

#### 5xx
<ul>
    <li>서버 문제로 오류 발생</li>
    <li>재시도 하면 성공할 수 있음</li>
</ul>
<ul>
    <li>500 Internal Server Error</li>
    <ul>
        <li>서버 문제로 오류 발생</li>
    </ul>    
    <li>503 Service Unavailable</li>
    <ul>
        <li>서비스 이용 불가</li>
        <li>서버가 일시적인 과부하 및 예정된 작업으로 잠시 요청을 처리할 수 없음</li>
    </ul>
</ul>


