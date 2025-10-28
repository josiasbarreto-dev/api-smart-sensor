package io.github.api_smart_sensor.repository;

import io.github.api_smart_sensor.model.SensorData;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SensorDataRepository extends MongoRepository<SensorData, String> {
}
