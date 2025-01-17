package com.lcaohoanq.fucar.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
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
@Table(name = "CarProducer")
//@ToString(exclude = {"cars"})
public class CarProducer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProducerID", nullable = false)
    private Integer producerId;

    @Column(name = "ProducerName", nullable = false)
    private String producerName;

    @Column(name = "Address", nullable = false)
    private String address;

    @Column(name = "Country", nullable = false)
    private String country;

    @OneToMany(mappedBy = "producer", cascade = CascadeType.ALL)
    private List<Car> cars;

	@Override
	public String toString() {
		return producerName;
	}
    
    
    
}
