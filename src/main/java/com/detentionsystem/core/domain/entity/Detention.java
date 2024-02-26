package com.detentionsystem.core.domain.entity;

import com.detentionsystem.core.converter.OrganizationCodeConverter;
import com.detentionsystem.core.converter.StatusTypeConverter;
import com.detentionsystem.core.domain.enums.OrganizationCode;
import com.detentionsystem.core.domain.enums.StatusType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Table(name = "detentions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "person")
@Builder
@EqualsAndHashCode(callSuper = false)
public class Detention extends BaseEntity {

	@Column(name = "organ_code")
	@Convert(converter = OrganizationCodeConverter.class)
	private OrganizationCode organizationCode;

	@Column(name = "doc_date")
	private LocalDate docDate;

	@Column(name = "doc_num", unique = true)
	private String docNum;

	@Column(name = "purpose")
	private String purpose;

	@Column(name = "amount")
	private Long amount;

	@Column(name = "status_type")
	@Convert(converter = StatusTypeConverter.class)
	private StatusType statusType;

	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinColumn(name = "person")
	private Person person;
}
