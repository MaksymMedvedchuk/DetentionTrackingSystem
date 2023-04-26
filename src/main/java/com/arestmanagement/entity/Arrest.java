package com.arestmanagement.entity;


import com.arestmanagement.converter.OrganizationCodeConverter;
import com.arestmanagement.converter.StatusTypeConverter;
import com.arestmanagement.util.OrganizationCode;
import com.arestmanagement.util.StatusType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "arrests")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "person")
@Builder
@EqualsAndHashCode(callSuper = true)
public class Arrest extends BaseEntity {

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
