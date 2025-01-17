package com.lcaohoanq.fucar.models;

import com.lcaohoanq.fucar.enums.ECarStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "Car")
//@ToString(exclude = {"rentals", "reviews"})
public class Car implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CarID", nullable = false)
    private Integer carId;

    @Column(name = "CarName", nullable = false)
    private String carName;

    @Column(name = "CarModelYear", nullable = false)
    private Integer carModelYear;

    @Column(name = "Color", nullable = false)
    private String color;

    @Column(name = "Capacity", nullable = false)
    private Integer capacity;

    @Column(name = "Description", nullable = false)
    private String description;

    @Column(name = "ImportDate", nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate importDate;

    @Column(name = "RentPrice", nullable = false)
    private BigDecimal rentPrice;

    @ManyToOne
    @JoinColumn(name = "ProducerID", nullable = false)
    private CarProducer producer;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false)
    private ECarStatus status;

    @OneToMany(mappedBy = "car")
    private List<CarRental> rentals;

    @OneToMany(mappedBy = "car")
    private List<Review> reviews;

    @Override
    public String toString() {
        return String.valueOf(carId);
    }
}
