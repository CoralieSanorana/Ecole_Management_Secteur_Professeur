package com.example.back_ecole.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "type_id")
    private Long typeId;

    @Column(nullable = false)
    private String titre;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @Column(name = "lien_action")
    private String lienAction;

    @Column(name = "est_lu")
    private Boolean estLu = false;

    @Column(name = "date_lecture")
    private LocalDateTime dateLecture;

    @Column(name = "entite_type")
    private String entiteType;

    @Column(name = "entite_id")
    private Long entiteId;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLienAction() {
        return lienAction;
    }

    public void setLienAction(String lienAction) {
        this.lienAction = lienAction;
    }

    public Boolean getEstLu() {
        return estLu;
    }

    public void setEstLu(Boolean estLu) {
        this.estLu = estLu;
    }

    public LocalDateTime getDateLecture() {
        return dateLecture;
    }

    public void setDateLecture(LocalDateTime dateLecture) {
        this.dateLecture = dateLecture;
    }

    public String getEntiteType() {
        return entiteType;
    }

    public void setEntiteType(String entiteType) {
        this.entiteType = entiteType;
    }

    public Long getEntiteId() {
        return entiteId;
    }

    public void setEntiteId(Long entiteId) {
        this.entiteId = entiteId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
