package com.marvell.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.marvell.domain.Distribuidor} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DistribuidorDTO implements Serializable {

    private Long id;

    private String nombre;

    private String sitioWeb;

    private MarvellDTO distribuidoid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSitioWeb() {
        return sitioWeb;
    }

    public void setSitioWeb(String sitioWeb) {
        this.sitioWeb = sitioWeb;
    }

    public MarvellDTO getDistribuidoid() {
        return distribuidoid;
    }

    public void setDistribuidoid(MarvellDTO distribuidoid) {
        this.distribuidoid = distribuidoid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DistribuidorDTO)) {
            return false;
        }

        DistribuidorDTO distribuidorDTO = (DistribuidorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, distribuidorDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DistribuidorDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", sitioWeb='" + getSitioWeb() + "'" +
            ", distribuidoid=" + getDistribuidoid() +
            "}";
    }
}
