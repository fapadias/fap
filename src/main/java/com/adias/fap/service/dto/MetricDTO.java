package com.adias.fap.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Metric entity.
 */
public class MetricDTO implements Serializable {

    private Long id;

    private String name;

    private Long costGrideId;

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

    public Long getCostGrideId() {
        return costGrideId;
    }

    public void setCostGrideId(Long costGrideId) {
        this.costGrideId = costGrideId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MetricDTO metricDTO = (MetricDTO) o;
        if(metricDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), metricDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MetricDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
