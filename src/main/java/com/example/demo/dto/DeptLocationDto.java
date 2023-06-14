package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class DeptLocationDto {

    private Long departmentId;
    private String department_name;
    private Long manager_id;
    private Long locationId;

    private String street_address;
    private String postal_code;
    private String city;
    private String state_province;
    private String country_id;
}
