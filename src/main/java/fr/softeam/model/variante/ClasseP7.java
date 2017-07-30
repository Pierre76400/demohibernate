package fr.softeam.model.variante;

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
public class ClasseP7 {

	@Id
	@GeneratedValue
	@Column(name = "id_classe")
	private long id;

	@Column(name = "nom")
	private String nom;

	@ManyToOne(cascade = CascadeType.ALL)
	private ProfesseurP7 professeur;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "classe", fetch = FetchType.LAZY)
	private Set<EleveP7> eleves = new HashSet<EleveP7>();

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

	public ProfesseurP7 getProfesseur() {
		return professeur;
	}

	public void setProfesseur(ProfesseurP7 professeur) {
		this.professeur = professeur;
	}

	public Set<EleveP7> getEleves() {
		return eleves;
	}

	public void setEleves(Set<EleveP7> eleves) {
		this.eleves = eleves;
	}

	public void addEleve(EleveP7 eleve) {
		eleves.add(eleve);
		eleve.setClasse(this);
	}

	public void removeEleve(EleveP7 eleve) {
		eleve.setClasse(null);
		this.eleves.remove(eleve);
	}
}
// @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)