package com.gav.docsapp.repositories;

import com.gav.docsapp.entities.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocRepository extends JpaRepository<Document,Long> {
    boolean existsByNumber(String number);
}
