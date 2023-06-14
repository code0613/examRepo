package com.example.demo.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Entity
@NoArgsConstructor
public class Departments {

    @Id
    private Long departmentId;

    @Column(nullable = false)
    private String department_name;

    @Column
    private Long manager_id;

    @Column
    private Long locationId;
}
