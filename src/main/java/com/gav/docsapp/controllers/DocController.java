package com.gav.docsapp.controllers;

import com.gav.docsapp.entities.Document;
import com.gav.docsapp.lists.DocList;
import com.gav.docsapp.services.DocService;
import com.gav.docsapp.services.sortTypes.DocType;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/api/docs")
public class DocController {
    private final DocService _docService;

    public DocController(DocService docService) {
        _docService = docService;
    }

    @RequestMapping("/")
    private String getAll(@RequestParam(required = false) Optional<String> filterBy,
                          @RequestParam(required = false) Optional<String> filterValue,
                          @RequestParam(required = false) Optional<String> orderBy,
                          @RequestParam(required = false) Optional<String> search,
                          Model model) {
        DocList result = _docService.getAll();

        try {

            if (filterBy.isPresent() && filterValue.isPresent()&&!filterValue.get().isEmpty()) {
                result = result.filter(DocType.valueOf(filterBy.get()), filterValue.get());
            }
            if (orderBy.isPresent()) result = result.sort(DocType.valueOf(orderBy.get()));
            if (search.isPresent()) result = result.search(search.get());
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
        }
        model.addAttribute("listOfDocs", result.getDocs());
        return "Main";
    }
    @RequestMapping("/upload")
    private String getUploadPage(){
        return "create";
    }

    @PostMapping(value = "/upload")
    private String add(@RequestParam(name = "number") String number,
                                  @RequestParam(name = "type") String type,
                                  @RequestParam(name = "date") String date,
                                  @RequestParam(name = "doc") MultipartFile doc,
                       Model model) {
        Document document = new Document();
        document.setDate(LocalDate.parse(date));
        document.setType(type);
        document.setNumber(number);
        document.setFileName(doc.getOriginalFilename());
        try {
            document.setDoc(doc.getBytes());
            _docService.add(document);
        } catch (IOException|IllegalArgumentException e) {
            return "create";
        }
        return "redirect:/api/docs/";
    }

    @PutMapping("/{docId}/update")
    private ResponseEntity<?> update(@PathVariable(name = "docId") Long docId,
                                     @RequestParam(name = "number") String number,
                                     @RequestParam(name = "type") String type,
                                     @RequestParam(name = "date") String date,
                                     @RequestParam(name = "doc") MultipartFile doc) {

        Document document = new Document();
        document.setId(docId);
        document.setDate(LocalDate.parse(date));
        document.setType(type);
        document.setNumber(number);
        try {
            document.setDoc(doc.getBytes());
            _docService.update(document);
        } catch (IOException | IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Документ сохранен", HttpStatus.OK);
    }

    @DeleteMapping("/{docId}/delete")
    private ResponseEntity<?> update(@PathVariable(name = "docId") Long docId) {
        try {
            _docService.delete(docId);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Документ сохранен", HttpStatus.OK);
    }

    @RequestMapping(path = "/{docId}/download", method = RequestMethod.GET)
    public ResponseEntity<?> download(@PathVariable("docId") Long docID) throws IOException {
        Optional<Document> document = _docService.getOne(docID);
        if (document.isPresent()) {
            InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(document.get().getDoc()));
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+document.get().getFileName());
            return ResponseEntity.ok().headers(headers)
                    .contentLength(document.get().getDoc().length)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        }
        return ResponseEntity.badRequest().body("Неизвестная ошибка");
    }


}
