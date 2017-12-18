package otus.myjsonwriter;

import com.google.common.base.Objects;

import java.util.Arrays;

public class MyTestClassInternal{

    public String valueString = "string";
    public int number = 1;
    public int [] arrayInt = {1,1,1};

    public MyTestClassInternal(String valueString, int number){
        this.valueString = valueString;
        this.number = number;
    }

        @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        MyTestClassInternal that = (MyTestClassInternal) object;
        return number == that.number &&
                Objects.equal(valueString, that.valueString) &&
                Arrays.equals(arrayInt, that.arrayInt);
    }

}
