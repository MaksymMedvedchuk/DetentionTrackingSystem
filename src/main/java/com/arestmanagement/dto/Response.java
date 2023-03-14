package com.example.customerarrestsystem.dto;

import lombok.*;

@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Response {

    private Integer arrestId;
    private Integer resultCode;
    private String resultText;
}
