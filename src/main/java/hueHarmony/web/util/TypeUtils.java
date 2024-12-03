package hueHarmony.web.util;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TypeUtils {

    // Map of wrapper types to their corresponding primitive types
    private static final Map<Class<?>, Class<?>> WRAPPER_TO_PRIMITIVE_MAP = new HashMap<>();

    static {
        WRAPPER_TO_PRIMITIVE_MAP.put(Boolean.class, boolean.class);
        WRAPPER_TO_PRIMITIVE_MAP.put(Byte.class, byte.class);
        WRAPPER_TO_PRIMITIVE_MAP.put(Character.class, char.class);
        WRAPPER_TO_PRIMITIVE_MAP.put(Double.class, double.class);
        WRAPPER_TO_PRIMITIVE_MAP.put(Float.class, float.class);
        WRAPPER_TO_PRIMITIVE_MAP.put(Integer.class, int.class);
        WRAPPER_TO_PRIMITIVE_MAP.put(Long.class, long.class);
        WRAPPER_TO_PRIMITIVE_MAP.put(Short.class, short.class);
    }

    public Class<?> toPrimitive(Class<?> clazz) {
        if (clazz == null) {
            return null;
        }
        return WRAPPER_TO_PRIMITIVE_MAP.getOrDefault(clazz, clazz);
    }

    public boolean isPrimitiveOrWrapper(Object value) {
        if (value == null) {
            return false;
        }
        Class<?> clazz = value.getClass();
        return clazz.isPrimitive() || WRAPPER_TO_PRIMITIVE_MAP.containsKey(clazz);
    }
}

