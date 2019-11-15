package com.junction.vk.util;

import javax.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ApiUtils {
    private static final Logger logger = LoggerFactory.getLogger(ApiUtils.class);

    private ApiUtils() {
    }

    @Nullable
    public static Long parseIdFromLink(String link) {
        if (link != null) {
            String[] split = link.split("/");
            if (split.length > 2) {
                try {
                    return Long.parseLong(split[split.length - 1].split(".")[0]);
                } catch (NumberFormatException ex) {
                    logger.error("Can't parse link: {}.", link, ex);
                }
            }
        }
        return null;
    }
}
