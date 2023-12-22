package com.arestmanagement.core.domain.dto;

import com.arestmanagement.core.domain.enums.ResultCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
