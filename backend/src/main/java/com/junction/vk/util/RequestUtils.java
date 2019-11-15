package com.junction.vk.util;

import javax.servlet.http.HttpServletRequest;

public final class RequestUtils {
    public static final String SECURITY_TOKEN_HEADER = "";

    private RequestUtils() {
    }

    public static String getMiniAppToken(HttpServletRequest request) {
        return request.getHeader(SECURITY_TOKEN_HEADER);
    }
}
