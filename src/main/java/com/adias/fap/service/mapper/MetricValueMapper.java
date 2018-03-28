package com.adias.fap.service.mapper;

import com.adias.fap.domain.*;
import com.adias.fap.service.dto.MetricValueDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MetricValue and its DTO MetricValueDTO.
 */
@Mapper(componentModel = "spring", uses = {MetricMapper.class})
public interface MetricValueMapper extends EntityMapper<MetricValueDTO, MetricValue> {

    @Mapping(source = "metric.id", target = "metricId")
    MetricValueDTO toDto(MetricValue metricValue);

    @Mapping(target = "dimensions", ignore = true)
    @Mapping(source = "metricId", target = "metric")
    MetricValue toEntity(MetricValueDTO metricValueDTO);

    default MetricValue fromId(Long id) {
        if (id == null) {
            return null;
        }
        MetricValue metricValue = new MetricValue();
        metricValue.setId(id);
        return metricValue;
    }
}
