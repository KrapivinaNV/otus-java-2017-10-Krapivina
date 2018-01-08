package otus.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class UserDataSet extends DataSet {

    private String name;
    private Number age;
    // private AddressDataSet address;
    // private PhoneDataSet[] phone;

    public UserDataSet() {
    }

    public UserDataSet(Long id) {
        setId(id);
    }

    public UserDataSet(String name, Number age) {
        this.name = name;
        this.age = age;
    }

    public void setId(long id){
        super.setId(id);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return super.getId();
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "age")
    public Number getAge() {
        return age;
    }

    public void setAge(Number age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "UserDataSet{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
