package me.aliakkaya.auditexample.utils;
import java.lang.reflect.Field;

public class UpdateUtil {

    public static <T> void updateObject(T target, T source) {
        if (target == null || source == null) return;

        Class<?> clazz = source.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            try {
                field.setAccessible(true); // Make private fields accessible TODO: check whether this creates security issues

                Object value = field.get(source);
                if (value != null) {
                    field.set(target, value);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace(); // Handle the exception as desired
            }
        }
    }
}