
1. [2025-02-17] 채팅 기능 구현

```
1) WebSocketConfig에 CustomWebSocketHandler랑 path(예시: /chat)를 입력합니다. 
2) 이제 Client는 해당 path로 WebSocket에 연결할 수 있습니다. (예시: http://localhost:9001/chat)
3) CustomWebSocketHandler에서는 Client가 보내는 메시지를 처리하고, 응답을 전송하며, WebSocket 연결의 상태를 관리합니다.
4) 2025년 02월 17일 현재, Client가 보내는 메시지는 Server에 등록된 모든 세션에 브로드캐스팅 됩니다.  
```

