package fr.softeam.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Classe {

	@Id
	@GeneratedValue
	private long id;

	@Column(name = "nom")
	private String nom;

	@ManyToOne(cascade = CascadeType.ALL)
	private Professeur professeur;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "classe", fetch = FetchType.LAZY)
	private Set<Eleve> eleves = new HashSet<Eleve>();

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

	public Professeur getProfesseur() {
		return professeur;
	}

	public void setProfesseur(Professeur professeur) {
		this.professeur = professeur;
	}

	public Set<Eleve> getEleves() {
		return eleves;
	}

	public void setEleves(Set<Eleve> eleves) {
		this.eleves = eleves;
	}

	public void addEleve(Eleve eleve) {
		eleves.add(eleve);
		eleve.setClasse(this);
	}

	public void removeEleve(Eleve eleve) {
		eleve.setClasse(null);
		this.eleves.remove(eleve);
	}
}
