package com.adias.fap.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.adias.fap.domain.enumeration.DimensionType;

/**
 * A DTO for the Dimension entity.
 */
public class DimensionDTO implements Serializable {

    private Long id;

    private String name;

    private DimensionType type;

    private Long costGrideId;

    private Long metricId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DimensionType getType() {
        return type;
    }

    public void setType(DimensionType type) {
        this.type = type;
    }

    public Long getCostGrideId() {
        return costGrideId;
    }

    public void setCostGrideId(Long costGrideId) {
        this.costGrideId = costGrideId;
    }

    public Long getMetricId() {
        return metricId;
    }

    public void setMetricId(Long metricId) {
        this.metricId = metricId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DimensionDTO dimensionDTO = (DimensionDTO) o;
        if(dimensionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dimensionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DimensionDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
