package com.adias.fap.service.mapper;

import com.adias.fap.domain.*;
import com.adias.fap.service.dto.CostGrideDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CostGride and its DTO CostGrideDTO.
 */
@Mapper(componentModel = "spring", uses = {TransporterMapper.class})
public interface CostGrideMapper extends EntityMapper<CostGrideDTO, CostGride> {

    @Mapping(source = "transporter.id", target = "transporterId")
    CostGrideDTO toDto(CostGride costGride);

    @Mapping(source = "transporterId", target = "transporter")
    CostGride toEntity(CostGrideDTO costGrideDTO);

    default CostGride fromId(Long id) {
        if (id == null) {
            return null;
        }
        CostGride costGride = new CostGride();
        costGride.setId(id);
        return costGride;
    }
}
