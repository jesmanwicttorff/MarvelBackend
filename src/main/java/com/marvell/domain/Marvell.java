package com.marvell.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Marvell.
 */
@Entity
@Table(name = "marvell")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Marvell implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "imagenurl")
    private String imagenurl;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "distribuidoid")
    @JsonIgnoreProperties(value = { "distribuidoid" }, allowSetters = true)
    private Set<Distribuidor> distribuidors = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Marvell id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Marvell nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public Marvell descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagenurl() {
        return this.imagenurl;
    }

    public Marvell imagenurl(String imagenurl) {
        this.setImagenurl(imagenurl);
        return this;
    }

    public void setImagenurl(String imagenurl) {
        this.imagenurl = imagenurl;
    }

    public Set<Distribuidor> getDistribuidors() {
        return this.distribuidors;
    }

    public void setDistribuidors(Set<Distribuidor> distribuidors) {
        if (this.distribuidors != null) {
            this.distribuidors.forEach(i -> i.setDistribuidoid(null));
        }
        if (distribuidors != null) {
            distribuidors.forEach(i -> i.setDistribuidoid(this));
        }
        this.distribuidors = distribuidors;
    }

    public Marvell distribuidors(Set<Distribuidor> distribuidors) {
        this.setDistribuidors(distribuidors);
        return this;
    }

    public Marvell addDistribuidor(Distribuidor distribuidor) {
        this.distribuidors.add(distribuidor);
        distribuidor.setDistribuidoid(this);
        return this;
    }

    public Marvell removeDistribuidor(Distribuidor distribuidor) {
        this.distribuidors.remove(distribuidor);
        distribuidor.setDistribuidoid(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Marvell)) {
            return false;
        }
        return getId() != null && getId().equals(((Marvell) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Marvell{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", imagenurl='" + getImagenurl() + "'" +
            "}";
    }
}
