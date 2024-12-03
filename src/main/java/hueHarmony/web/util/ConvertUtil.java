package hueHarmony.web.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

@Component
public class ConvertUtil {

    public static <T> List<T> convertJsonToList(Object array, TypeReference<List<T>> typeReference) throws JsonProcessingException {
        if (array == null) return null;

        ObjectMapper mapper = new ObjectMapper();
        String json = (String) array;
        return mapper.readValue(json, typeReference);
    }

    public static String[] convertObjectToStringArray(Object object) {
        if (object instanceof String[]) {
            return (String[]) object;
        } else {
            throw new IllegalArgumentException("The provided object is not a String array");
        }
    }

    public static <T> T[] convertRangeToArray(String range, Function<String, T> parser, T[] emptyArray){
        if (range == null) return emptyArray;
        else {
            return Arrays.stream(range.split(","))
                    .map(parser)
                    .toArray(size -> Arrays.copyOf(emptyArray, size));
        }

    }
}
