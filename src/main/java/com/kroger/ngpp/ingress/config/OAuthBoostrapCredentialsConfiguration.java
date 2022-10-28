package com.kroger.ngpp.ingress.config;

import com.kroger.commons.boot.autoconfigure.krypt.aes.KryptPropertySourceAutoConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;

@Configuration
@EnableConfigurationProperties
@ConditionalOnProperty(value = "kroger.security.oauth.auto-configure.enabled", matchIfMissing =
        true)
@ConditionalOnClass(name = {
        "org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails",
        "org.springframework.security.oauth2.client.OAuth2RestTemplate"})
@AutoConfigureAfter({
        KryptPropertySourceAutoConfiguration.class})
public class OAuthBoostrapCredentialsConfiguration {

    /**
     * Fetch the configuration details from boostrap.yml file and set to the client credential details
     *
     * @return
     */
    @Bean
    @Qualifier("optimizedRestResource")
    @ConfigurationProperties("kroger.optimized.security.oauth2.client")
    public ClientCredentialsResourceDetails optimizedResourceDetails() {
        ClientCredentialsResourceDetails clientCredentialsResourceDetails = new ClientCredentialsResourceDetails();
        return clientCredentialsResourceDetails;
    }

    /**
     * Autowire the above resource configuration, pass it as an argument to create the rest template for connecting ingress controller
     *
     * @param resource
     * @return
     */
    @Bean("oAuth2RestTemplateConfig")
    public OAuth2RestTemplate optimizedRestTemplate(@Qualifier("optimizedRestResource") ClientCredentialsResourceDetails resource) {
        OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resource);
        return restTemplate;
    }

}
