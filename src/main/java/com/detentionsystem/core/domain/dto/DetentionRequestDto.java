package com.detentionsystem.core.domain.dto;

import com.detentionsystem.core.domain.enums.OrganizationCode;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class DetentionRequestDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotEmpty(message = "firstName can't be empty. Please enter value")
    @Pattern(regexp = "[A-Za-z\\-]+", message = "You enter the wrong character. Valid values: letters of the Latin alphabet and the symbol '-'")
    @Size(max = 100, message = "The maximum length of the firstName can be no more than 100 characters. Please enter less value")
    @Schema(example = "Maks")
    private String firstName;

    @NotEmpty(message = "lastName can't be empty. Please enter value")
    @Pattern(regexp = "[A-Za-z\\-]+", message = "You enter the wrong character. Valid values: letters of the Latin alphabet and the symbol '-'")
    @Size(max = 100, message = "The maximum length of the lastName can be no more than 100 characters. Please enter less value")
    @Schema(example = "Medved")
    private String lastName;

    @Valid
    private IdentityDocumentDto identityDocumentDto;

    private OrganizationCode organCode;

    @Valid
    private DetentionDto detentionDto;

    @Email
    private String email;
}
