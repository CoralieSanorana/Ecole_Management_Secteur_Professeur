package com.example.back_ecole.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "supports_cours")
public class SupportCours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "affectation_id")
    private Long affectationId;

    @Column(name = "type_fichier_id")
    private Long typeFichierId;

    @Column(nullable = false)
    private String titre;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "fichier_url")
    private String fichierUrl;

    @Column(name = "type_contenu")
    private String typeContenu = "lecon";

    @Column(name = "date_limite")
    private LocalDateTime dateLimite;

    @Column(name = "accepte_retard")
    private Boolean accepteRetard = false;

    @Column(name = "is_archived")
    private Boolean isArchived = false;

    @Column(name = "cree_par")
    private Long creePar;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAffectationId() {
        return affectationId;
    }

    public void setAffectationId(Long affectationId) {
        this.affectationId = affectationId;
    }

    public Long getTypeFichierId() {
        return typeFichierId;
    }

    public void setTypeFichierId(Long typeFichierId) {
        this.typeFichierId = typeFichierId;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFichierUrl() {
        return fichierUrl;
    }

    public void setFichierUrl(String fichierUrl) {
        this.fichierUrl = fichierUrl;
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

    public Boolean getIsArchived() {
        return isArchived;
    }

    public void setIsArchived(Boolean isArchived) {
        this.isArchived = isArchived;
    }

    public Long getCreePar() {
        return creePar;
    }

    public void setCreePar(Long creePar) {
        this.creePar = creePar;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
