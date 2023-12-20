package com.kroger.ngpp.ingress.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;

@Configuration
@EnableConfigurationProperties
@ConditionalOnProperty(value = "kroger.security.oauth.auto-configure.enabled", matchIfMissing =
        true)
@ConditionalOnClass(name = {
        "org.springframework.cloud.config.client.ConfigServicePropertySourceLocator",
        "org.springframework.cloud.config.client.ConfigClientProperties",
        "org.springframework.security.oauth2.client.OAuth2RestTemplate"})
@AutoConfigureAfter({
        OAuthBoostrapCredentialsConfiguration.class})
public class OAuthClientConfiguration {

    /**
     * @param configClientProperties
     * @param restTemplate
     * @return
     */
//    @Bean
//    public ConfigServicePropertySourceLocator optimizedConfigServicePropertySourceLocator(
//            ConfigClientProperties configClientProperties,
//            @Qualifier("oAuth2RestTemplateConfig") OAuth2RestTemplate restTemplate) {
//        ConfigServicePropertySourceLocator configServicePropertySourceLocator = new ConfigServicePropertySourceLocator(
//                configClientProperties);
//        configServicePropertySourceLocator.setRestTemplate(restTemplate);
//        return configServicePropertySourceLocator;
//    }

    /**
     * @return
     */
    @Bean
    protected ClientHttpRequestFactory requestFactory() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(10000);
        requestFactory.setReadTimeout(10000);
        return requestFactory;
    }

}
