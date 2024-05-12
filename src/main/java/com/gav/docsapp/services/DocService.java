package com.gav.docsapp.services;

import com.gav.docsapp.entities.Document;
import com.gav.docsapp.lists.DocList;
import com.gav.docsapp.repositories.DocRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class DocService extends BaseService<Document, DocRepository> {

    public DocService(DocRepository docRepository){
        super(docRepository);
    }
    @Override
    public void add(Document elem) throws IllegalArgumentException {
        if (!_repository.existsByNumber(elem.getNumber()))this._repository.save(elem);
        else throw new IllegalArgumentException("Уже существует");
    }

    @Override
    public Optional<Document> getOne(long id) {
        return this._repository.findById(id);
    }

    @Override
    public DocList getAll() {
        return new DocList(this._repository.findAll());
    }

    @Override
    public void update(Document updatable) {
        if (_repository.existsById(updatable.getId())) {
            _repository.save(updatable);
        } else {
            throw new IllegalArgumentException("Не существует");
        }
    }

    @Override
    public void delete(long id) {
        if (_repository.existsById(id)) {
            _repository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Не существует");
        }
    }



}
