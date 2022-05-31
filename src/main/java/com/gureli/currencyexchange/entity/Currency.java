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
@Table(name = "currencies")
@EqualsAndHashCode(of = "id")
public class Currency implements Serializable {
    @Id
    @SequenceGenerator(name = "currency_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "currency_seq")
    private Long id;

    @Column(name = "code", length = 3, nullable = false, unique = true)
    private String code;

    @Column(name = "description", length = 100, nullable = false)
    private String description;

    @OneToMany(mappedBy = "currency")
    private List<Account> accounts;
}
