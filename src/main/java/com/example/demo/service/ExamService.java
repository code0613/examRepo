package com.example.demo.service;

import com.example.demo.Entity.Employees;
import com.example.demo.Entity.Job_History;
import com.example.demo.dto.EmployeesResponseDto;
import com.example.demo.dto.HistoryResponseDto;
import com.example.demo.global.exception.CustomException;
import com.example.demo.global.exception.StatusMsgCode;
import com.example.demo.repository.EmployeesRepository;
import com.example.demo.repository.HistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ExamService {

    private final EmployeesRepository employeesRepository;
    private final HistoryRepository historyRepository;

    @Transactional(readOnly = true)
    public EmployeesResponseDto getUserProfile(Long id) {
        Employees employees = employeesRepository.findById(id).orElseThrow(
                () -> new CustomException(StatusMsgCode.USER_NOT_FOUNT)
        );

        return EmployeesResponseDto.builder()
                .employeeId(employees.getEmployee_id())
                .firstName(employees.getFirst_name())
                .lastName(employees.getLast_name())
                .email(employees.getEmail())
                .phoneNumber(employees.getPhone_number())
                .hireDate(employees.getHire_date())
                .jobId(employees.getJob_id())
                .salary(employees.getSalary())
                .commissionPct(employees.getCommission_pct())
                .managerId(employees.getManager_id())
                .departmentId(employees.getDepartment_id())
                .build();
    }
    @Transactional(readOnly = true)
    public List<HistoryResponseDto> getUserHistory(Long id) {
        List<Job_History> historyList = historyRepository.findAllByEmployeeId(id);

        List<HistoryResponseDto> responseDtoList = new ArrayList<>();
        for (Job_History tempHistory : historyList) {
            responseDtoList.add(HistoryResponseDto.builder()
                    .employee_id(tempHistory.getEmployeeId())
                    .start_date(tempHistory.getStart_date())
                    .end_date(tempHistory.getEnd_date())
                    .job_id(tempHistory.getJob_id())
                    .department_id(tempHistory.getDepartment_id())
                    .build());
        }
        return responseDtoList;
    }

    public List<Job_History> getAllUserHistory() {
        return historyRepository.findAll();
    }
}
