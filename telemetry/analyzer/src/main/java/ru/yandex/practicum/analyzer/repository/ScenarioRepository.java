package ru.yandex.practicum.analyzer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.yandex.practicum.analyzer.model.Scenario;

import java.util.List;
import java.util.Optional;

public interface ScenarioRepository extends JpaRepository<Scenario, Long> {
    List<Scenario> findByHubId(String hubId);

    Optional<Scenario> findByHubIdAndName(String hubId, String name);

    void deleteByName(String name);

    Boolean existsByName(String name);


    @Query("SELECT COUNT(c) FROM Scenario s JOIN s.conditions c WHERE KEY(c) = :sensorId")
    long countConditionsBySensorId(@Param("sensorId") String sensorId);

    @Query("SELECT COUNT(a) FROM Scenario s JOIN s.actions a WHERE KEY(a) = :sensorId")
    long countActionsBySensorId(@Param("sensorId") String sensorId);
}