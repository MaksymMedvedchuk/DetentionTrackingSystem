package com.detentionsystem.core.domain.dto;

import com.detentionsystem.core.domain.enums.ResultCode;
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

	private Long arrestId;

	private ResultCode resultCode;

	private String resultText;

	public ResponseDto(ResultCode resultCode, String resultText) {
		this.resultCode = resultCode;
		this.resultText = resultText;
	}
}
