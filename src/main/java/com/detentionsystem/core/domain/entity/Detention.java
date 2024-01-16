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
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

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
    @PastOrPresent
    private LocalDate docDate;

    @Column(name = "doc_num", unique = true)
    @Size(max = 30, message = "The maximum length of the document number can be no more than 30 characters. Please enter less value")
    @Pattern(regexp = "[a-zA-Z\\d#№-]+", message = "You enter the wrong character. Please enter allowed symbols letters " +
            "of the Latin alphabet, numbers, and symbol #, №, -")
    private String docNum;

    @Column(name = "purpose")
    @Size(max = 1000, message = "The maximum length of the purpose can be no more than 1000 characters. Please enter less value")
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
