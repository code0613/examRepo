package com.example.demo.service;

import com.example.demo.Entity.*;
import com.example.demo.dto.DeptLocationDto;
import com.example.demo.dto.EmployeesResponseDto;
import com.example.demo.dto.HistoryResponseDto;
import com.example.demo.dto.OpenApiResponseDto;
import com.example.demo.global.exception.CustomException;
import com.example.demo.global.exception.StatusMsgCode;
import com.example.demo.repository.*;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ExamService {

    private final EmployeesRepository employeesRepository;
    private final HistoryRepository historyRepository;
    private final JobsRepository jobsRepository;
    private final DepartmentsRepository departmentsRepository;
    private final LocationsRepository locationsRepository;

    // 특정 사원의 현재 정보 조회 가능한 API
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
                .jobId(employees.getJobId())
                .salary(employees.getSalary())
                .commissionPct(employees.getCommission_pct())
                .managerId(employees.getManager_id())
                .departmentId(employees.getDepartment_id())
                .build();
    }

    //  특정 사원의 이력 정보 조회 가능한 API
    @Transactional(readOnly = true)
    public HistoryResponseDto getUserHistory(Long id) {
        Job_History history = historyRepository.findById(id).orElseThrow(
                () -> new CustomException(StatusMsgCode.USER_NOT_FOUNT)
        );
       return HistoryResponseDto.builder()
                    .employee_id(history.getEmployeeId())
                    .start_date(history.getStart_date())
                    .end_date(history.getEnd_date())
                    .job_id(history.getJob_id())
                    .department_id(history.getDepartment_id())
                    .build();

    }

    // 부서 및 위치 정보 조회 가능한 API
    @Transactional(readOnly = true)
    public DeptLocationDto getDeptLocation(Long id) {
        Departments departments = departmentsRepository.findById(id).orElseThrow(
                () -> new CustomException(StatusMsgCode.DEPARTMENT_NOT_FOUNT)
        );

        Locations locations = locationsRepository.findById(departments.getLocationId()).orElseThrow(
                () -> new CustomException(StatusMsgCode.BAD_REQUEST)
        );

        return DeptLocationDto.builder()
                .departmentId(departments.getDepartmentId())
                .department_name(departments.getDepartment_name())
                .manager_id(departments.getManager_id())
                .locationId(departments.getLocationId())
                .street_address(locations.getStreet_address())
                .postal_code(locations.getPostal_code())
                .city(locations.getCity())
                .state_province(locations.getState_province())
                .country_id(locations.getCountry_id())
                .build();
    }


    // 특정 부서의 급여를 특정 비율로 인상 및 사원 정보 업데이트 할 수 있는 API
    @Transactional
    public void raiseSalary(String name, Double rate) {
        Jobs jobs = jobsRepository.findByJobId(name).orElseThrow(
                () -> new CustomException(StatusMsgCode.JOBS_NOT_FOUNT)
        );

        if (rate < -100) {
            throw new CustomException(StatusMsgCode.BAD_REQUEST);
        } else {
            Double raisedMinSal = jobs.getMin_salary() * (rate / 100);
            Double raisedMaxSal = jobs.getMax_salary() * (rate / 100);

            jobs.update(jobs.getMin_salary()+raisedMinSal,jobs.getMax_salary()+raisedMaxSal);
        }

        List<Employees> employeeList = employeesRepository.findAllByJobId(name);
        for (Employees tempEmployee : employeeList) {
            Double changedSalary = tempEmployee.getSalary() * (rate / 100);
            tempEmployee.update(tempEmployee.getSalary() + changedSalary);
        }
    }

    // 인천광역시 주차장 정보 조회  API
    @Transactional(readOnly = true)
    public List<OpenApiResponseDto> getOpenApi() {

        try {
            StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/6280000/ICParkInfo/ParkingOperInfo");
            urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") +
                    "=ezaoFpGA%2FbYeLTtTuSuX%2FikQJkhN%2FSLZiTWeeUUm7bKfp70AUZpOZEw4n7Vu3X%2BoMLCHyoBMvqPGiV3sRFnKFg%3D%3D"); /*Service Key*/
            urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
            urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
            URL url = new URL(urlBuilder.toString());
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));

            String returnLine;
            StringBuilder response = new StringBuilder();

            while((returnLine = bufferedReader.readLine()) != null) {
                response.append(returnLine);
            }
            urlConnection.disconnect();

            // JSON 파싱
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(response.toString(), JsonObject.class);
            JsonObject responseBody = jsonObject.getAsJsonObject("response").getAsJsonObject("body");
            JsonElement itemsElement = responseBody.get("items");

            List<OpenApiResponseDto> openApiResponseDtoList = new ArrayList<>();
            if (itemsElement.isJsonArray()) {
                JsonArray itemArray = itemsElement.getAsJsonArray();

                for (JsonElement itemElement : itemArray) {
                    JsonObject itemObject = itemElement.getAsJsonObject();

                    Long parkID = itemObject.get("parkID").getAsLong();
                    String name = itemObject.get("name").getAsString();
                    String addrDetail = itemObject.get("addrDetail").getAsString();
                    Long totalLots = itemObject.get("totalLots").getAsLong();

                    OpenApiResponseDto responseDto = new OpenApiResponseDto();
                    responseDto.setParkID(parkID);
                    responseDto.setName(name);
                    responseDto.setAddrDetail(addrDetail);
                    responseDto.setTotalLots(totalLots);

                    openApiResponseDtoList.add(responseDto);
                }
                return openApiResponseDtoList;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
