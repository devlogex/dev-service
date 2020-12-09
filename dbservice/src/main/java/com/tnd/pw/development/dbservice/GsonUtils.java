package com.tnd.pw.development.dbservice;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GsonUtils {
    private static final Gson gson = new Gson();
    public static String convertToString(Object obj) {
        return gson.toJson(obj);
    }
    public static <T> List<T> toListObject(String json, Class<T> type) {
        return gson.fromJson(json, new ListOf<>(type));
    }

    static class ListOf<T> implements ParameterizedType {
        private final Class<T> type;

        public ListOf(Class<T> type) {
            this.type = type;
        }

        public Type[] getActualTypeArguments() {
            return new Type[]{this.type};
        }

        public Type getRawType() {
            return List.class;
        }

        public Type getOwnerType() {
            return null;
        }
    }
}
