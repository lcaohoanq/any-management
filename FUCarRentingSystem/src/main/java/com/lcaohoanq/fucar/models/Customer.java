package com.lcaohoanq.fucar.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
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
@Table(name = "Customer")
//@ToString(exclude = {"rentals", "reviews"})
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CustomerID", nullable = false)
    private Integer customerId;

    @Column(name = "CustomerName", nullable = false)
    private String customerName;

    @Column(name = "Mobile", nullable = false)
    private String mobile;

    @Column(name = "Birthday", nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate birthday;

    @Column(name = "IdentityCard", nullable = false)
    private String identityCard;

    @Column(name = "LicenceNumber", nullable = false)
    private String licenceNumber;

    @Column(name = "LicenceDate", nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate licenceDate;

    @Column(name = "Email", nullable = false)
    private String email;

    @Column(name = "Password", nullable = false)
    private String password;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    private Account account;

    @OneToMany(mappedBy = "customer")
    private List<CarRental> rentals;

    @OneToMany(mappedBy = "customer")
    private List<Review> reviews;

    @Override
    public String toString() {
        return  String.valueOf(customerId);
    }
}
