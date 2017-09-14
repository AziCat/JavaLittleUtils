/*
 * Copyright 2012-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.sinobest.websocket.base;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import cn.sinobest.websocket.handler.SimpleClientWebSocketHandler;

/**
 * 消息中心启动类
 * @author yjh
 * @date 2017.09.07
 */
@Configuration
@EnableAutoConfiguration
@EnableWebSocket
@ComponentScan(basePackages={"cn.sinobest"})
public class SampleWebSocketApplication extends SpringBootServletInitializer
		implements WebSocketConfigurer {
	private Log logger = LogFactory.getLog(SampleWebSocketApplication.class);
	/**
	 * WebSocket注册方法
	 * @param registry WebSocketHandlerRegistry
	 */
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(simpleClientWebSocketHandler(), "/connect")
				.addInterceptors(new HandshakeInterceptor())
				.setAllowedOrigins("*")//跨域处理
				.withSockJS();
		logger.info("/connect注册成功");
	}

	@Bean
	WebSocketHandler simpleClientWebSocketHandler() {
		return new SimpleClientWebSocketHandler();
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SampleWebSocketApplication.class);
	}

	public static void main(String[] args) {
		ApplicationContext app = SpringApplication.run(SampleWebSocketApplication.class, args);
		//设置上下文对象
		SpringContextUtil.setApplicationContext(app);
	}

}
