package com.marvell.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Distribuidor.
 */
@Entity
@Table(name = "distribuidor")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Distribuidor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "sitio_web")
    private String sitioWeb;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "distribuidors" }, allowSetters = true)
    private Marvell distribuidoid;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Distribuidor id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Distribuidor nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSitioWeb() {
        return this.sitioWeb;
    }

    public Distribuidor sitioWeb(String sitioWeb) {
        this.setSitioWeb(sitioWeb);
        return this;
    }

    public void setSitioWeb(String sitioWeb) {
        this.sitioWeb = sitioWeb;
    }

    public Marvell getDistribuidoid() {
        return this.distribuidoid;
    }

    public void setDistribuidoid(Marvell marvell) {
        this.distribuidoid = marvell;
    }

    public Distribuidor distribuidoid(Marvell marvell) {
        this.setDistribuidoid(marvell);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Distribuidor)) {
            return false;
        }
        return getId() != null && getId().equals(((Distribuidor) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Distribuidor{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", sitioWeb='" + getSitioWeb() + "'" +
            "}";
    }
}
