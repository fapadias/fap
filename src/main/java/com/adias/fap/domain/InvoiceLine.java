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
 * A InvoiceLine.
 */
@Entity
@Table(name = "invoice_line")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class InvoiceLine implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "amount")
    private Double amount;

    @ManyToOne
    private EntryData entryData;

    @OneToMany(mappedBy = "invoiceLine")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<InvoiceKeyValue> attributes = new HashSet<>();

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

    public InvoiceLine name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAmount() {
        return amount;
    }

    public InvoiceLine amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public EntryData getEntryData() {
        return entryData;
    }

    public InvoiceLine entryData(EntryData entryData) {
        this.entryData = entryData;
        return this;
    }

    public void setEntryData(EntryData entryData) {
        this.entryData = entryData;
    }

    public Set<InvoiceKeyValue> getAttributes() {
        return attributes;
    }

    public InvoiceLine attributes(Set<InvoiceKeyValue> invoiceKeyValues) {
        this.attributes = invoiceKeyValues;
        return this;
    }

    public InvoiceLine addAttributes(InvoiceKeyValue invoiceKeyValue) {
        this.attributes.add(invoiceKeyValue);
        invoiceKeyValue.setInvoiceLine(this);
        return this;
    }

    public InvoiceLine removeAttributes(InvoiceKeyValue invoiceKeyValue) {
        this.attributes.remove(invoiceKeyValue);
        invoiceKeyValue.setInvoiceLine(null);
        return this;
    }

    public void setAttributes(Set<InvoiceKeyValue> invoiceKeyValues) {
        this.attributes = invoiceKeyValues;
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
        InvoiceLine invoiceLine = (InvoiceLine) o;
        if (invoiceLine.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), invoiceLine.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InvoiceLine{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", amount=" + getAmount() +
            "}";
    }
}
