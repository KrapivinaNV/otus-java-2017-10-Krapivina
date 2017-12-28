package otus.myorm;

public class UserDataSet extends DataSet {
    private String name;
    private Number age;

    public UserDataSet(Long id) {
        setId(id);
    }


    public UserDataSet(long id, String name, Number age) {
        setId(id);

        this.name = name;
        this.age = age;
    }

    public String getName() {
        return this.name;
    }

    public Number getAge() {
        return this.age;
    }

    @Override
    public String toString() {
        return "UserDataSet{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
