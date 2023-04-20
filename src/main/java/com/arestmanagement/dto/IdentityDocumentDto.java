package com.arestmanagement.dto;

import com.arestmanagement.util.ExternalIdentityDocumentType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate issueDate;
}
