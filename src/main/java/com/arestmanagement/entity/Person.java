package com.example.customerarrestsystem.entity;

import com.example.customerarrestsystem.converter.IdentityDocumentTypeConverter;
import com.example.customerarrestsystem.util.IdentityDocumentType;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "persons")
@Getter
@Setter
@ToString
public class Person extends BaseEntity {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "birthplace")
    private String birthplace;

    //todo ident_doc_type
    @Column(name = "type_ident_doc")
    @Convert(converter = IdentityDocumentTypeConverter.class)
    private IdentityDocumentType typeIdentDoc;

    //todo doc_number_series
    @Column(name = "number_series_doc")
    private String numberSeriesDoc;

    @Column(name = "issue_date")
    private Date issueDate;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    //todo remove JsonManagedReference
    @JsonManagedReference
    private List<Arrest> arrests;


}
