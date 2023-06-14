package com.example.demo.controller;

import com.example.demo.dto.DataMsgResponseDto;
import com.example.demo.global.exception.StatusMsgCode;
import com.example.demo.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ExamController {

    private final ExamService examService;

    @GetMapping("/profile/{id}")
    public ResponseEntity<DataMsgResponseDto> getUserProfile(@PathVariable Long id) {
        return ResponseEntity.ok(new DataMsgResponseDto(StatusMsgCode.SUCCESS,examService.getUserProfile(id)));
    }

    @GetMapping("/history/{id}")
    public ResponseEntity<DataMsgResponseDto> getUserHistory(@PathVariable Long id) {
        return ResponseEntity.ok(new DataMsgResponseDto(StatusMsgCode.SUCCESS,examService.getUserHistory(id)));
    }

    @GetMapping("/departments/{id}")
    public ResponseEntity<DataMsgResponseDto> getDeptLocation(@PathVariable Long id) {
        return ResponseEntity.ok(new DataMsgResponseDto(StatusMsgCode.SUCCESS,examService.getDeptLocation(id)));
    }

    @PostMapping("/raise/{name}/{rate}")
    public ResponseEntity<DataMsgResponseDto> raiseSalary(@PathVariable String name, @PathVariable Double rate) {
        examService.raiseSalary(name,rate);
        return ResponseEntity.ok(new DataMsgResponseDto(StatusMsgCode.SUCCESS));
    }

    @GetMapping("/openApi")
    public ResponseEntity<DataMsgResponseDto> getOpenApi() {
        return ResponseEntity.ok(new DataMsgResponseDto(StatusMsgCode.SUCCESS,examService.getOpenApi()));
    }
}

