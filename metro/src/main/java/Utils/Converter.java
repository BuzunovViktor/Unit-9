package Utils;

import Metro.ResultObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;

public class Converter {

    private static final String path = System.getProperty("user.dir") + File.separator;
    private static final String directoryName = "data" + File.separator;

    public static void toJSON(ResultObject resultObject, String fileName) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(new File(path + directoryName + fileName), resultObject);
        System.out.println("json created!");
    }

    public static ResultObject toJavaObject(String fileName) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(path + directoryName + fileName), ResultObject.class);
    }

}