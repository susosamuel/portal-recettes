package com.recettes.portalrecettes.persistence;

import com.recettes.portalrecettes.models.Recettes;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecettesDao extends CrudRepository<Recettes, Integer>{
}