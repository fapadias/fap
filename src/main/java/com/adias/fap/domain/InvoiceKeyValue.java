package com.adias.fap.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A InvoiceKeyValue.
 */
@Entity
@Table(name = "invoice_key_value")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class InvoiceKeyValue implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "jhi_key")
    private String key;

    @Column(name = "jhi_value")
    private String value;

    @ManyToOne
    private InvoiceLine invoiceLine;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public InvoiceKeyValue key(String key) {
        this.key = key;
        return this;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public InvoiceKeyValue value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public InvoiceLine getInvoiceLine() {
        return invoiceLine;
    }

    public InvoiceKeyValue invoiceLine(InvoiceLine invoiceLine) {
        this.invoiceLine = invoiceLine;
        return this;
    }

    public void setInvoiceLine(InvoiceLine invoiceLine) {
        this.invoiceLine = invoiceLine;
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
        InvoiceKeyValue invoiceKeyValue = (InvoiceKeyValue) o;
        if (invoiceKeyValue.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), invoiceKeyValue.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InvoiceKeyValue{" +
            "id=" + getId() +
            ", key='" + getKey() + "'" +
            ", value='" + getValue() + "'" +
            "}";
    }
}
