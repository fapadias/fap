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
 * A Metric.
 */
@Entity
@Table(name = "metric")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Metric implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "metric")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Dimension> dimensions = new HashSet<>();

    @ManyToOne
    private CostGride costGride;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Metric name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Dimension> getDimensions() {
        return dimensions;
    }

    public Metric dimensions(Set<Dimension> dimensions) {
        this.dimensions = dimensions;
        return this;
    }

    public Metric addDimensions(Dimension dimension) {
        this.dimensions.add(dimension);
        dimension.setMetric(this);
        return this;
    }

    public Metric removeDimensions(Dimension dimension) {
        this.dimensions.remove(dimension);
        dimension.setMetric(null);
        return this;
    }

    public void setDimensions(Set<Dimension> dimensions) {
        this.dimensions = dimensions;
    }

    public CostGride getCostGride() {
        return costGride;
    }

    public Metric costGride(CostGride costGride) {
        this.costGride = costGride;
        return this;
    }

    public void setCostGride(CostGride costGride) {
        this.costGride = costGride;
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
        Metric metric = (Metric) o;
        if (metric.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), metric.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Metric{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
