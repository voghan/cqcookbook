package com.cookbook.cq.utilities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Reader;

/**
 * Facade over JSON implementation.
 */
public class JsonUtil {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static final String convert(Object obj) {
        return GSON.toJson(obj);
    }

    public static final <T> T fromJson(String json, Class<T> classOfT) {
        return GSON.fromJson(json, classOfT);
    }

    public static Gson getUtil() {
        return GSON;
    }

    public static final <T> T fromReader(Reader reader, Class<T> classOfT) throws IOException {
        return GSON.fromJson(reader, classOfT);
    }

    public static final <T> T fromRequest(HttpServletRequest request, Class<T> classOfT)
        throws IOException {
        return fromReader(request.getReader(), classOfT);
    }
}
