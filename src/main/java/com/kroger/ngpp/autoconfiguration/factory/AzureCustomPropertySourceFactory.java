package com.kroger.ngpp.autoconfiguration.factory;

import org.springframework.beans.factory.config.YamlProcessor;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.Optional;
import java.util.Properties;

import static org.springframework.beans.factory.config.YamlProcessor.MatchStatus.ABSTAIN;
import static org.springframework.beans.factory.config.YamlProcessor.MatchStatus.FOUND;
import static org.springframework.beans.factory.config.YamlProcessor.MatchStatus.NOT_FOUND;

public class AzureCustomPropertySourceFactory implements PropertySourceFactory {

    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource encodedResource) {
        String activeProfile = Optional.ofNullable(System.getenv("SPRING_PROFILES_ACTIVE"))
                .orElse(System.getProperty("spring.profiles.active"));

        YamlPropertiesFactoryBean yamlFactory = new YamlPropertiesFactoryBean();
        yamlFactory.setDocumentMatchers(properties -> {
            String profileProperty = properties.getProperty("spring.profiles");
            System.out.println("Hi RPO: profile is: "+properties.getProperty("spring.profiles"));
            if (StringUtils.isEmpty(profileProperty)) {
                return ABSTAIN;
            }
            YamlProcessor.MatchStatus profileContains = activeProfile.contains(profileProperty) ? FOUND : NOT_FOUND;
            return profileContains;
        });
        yamlFactory.setResources(encodedResource.getResource());
        Properties properties = yamlFactory.getObject();
        System.out.println("Hi RPO: Active profiles are: "+ activeProfile);
        System.out.println("Hi RPO: Properties are: "+ properties);
        return new PropertiesPropertySource(Objects.requireNonNull(encodedResource.getResource().getFilename()), properties);
    }
}
