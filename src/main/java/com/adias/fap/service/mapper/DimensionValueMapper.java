package com.adias.fap.service.mapper;

import com.adias.fap.domain.*;
import com.adias.fap.service.dto.DimensionValueDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity DimensionValue and its DTO DimensionValueDTO.
 */
@Mapper(componentModel = "spring", uses = {DimensionMapper.class, MetricValueMapper.class})
public interface DimensionValueMapper extends EntityMapper<DimensionValueDTO, DimensionValue> {

    @Mapping(source = "dimension.id", target = "dimensionId")
    @Mapping(source = "metricValue.id", target = "metricValueId")
    DimensionValueDTO toDto(DimensionValue dimensionValue);

    @Mapping(source = "dimensionId", target = "dimension")
    @Mapping(source = "metricValueId", target = "metricValue")
    DimensionValue toEntity(DimensionValueDTO dimensionValueDTO);

    default DimensionValue fromId(Long id) {
        if (id == null) {
            return null;
        }
        DimensionValue dimensionValue = new DimensionValue();
        dimensionValue.setId(id);
        return dimensionValue;
    }
}
