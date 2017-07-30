package fr.softeam.model.variante;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class EleveP7 {

	@Id
	@GeneratedValue
	@Column(name = "id_eleve")
	private long id;

	@Column(name = "nom")
	private String nom;

	@Column(name = "prenom")
	private String prenom;

	@ManyToOne
	private ClasseP7 classe;

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

	public ClasseP7 getClasse() {
		return classe;
	}

	public void setClasse(ClasseP7 classe) {
		this.classe = classe;
	}

}
