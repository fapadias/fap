package com.adias.fap.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A CostGride.
 */
@Entity
@Table(name = "cost_gride")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CostGride implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "version")
    private String version;

    @ManyToOne
    private Transporter transporter;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public CostGride fileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getVersion() {
        return version;
    }

    public CostGride version(String version) {
        this.version = version;
        return this;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Transporter getTransporter() {
        return transporter;
    }

    public CostGride transporter(Transporter transporter) {
        this.transporter = transporter;
        return this;
    }

    public void setTransporter(Transporter transporter) {
        this.transporter = transporter;
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
        CostGride costGride = (CostGride) o;
        if (costGride.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), costGride.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CostGride{" +
            "id=" + getId() +
            ", fileName='" + getFileName() + "'" +
            ", version='" + getVersion() + "'" +
            "}";
    }
}
