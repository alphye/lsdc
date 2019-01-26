package com.lishengzn.lsdc.kernel.as.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

/**
 * web socket配置
 * Created by earl on 2017/4/11.
 */
@Configuration
//开启对websocket的支持,使用stomp协议传输代理消息，
// 这时控制器使用@MessageMapping和@RequestMaping一样
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    /*config.enableSimpleBroker("/topic","/user");这句表示在topic和user这两个域上可以向客户端发消息；
    config.setUserDestinationPrefix("/user/");这句表示给指定用户发送（一对一）的主题前缀是“/user/”;
    config.setApplicationDestinationPrefixes("/app"); 这句表示客户端向服务端发送时的主题上面需要加"/app"作为前缀；
    registry.addEndpoint("/webServer").withSockJS();这个和客户端创建连接时的url有关，后面在客户端的代码中可以看到。
    */

    /**
     * 服务器要监听的端口，message会从这里进来，要对这里加一个Handler
     * 这样在网页中就可以通过websocket连接上服务了
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        //注册stomp的节点，映射到指定的url,并指定使用sockjs协议
        stompEndpointRegistry.addEndpoint("/contactSocket").withSockJS().setInterceptors(httpSessionIdHandshakeInterceptor());
    }

    //配置消息代理
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        ThreadPoolTaskScheduler te = new ThreadPoolTaskScheduler();
        te.initialize();
        // queue、topic、user代理
        registry.enableSimpleBroker("/queue", "/topic", "/rtopic","/stopic","/user")
                .setHeartbeatValue(new long[]{5000,5000}).setTaskScheduler(te);
        registry.setUserDestinationPrefix("/user/");
    }

    /**
     * 消息传输参数配置
     */
    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
        registry.setMessageSizeLimit(10240) //设置消息字节数大小
                .setSendBufferSizeLimit(10240)//设置消息缓存大小
                .setSendTimeLimit(10000); //设置消息发送时间限制毫秒
    }


    /**
     * 输入通道参数设置
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.taskExecutor().corePoolSize(4) //设置消息输入通道的线程池线程数
                .maxPoolSize(8)//最大线程数
                .keepAliveSeconds(60);//线程活动时间
        registration.setInterceptors(presenceChannelInterceptor());
    }

    /**
     * 输出通道参数设置
     */
    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        registration.taskExecutor().corePoolSize(4).maxPoolSize(8);
        registration.setInterceptors(presenceChannelInterceptor());
    }

    @Bean
    public HttpSessionIdHandshakeInterceptor httpSessionIdHandshakeInterceptor() {
        return new HttpSessionIdHandshakeInterceptor();
    }

    @Bean
    public PresenceChannelInterceptor presenceChannelInterceptor() {
        return new PresenceChannelInterceptor();
    }


}

