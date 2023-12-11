package com.marvell.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.marvell.domain.Marvell} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MarvellDTO implements Serializable {

    private Long id;

    private String nombre;

    private String descripcion;

    private String imagenurl;

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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagenurl() {
        return imagenurl;
    }

    public void setImagenurl(String imagenurl) {
        this.imagenurl = imagenurl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MarvellDTO)) {
            return false;
        }

        MarvellDTO marvellDTO = (MarvellDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, marvellDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MarvellDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", imagenurl='" + getImagenurl() + "'" +
            "}";
    }
}
