package otus.data;

import otus.myorm.annotations.MyManyToOne;

import javax.persistence.*;

@Entity
@Table(name = "phone")
public class PhoneDataSet extends DataSet {
    private String number;
    @MyManyToOne
    private UserDataSet user;

    public PhoneDataSet() {
    }

    public PhoneDataSet(Long id) {
        setId(id);
    }

    public PhoneDataSet(String number) {
        this.number = number;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return super.getId();
    }

    public void setId(long id) {
        super.setId(id);
    }

    @Column(name = "number")
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    public UserDataSet getUser() {
        return user;
    }

    public void setUser(UserDataSet user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "PhoneDataSet{" +
                "number='" + number + '\'' +
                '}';
    }
}
