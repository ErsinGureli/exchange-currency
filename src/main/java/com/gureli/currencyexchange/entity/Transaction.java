package com.gureli.currencyexchange.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transactions")
@EqualsAndHashCode(of = "id")
public class Transaction implements Serializable {
    @Id
    @SequenceGenerator(name = "transaction_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_seq")
    private Long id;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "exchange_rate", nullable = false)
    private BigDecimal exchangeRate;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @CreationTimestamp
    @Column(name = "cr_date")
    private ZonedDateTime creationDate;

}
