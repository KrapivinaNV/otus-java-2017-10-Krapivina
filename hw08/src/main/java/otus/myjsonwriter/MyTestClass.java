package otus.myjsonwriter;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class MyTestClass {
    private List<MyTestClassInternal> listTests = ImmutableList.of(new MyTestClassInternal("stringInternal1", 1), new MyTestClassInternal("stringInternal2", 2));
    private List<String> listStrings = ImmutableList.of("string1", "string2");
    private MyTestClassInternal myTestClassInternal = new MyTestClassInternal("stringInternal100", 100);
    private int valueInt = 0;
    private double valueDouble = 1;
    private String string = "123456789";
    private String[] arrayStrings = {"str1", "str2"};
    private String myString = null;
    private String empty = "";
    private int[] nullsArray = null;
    private Collection nullsCollection = null;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        MyTestClass that = (MyTestClass) object;
        return valueInt == that.valueInt &&
                Double.compare(that.valueDouble, valueDouble) == 0 &&
                Objects.equal(listTests, that.listTests) &&
                Objects.equal(listStrings, that.listStrings) &&
                Objects.equal(myTestClassInternal, that.myTestClassInternal) &&
                Objects.equal(string, that.string) &&
                Arrays.equals(arrayStrings, that.arrayStrings) &&
                Objects.equal(myString, that.myString) &&
                Objects.equal(empty, that.empty) &&
                Arrays.equals(nullsArray, that.nullsArray) &&
                Objects.equal(nullsCollection, that.nullsCollection);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(listTests, listStrings, myTestClassInternal, valueInt, valueDouble, string, arrayStrings, myString, empty, nullsArray, nullsCollection);
    }
}

