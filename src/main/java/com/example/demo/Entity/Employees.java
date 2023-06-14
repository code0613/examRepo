package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
public class Employees {

    @Id
    private Long employee_id;

    @Column
    private String first_name;

    @Column(nullable = false)
    private String last_name;

    @Column(nullable = false)
    private String email;

    @Column
    private String phone_number;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDateTime hire_date;

    @Column(nullable = false)
    private String jobId;

    @Column(nullable = false)
    private Double salary;

    @Column
    private Double commission_pct;

    @Column
    private Long manager_id;

    @Column
    private Long department_id;

    public void update(Double Salary) {
        this.salary = Salary;
    }
}
