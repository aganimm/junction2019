package com.junction.vk.repository.http;

import com.junction.vk.util.HttpUtils;

public abstract class AbstractHttpRepository {
    protected final HttpUtils httpUtils;

    public AbstractHttpRepository(HttpUtils httpUtils) {
        this.httpUtils = httpUtils;
    }
}
