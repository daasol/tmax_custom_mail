# tmax_custom_mail

## custom mail jar 파일 생성
1. Project > Export > Java.Runnable JAR file 생성

## jar 파일 실행 
1. hypercloud 에서 임의 pod 띄운 후, 해당 jar 파일 실행 
    jar 실행을 위해서 jdk 설치 필수 
2. jar 실행을 위한 명령어 
    java -jar {jar_file}.jar
    
## 동작 
1. Config.TITLE, Config.BODY 에 명시된 제목과 바디로 메일 본문 생성
2. Config.HOST:Config.PORT 메일 서버에 전송 (Config.SENDERID, Config.SENDERPW 로 접속)
3. Config.RECEIVER에 메일 발송

## 정상동작 확인
1. logger >> Sender : no_reply.wapl@tmax.co.kr
2. Message sent. 로그 확인 


### 주의사항
mail 서버에 콜을 날릴 뿐, 응답을 받지 않기 때문에 전송 완료를 보장하지 않음


## Project 하위 디렉토리
src 

 └─config
 
    └─Config.java        // 메일 제목/바디, 메일 sender/receiver, 메일 서버 
   
 └─mail
 
    └─CustomMail.java    // Sender() 
   
 └─main
 
    └─main.java.         // main 
   
 └─lib 
 
    └─mail-1.4.7.jar.    // mail 전송을 위한 라이브러리 
   
 
