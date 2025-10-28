package io.github.api_smart_sensor.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.Map;

@Builder
public record SensorDataRequest(
        @NotNull(message = "Sensor ID cannot be null")
        @Positive(message = "Sensor ID must be a positive number")
        Long sensorId,
        @Valid
        @Size(min = 1, message = "Readings must contain at least one entry")
        Map<String, Object> readings,
        @Valid
        @Size(min = 1, message = "Metadata must contain at least one entry")
        Map<String, Object> metadata
) {
}
