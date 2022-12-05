package com.fntsoftware.camel.httpproxy;

import org.apache.camel.EndpointInject;
import org.apache.camel.component.http.HttpEndpoint;
import org.apache.http.HttpHost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

@SpringBootTest
class HttpproxyApplicationTests {

	@EndpointInject("http-proxy:outbound/properties")
	protected HttpEndpoint httpProxyOutboundPropertiesEndpoint;

	@EndpointInject("http-proxy:outbound/configurer")
	protected HttpEndpoint httpProxyOutboundConfigurerEndpoint;

	@Test
	void proxyIsSetWithProperties() {
		Assertions.assertNotNull(httpProxyOutboundPropertiesEndpoint);

		// Make sure the endpoint created the HTTP client
		httpProxyOutboundPropertiesEndpoint.getHttpClient();

		// The properties were set in the configuration class
		Assertions.assertEquals("proxyHost", httpProxyOutboundPropertiesEndpoint.getProxyHost());
		Assertions.assertEquals(8123, httpProxyOutboundPropertiesEndpoint.getProxyPort());

		// The configurer was _not_ set in the configuration class
		Assertions.assertNull(httpProxyOutboundPropertiesEndpoint.getHttpClientConfigurer());

		// The properties should be respected when creating the HTTP client
		HttpClientBuilder httpClientBuilder = httpProxyOutboundPropertiesEndpoint.getClientBuilder();
		HttpHost proxy = (HttpHost) ReflectionTestUtils.getField(httpClientBuilder, "proxy");
		Assertions.assertNotNull(proxy);
		Assertions.assertEquals("proxyHost", proxy.getHostName());
		Assertions.assertEquals(8123, proxy.getPort());
	}

	@Test
	void proxyIsSetWithConfigurer() {
		Assertions.assertNotNull(httpProxyOutboundConfigurerEndpoint);

		// Make sure the endpoint created the HTTP client
		httpProxyOutboundConfigurerEndpoint.getHttpClient();

		// The properties were _not_ set in the configuration class
		Assertions.assertNull(httpProxyOutboundConfigurerEndpoint.getProxyHost());
		Assertions.assertEquals(0, httpProxyOutboundConfigurerEndpoint.getProxyPort());

		// The configurer was set in the configuration class
		Assertions.assertNotNull(httpProxyOutboundConfigurerEndpoint.getHttpClientConfigurer());

		// The configurer is respected when creating the HTTP client
		HttpClientBuilder httpClientBuilder = httpProxyOutboundConfigurerEndpoint.getClientBuilder();
		HttpHost proxy = (HttpHost) ReflectionTestUtils.getField(httpClientBuilder, "proxy");
		Assertions.assertNotNull(proxy);
		Assertions.assertEquals("proxyHost", proxy.getHostName());
		Assertions.assertEquals(8123, proxy.getPort());
	}

}
