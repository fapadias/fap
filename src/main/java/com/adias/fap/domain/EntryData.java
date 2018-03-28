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
 * A EntryData.
 */
@Entity
@Table(name = "entry_data")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EntryData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "document_name")
    private String documentName;

    @Column(name = "invoice_total")
    private Double invoiceTotal;

    @OneToMany(mappedBy = "entryData")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<InvoiceLine> lines = new HashSet<>();

    @ManyToOne
    private Transporter transporter;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentName() {
        return documentName;
    }

    public EntryData documentName(String documentName) {
        this.documentName = documentName;
        return this;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public Double getInvoiceTotal() {
        return invoiceTotal;
    }

    public EntryData invoiceTotal(Double invoiceTotal) {
        this.invoiceTotal = invoiceTotal;
        return this;
    }

    public void setInvoiceTotal(Double invoiceTotal) {
        this.invoiceTotal = invoiceTotal;
    }

    public Set<InvoiceLine> getLines() {
        return lines;
    }

    public EntryData lines(Set<InvoiceLine> invoiceLines) {
        this.lines = invoiceLines;
        return this;
    }

    public EntryData addLines(InvoiceLine invoiceLine) {
        this.lines.add(invoiceLine);
        invoiceLine.setEntryData(this);
        return this;
    }

    public EntryData removeLines(InvoiceLine invoiceLine) {
        this.lines.remove(invoiceLine);
        invoiceLine.setEntryData(null);
        return this;
    }

    public void setLines(Set<InvoiceLine> invoiceLines) {
        this.lines = invoiceLines;
    }

    public Transporter getTransporter() {
        return transporter;
    }

    public EntryData transporter(Transporter transporter) {
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
        EntryData entryData = (EntryData) o;
        if (entryData.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), entryData.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EntryData{" +
            "id=" + getId() +
            ", documentName='" + getDocumentName() + "'" +
            ", invoiceTotal=" + getInvoiceTotal() +
            "}";
    }
}
