package fr.softeam.model.variante;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class EleveP3 {

	@Id
	@GeneratedValue
	@Column(name = "id_eleve")
	private long id;

	@Column(name = "nom")
	private String nom;

	@Column(name = "prenom")
	private String prenom;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

}
