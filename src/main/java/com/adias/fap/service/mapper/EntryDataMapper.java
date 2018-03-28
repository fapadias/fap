package com.adias.fap.service.mapper;

import com.adias.fap.domain.*;
import com.adias.fap.service.dto.EntryDataDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity EntryData and its DTO EntryDataDTO.
 */
@Mapper(componentModel = "spring", uses = {TransporterMapper.class})
public interface EntryDataMapper extends EntityMapper<EntryDataDTO, EntryData> {

    @Mapping(source = "transporter.id", target = "transporterId")
    EntryDataDTO toDto(EntryData entryData);

    @Mapping(target = "lines", ignore = true)
    @Mapping(source = "transporterId", target = "transporter")
    EntryData toEntity(EntryDataDTO entryDataDTO);

    default EntryData fromId(Long id) {
        if (id == null) {
            return null;
        }
        EntryData entryData = new EntryData();
        entryData.setId(id);
        return entryData;
    }
}
