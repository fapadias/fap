package com.adias.fap.service.mapper;

import com.adias.fap.domain.*;
import com.adias.fap.service.dto.MetricDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Metric and its DTO MetricDTO.
 */
@Mapper(componentModel = "spring", uses = {CostGrideMapper.class})
public interface MetricMapper extends EntityMapper<MetricDTO, Metric> {

    @Mapping(source = "costGride.id", target = "costGrideId")
    MetricDTO toDto(Metric metric);

    @Mapping(target = "dimensions", ignore = true)
    @Mapping(source = "costGrideId", target = "costGride")
    Metric toEntity(MetricDTO metricDTO);

    default Metric fromId(Long id) {
        if (id == null) {
            return null;
        }
        Metric metric = new Metric();
        metric.setId(id);
        return metric;
    }
}
