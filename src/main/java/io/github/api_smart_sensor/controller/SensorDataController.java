package io.github.api_smart_sensor.controller;

import io.github.api_smart_sensor.dto.SensorDataRequest;
import io.github.api_smart_sensor.dto.SensorDataResponse;
import io.github.api_smart_sensor.service.SensorDataInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sensors")
@Tag(name = "Sensor Data API", description = "API for managing sensor data")
public class SensorDataController {
    private final SensorDataInterface sensorDataService;

    public SensorDataController(SensorDataInterface sensorDataService) {
        this.sensorDataService = sensorDataService;
    }

    @Operation(summary = "Receive sensor data", description = "Endpoint to receive and store sensor data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sensor data created successfully", content = @Content(schema = @Schema(implementation = SensorDataResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid sensor data request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<SensorDataResponse> receiveSensorData(@Valid @RequestBody SensorDataRequest sensorDataRequest) {
        var sensorDataResponse = sensorDataService.saveSensorData(sensorDataRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(sensorDataResponse);
    }

    @Operation(summary = "Get sensor data by ID", description = "Endpoint to retrieve sensor data by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sensor data retrieved successfully", content = @Content(schema = @Schema(implementation = SensorDataResponse.class))),
            @ApiResponse(responseCode = "404", description = "Sensor data not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<SensorDataResponse> getById(@PathVariable String id) {
        var sensorDataResponse = sensorDataService.getSensorDataById(id);
        return ResponseEntity.ok(sensorDataResponse);
    }

    @Operation(summary = "Delete sensor data by ID", description = "Endpoint to delete sensor data by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Sensor data deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Sensor data not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<SensorDataResponse> delete(@PathVariable String id) {
         sensorDataService.deleteSensorDataById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Get all sensor data", description = "Endpoint to retrieve all sensor data with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sensor data retrieved successfully", content = @Content(schema = @Schema(implementation = SensorDataResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<Page<SensorDataResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        var sensorDataResponses = sensorDataService.getAllSensorData(page, size);
        return ResponseEntity.ok(sensorDataResponses);
    }
}