package org.example.layout;

import org.example.constants.GlobalConstants;
import org.example.persistence.model.Rent;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

public class RentReportLayoutService extends AbstractLayoutGenerator {

    private static final Integer LINE_SIZE = 100;

    public String getRentReportFileName(String generationDate) {
        return GlobalConstants.BASE_DIRECTORY + "RelatorioAlugueis_" + generationDate.replaceAll("/", "") + ".txt";
    }

    public List<String> createHeader(String generationDate) {
        return List.of("Data: " + generationDate,
                generateDashLine(LINE_SIZE));
    }

    public List<String> createDetails(List<Rent> rents) {
        return rents.stream().map(this::getDetail).collect(Collectors.toList());
    }

    public String createTrailer(Long unpaidTotal) {
        return "Total ainda não pago: " + unpaidTotal;
    }

    private String getDetail(Rent rent) {
        if (rent.getCliente().getTelefone() != null) {
            String formattedPhone = String.format("+%s(%s)%s-%s",
                    rent.getCliente().getTelefone().substring(0, 2),
                    rent.getCliente().getTelefone().substring(2, 4),
                    rent.getCliente().getTelefone().substring(4, 9),
                    rent.getCliente().getTelefone().substring(9));
            return String
                    .format("%s | %s | %d | %s | %s | %s | %d | %s",
                            rent.getDataAluguel(),
                            rent.getCarro().getModelo(),
                            rent.getCarro().getKm(),
                            rent.getCliente().getNome(),
                            formattedPhone,
                            rent.getDataDevolucao(),
                            rent.getValor(),
                            rent.isPago() ? "SIM" : "NAO");
        }
        return null;
    }
}