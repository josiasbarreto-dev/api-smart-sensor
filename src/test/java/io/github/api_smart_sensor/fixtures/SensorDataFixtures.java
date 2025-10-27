package io.github.api_smart_sensor.fixtures;

import io.github.api_smart_sensor.dto.SensorDataRequest;
import io.github.api_smart_sensor.dto.SensorDataResponse;
import io.github.api_smart_sensor.model.SensorData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SensorDataFixtures {
    public static SensorDataRequest.SensorDataRequestBuilder createValidSensorDataRequestBuilder(){
        return SensorDataRequest.builder()
                .sensorId(1L)
                .readings(Map.of("temperature", 22.5, "humidity", 60))
                .metadata(Map.of("location", "Room 101", "unit", "Celsius"));
    }

    public static SensorDataRequest.SensorDataRequestBuilder createInvalidSensorDataRequestBuilder(){
        return SensorDataRequest.builder()
                .sensorId(-1L)
                .readings(Map.of())
                .metadata(Map.of());
    }

    public static SensorDataResponse.SensorDataResponseBuilder createValidSensorDataResponseBuilder(){
        return SensorDataResponse.builder()
                .id("68fbeb7a7587eb5b8d48eb16")
                .sensorId(1L);
    }

    public static SensorData.SensorDataBuilder createValidSensorDataBuilder(){
        return SensorData.builder()
                .id(null)
                .sensorId(1L)
                .readings(Map.of("temperature", 22.5, "humidity", 60))
                .metadata(Map.of("location", "Room 101", "unit", "Celsius"));
    }

    public static Page<SensorDataResponse> createPaginatedSensorDataResponseBuilder(int totalElements, Pageable pageable){
        List<SensorDataResponse> content = IntStream.range(pageable.getPageNumber() * pageable.getPageSize(),
                        Math.min((pageable.getPageNumber() + 1) * pageable.getPageSize(), (int) totalElements))
                .mapToObj(i -> SensorDataResponse.builder()
                        .id("sensor-data-id-" + i)
                        .sensorId((long) i)
                        .build())
                .collect(Collectors.toList());

        return new PageImpl<>(content, pageable, totalElements);
    }

    public static SensorDataRequest createValidSensorDataRequest(){
        return createValidSensorDataRequestBuilder().build();
    }

    public static SensorDataRequest createInvalidSensorDataRequest(){
        return createInvalidSensorDataRequestBuilder().build();
    }

    public static SensorDataResponse createValidSensorDataResponse(){
        return createValidSensorDataResponseBuilder().build();
    }

    public static SensorData createValidSensorData(){
        return createValidSensorDataBuilder().build();
    }

    public static Page<SensorDataResponse> createPaginatedSensorDataResponse(int totalElements, Pageable pageable){
        return createPaginatedSensorDataResponseBuilder(totalElements, pageable);
    }
}