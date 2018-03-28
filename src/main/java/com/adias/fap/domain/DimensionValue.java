package com.adias.fap.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DimensionValue.
 */
@Entity
@Table(name = "dimension_value")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DimensionValue implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "string_value")
    private String stringValue;

    @Column(name = "lower_bound")
    private String lowerBound;

    @Column(name = "upper_bound")
    private String upperBound;

    @ManyToOne
    private Dimension dimension;

    @ManyToOne
    private MetricValue metricValue;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStringValue() {
        return stringValue;
    }

    public DimensionValue stringValue(String stringValue) {
        this.stringValue = stringValue;
        return this;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public String getLowerBound() {
        return lowerBound;
    }

    public DimensionValue lowerBound(String lowerBound) {
        this.lowerBound = lowerBound;
        return this;
    }

    public void setLowerBound(String lowerBound) {
        this.lowerBound = lowerBound;
    }

    public String getUpperBound() {
        return upperBound;
    }

    public DimensionValue upperBound(String upperBound) {
        this.upperBound = upperBound;
        return this;
    }

    public void setUpperBound(String upperBound) {
        this.upperBound = upperBound;
    }

    public Dimension getDimension() {
        return dimension;
    }

    public DimensionValue dimension(Dimension dimension) {
        this.dimension = dimension;
        return this;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    public MetricValue getMetricValue() {
        return metricValue;
    }

    public DimensionValue metricValue(MetricValue metricValue) {
        this.metricValue = metricValue;
        return this;
    }

    public void setMetricValue(MetricValue metricValue) {
        this.metricValue = metricValue;
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
        DimensionValue dimensionValue = (DimensionValue) o;
        if (dimensionValue.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dimensionValue.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DimensionValue{" +
            "id=" + getId() +
            ", stringValue='" + getStringValue() + "'" +
            ", lowerBound='" + getLowerBound() + "'" +
            ", upperBound='" + getUpperBound() + "'" +
            "}";
    }
}
