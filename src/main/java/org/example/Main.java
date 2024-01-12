package org.example;

import org.example.service.ReportService;
import org.example.service.internal.InitService;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {
        new InitService().InitiateServices();

        new ReportService().generateCarsReport(LocalDate.now());
    }
}