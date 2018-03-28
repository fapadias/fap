package com.adias.fap.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the MetricValue entity.
 */
public class MetricValueDTO implements Serializable {

    private Long id;

    private Double value;

    private String faceValue;

    private Long metricId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getFaceValue() {
        return faceValue;
    }

    public void setFaceValue(String faceValue) {
        this.faceValue = faceValue;
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

        MetricValueDTO metricValueDTO = (MetricValueDTO) o;
        if(metricValueDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), metricValueDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MetricValueDTO{" +
            "id=" + getId() +
            ", value=" + getValue() +
            ", faceValue='" + getFaceValue() + "'" +
            "}";
    }
}
