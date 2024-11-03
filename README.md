

# 중앙관리 서버
클라이언트로 부터 들어온 요청을 여러 분산되어있는 마이크로서비스에 요청하기위해 중간에서 라우팅경로를 설정 해줍니다.

# 필수 의존성
빌드 도구 : Gradle
<br>
dependencies {
	implementation 'org.springframework.cloud:spring-cloud-starter-gateway'
 }

# 사용 이유
흩어져 있는 마이크로 서비스 서버의 요청 URL을 스프링 클라우드 게이트웨이는 MSA 가장 앞단에서 
<br>
클라이언트들로 부터 오는 요청을 받은후 경로와 조건에 알맞은 마이크로서비스 로직에 요청을 전달하기 위해서 입니다.
<br>
스프링 클라우드 게이트웨이는 비동기 방식이아닌 논블로킹 방식이다.


# 시스템 아키텍처
![화면 캡처 2024-11-03 174121](https://github.com/user-attachments/assets/803d0c49-0b52-4082-a861-0433e0962213)
