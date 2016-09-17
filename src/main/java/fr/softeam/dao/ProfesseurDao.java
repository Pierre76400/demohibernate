package fr.softeam.dao;

import org.springframework.data.repository.CrudRepository;

import fr.softeam.model.Professeur;

public interface ProfesseurDao extends CrudRepository<Professeur, Long> {

}
