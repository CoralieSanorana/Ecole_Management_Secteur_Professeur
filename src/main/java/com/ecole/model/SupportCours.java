package com.ecole.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "supports_cours")
public class SupportCours {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "affectation_id")
    private AffectationEnseignement affectation;

    @ManyToOne
    @JoinColumn(name = "type_fichier_id")
    private TypeFichier typeFichier;

    @Column(nullable = false)
    private String titre;

    @Column(name = "url_fichier", length = 500, nullable = false)
    private String urlFichier;

    @Column(name = "type_contenu")
    private String typeContenu; // 'cours' or 'devoir'

    @Column(name = "date_limite")
    private LocalDateTime dateLimite;

    @Column(name = "accepte_retard")
    private Boolean accepteRetard = false;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public SupportCours() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AffectationEnseignement getAffectation() {
        return affectation;
    }

    public void setAffectation(AffectationEnseignement affectation) {
        this.affectation = affectation;
    }

    public TypeFichier getTypeFichier() {
        return typeFichier;
    }

    public void setTypeFichier(TypeFichier typeFichier) {
        this.typeFichier = typeFichier;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getUrlFichier() {
        return urlFichier;
    }

    public void setUrlFichier(String urlFichier) {
        this.urlFichier = urlFichier;
    }

    public String getTypeContenu() {
        return typeContenu;
    }

    public void setTypeContenu(String typeContenu) {
        this.typeContenu = typeContenu;
    }

    public LocalDateTime getDateLimite() {
        return dateLimite;
    }

    public void setDateLimite(LocalDateTime dateLimite) {
        this.dateLimite = dateLimite;
    }

    public Boolean getAccepteRetard() {
        return accepteRetard;
    }

    public void setAccepteRetard(Boolean accepteRetard) {
        this.accepteRetard = accepteRetard;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}