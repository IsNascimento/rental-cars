package org.example.layout;

import org.apache.commons.lang3.StringUtils;
import org.example.constants.GlobalConstants;
import org.example.persistence.model.Car;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CarReportLayout extends AbstractLayoutGenerator {

    private static final Integer LINE_SIZE = 65;

    private Integer lineCount = 0;

    public String getCarReportFileName(String generationDate) {
        return GlobalConstants.BASE_DIRECTORY + "RelatorioDeCarros_" + generationDate.replaceAll("/", "") + ".txt";
    }

    public List<String> createHeader(String generationDate) {
        return List.of("Data: " + generationDate,
                generateDashLine(LINE_SIZE));
    }

    public List<String> createDetails(List<Car> cars) {
        lineCount = 1;
        var detailList = new ArrayList<String>();
        detailList.add("Linha Fabricante          Modelo              Ano    Kilometragem");
        detailList.add(generateDashLine(LINE_SIZE));
        detailList.addAll(cars.stream().map(this::getDetail).collect(Collectors.toList()));
        detailList.add(generateDashLine(LINE_SIZE));
        return detailList;
    }

    public List<String> createTrailer(List<Car> cars) {
        return List.of("Total de carros que devem ser trocados: " + StringUtils.leftPad(String.valueOf(cars.stream().filter(this::shouldChangeCar).count()), 25),
                generateDashLine(LINE_SIZE));
    }

    private String getFirstHeaderLine(String date) {
        return "Data: " + date + generateBlankLine(48) + "-";
    }

    private String getDetail(Car car) {
        return formatNumberFieldSize(lineCount, 5) + " "
                + StringUtils.rightPad(car.getFabricante(), 19) + " "
                + StringUtils.rightPad(car.getModelo(), 19) + " "
                + car.getAno() + "  "
                + formatNumberFieldSize(car.getKm(), 13);
    }

    private boolean shouldChangeCar(Car car) {
        return (LocalDate.now().getYear() - 5) <= Integer.parseInt(car.getAno()) || 300000 > car.getKm();
    }
}