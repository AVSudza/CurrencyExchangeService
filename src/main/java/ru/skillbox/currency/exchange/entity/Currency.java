package ru.skillbox.currency.exchange.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    @SequenceGenerator(name = "sequence", sequenceName = "create_sequence", allocationSize = 0)
    @Column(name = "id")
    private Long id;

    @Column(name = "valute_id")
    private String valuteId;

    @Column(name = "name")
    private String name;

    @Column(name = "nominal")
    private int nominal;

    @Column(name = "value", columnDefinition = "NUMERIC(19,5)")
    private Double value;

    @Column(name = "iso_num_code")
    private String isoNumCode;

    @Column(name = "iso_char_code")
    private String isoCharCode;

}
