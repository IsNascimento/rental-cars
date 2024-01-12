package org.example.mapper;

import org.example.persistence.model.Car;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CarMapper {

    public List<Car> fromResultMap(List<Map<String, Object>> resultMap) throws SQLException {
        return resultMap.stream().map(this::createCar).collect(Collectors.toList());
    }

    private Car createCar(Map<String, Object> objectMap) {
        var car = new Car();
        car.setId(Long.valueOf(objectMap.get("ID").toString()));
        car.setModelo(objectMap.get("MODELO").toString());
        car.setAno(objectMap.get("ANO").toString());
        car.setQtdPassageiros((Integer) objectMap.get("QTD_PASSAGEIROS"));
        car.setKm((Integer) objectMap.get("KM"));
        car.setFabricante(objectMap.get("FABRICANTE").toString());
        car.setVlrDiaria(Long.valueOf(objectMap.get("VLR_DIARIA").toString()));
        return car;
    }
}
