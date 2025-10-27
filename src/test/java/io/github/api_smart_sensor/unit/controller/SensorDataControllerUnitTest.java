package io.github.api_smart_sensor.unit.controller;

import io.github.api_smart_sensor.controller.SensorDataController;
import io.github.api_smart_sensor.dto.SensorDataResponse;
import io.github.api_smart_sensor.exception.BusinessException;
import io.github.api_smart_sensor.fixtures.SensorDataFixtures;
import io.github.api_smart_sensor.service.SensorDataInterface;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Unit tests for SensorDataController")
@Tag("unit")
public class SensorDataControllerUnitTest {
    @InjectMocks
    private SensorDataController sensorDataController;

    @Mock
    private SensorDataInterface sensorDataService;

    @Test
    @DisplayName("Deve salvar dados do sensor com sucesso e retornar o status 201")
    void shouldSaveSensorDataSuccessfullyAndReturnStatus201() {
        var sensorDataRequest = SensorDataFixtures.createValidSensorDataRequest();
        var sensorDataResponse = SensorDataFixtures.createValidSensorDataResponse();

        when(sensorDataService.saveSensorData(sensorDataRequest)).thenReturn(sensorDataResponse);
        ResponseEntity<SensorDataResponse> response = sensorDataController.receiveSensorData(sensorDataRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(sensorDataResponse, response.getBody());
        verifyNoMoreInteractions(sensorDataService);
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar salvar dados do sensor com dados inválidos")
    void shouldThrowExceptionWhenSavingInvalidSensorData() {
        var invalidSensorDataRequest = SensorDataFixtures.createInvalidSensorDataRequest();

        String errorMessage = "Invalid sensor data";
        when(sensorDataService.saveSensorData(invalidSensorDataRequest))
                .thenThrow(new IllegalArgumentException(errorMessage));

        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> sensorDataController.receiveSensorData(invalidSensorDataRequest)
        );

        assertEquals(errorMessage, exception.getMessage());
        verifyNoMoreInteractions(sensorDataService);
    }

    @Test
    @DisplayName("Deve recuperar dados do sensor por ID com sucesso e retornar o status 200")
    void shouldGetSensorDataByIdSuccessfullyAndReturnStatus200() {
        var sensorDataId = "valid-id";
        var sensorDataResponse = SensorDataFixtures.createValidSensorDataResponse();

        when(sensorDataService.getSensorDataById(sensorDataId)).thenReturn(sensorDataResponse);
        ResponseEntity<SensorDataResponse> response = sensorDataController.getById(sensorDataId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sensorDataResponse, response.getBody());
        verifyNoMoreInteractions(sensorDataService);
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar recuperar dados do sensor com ID inválido")
    void shouldThrowExceptionWhenGettingSensorDataByInvalidId() {
        var invalidSensorDataId = "invalid-id";

        String errorMessage = "SENSOR_DATA_NOT_FOUND";
        String messageDetails = "Sensor not found with the id: " + invalidSensorDataId;
        when(sensorDataService.getSensorDataById(invalidSensorDataId))
                .thenThrow(new BusinessException("Sensor not found with the id: "+ invalidSensorDataId, HttpStatus.NOT_FOUND, errorMessage));

        Exception exception = assertThrows(
                BusinessException.class,
                () -> sensorDataController.getById(invalidSensorDataId)
        );

        assertEquals(messageDetails, exception.getMessage());
        verifyNoMoreInteractions(sensorDataService);
    }

    @Test
    @DisplayName("Deve deletar dados do sensor por ID com sucesso e retornar o status 204")
    void shouldDeleteSensorDataByIdSuccessfullyAndReturnStatus204() {
        var sensorDataId = "valid-id";

        ResponseEntity<SensorDataResponse> response = sensorDataController.delete(sensorDataId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar deletar dados do sensor com ID inválido")
    void shouldThrowExceptionWhenDeletingSensorDataByInvalidId() {
        var invalidSensorDataId = "invalid-id";

        String errorMessage = "SENSOR_DATA_NOT_FOUND";
        String messageDetails = "Sensor not found with the id: " + invalidSensorDataId;
        doThrow(new BusinessException("Sensor not found with the id: "+ invalidSensorDataId, HttpStatus.NOT_FOUND, errorMessage))
                .when(sensorDataService).deleteSensorDataById(invalidSensorDataId);

        Exception exception = assertThrows(
                BusinessException.class,
                () -> sensorDataController.delete(invalidSensorDataId)
        );

        assertEquals(messageDetails, exception.getMessage());
        verifyNoMoreInteractions(sensorDataService);
    }

    @Test
    @DisplayName("Deve retornar uma lista paginada de dados do sensor com sucesso e retornar o status 200")
    void shouldReturnPaginatedSensorDataListSuccessfullyAndReturnStatus200() {
        int page = 0;
        int size = 10;

        Pageable pageable = PageRequest.of(page, size);
        var listSensorDataResponse = SensorDataFixtures.createPaginatedSensorDataResponse(10, pageable);

        when(sensorDataService.getAllSensorData(0, 10)).thenReturn(listSensorDataResponse);

        ResponseEntity<Page<SensorDataResponse>> response = sensorDataController.getAll(page, size);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(listSensorDataResponse, response.getBody());
        verifyNoMoreInteractions(sensorDataService);
    }

}