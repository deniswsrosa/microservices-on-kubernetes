package com.cb.springdata.sample.entities;


import lombok.*;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Address {

    private String streetName;

    private String houseNumber;

    private String postalCode;

    private String city;

    private String country;
}
