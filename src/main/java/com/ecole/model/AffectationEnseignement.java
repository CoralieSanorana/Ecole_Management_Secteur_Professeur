package com.example.back_ecole.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "affectations_enseignement", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"matiere_id", "classe_id", "annee_scolaire_id"})
})
public class AffectationEnseignement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "professeur_id")
    private Long professeurId;

    @Column(name = "matiere_id")
    private Long matiereId;

    @Column(name = "classe_id")
    private Long classeId;

    @Column(name = "annee_scolaire_id")
    private Long anneeScolaireId;

    @Column(name = "heures_hebdo", precision = 4, scale = 1)
    private BigDecimal heuresHebdo;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProfesseurId() {
        return professeurId;
    }

    public void setProfesseurId(Long professeurId) {
        this.professeurId = professeurId;
    }

    public Long getMatiereId() {
        return matiereId;
    }

    public void setMatiereId(Long matiereId) {
        this.matiereId = matiereId;
    }

    public Long getClasseId() {
        return classeId;
    }

    public void setClasseId(Long classeId) {
        this.classeId = classeId;
    }

    public Long getAnneeScolaireId() {
        return anneeScolaireId;
    }

    public void setAnneeScolaireId(Long anneeScolaireId) {
        this.anneeScolaireId = anneeScolaireId;
    }

    public BigDecimal getHeuresHebdo() {
        return heuresHebdo;
    }

    public void setHeuresHebdo(BigDecimal heuresHebdo) {
        this.heuresHebdo = heuresHebdo;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
