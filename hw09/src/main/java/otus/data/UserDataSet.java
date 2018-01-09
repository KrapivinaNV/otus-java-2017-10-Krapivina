package otus.data;

import org.hibernate.annotations.Type;
import otus.myorm.annotations.MyOneToOne;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user")
public class UserDataSet extends DataSet {

    private String name;
    private Number age;
    @MyOneToOne
    private AddressDataSet address;
    private Set<PhoneDataSet> phones;

    public UserDataSet() {
    }

    public UserDataSet(Long id) {
        setId(id);
    }

    public UserDataSet(String name, Number age, AddressDataSet address, Set<PhoneDataSet> phones) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.phones = phones;
    }

    public UserDataSet(String name, Number age, AddressDataSet address) {
        this.name = name;
        this.age = age;
        this.address = address;
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

    @Column(name = "age", nullable = false)
    @Type(type = "java.lang.Integer")
    public Number getAge() {
        return age;
    }

    public void setAge(Number age) {
        this.age = age;
    }

    @OneToOne(cascade = CascadeType.ALL)
    public AddressDataSet getAddress() {
        return address;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Set<PhoneDataSet> getPhones() {
        return phones;
    }

    public void setAddress(AddressDataSet address) {
        this.address = address;
    }

    public void setPhones(Set<PhoneDataSet> phones) {
        this.phones = phones;
    }

    @Override
    public String toString() {
        return "UserDataSet{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", address=" + address +
                ", phones=" + phones +
                '}';
    }
}
