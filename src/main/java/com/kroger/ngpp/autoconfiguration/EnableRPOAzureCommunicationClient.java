package com.kroger.ngpp.autoconfiguration;

import com.kroger.ngpp.common.config.EnableRPOCommonClient;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(RPOAzureCommConfiguration.class)
@EnableCaching
@EnableRPOCommonClient
public @interface EnableRPOAzureCommunicationClient {
}
