package otus.data;

import javax.persistence.*;

@Entity
@Table(name = "address")
public class AddressDataSet extends DataSet {
    private String street;

    public AddressDataSet(){}

    public AddressDataSet(Long id) {
        setId(id);
    }

    public AddressDataSet(String street) { this.street = street; }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return super.getId();
    }

    public void setId(long id){ super.setId(id); }

    @Column(name = "street")
    public String getStreet(){
        return street;
    }

    public void setStreet(String street){this.street = street;}

}
