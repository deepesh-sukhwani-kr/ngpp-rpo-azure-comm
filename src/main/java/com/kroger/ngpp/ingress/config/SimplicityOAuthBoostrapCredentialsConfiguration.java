package com.kroger.ngpp.ingress.config;

import com.kroger.commons.boot.autoconfigure.krypt.aes.KryptPropertySourceAutoConfiguration;
import com.kroger.commons.security.oauth.AbstractOAuth2ClientWebSecurityConfiguration;
import com.kroger.commons.security.oauth.KrogerSecurityMiscellany;
import com.kroger.commons.security.oauth.OAuthSecurity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.converter.protobuf.ProtobufJsonFormatHttpMessageConverter;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler;

@Configuration
@EnableConfigurationProperties
@ConditionalOnProperty(value = "kroger.azure.security.oauth.auto-configure.enabled", matchIfMissing =
        true)
@ConditionalOnClass(name = {
        "org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails",
        "org.springframework.security.oauth2.client.OAuth2RestTemplate"})
@AutoConfigureAfter({
        KryptPropertySourceAutoConfiguration.class})
public class SimplicityOAuthBoostrapCredentialsConfiguration  extends AbstractOAuth2ClientWebSecurityConfiguration
{
    private static final String clientRegistrationId = "kroger-service";
    private RestTemplateBuilder restTemplateBuilder;

    @Bean("oauth2RestTemplate")
    public RestTemplate oAuth2RestTemplate()
    {
        KrogerSecurityMiscellany securityMiscellany = this.securityMiscellany();
        ClientRegistration clientRegistration = securityMiscellany
                .client().getClientRegistrationRepository()
                .findByRegistrationId(clientRegistrationId);

        if (null == clientRegistration)
        {
            throw new IllegalArgumentException(
                    ("Invalid Client Registration with ID:" + clientRegistrationId));
        }


        return restTemplateBuilder
                .messageConverters(new ProtobufJsonFormatHttpMessageConverter(),
                        new OAuth2AccessTokenResponseHttpMessageConverter())
                .errorHandler((new OAuth2ErrorResponseErrorHandler()))
                .basicAuthentication(clientRegistration.getClientId(),
                        clientRegistration.getClientSecret()).build();
    }

    @Override
    protected void configure(OAuthSecurity oAuthSecurity) throws Exception {

    }
}
