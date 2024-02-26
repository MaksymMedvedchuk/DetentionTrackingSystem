package com.detentionsystem.core.domain.dto;

import com.detentionsystem.core.domain.enums.ExternalIdentityDocumentType;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.time.LocalDate;

@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IdentityDocumentDto {

    private ExternalIdentityDocumentType organPassportCode;

    @NotBlank(message = "numberSeries can't be empty. Please enter value")
    private String numberSeries;

    @NotNull(message = "issueDate can't be empty. Please enter value")
    @PastOrPresent(message = "issueDate must be past.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate issueDate;
}
