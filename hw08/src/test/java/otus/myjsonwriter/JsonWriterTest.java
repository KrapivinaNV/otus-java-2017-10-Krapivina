package otus.myjsonwriter;

import com.google.gson.Gson;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class JsonWriterTest {

    private static final Gson GSON = new Gson();

    @Test
    public void objectToJson() {
        MyTestClass myTest = new MyTestClass();
        String writeToString = JsonWriter.writeToString(myTest);

        assertEquals(GSON.toJson(new MyTestClass()), writeToString);
    }

    @Test
    public void jsonToObject() {
        MyTestClass myTest = new MyTestClass();
        String writeToString = JsonWriter.writeToString(myTest);
        MyTestClass myTestClassAfterSerialization = GSON.fromJson(writeToString, MyTestClass.class);

        assertEquals(myTestClassAfterSerialization, myTest);
    }
}
