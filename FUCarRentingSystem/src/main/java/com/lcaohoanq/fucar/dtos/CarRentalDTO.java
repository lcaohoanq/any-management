package com.lcaohoanq.fucar.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CarRentalDTO {
    private Long id;
    private String customerName;
    private String carName;
    private LocalDate pickupDate;
    private LocalDate returnDate;
    private BigDecimal rentPrice;

}
