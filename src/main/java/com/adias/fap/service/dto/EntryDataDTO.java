package com.adias.fap.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the EntryData entity.
 */
public class EntryDataDTO implements Serializable {

    private Long id;

    private String documentName;

    private Double invoiceTotal;

    private Long transporterId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public Double getInvoiceTotal() {
        return invoiceTotal;
    }

    public void setInvoiceTotal(Double invoiceTotal) {
        this.invoiceTotal = invoiceTotal;
    }

    public Long getTransporterId() {
        return transporterId;
    }

    public void setTransporterId(Long transporterId) {
        this.transporterId = transporterId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EntryDataDTO entryDataDTO = (EntryDataDTO) o;
        if(entryDataDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), entryDataDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EntryDataDTO{" +
            "id=" + getId() +
            ", documentName='" + getDocumentName() + "'" +
            ", invoiceTotal=" + getInvoiceTotal() +
            "}";
    }
}
