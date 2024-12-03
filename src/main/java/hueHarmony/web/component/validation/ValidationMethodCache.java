package hueHarmony.web.component.validation;

import hueHarmony.web.util.TypeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ValidationMethodCache {

    private final TypeUtils typeUtils;
    private final Map<String, Method> methodCache = new ConcurrentHashMap<>();

    public Method getCachedMethod(Class<?> clazz, String methodName, Object[] args) {
        Class<?>[] argTypes = getArgumentTypes(args);
        String cacheKey = keyGenerate(clazz, methodName, argTypes);
        return methodCache.computeIfAbsent(cacheKey, key -> {
            try {
                return clazz.getMethod(methodName, argTypes);
            } catch (Exception e) {
                throw new RuntimeException("Error caching method " + methodName + " in class " + clazz.getName(), e);
            }
        });
    }

    private String keyGenerate(Class<?> clazz, String methodName, Class<?>[] args){

        return clazz.getName() +
                "#" +
                methodName +
                "(" +
                Arrays.stream(args).map(Class::getName).collect(Collectors.joining(", ")) +
                ")";
    }

    private Class<?>[] getArgumentTypes(Object[] args){
        return Arrays.stream(args).map(arg -> typeUtils.isPrimitiveOrWrapper(arg)?typeUtils.toPrimitive(arg.getClass()):arg.getClass()).toArray(Class<?>[]::new);
    }
}
