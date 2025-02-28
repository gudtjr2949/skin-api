package com.personal.skin_api.common.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CertCodeGenerator {

    public String createCertCodeAtMail() {
        return UUID.randomUUID().toString().substring(0, 6);
    }
}
