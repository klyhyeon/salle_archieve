package com.example.demo.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebsocketBrokerConfig implements WebSocketMessageBrokerConfigurer {

	private static final Logger LOGGER = LoggerFactory.getLogger(WebsocketBrokerConfig.class);

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		
		//for subscribe prefix
		registry.enableSimpleBroker("/topic");
		//for publish prefix
		registry.setApplicationDestinationPrefixes("/app");
		//user destination provides ability to have unique user queue
		registry.setUserDestinationPrefix("/user");
		
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		
		registry.addEndpoint("/broadcast")
			.withSockJS()
			.setHeartbeatTime(60_000);
//
//		//when browser or network cause problems fallback allows realtime connectivity 
//		registry.addEndpoint("/broadcast")
//				.withSockJS()
//				.setHeartbeatTime(60_000);
		
		//send message to single user
		registry.addEndpoint("/chat")
				.withSockJS();
				
	}

	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		
		registration.interceptors(new UserInterceptor());
	}
	
	

//	@Override
//	public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
//		
//		DefaultContentTypeResolver resolver = new DefaultContentTypeResolver();
//		resolver.setDefaultMimeType(MimeTypeUtils.APPLICATION_JSON);
//		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
//		converter.setObjectMapper(new ObjectMapper());
//		converter.setContentTypeResolver(resolver);
//		messageConverters.add(converter);
//		
//		return false;
//	}


	
	
	
	

	
	

}
