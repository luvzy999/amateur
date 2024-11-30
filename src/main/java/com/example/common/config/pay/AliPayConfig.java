package com.example.common.config.pay;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


@Configuration
@Data
@Component
@ConfigurationProperties(prefix = "alipay")
public class AliPayConfig {
    /**
     * AppId
     */
    private String appId;

    private String appPrivateKey;

    private String alipayPublicKey;

    private String notifyUrl;

    private String charset = "UTF-8";

    private String signType = "RSA2";

    private String format = "json";

    private String gateway;

    private String returnUrl = "http://localhost:8080/doctorCard";

    @Override
    public String toString() {
        return "AliPayConfig{" +
                "appId='" + appId + '\'' +
                ", appPrivateKey='" + appPrivateKey + '\'' +
                ", alipayPublicKey='" + alipayPublicKey + '\'' +
                ", notifyUrl='" + notifyUrl + '\'' +
                ", charset='" + charset + '\'' +
                ", signType='" + signType + '\'' +
                ", gateway='" + gateway + '\'' +
                '}';
    }
}
