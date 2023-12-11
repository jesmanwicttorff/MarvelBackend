package com.marvell.repository;

import com.marvell.domain.Zapatos;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ZapatosRepository extends MongoRepository<Zapatos, String> {
    List<Zapatos> findByModelo(String modelo);
}
