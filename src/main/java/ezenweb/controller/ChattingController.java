package ezenweb.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

@Component // 스프링 컨테이너에 빈 등록
public class ChattingController extends TextWebSocketHandler {
    private static List<WebSocketSession> 접속명단 = new ArrayList<>();
    // 1. 클라이언트소켓과 연동 성공했을때. 이후 행동/메소드 정의
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        접속명단.add( session ); // 클라이언트 세션이 들어왔을때 리스트에 저장 [ 왜???다른 세션과 통신하기 위해 ]
    }
    // 2. 클라이언소켓과 세션 오류가 발생했을때 이후 행동/메소드 정의
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.out.println("session = " + session + ", exception = " + exception);

    }
    // 3. 클라언트소켓과 연동이 끊겼을때. 이후 행동/메소드 정의
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        접속명단.remove( session ); // 클라이언트 세션이 나갔을때 리스트에 제거
    }
    // 4. 클라이언트소켓으로부터 메시지를 받았을때. 이후 행동/메소드 정의
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        for( WebSocketSession 세션 : 접속명단 ){ 세션.sendMessage( message );  }
    }
}