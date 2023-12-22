package com.arestmanagement.core.domain.dto;

import com.arestmanagement.core.domain.enums.ExternalIdentityDocumentType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("Type")
    private ExternalIdentityDocumentType type;

    @NotBlank(message = "NumberSeries can't be empty. Please enter value")
    @JsonProperty("NumberSeries")
    private String numberSeries;

    @NotNull(message = "IssueDate can't be empty. Please enter value")
    @PastOrPresent(message = "IssueDate must be past.")
    @JsonProperty("IssueDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate issueDate;
}
