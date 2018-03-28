package com.adias.fap.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import com.adias.fap.domain.enumeration.DimensionType;

/**
 * A Dimension.
 */
@Entity
@Table(name = "dimension")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Dimension implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private DimensionType type;

    @ManyToOne
    private CostGride costGride;

    @ManyToOne
    private Metric metric;

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

    public Dimension name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DimensionType getType() {
        return type;
    }

    public Dimension type(DimensionType type) {
        this.type = type;
        return this;
    }

    public void setType(DimensionType type) {
        this.type = type;
    }

    public CostGride getCostGride() {
        return costGride;
    }

    public Dimension costGride(CostGride costGride) {
        this.costGride = costGride;
        return this;
    }

    public void setCostGride(CostGride costGride) {
        this.costGride = costGride;
    }

    public Metric getMetric() {
        return metric;
    }

    public Dimension metric(Metric metric) {
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
        Dimension dimension = (Dimension) o;
        if (dimension.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dimension.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Dimension{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
