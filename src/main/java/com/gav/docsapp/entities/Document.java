package com.gav.docsapp.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "docs", indexes = @Index(columnList = "number"))
@Getter
@Setter
public class Document extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, name = "number")

    private String number;
    @Column(name = "type")
    private String type;
    @Column(name = "date")
    private LocalDate date;

    @Column(name = "file_name")
    private String fileName;
    @Column(name = "doc_file")
    private byte[] doc;




}
