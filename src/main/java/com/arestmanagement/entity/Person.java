package com.arestmanagement.entity;

import com.arestmanagement.converter.InnerIdentityDocumentTypeConverter;
import com.arestmanagement.converter.LocalDateConverter;
import com.arestmanagement.util.InternalIdentityDocumentType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "persons")
@Getter
@Setter
@ToString(exclude = "arrests")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Person extends BaseEntity {

    @Column(name = "first_name")
    @Size(max = 100, message = "The maximum length of the first name can be no more than 100 characters. Please enter less value")
    private String firstName;

    @Column(name = "last_name")
    @Size(max = 100, message = "The maximum length of the last name can be no more than 100 characters. Please enter less value")
    private String lastName;

    @Column(name = "birthday")
    @Past
    @Convert(converter = LocalDateConverter.class)
    private LocalDate birthday;

    @Column(name = "birthplace")
    @Size(max = 250, message = "The maximum length of the birth place can be no more than 250 characters. Please enter less value")
    private String birthplace;

    @Column(name = "ident_doc_type")
    @Convert(converter = InnerIdentityDocumentTypeConverter.class)
    private InternalIdentityDocumentType identDocType;

    @Column(name = "doc_number_series")
    private String docNumberSeries;

    @Column(name = "issue_date")
    @PastOrPresent
    private LocalDate issueDate;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private List<Arrest> arrests;

}
