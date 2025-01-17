package com.lcaohoanq.fucar.models;

import com.lcaohoanq.fucar.enums.ERentalStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
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
@Table(name = "CarRental")
@ToString(exclude = {"customer", "car"})
public class CarRental implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "CustomerID", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "CarID", nullable = false)
    private Car car;

    @Column(name = "PickupDate", nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate pickupDate;

    @Column(name = "ReturnDate", nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate returnDate;

    @Column(name = "RentPrice", nullable = false)
    private BigDecimal rentPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false)
    private ERentalStatus status;
}
