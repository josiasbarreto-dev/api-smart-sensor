package io.github.api_smart_sensor.unit.service;

import io.github.api_smart_sensor.dto.SensorDataRequest;
import io.github.api_smart_sensor.dto.SensorDataResponse;
import io.github.api_smart_sensor.exception.BusinessException;
import io.github.api_smart_sensor.fixtures.SensorDataFixtures;
import io.github.api_smart_sensor.mapper.SensorDataMapper;
import io.github.api_smart_sensor.model.SensorData;
import io.github.api_smart_sensor.repository.SensorDataRepository;
import io.github.api_smart_sensor.service.impl.SensorDataService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Unit tests for SensorDataService")
public class SensorDataServiceUnitTest {
    @InjectMocks
    private SensorDataService sensorDataService;

    @Mock
    private SensorDataRepository sensorDataRepository;

    @Mock
    private SensorDataMapper mapper;

    @Test
    @DisplayName("Deve salvar dados do sensor com sucesso")
    void shouldSaveSensorDataSuccessfully() {
        SensorDataRequest sensorDataRequest = SensorDataFixtures.createValidSensorDataRequest();
        SensorData sensorDataEntity = SensorDataFixtures.createValidSensorData();
        SensorDataResponse sensorDataResponse = SensorDataFixtures.createValidSensorDataResponse();

        when(mapper.toEntity(sensorDataRequest)).thenReturn(sensorDataEntity);
        when(sensorDataRepository.save(sensorDataEntity)).thenReturn(sensorDataEntity);
        when(mapper.toDto(sensorDataEntity)).thenReturn(sensorDataResponse);

        SensorDataResponse result = sensorDataService.saveSensorData(sensorDataRequest);

        assertNotNull(result);
        assertEquals(sensorDataResponse, result);

        verify(sensorDataRepository).save(sensorDataEntity);
        verifyNoMoreInteractions(sensorDataRepository);
    }

    @Test
    @DisplayName("Deve buscar dados do sensor por ID com sucesso")
    void shouldGetSensorDataByIdSuccessfully() {
        String sensorDataId = "valid-id";
        SensorData sensorDataEntity = SensorDataFixtures.createValidSensorData();
        SensorDataResponse sensorDataResponse = SensorDataFixtures.createValidSensorDataResponse();

        when(sensorDataRepository.findById(sensorDataId)).thenReturn(Optional.ofNullable(sensorDataEntity));
        when(mapper.toDto(sensorDataEntity)).thenReturn(sensorDataResponse);

        SensorDataResponse result = sensorDataService.getSensorDataById(sensorDataId);

        assertNotNull(result);
        assertEquals(sensorDataResponse, result);

        verify(sensorDataRepository).findById(sensorDataId);
        verifyNoMoreInteractions(sensorDataRepository);
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar dados do sensor com ID inexistente")
    void shouldThrowExceptionWhenGettingSensorDataWithNonExistentId() {
        String nonExistentId = "non-existent-id";

        when(sensorDataRepository.findById(nonExistentId)).thenReturn(Optional.empty());
        Exception exception = assertThrows(
                BusinessException.class, () -> {
                    sensorDataService.getSensorDataById(nonExistentId);
                });
        String expectedMessage = "Sensor data not found with id: " + nonExistentId;
        assertEquals(expectedMessage, exception.getMessage());
        verifyNoMoreInteractions(sensorDataRepository, mapper);
    }

    @Test
    @DisplayName("Deve deletar dados do sensor por ID com sucesso")
    void shouldDeleteSensorDataByIdSuccessfully() {
        String sensorDataId = "valid-id";
        SensorData sensorDataEntity = SensorDataFixtures.createValidSensorData();

        when(sensorDataRepository.findById(sensorDataId)).thenReturn(Optional.ofNullable(sensorDataEntity));
        doNothing().when(sensorDataRepository).delete(sensorDataEntity);

        assertDoesNotThrow(() -> sensorDataService.deleteSensorDataById(sensorDataId));

        verify(sensorDataRepository).delete(sensorDataEntity);
        verify(sensorDataRepository).findById(sensorDataId);
        verifyNoMoreInteractions(sensorDataRepository);
    }

    @Test
    @DisplayName("Deve lançar exceção ao deletar dados do sensor com ID inexistente")
    void shouldThrowExceptionWhenDeletingSensorDataWithNonExistentId() {
        String nonExistentId = "non-existent-id";

        when(sensorDataRepository.findById(nonExistentId)).thenReturn(Optional.empty());
        Exception exception = assertThrows(
                BusinessException.class, () -> {
                    sensorDataService.deleteSensorDataById(nonExistentId);
                });

        String expectedMessage = "Sensor data not found with id: " + nonExistentId;
        assertEquals(expectedMessage, exception.getMessage());
        verifyNoMoreInteractions(sensorDataRepository);
    }
}
