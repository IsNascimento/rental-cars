package org.example.service;

import org.example.log.Logger;
import org.example.persistence.model.Car;
import org.example.persistence.model.Client;
import org.example.persistence.model.Rent;
import org.example.persistence.repository.CarRepository;
import org.example.persistence.repository.ClientRepository;
import org.example.persistence.repository.RentRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class RentReportService {

    private final CarRepository carRepository = CarRepository.getInstance();
    private final RentRepository rentRepository = RentRepository.getInstance();
    private final Logger logger = Logger.getInstance();

    public List<String> readRentReportFile() throws IOException {
        String filePath = "src/main/resources/RentReport.rtn";
        return Files.readAllLines(Paths.get(filePath));
    }

    public void processRentReportLine(String line) {
        String carId = line.substring(0, 2);
        String clientId = line.substring(2, 4);
        String rentDate = line.substring(4, 12);
        String returnDate = line.substring(12, 20);

        try {
            Car car = carRepository.getById(Integer.parseInt(carId));
            Client client = ClientRepository.getInstance().getById(Integer.parseInt(clientId));

            if (car == null || client == null) {
                logger.warning("Carro com ID " + carId + " ou cliente com ID " + clientId + " não encontrado.");
                return;
            }

            Rent rent = new Rent();
            rent.setCarro(car);
            rent.setCliente(client);
            rent.setDataAluguel(LocalDate.parse(rentDate, DateTimeFormatter.ofPattern("yyyyMMdd")));
            rent.setDataDevolucao(LocalDate.parse(returnDate, DateTimeFormatter.ofPattern("yyyyMMdd")));
            rent.setValor(car.getVlrDiaria() * ChronoUnit.DAYS.between(rent.getDataAluguel(), rent.getDataDevolucao()));
            rent.setPago(false);

            rentRepository.save(rent);
        } catch (SQLException e) {
            if (e.getMessage().contains("foreign key constraint")) {
                logger.warning("Cliente ou carro não encontrado: " + e.getMessage());
            } else {
                logger.error("Erro ao processar linha do relatório de aluguel.");
                logger.error(e);
                throw new RuntimeException(e);
            }
        }
    }

    public void processRentReport() {
        try {
            List<String> lines = readRentReportFile();
            lines.forEach(this::processRentReportLine);
        } catch (IOException e) {
            logger.error("Erro ao ler arquivo RentReport.rtn.");
            logger.error(e);
            throw new RuntimeException(e);
        }
    }
}