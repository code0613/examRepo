package com.example.demo.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Entity
@NoArgsConstructor
public class Locations {

    @Id
    private Long locationId;

    @Column
    private String street_address;

    @Column
    private String postal_code;

    @Column(nullable = false)
    private String city;

    @Column
    private String state_province;

    @Column(nullable = false)
    private String country_id;
}
