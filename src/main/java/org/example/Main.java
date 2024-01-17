package org.example;

import org.example.service.ReportService;
import org.example.service.RentReportGenerationService;
import org.example.service.internal.InitService;
import org.example.persistence.repository.RentRepository;
import org.example.log.Logger;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Main {

    public static void main(String[] args) {
        try {
            new InitService().InitiateServices();
            new ReportService().generateCarsReport(LocalDate.now());

            // Carregar aluguéis do arquivo
            RentRepository.getInstance().loadRentsFromFile();

            // Gerar relatório de aluguéis
            String generationDate = LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy"));
            Logger logger = Logger.getInstance();
            logger.info("Iniciando a geração do relatório de aluguéis");
            new RentReportGenerationService().generateRentReport(generationDate);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}