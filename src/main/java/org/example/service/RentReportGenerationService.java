package org.example.service;

import org.example.layout.RentReportLayoutService;
import org.example.log.Logger;
import org.example.persistence.model.Client;
import org.example.persistence.model.Rent;
import org.example.persistence.repository.RentRepository;
import org.example.util.FileUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RentReportGenerationService {

    private final RentRepository rentRepository = RentRepository.getInstance();
    private final Logger logger = Logger.getInstance();
    private static final String BASE_DIRECTORY = "/RentalCars/";

    public void generateRentReport(String generationDate) {
        try {
            var rents = rentRepository.buscarTodos();
            var lines = new ArrayList<String>();
            lines.add("Data: " + generationDate);
            lines.add("---------------------------------------------");
            lines.add("| Data do aluguel | Modelo do carro | Km do carro | Nome do cliente | Telefone do cliente | Data de devolução | Valor | Pago |");
            lines.add("---------------------------------------------");
            double totalNaoPago = 0.0;

            for (Rent rent : rents) {
                Client cliente = rent.getCliente();
                lines.add("| " + rent.getDataAluguel() + " | " + rent.getCarro().getModelo() + " | " +
                        rent.getCarro().getKm() + " | " + cliente.getNome() + " | " +
                        formatTelefone(cliente.getTelefone()) + " | " + rent.getDataDevolucao() + " | " +
                        rent.getValor() + " | " + (rent.isPago() ? "SIM" : "NAO") + " |");

                if (!rent.isPago()) {
                    totalNaoPago += rent.getValor();
                }
            }

            lines.add("---------------------------------------------");
            lines.add("Valor Total Não Pago: " + totalNaoPago);

            String reportFileName = "RelatorioAlugueis_" + generationDate.replace("/", "") + ".txt";
            String directoryPath = BASE_DIRECTORY;

            FileUtils.createDirectory(directoryPath);
            String filePath = directoryPath + reportFileName;
            FileUtils.writeFile(filePath, lines);
        } catch (SQLException e) {
            logger.error("Erro ao buscar dados para gerar relatório de aluguéis.");
            logger.error(e);
            throw new RuntimeException(e);
        } catch (IOException e) {
            logger.error("Erro ao escrever arquivo do relatório de aluguéis.");
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    private String formatTelefone(String telefone) {
        if (telefone != null && telefone.length() >= 10) {
            return "+XX(" + telefone.substring(0, 2) + ")" + telefone.substring(2, 7) + "-" + telefone.substring(7);
        } else {
            return "Telefone inválido";
        }
    }
}