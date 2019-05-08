package com.yuan.house.config.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;
import org.springframework.web.socket.handler.WebSocketHandlerDecoratorFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    /**
     * 在线用户列表
     */
    private static Map<String, String> users = new ConcurrentHashMap<>();

    @Autowired
    private SimpMessagingTemplate template;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setUserDestinationPrefix("/user");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/house/chat").withSockJS();
    }

    @Override
    public void configureWebSocketTransport(final WebSocketTransportRegistration registration) {
        registration.addDecoratorFactory(new WebSocketHandlerDecoratorFactory() {
            @Override
            public WebSocketHandler decorate(final WebSocketHandler handler) {
                return new WebSocketHandlerDecorator(handler) {
                    @Override
                    public void afterConnectionEstablished(final WebSocketSession session) throws Exception {
                        if (!users.containsKey(session.getPrincipal().getName())) {
                            users.put(session.getPrincipal().getName(), session.getId());
                            //template.convertAndSend("/topic/userlist", JSON.toJSON(user));
                            System.out.println("连接成功：" + session.getPrincipal().getName());
                            super.afterConnectionEstablished(session);
                        }
                    }

                    @Override
                    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus)
                            throws Exception {
                        if (users.containsKey(session.getPrincipal().getName())) {
                            users.remove(session.getPrincipal().getName());
                            //template.convertAndSend("/topic/userlist", JSON.toJSON(user));
                            System.out.println("断开连接: " + session.getPrincipal().getName());
                            super.afterConnectionClosed(session, closeStatus);
                        }
                    }
                };
            }
        });
        super.configureWebSocketTransport(registration);
    }

    public static Map<String, String> getUsers() {
        return users;
    }
}
