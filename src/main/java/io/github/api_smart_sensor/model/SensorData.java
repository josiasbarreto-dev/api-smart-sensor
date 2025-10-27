package io.github.api_smart_sensor.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "sensor_data")
@Builder
public class SensorData {
    @Id
    private String id;
    private Long sensorId;
    private Map<String, Object> readings;
    private Map<String, Object> metadata;
}
