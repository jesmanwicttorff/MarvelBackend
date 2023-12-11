package com.marvell.service.dto;

import java.io.Serializable;
import java.util.Objects;

public class ZapatosDTO implements Serializable {

    private String id;

    private String modelo;

    private String color;

    private String descripcion;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ZapatosDTO)) {
            return false;
        }

        ZapatosDTO zapatosDTO = (ZapatosDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, zapatosDTO.id);
    }

    @Override
    public String toString() {
        return (
            "ZapatosDTO{" +
            "id=" +
            getId() +
            ", modelo='" +
            getModelo() +
            "'" +
            ", color='" +
            getColor() +
            "'" +
            ", descripcion='" +
            getDescripcion() +
            "'" +
            "}"
        );
    }
}
