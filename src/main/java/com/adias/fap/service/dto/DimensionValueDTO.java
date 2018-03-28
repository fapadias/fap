package com.adias.fap.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the DimensionValue entity.
 */
public class DimensionValueDTO implements Serializable {

    private Long id;

    private String stringValue;

    private String lowerBound;

    private String upperBound;

    private Long dimensionId;

    private Long metricValueId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public String getLowerBound() {
        return lowerBound;
    }

    public void setLowerBound(String lowerBound) {
        this.lowerBound = lowerBound;
    }

    public String getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(String upperBound) {
        this.upperBound = upperBound;
    }

    public Long getDimensionId() {
        return dimensionId;
    }

    public void setDimensionId(Long dimensionId) {
        this.dimensionId = dimensionId;
    }

    public Long getMetricValueId() {
        return metricValueId;
    }

    public void setMetricValueId(Long metricValueId) {
        this.metricValueId = metricValueId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DimensionValueDTO dimensionValueDTO = (DimensionValueDTO) o;
        if(dimensionValueDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dimensionValueDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DimensionValueDTO{" +
            "id=" + getId() +
            ", stringValue='" + getStringValue() + "'" +
            ", lowerBound='" + getLowerBound() + "'" +
            ", upperBound='" + getUpperBound() + "'" +
            "}";
    }
}
