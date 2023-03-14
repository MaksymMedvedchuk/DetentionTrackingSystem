package com.example.customerarrestsystem.dto;

import lombok.*;

import java.util.List;
import java.util.UUID;

@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ArrestRequest {

    private UUID requestId;
    private String firstName;
    private String lastName;
    //todo one to one
    private IdentityDocument identityDocument;
    //todo organCode, arrest
    private Integer organCode;
    private Arrest arrest;


}
