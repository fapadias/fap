package com.adias.fap.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A MetricValue.
 */
@Entity
@Table(name = "metric_value")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MetricValue implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "jhi_value")
    private Double value;

    @Column(name = "face_value")
    private String faceValue;

    @OneToMany(mappedBy = "metricValue")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DimensionValue> dimensions = new HashSet<>();

    @ManyToOne
    private Metric metric;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getValue() {
        return value;
    }

    public MetricValue value(Double value) {
        this.value = value;
        return this;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getFaceValue() {
        return faceValue;
    }

    public MetricValue faceValue(String faceValue) {
        this.faceValue = faceValue;
        return this;
    }

    public void setFaceValue(String faceValue) {
        this.faceValue = faceValue;
    }

    public Set<DimensionValue> getDimensions() {
        return dimensions;
    }

    public MetricValue dimensions(Set<DimensionValue> dimensionValues) {
        this.dimensions = dimensionValues;
        return this;
    }

    public MetricValue addDimensions(DimensionValue dimensionValue) {
        this.dimensions.add(dimensionValue);
        dimensionValue.setMetricValue(this);
        return this;
    }

    public MetricValue removeDimensions(DimensionValue dimensionValue) {
        this.dimensions.remove(dimensionValue);
        dimensionValue.setMetricValue(null);
        return this;
    }

    public void setDimensions(Set<DimensionValue> dimensionValues) {
        this.dimensions = dimensionValues;
    }

    public Metric getMetric() {
        return metric;
    }

    public MetricValue metric(Metric metric) {
        this.metric = metric;
        return this;
    }

    public void setMetric(Metric metric) {
        this.metric = metric;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MetricValue metricValue = (MetricValue) o;
        if (metricValue.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), metricValue.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MetricValue{" +
            "id=" + getId() +
            ", value=" + getValue() +
            ", faceValue='" + getFaceValue() + "'" +
            "}";
    }
}
