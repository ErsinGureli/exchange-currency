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
@Table(name = "addresses")
@EqualsAndHashCode(of = "id")
public class Address implements Serializable {
    @Id
    @SequenceGenerator(name = "address_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_seq")
    private Long id;
    @Column(name = "address", length = 250, nullable = false, unique = true)
    private String fullAddress;
    @ManyToMany
    @JoinTable(
            name = "address_customer",
            joinColumns = @JoinColumn(name = "address_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id"))
    private List<Customer> customers;

    public void addCustomer(Customer customer) {
        this.getCustomers().add(customer);
        customer.getAddresses().add(this);
    }

    public void removeCustomer(Customer customer) {
        this.getCustomers().remove(customer);
        customer.getAddresses().remove(this);
    }
}
