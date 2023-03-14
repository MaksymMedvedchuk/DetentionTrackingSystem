package com.example.customerarrestsystem.dto;

import lombok.*;

import java.util.Date;

@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IdentityDocument {

    private int type;
    private String numberSeries;
    private Date issueDate;

}
