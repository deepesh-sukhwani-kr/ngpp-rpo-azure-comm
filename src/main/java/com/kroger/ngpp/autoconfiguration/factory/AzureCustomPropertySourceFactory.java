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
                .orElse("cloud");
        System.out.println("RegularPriceOptimization - Profiles passed from deployment configs: "+ activeProfile);
        YamlPropertiesFactoryBean yamlFactory = new YamlPropertiesFactoryBean();
        yamlFactory.setDocumentMatchers(properties -> {
            String profileFromApplication = properties.getProperty("spring.config.activate.on-profile");
            if (!StringUtils.hasLength(profileFromApplication)) {
                return ABSTAIN;
            }
            YamlProcessor.MatchStatus isAProfileMatch = activeProfile.contains(profileFromApplication) ? FOUND : NOT_FOUND;
            if (isAProfileMatch.equals(FOUND)) {
                System.out.println("RegularPriceOptimization - Profile match found: "+profileFromApplication);
            }
            return isAProfileMatch;
        });
        yamlFactory.setResources(encodedResource.getResource());
        Properties properties = yamlFactory.getObject();
        System.out.println("RegularPriceOptimization - Configuration properties are: "+ properties);
        return new PropertiesPropertySource(Objects.requireNonNull(encodedResource.getResource().getFilename()), properties);
    }
}
