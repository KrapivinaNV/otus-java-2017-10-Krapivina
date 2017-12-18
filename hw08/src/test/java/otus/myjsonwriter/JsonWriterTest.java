package otus.myjsonwriter;

import com.google.gson.Gson;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class JsonWriterTest {

    @Test
    public void objectToJson() {
        MyTestClass myTest = new MyTestClass();
        String writeToString = JsonWriter.writeToString(myTest);

        assertEquals(new Gson().toJson(new MyTestClass()), writeToString);
    }


    @Test
    public void jsonToObject() {
        MyTestClass myTest = new MyTestClass();
        String writeToString = JsonWriter.writeToString(myTest);
        MyTestClass myTestClassAfterSerialization = new Gson().fromJson(writeToString, MyTestClass.class);

        assertEquals(myTestClassAfterSerialization, myTest);
    }


}
