package io.github.api_smart_sensor.mapper;

import io.github.api_smart_sensor.dto.SensorDataRequest;
import io.github.api_smart_sensor.dto.SensorDataResponse;
import io.github.api_smart_sensor.model.SensorData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SensorDataMapper {
    @Mapping(target = "id", ignore = true)
    SensorData toEntity(SensorDataRequest sensorDataRequest);

    SensorDataResponse toDto(SensorData sensorData);
}