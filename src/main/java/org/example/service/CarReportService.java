package org.example.service;

import org.example.layout.CarReportLayout;
import org.example.log.Logger;
import org.example.persistence.model.Car;
import org.example.persistence.repository.CarRepository;
import org.example.util.FileUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarReportService {

    private static CarReportService instance;

    public static CarReportService getInstance() {
        if (instance == null) {
            instance = new CarReportService();
        }
        return instance;
    }

    private final CarRepository carRepository = CarRepository.getInstance();
    private final Logger logger = Logger.getInstance();

    private CarReportService() {
    }

    protected void generateCarReport(String generationDate) {
        try {
            var layout = new CarReportLayout();
            new FileUtils().writeFileIfNotExists(layout.getCarReportFileName(generationDate), generateLines(generationDate, carRepository.listar(), layout));
        } catch (SQLException e) {
            logger.error("Erro ao buscar dados para gerar relatório de carros.");
            logger.error(e);
            throw new RuntimeException(e);
        } catch (IOException e) {
            logger.error("Erro ao escrever arquivo do relatório de carros.");
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    private List<String> generateLines(String generationDate, List<Car> cars, CarReportLayout layout) {
        var lines = new ArrayList<String>();
        lines.addAll(layout.createHeader(generationDate));
        lines.addAll(layout.createDetails(cars));
        lines.addAll(layout.createTrailer(cars));
        return lines;
    }

}
