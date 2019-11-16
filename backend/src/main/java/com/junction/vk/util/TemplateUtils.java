package com.junction.vk.util;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;

public class TemplateUtils {
    private static final DefaultMustacheFactory factory = new DefaultMustacheFactory();

    public static String buildTemplate(String template, Map<String, Object> params) {
        Mustache mustache = factory.compile(new StringReader(template), template, "{", "}");
        StringWriter out = new StringWriter();
        mustache.execute(out, params);
        return out.toString();
    }
}
