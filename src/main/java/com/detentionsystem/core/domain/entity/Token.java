package com.detentionsystem.core.domain.entity;

import com.detentionsystem.core.domain.enums.TokenType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "tokens")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Token {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	@Size(max = 1000)
	private String tokenValue;

	@Column
	@Enumerated(EnumType.STRING)
	private TokenType tokenType;

	@Column
	private LocalDateTime expirationDate;

	@Column(name = "user_id")
	private Long userId;
}
