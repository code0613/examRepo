package com.example.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class OpenApiResponseDto {

    private Long parkID;
    private String name;
    private String addrDetail;
    private Long totalLots;
}
