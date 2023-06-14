package com.example.demo.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Entity
@NoArgsConstructor
public class Jobs {

    @Id
    private String jobId;

    @Column(nullable = false)
    private String job_title;

    @Column
    private Double min_salary;

    @Column
    private Double max_salary;

    public void update(Double minSalary, Double maxSalary) {
        this.min_salary = minSalary;
        this.max_salary = maxSalary;
    }
}
