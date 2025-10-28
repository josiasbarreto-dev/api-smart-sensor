package io.github.api_smart_sensor.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.api_smart_sensor.fixtures.SensorDataFixtures;
import io.github.api_smart_sensor.model.SensorData;
import io.github.api_smart_sensor.repository.SensorDataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@Tag("integration")
public class SensorDataControllerIntegrationTest {
    private static final int MONGO_PORT = 27017;

    @Container
    public static GenericContainer<?> mongoDBContainer = new GenericContainer<>(DockerImageName.parse("mongo:7.0"))
            .withExposedPorts(MONGO_PORT);
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private SensorDataRepository sensorDataRepository;

    @DynamicPropertySource
    static void setMongoProperties(DynamicPropertyRegistry registry) {
        String mongoUri = String.format("mongodb://%s:%d/testdb",
                mongoDBContainer.getHost(),
                mongoDBContainer.getMappedPort(MONGO_PORT));

        registry.add("spring.data.mongodb.uri", () -> mongoUri);
    }

    @BeforeEach
    void setUp() {
        sensorDataRepository.deleteAll();
    }

    @Test
    @DisplayName("Deve salvar dados do sensor com sucesso")
    void shouldSaveSensorDataSuccessfully() throws Exception {
        var sensorDataRequest = SensorDataFixtures.createValidSensorDataRequest();

        mockMvc.perform(post("/api/v1/sensors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(sensorDataRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.sensorId").value(sensorDataRequest.sensorId()));

        assertThat(sensorDataRepository.count()).isEqualTo(1);
    }

    @Test
    @DisplayName("Deve retornar erro ao salvar dados do sensor com request inválido")
    void shouldReturnErrorWhenSavingSensorDataWithInvalidRequest() throws Exception {
        var invalidSensorDataRequest = SensorDataFixtures.createInvalidSensorDataRequest();

        mockMvc.perform(post("/api/v1/sensors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(invalidSensorDataRequest)))
                .andExpect(status().isBadRequest());

        assertThat(sensorDataRepository.count()).isEqualTo(0);
    }

    @Test
    @DisplayName("Deve buscar dados do sensor por ID com sucesso")
    void shouldGetSensorDataByIdSuccessfully() throws Exception {
        SensorData savedSensor = sensorDataRepository.save(SensorDataFixtures.createValidSensorData());

        mockMvc.perform(get("/api/v1/sensors/{id}", savedSensor.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedSensor.getId()))
                .andExpect(jsonPath("$.sensorId").value(savedSensor.getSensorId()));
    }

    @Test
    @DisplayName("Deve retornar Status 404 ao buscar dados do sensor com ID inexistente")
    void shouldReturn404WhenGettingSensorDataWithNonExistentId() throws Exception {
        String nonExistentId = "64b7f8f4e1b2c3d4f5a6b7c8";

        mockMvc.perform(get("/api/v1/sensors/{id}", nonExistentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve deletar dados do sensor por ID com sucesso")
    void shouldDeleteSensorDataByIdSuccessfully() throws Exception {
        SensorData savedSensor = sensorDataRepository.save(SensorDataFixtures.createValidSensorData());

        mockMvc.perform(delete("/api/v1/sensors/{id}", savedSensor.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        assertThat(sensorDataRepository.findById(savedSensor.getId())).isNotPresent();
    }

    @Test
    @DisplayName("Deve retornar Status 404 ao deletar dados do sensor com ID inexistente")
    void shouldReturn404WhenDeletingSensorDataWithNonExistentId() throws Exception {
        String nonExistentId = "64b7f8f4e1b2c3d4f5a6b7c8";

        mockMvc.perform(delete("/api/v1/sensors/{id}", nonExistentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve listar todos os dados dos sensores com paginação")
    void shouldListAllSensorDataWithPagination() throws Exception {
        for (int i = 0; i < 5; i++) {
            sensorDataRepository.save(SensorDataFixtures.createValidSensorData());
        }

        var result = mockMvc.perform(get("/api/v1/sensors")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(5))
                .andExpect(jsonPath("$.totalElements").value(5))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.number").value(0));
    }
}