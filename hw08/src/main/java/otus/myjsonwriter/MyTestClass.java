package otus.myjsonwriter;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;

import java.util.Arrays;
import java.util.List;

public class MyTestClass {
    public List<MyTestClassInternal> listTests = ImmutableList.of(new MyTestClassInternal("stringInternal1", 1), new MyTestClassInternal("stringInternal2", 2));
    public List<String> listStrings = ImmutableList.of("string1", "string2");
    public MyTestClassInternal myTestClassInternal = new MyTestClassInternal("stringInternal100", 100);
    public int valueInt = 0;
    public double valueDouble = 1;
    public String string = "123456789";
    public String[] arrayStrings = {"str1", "str2"};

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
                Arrays.equals(arrayStrings, that.arrayStrings);
    }

}

