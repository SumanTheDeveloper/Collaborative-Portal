package com.niit.Configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer 
{

	public void registerStompEndpoints(StompEndpointRegistry registry)
	{
		registry.addEndpoint("/chatmodule").withSockJS();
	}

	public void configureClientInboundChannel(ChannelRegistration registration)
	{

	}

	public void configureClientOutboundChannel(ChannelRegistration registration)
	{

	}

	public void configureMessageBroker(MessageBrokerRegistry registry) 
	{
		registry.setApplicationDestinationPrefixes("/app");
		registry.enableSimpleBroker("/topic/","/queue/");
	}

}
