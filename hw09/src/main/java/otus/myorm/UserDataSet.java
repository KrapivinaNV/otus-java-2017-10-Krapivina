package otus.myorm;

public class UserDataSet extends DataSet {
    private String name;
    private Number age;

    public UserDataSet(Long id) {
        setId(id);
    }

    UserDataSet(String name, Number age) {
        this.name = name;
        this.age = age;
    }

    public void setId(long id){
        super.setId(id);
    }


    @Override
    public String toString() {
        return "UserDataSet{" +
                "id=" + getId() +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
