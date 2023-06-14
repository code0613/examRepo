package com.example.demo.controller;

import com.example.demo.dto.DataMsgResponseDto;
import com.example.demo.global.exception.StatusMsgCode;
import com.example.demo.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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

    @GetMapping("/history")
    public ResponseEntity<DataMsgResponseDto> getAllUserHistory() {
        return ResponseEntity.ok(new DataMsgResponseDto(StatusMsgCode.SUCCESS,examService.getAllUserHistory()));
    }
}

