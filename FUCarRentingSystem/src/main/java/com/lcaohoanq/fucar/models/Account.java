package com.lcaohoanq.fucar.models;

import com.lcaohoanq.fucar.enums.ERole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Account")
@ToString(exclude = "customer")
public class Account implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AccountID")
    private int accountID;

    @Column(name = "AccountName")
    private String accountName;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "Role")
    private ERole role;

    @OneToOne
    @JoinColumn(name = "CustomerID", nullable = false)
    private Customer customer;
}
