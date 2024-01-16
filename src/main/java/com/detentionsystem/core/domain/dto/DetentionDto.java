package com.detentionsystem.core.domain.dto;

import com.detentionsystem.core.domain.enums.OperationType;
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
public class DetentionDto {

    @NotNull(message = "docDate can't be empty. Please enter value")
    @PastOrPresent(message = "docDate must be past.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate docDate;

    @NotEmpty(message = "docNum can't be empty. Please enter value")
    @Size(max = 30, message = "The maximum length of the docNum can be no more than 30 characters. Please enter less value")
    @Pattern(regexp = "[a-zA-Z\\d#№-]+", message = "You enter the wrong character. Valid values: letters of the Latin " +
            "alphabet and symbols '-', '#', '№'")
    private String docNum;

    @NotEmpty(message = "purpose cannot be empty. Please enter value")
    @Size(max = 1000, message = "The maximum length of the purpose can be no more than 1000 characters. Please enter less value")
    private String purpose;


    @Positive(message = "amount can be only positive. Please enter a positive value")
    private Long amount;

    private String refDocNum;

    private OperationType operation;
}
