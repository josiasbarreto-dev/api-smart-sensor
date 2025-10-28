package io.github.api_smart_sensor.dto;

import lombok.Builder;

@Builder
public record SensorDataResponse(
        String id,
        Long sensorId
) {
}
