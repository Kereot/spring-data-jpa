package com.gb.market.auth.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@Data
@ConfigurationProperties(JwtProperties.PREFIX)
public class JwtProperties {
    public static final String PREFIX = "jwt";
    private String secret;
    private Duration expireTime;
}
