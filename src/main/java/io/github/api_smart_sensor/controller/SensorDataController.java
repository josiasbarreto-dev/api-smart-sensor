package io.github.api_smart_sensor.controller;

import io.github.api_smart_sensor.dto.SensorDataRequest;
import io.github.api_smart_sensor.dto.SensorDataResponse;
import io.github.api_smart_sensor.service.SensorDataInterface;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sensors")
public class SensorDataController {
    private final SensorDataInterface sensorDataService;

    public SensorDataController(SensorDataInterface sensorDataService) {
        this.sensorDataService = sensorDataService;
    }

    @PostMapping
    public ResponseEntity<SensorDataResponse> receiveSensorData(@Valid @RequestBody SensorDataRequest sensorDataRequest) {
        var sensorDataResponse = sensorDataService.saveSensorData(sensorDataRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(sensorDataResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SensorDataResponse> getById(@PathVariable String id) {
        var sensorDataResponse = sensorDataService.getSensorDataById(id);
        return ResponseEntity.ok(sensorDataResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SensorDataResponse> delete(@PathVariable String id) {
         sensorDataService.deleteSensorDataById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<Page<SensorDataResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        var sensorDataResponses = sensorDataService.getAllSensorData(page, size);
        return ResponseEntity.ok(sensorDataResponses);
    }
}