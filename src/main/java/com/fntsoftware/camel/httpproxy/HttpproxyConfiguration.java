package com.fntsoftware.camel.httpproxy;

import org.apache.camel.CamelContext;
import org.apache.camel.component.http.CompositeHttpConfigurer;
import org.apache.camel.component.http.HttpClientConfigurer;
import org.apache.camel.component.http.HttpComponent;
import org.apache.camel.component.http.HttpEndpoint;
import org.apache.camel.component.http.ProxyHttpClientConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpproxyConfiguration {

	@Bean(name = "http-proxy")
	public HttpComponent httpProxy() {
		return new HttpComponent();
	}

	@Bean
	@Autowired
	public HttpEndpoint httpProxyOutboundPropertiesEndpoint(
			CamelContext camelContext) {

		HttpEndpoint httpEndpoint = camelContext.getEndpoint(
			"http-proxy:outbound/properties",
			HttpEndpoint.class);

		// Configure the proxy on the endpoint with properties
		httpEndpoint.setProxyHost("proxyHost");
		httpEndpoint.setProxyPort(8123);

		return httpEndpoint;
	}

	@Bean
	@Autowired
	public HttpEndpoint httpProxyOutboundConfigurerEndpoint(
			CamelContext camelContext) {

		HttpEndpoint httpEndpoint = camelContext.getEndpoint(
			"http-proxy:outbound/configurer",
			HttpEndpoint.class);

		// Configure the proxy on the endpoint with a configurer
		HttpClientConfigurer configurer = CompositeHttpConfigurer.combineConfigurers(
			httpEndpoint.getHttpClientConfigurer(),
			new ProxyHttpClientConfigurer(
					"proxyHost",
					8123,
					"http"));
		httpEndpoint.setHttpClientConfigurer(configurer);

		return httpEndpoint;
	}

}
