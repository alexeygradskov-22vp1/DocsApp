package com.gav.docsapp.lists;

import com.gav.docsapp.entities.Document;
import com.gav.docsapp.services.sortTypes.DocType;
import lombok.Getter;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class DocList extends BaseList<Document> {


    public DocList(List<Document> documents){
        super(documents);

    }
    @Override
    public DocList sort(DocType docType) {
        Comparator<Document> comparator = switch (docType) {
            case Date -> Comparator.comparing(Document::getDate);
            case Number -> Comparator.comparing(Document::getNumber);
        };
        _list = _list.stream().sorted(comparator).collect(Collectors.toList());
        return this;
    }

    public DocList filter(DocType docType,String value){
        Predicate<Document> predicate = (x)-> x.getDate().equals(LocalDate.parse(value));

        _list = _list.stream().filter(predicate).collect(Collectors.toList());
        return this;
    }

    public List<Document> getDocs(){
        return _list;
    }

    public DocList search(String value){
        Predicate<Document> predicate = (x)->x.getNumber().contains(value);
        _list = _list.stream().filter(predicate).collect(Collectors.toList());
        return this;
    }
}
