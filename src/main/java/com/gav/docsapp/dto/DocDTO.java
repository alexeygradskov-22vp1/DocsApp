package com.gav.docsapp.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
@Getter
@Setter
public class DocDTO {

    private String number;

    private String type;

    private Instant date;

    public MultipartFile doc;

}
