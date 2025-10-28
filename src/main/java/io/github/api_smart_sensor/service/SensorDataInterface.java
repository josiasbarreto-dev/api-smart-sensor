package io.github.api_smart_sensor.service;

import io.github.api_smart_sensor.dto.SensorDataRequest;
import io.github.api_smart_sensor.dto.SensorDataResponse;
import org.springframework.data.domain.Page;

public interface SensorDataInterface {
    SensorDataResponse saveSensorData(SensorDataRequest sensorDataRequest);
    SensorDataResponse getSensorDataById(String id);
    void deleteSensorDataById(String id);
    Page<SensorDataResponse> getAllSensorData(int page, int size);
}