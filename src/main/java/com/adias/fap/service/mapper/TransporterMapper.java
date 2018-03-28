package com.adias.fap.service.mapper;

import com.adias.fap.domain.*;
import com.adias.fap.service.dto.TransporterDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Transporter and its DTO TransporterDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface TransporterMapper extends EntityMapper<TransporterDTO, Transporter> {

    @Mapping(source = "user.id", target = "userId")
    TransporterDTO toDto(Transporter transporter);

    @Mapping(source = "userId", target = "user")
    Transporter toEntity(TransporterDTO transporterDTO);

    default Transporter fromId(Long id) {
        if (id == null) {
            return null;
        }
        Transporter transporter = new Transporter();
        transporter.setId(id);
        return transporter;
    }
}
