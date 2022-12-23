package org.example.config;

import org.apache.catalina.Context;
import org.apache.catalina.core.StandardContext;
import org.apache.tomcat.websocket.server.WsContextListener;
import org.example.websocket.EchoAnnotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.xiqiu.north.North;
import top.xiqiu.north.annotation.Component;

import javax.annotation.PostConstruct;
import javax.websocket.DeploymentException;
import javax.websocket.server.ServerContainer;
import javax.websocket.server.ServerEndpointConfig;

import static org.apache.tomcat.websocket.server.Constants.SERVER_CONTAINER_SERVLET_CONTEXT_ATTRIBUTE;

@Component
public class WebConfig {
    /**
     * logger
     **/
    private final Logger logger = LoggerFactory.getLogger(WebConfig.class);

    // @PostConstruct
    public void initWebSocket() {
        final StandardContext context = North.getContext();

        ServerContainer serverContainer = (ServerContainer) context.getServletContext().getAttribute(SERVER_CONTAINER_SERVLET_CONTEXT_ATTRIBUTE);
        ServerEndpointConfig serverEndpointConfig = ServerEndpointConfig.Builder.create(EchoAnnotation.class, "/websocket/echo").build();
        try {
            serverContainer.addEndpoint(serverEndpointConfig);
        } catch (DeploymentException e) {
            e.printStackTrace();
        }

        // WsServerContainer sc = new WsServerContainer(context.getServletContext());
        // new WsServerContainer(context.getServletContext());
        // WsWebSocketContainer sc = new WsWebSocketContainer();
        // WsServerContainer sc = new WsServerContainer(context.getServletContext());

    }
}
