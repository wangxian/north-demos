package org.example.websocket;

import javax.websocket.*;
import javax.websocket.server.ServerContainer;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint("/websocket/echo")
public class EchoAnnotation {
    @OnOpen
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        System.out.println("已连接，客户端ID：" + session.getId());
    }

    @OnMessage
    public void echoTextMessage(Session session, String msg, boolean last) {
        System.out.println("收到消息：" + msg);
        try {
            if (session.isOpen()) {
                session.getBasicRemote().sendText( "[server]" + msg, last);
            }
        } catch (IOException e) {
            try {
                session.close();
            } catch (IOException e1) {
                // Ignore
            }
        }
    }

    /**
     * Process a received pong. This is a NO-OP.
     *
     * @param pm Ignored.
     */
    @OnMessage
    public void echoPongMessage(PongMessage pm) {
        // NO-OP
    }
}
