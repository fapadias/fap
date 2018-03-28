package com.adias.fap.service.mapper;

import com.adias.fap.domain.*;
import com.adias.fap.service.dto.DimensionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Dimension and its DTO DimensionDTO.
 */
@Mapper(componentModel = "spring", uses = {CostGrideMapper.class, MetricMapper.class})
public interface DimensionMapper extends EntityMapper<DimensionDTO, Dimension> {

    @Mapping(source = "costGride.id", target = "costGrideId")
    @Mapping(source = "metric.id", target = "metricId")
    DimensionDTO toDto(Dimension dimension);

    @Mapping(source = "costGrideId", target = "costGride")
    @Mapping(source = "metricId", target = "metric")
    Dimension toEntity(DimensionDTO dimensionDTO);

    default Dimension fromId(Long id) {
        if (id == null) {
            return null;
        }
        Dimension dimension = new Dimension();
        dimension.setId(id);
        return dimension;
    }
}
