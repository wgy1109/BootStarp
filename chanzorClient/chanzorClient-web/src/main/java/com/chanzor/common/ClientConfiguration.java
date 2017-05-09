package com.chanzor.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.chanzor.sms.common.utils.SequenceGenerator;

@Configuration
public class ClientConfiguration {
	
	@Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory clientHttpRequestFactory) {
        return new RestTemplate(clientHttpRequestFactory);
    }

    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setReadTimeout(3600000);
        factory.setConnectTimeout(3600000);
        return factory;
    }

	@Bean(name = "voiceIdGenerator")
	public SequenceGenerator moIdGenerator() {
		return new SequenceGenerator(2);
	}
}
