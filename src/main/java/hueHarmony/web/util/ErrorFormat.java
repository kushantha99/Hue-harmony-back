package hueHarmony.web.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ErrorFormat {

    private static final Pattern pattern = Pattern.compile(".*\\d+.*");
    private final Map<String, Method> methodCache = new ConcurrentHashMap<>();

    public Map<String, Object> formatValidationErrors(BindingResult bindingResult, Object object) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Map<String, Object> errors = new HashMap<>();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            addFieldError(object, errors, fieldError);
        }

        for (ObjectError globalError : bindingResult.getGlobalErrors()) {
            addGlobalError(object, errors, globalError);
        }

        return errors;
    }

    private void addFieldError(Object object, Map<String, Object> errors, FieldError fieldError) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String fieldPath = fieldError.getField();
        String[] pathParts = fieldPath.split("\\.");

        Map<String, Object> currentLevel = errors;
        Object currentObjLevel = object;

        for (int i = 0; i < pathParts.length - 1; i++) {
            String part = pathParts[i];
            Matcher matcher = pattern.matcher(part);
            if(!(currentObjLevel instanceof List<?>) && matcher.matches()){
                currentObjLevel = getIndexedObject(currentObjLevel, part);
                part = part.replaceAll("\\d", String.valueOf(getIdentifier(currentObjLevel)));
                currentLevel = (Map<String, Object>) currentLevel.computeIfAbsent(part, k -> new HashMap<String, Object>());
            }else if(!(currentObjLevel instanceof List<?>) && !matcher.matches()){
                currentObjLevel = getObjectByMethod(currentObjLevel, "get" + capitalize(part));
                currentLevel = (Map<String, Object>) currentLevel.computeIfAbsent(part, k -> new HashMap<String, Object>());
            }else if(currentObjLevel instanceof List<?> list && matcher.matches()){
                currentObjLevel = getIndexedObjectFromList(list, part);
                part = part.replaceAll("\\d", String.valueOf(getIdentifier(currentObjLevel)));
                currentLevel = (Map<String, Object>) currentLevel.computeIfAbsent(part, k -> new HashMap<String, Object>());
            }
        }

        String field = pathParts[pathParts.length - 1];
        currentLevel.put(field, fieldError.getDefaultMessage());
    }

    //Need to test and update object error formatting if needed.
    private void addGlobalError(Object object, Map<String, Object> errors, ObjectError globalError) {
        String objectName = globalError.getObjectName();
        errors.put(objectName, globalError.getDefaultMessage());
    }

    private Object getIndexedObject(Object obj, String part) {
        int index = Integer.parseInt(part.replaceAll("[^0-9]", ""));
        String basePart = part.substring(0, part.length()-3);
        List<?> list = (List<?>) getObjectByMethod(obj, "get" + capitalize(basePart));
        return list.get(index);
    }

    private Object getIndexedObjectFromList(List<?> list, String part) {
        int index = Integer.parseInt(part.replaceAll("[^0-9]", ""));
        return list.get(index);
    }

    private Integer getIdentifier(Object obj) {
        return (Integer) getObjectByMethod(obj, "getIdentifier");
    }

    private Object getObjectByMethod(Object obj, String methodName) {
        try {
            Method method = getCachedMethod(obj.getClass(), methodName);
            return method.invoke(obj);
        } catch (Exception e) {
            throw new RuntimeException("Error invoking method " + methodName, e);
        }
    }

    private Method getCachedMethod(Class<?> clazz, String methodName) {
        String cacheKey = clazz.getName() + "#" + methodName;
        return methodCache.computeIfAbsent(cacheKey, key -> {
            try {
                for (Method method : clazz.getMethods()) {
                    if (method.getName().equals(methodName)) {
                        return method;
                    }
                }
                throw new RuntimeException("Method " + methodName + " not found in class " + clazz.getName());
            } catch (Exception e) {
                throw new RuntimeException("Error caching method " + methodName + " in class " + clazz.getName(), e);
            }
        });
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}