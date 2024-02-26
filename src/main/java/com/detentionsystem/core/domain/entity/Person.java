package com.detentionsystem.core.domain.entity;

import com.detentionsystem.core.converter.InnerIdentityDocumentTypeConverter;
import com.detentionsystem.core.converter.LocalDateConverter;
import com.detentionsystem.core.domain.enums.InternalIdentityDocumentType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "persons")
@Getter
@Setter
@ToString(exclude = "detentions")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class Person extends BaseEntity {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "birthday")
    @Convert(converter = LocalDateConverter.class)
    private LocalDate birthday;

    @Column(name = "birthplace")
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
    private List<Detention> detentions;

    @Column(unique = true, nullable = false)
    private String email;
}
