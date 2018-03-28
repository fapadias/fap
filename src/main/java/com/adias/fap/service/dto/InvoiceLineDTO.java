package com.adias.fap.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the InvoiceLine entity.
 */
public class InvoiceLineDTO implements Serializable {

    private Long id;

    private String name;

    private Double amount;

    private Long entryDataId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getEntryDataId() {
        return entryDataId;
    }

    public void setEntryDataId(Long entryDataId) {
        this.entryDataId = entryDataId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InvoiceLineDTO invoiceLineDTO = (InvoiceLineDTO) o;
        if(invoiceLineDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), invoiceLineDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InvoiceLineDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", amount=" + getAmount() +
            "}";
    }
}
