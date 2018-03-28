package com.adias.fap.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the CostGride entity.
 */
public class CostGrideDTO implements Serializable {

    private Long id;

    private String fileName;

    private String version;

    private Long transporterId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Long getTransporterId() {
        return transporterId;
    }

    public void setTransporterId(Long transporterId) {
        this.transporterId = transporterId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CostGrideDTO costGrideDTO = (CostGrideDTO) o;
        if(costGrideDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), costGrideDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CostGrideDTO{" +
            "id=" + getId() +
            ", fileName='" + getFileName() + "'" +
            ", version='" + getVersion() + "'" +
            "}";
    }
}
