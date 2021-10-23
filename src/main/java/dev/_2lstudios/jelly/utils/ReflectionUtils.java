package dev._2lstudios.jelly.utils;

import java.lang.reflect.Method;

public class ReflectionUtils {
    public static Method getMethod(String name, Class<?> clazz) {
        for (Method m : clazz.getDeclaredMethods()) {
            if (m.getName().equals(name))
                return m;
        }

        return null;
    }
}