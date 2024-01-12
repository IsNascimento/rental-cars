package org.example.layout;

public abstract class AbstractLayoutGenerator {

    protected String generateDashLine(Integer lineSize) {
        return String.format("%" + lineSize + "s", "").replaceAll(" ", "-");
    }

    protected String generateBlankLine(Integer lineSize) {
        return String.format("%" + lineSize + "s", "");
    }

    protected String formatNumberFieldSize(Integer value, Integer fieldSize) {
        return String.format("%" + fieldSize + "d", value);
    }
}
