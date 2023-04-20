package com.arestmanagement.dto;

import com.arestmanagement.util.ResultCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

@ToString
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ResponseDto {

    @JsonProperty("ArrestId")
    private Long arrestId;

    @JsonProperty("ResultCode")
    private ResultCode resultCode;

    @JsonProperty("ResultText")
    private String resultText;

    public ResponseDto(ResultCode resultCode, String resultText) {
        this.resultCode = resultCode;
        this.resultText = resultText;
    }
}
