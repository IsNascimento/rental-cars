package org.example.service;

import org.example.constants.GlobalConstants;
import org.example.log.Logger;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ReportService {

    private final CarReportService carReportService = CarReportService.getInstance();
    private final Logger logger = Logger.getInstance();


    public void generateCarsReport(LocalDate generationDate) {
        logger.info("Iniciando processo de geração do relatório de carros.");
        carReportService.generateCarReport(generationDate.format(DateTimeFormatter.ofPattern(GlobalConstants.VIEW_DATE_FORMAT)));
        logger.info("Relatório de carros gerado com sucesso, disponível em " + GlobalConstants.BASE_DIRECTORY + ".");
    }
}
