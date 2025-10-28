package io.github.api_smart_sensor.service.impl;

import io.github.api_smart_sensor.dto.SensorDataRequest;
import io.github.api_smart_sensor.dto.SensorDataResponse;
import io.github.api_smart_sensor.exception.BusinessException;
import io.github.api_smart_sensor.mapper.SensorDataMapper;
import io.github.api_smart_sensor.model.SensorData;
import io.github.api_smart_sensor.repository.SensorDataRepository;
import io.github.api_smart_sensor.service.SensorDataInterface;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class SensorDataService implements SensorDataInterface {
    private final SensorDataRepository sensorDataRepository;
    private final SensorDataMapper mapper;

    public SensorDataService(SensorDataRepository sensorDataRepository, SensorDataMapper mapper) {
        this.sensorDataRepository = sensorDataRepository;
        this.mapper = mapper;
    }


    @Override
    public SensorDataResponse saveSensorData(SensorDataRequest sensorDataRequest) {
        var sensorDataEntity = mapper.toEntity(sensorDataRequest);
        var sensorData = sensorDataRepository.save(sensorDataEntity);
        return mapper.toDto(sensorData);
    }

    @Override
    public SensorDataResponse getSensorDataById(String id) {
        var sensorData = getSensorData(id);
        return mapper.toDto(sensorData);
    }

    @Override
    public void deleteSensorDataById(String id) {
        sensorDataRepository.delete(getSensorData(id));
    }

    @Override
    public Page<SensorDataResponse> getAllSensorData(int page, int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<SensorData> sensorDataPage = sensorDataRepository.findAll(pageable);
        return sensorDataPage.map(mapper::toDto);
    }

    private SensorData getSensorData(String id) {
        return sensorDataRepository.findById(id).orElseThrow(
                () -> new BusinessException("Sensor data not found with id: " + id, HttpStatus.NOT_FOUND, "SENSOR_DATA_NOT_FOUND")
        );
    }
}