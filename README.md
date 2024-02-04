# SpringBootClasses

##프로젝트 환경설정
<hr>
### spring boot documentation
<a href="srping.io">srping.io</a> < spring boot < learn 에서 documenttation을 확인할 수 있다.
<br>여기서 spring이 index를 찾는 과정을 볼 수 있다.

### spring running 
<img src="C:\Users\adminmaster\IdeaProjects\SpringBootClasses\src\main\resources\img\spring.png"><br>
<ul>
<li>컨트롤러에서 return을 string으로 하면 view reslover가 화면을 찾아서 처리한다.</li>
<li>resource:templates/+{return 받은 view name}+.html</li>
</ul>

### spring run
<ol>
<li>./geadlew build</li>
<li>java -jar [build file]</li>
</ol>

### spring clean
<ol>
<li>./geadlew clean</li>
</ol>

## 스프링 웹 개발 기초
<hr>

### static page running 
<img src="C:\Users\adminmaster\IdeaProjects\SpringBootClasses\src\main\resources\img\static_page_running.png"><br>

## MVC pattern and template engine
<img src="C:\Users\adminmaster\IdeaProjects\SpringBootClasses\src\main\resources\img\mvc_pattern.png"><br>
<ol>
    <li>view는 보여주는 것에 집중해야 한다.</li>
</ol>

## API
<img src="C:\Users\adminmaster\IdeaProjects\SpringBootClasses\src\main\resources\img\API.png"><br>
<ol>
    <li>response body가 있으면 http Message Converter가 동작한다</li>
    <li>여기서 만약 return이 string이면 string Converter가 동작하고 객체면 json converter가 동작한다.</li>
</ol>

