package com.example.customerarrestsystem.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Arrest {
    //todo same names as in spec
    //todo do not use Date class - consider java 8 datetime classes
    private LocalDate docDate;
    private String docNum;
    private String purpose;
    private Long amount;
    private String refDocNum;
    //todo object Int
    private Integer operation;


}
