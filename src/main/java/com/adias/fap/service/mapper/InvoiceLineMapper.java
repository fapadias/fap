package com.adias.fap.service.mapper;

import com.adias.fap.domain.*;
import com.adias.fap.service.dto.InvoiceLineDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity InvoiceLine and its DTO InvoiceLineDTO.
 */
@Mapper(componentModel = "spring", uses = {EntryDataMapper.class})
public interface InvoiceLineMapper extends EntityMapper<InvoiceLineDTO, InvoiceLine> {

    @Mapping(source = "entryData.id", target = "entryDataId")
    InvoiceLineDTO toDto(InvoiceLine invoiceLine);

    @Mapping(source = "entryDataId", target = "entryData")
    @Mapping(target = "attributes", ignore = true)
    InvoiceLine toEntity(InvoiceLineDTO invoiceLineDTO);

    default InvoiceLine fromId(Long id) {
        if (id == null) {
            return null;
        }
        InvoiceLine invoiceLine = new InvoiceLine();
        invoiceLine.setId(id);
        return invoiceLine;
    }
}
