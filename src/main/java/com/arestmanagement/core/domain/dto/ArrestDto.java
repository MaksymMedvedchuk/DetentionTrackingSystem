package com.arestmanagement.core.domain.dto;

import com.arestmanagement.core.domain.enums.OperationType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArrestDto {

    @NotNull(message = "DocDate can't be empty. Please enter value")
    @PastOrPresent(message = "DocDate must be past.")
    @JsonProperty("DocDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate docDate;

    @NotEmpty(message = "DocNum can't be empty. Please enter value")
    @Size(max = 30, message = "The maximum length of the DocNum can be no more than 30 characters. Please enter less value")
    @Pattern(regexp = "[a-zA-Z\\d#№-]+", message = "You enter the wrong character. Valid values: letters of the Latin " +
            "alphabet and symbols '-', '#', '№'")
    @JsonProperty("DocNum")
    private String docNum;

    @NotEmpty(message = "Purpose cannot be empty. Please enter value")
    @Size(max = 1000, message = "The maximum length of the Purpose can be no more than 1000 characters. Please enter less value")
    @JsonProperty("Purpose")
    private String purpose;


    @Positive(message = "Amount can be only positive. Please enter a positive value")
    @JsonProperty("Amount")
    private Long amount;

    @JsonProperty("RefDocNum")
    private String refDocNum;

    @JsonProperty("Operation")
    private OperationType operation;
}
