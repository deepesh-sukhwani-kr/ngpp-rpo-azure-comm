package com.kroger.ngpp.autoconfiguration;

import com.kroger.ngpp.azurestorage.streams.AzureApiAdapter;
import com.kroger.ngpp.azurestorage.streams.DefaultAzureApiAdapter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AutoConfig {

    @Bean
    @ConditionalOnMissingBean
    AzureApiAdapter defaultAzureApiAdapter() {
        return new DefaultAzureApiAdapter();
    }
}
