package com.adias.fap.service.mapper;

import com.adias.fap.domain.*;
import com.adias.fap.service.dto.InvoiceKeyValueDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity InvoiceKeyValue and its DTO InvoiceKeyValueDTO.
 */
@Mapper(componentModel = "spring", uses = {InvoiceLineMapper.class})
public interface InvoiceKeyValueMapper extends EntityMapper<InvoiceKeyValueDTO, InvoiceKeyValue> {

    @Mapping(source = "invoiceLine.id", target = "invoiceLineId")
    InvoiceKeyValueDTO toDto(InvoiceKeyValue invoiceKeyValue);

    @Mapping(source = "invoiceLineId", target = "invoiceLine")
    InvoiceKeyValue toEntity(InvoiceKeyValueDTO invoiceKeyValueDTO);

    default InvoiceKeyValue fromId(Long id) {
        if (id == null) {
            return null;
        }
        InvoiceKeyValue invoiceKeyValue = new InvoiceKeyValue();
        invoiceKeyValue.setId(id);
        return invoiceKeyValue;
    }
}
