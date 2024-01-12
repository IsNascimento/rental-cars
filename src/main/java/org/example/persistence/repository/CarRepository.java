package org.example.persistence.repository;

import org.example.mapper.CarMapper;
import org.example.persistence.model.Car;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarRepository extends AbstractRepository {

    private static CarRepository instance;

    public static CarRepository getInstance() {
        if (instance == null) {
            instance = new CarRepository();
        }
        return instance;
    }

    private CarRepository() {
    }

    CarMapper carMapper = new CarMapper();

    public List<Car> listar() throws SQLException {
        String sql = "SELECT * FROM CARRO";
        return carMapper.fromResultMap(executeSelect(sql, new ArrayList<>()));
    }

    public Car getById(Integer id) throws SQLException {
        String sql = "SELECT * FROM CARRO WHERE ID = ?";
        return carMapper.fromResultMap(executeSelect(sql, List.of(id.toString()))).stream().findAny().orElse(null);
    }
}
