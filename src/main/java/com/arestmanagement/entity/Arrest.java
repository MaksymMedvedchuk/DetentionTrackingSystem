package com.example.customerarrestsystem.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "arrests")
@Getter
@Setter
@ToString
public class Arrest extends BaseEntity {

    @Column(name = "organ_code")
    //todo make all dictionaries enums
    private int organCode;

    @Column(name = "doc_date")
    private Date docDate;

    @Column(name = "doc_num")
    private String docNum;

    @Column(name = "purpose")
    private String purpose;

    @Column(name = "ref_doc_num")
    private String refDocNum;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "operation_type")
    //todo make status
    private int operationType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    @JsonBackReference //todo remove
    private Person person;
}
