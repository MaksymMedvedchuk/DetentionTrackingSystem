package com.arestmanagement.dto;

import com.arestmanagement.util.OrganizationCode;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ArrestRequestDto {

    @JsonProperty("RequestId")
    private UUID requestId;

    @NotEmpty(message = "FirstName can't be empty. Please enter value")
    @Pattern(regexp = "[A-Za-z\\-]+", message = "You enter the wrong character. Valid values: letters of the Latin alphabet and the symbol '-'")
    @Size(max = 100, message = "The maximum length of the FirstName can be no more than 100 characters. Please enter less value")
    @JsonProperty("FirstName")
    private String firstName;

    @NotEmpty(message = "LastName can't be empty. Please enter value")
    @Pattern(regexp = "[A-Za-z\\-]+", message = "You enter the wrong character. Valid values: letters of the Latin alphabet and the symbol '-'")
    @JsonProperty("LastName")
    @Size(max = 100, message = "The maximum length of the LastName can be no more than 100 characters. Please enter less value")
    private String lastName;

    @JsonProperty("IdentDoc")
    @Valid
    private IdentityDocumentDto identityDocumentDto;

    @JsonProperty("OrganCode")
    private OrganizationCode organCode;

    @JsonProperty("Arrest")
    @Valid
    private ArrestDto arrestDto;
}
