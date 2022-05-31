package com.gureli.currencyexchange.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customers")
@EqualsAndHashCode(of = "id")
public class Customer implements Serializable {
    @Id
    @SequenceGenerator(name = "customer_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_seq")
    private Long id;

    @Column(name = "full_name", length = 100, nullable = false)
    private String fullName;

    @OneToMany(mappedBy = "customer")
    private List<Account> accounts;

    @ManyToMany(mappedBy = "customers",
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            }
    )
    private List<Address> addresses;

    public void addAddress(Address address) {
        this.getAddresses().add(address);
        address.getCustomers().add(this);
    }

    public void removeAddress(Address address) {
        this.getAddresses().remove(address);
        address.getCustomers().remove(this);
    }
}
