package demo.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
@Configuration
@EnableWebMvc
@EnableWebSocket
public class WebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {
	/**
	 * 注册websocket
	 */
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(systemWebSocketHandler(), "/ws").addInterceptors(new HandshakeInterceptor());  
		  
        registry.addHandler(systemWebSocketHandler(), "/sockjs/ws").addInterceptors(new HandshakeInterceptor())  
                .withSockJS();  
        System.out.println("websocket注册成功");
	}
	@Bean  
    public WebSocketHandler systemWebSocketHandler() {  
        //return new InfoSocketEndPoint();  
        return new SystemWebSocketHandler();  
    } 

}
