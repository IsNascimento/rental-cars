package org.example.persistence.repository;

import org.example.configuration.DatabaseConnection;
import org.example.log.Logger;
import org.example.persistence.model.Car;
import org.example.persistence.model.Client;
import org.example.persistence.model.Rent;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class RentRepository extends AbstractRepository {

    private static RentRepository instance;
    private Logger logger = Logger.getInstance();

    public static RentRepository getInstance() {
        if (instance == null) {
            instance = new RentRepository();
        }
        return instance;
    }

    private RentRepository() {
    }

    public void loadRentsFromFile() throws IOException, SQLException {
        Path path = Paths.get("src/main/resources/RentReport.rtn");
        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);

        for (String line : lines) {
            // Verifica se a linha possui pelo menos 20 caracteres para formar as datas
            if (line.length() < 20) {
                logger.warning("Linha mal formada: " + line);
                continue;
            }

            int carId = Integer.parseInt(line.substring(0, 2));
            int clientId = Integer.parseInt(line.substring(2, 4));
            String rentDate = line.substring(4, 12);
            String returnDate = line.substring(12, 20);

            Car car = CarRepository.getInstance().buscar(carId);
            Client client = ClientRepository.getInstance().buscar(clientId);

            if (car == null) {
                logger.warning("Carro com ID " + carId + " não encontrado.");
                continue;
            }

            if (client == null) {
                logger.warning("Cliente com ID " + clientId + " não encontrado.");
                continue;
            }

            Rent rent = new Rent();
            rent.setCarro(car);
            rent.setCliente(client);
            rent.setDataAluguel(LocalDate.parse(rentDate, DateTimeFormatter.ofPattern("yyyyMMdd")));
            rent.setDataDevolucao(LocalDate.parse(returnDate, DateTimeFormatter.ofPattern("yyyyMMdd")));
            rent.setValor(car.getVlrDiaria() * ChronoUnit.DAYS.between(rent.getDataAluguel(), rent.getDataDevolucao()));
            rent.setPago(false);

            save(rent);
        }
    }

    public Long getNextRentId() throws SQLException {
        String sql = "SELECT MAX(ID) + 1 FROM ALUGUEL";
        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getLong(1);
            } else {
                return 1L;
            }
        }
    }

    public void save(Rent rent) throws SQLException {
        rent.setId(getNextRentId());

        String sql = "INSERT INTO ALUGUEL (ID, CARRO_ID, CLIENTE_ID, DATA_ALUGUEL, DATA_DEVOLUCAO, VALOR, PAGO) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setLong(1, rent.getId());
            statement.setInt(2, rent.getCarro().getId().intValue());
            statement.setInt(3, rent.getCliente().getId().intValue());
            statement.setString(4, rent.getDataAluguel().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
            statement.setString(5, rent.getDataDevolucao().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
            statement.setLong(6, rent.getValor());
            statement.setString(7, rent.isPago() ? "S" : "N");
            statement.execute();
        }
    }


    public List<Rent> buscarTodos() throws SQLException {
        List<Rent> rents = new ArrayList<>();
        String sql = "SELECT * FROM ALUGUEL";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Rent rent = new Rent();
                rent.setId((long) rs.getInt("ID"));
                rent.setCarro(CarRepository.getInstance().buscar(rs.getInt("CARRO_ID")));
                rent.setCliente(ClientRepository.getInstance().buscar(rs.getInt("CLIENTE_ID")));
                rent.setDataAluguel(LocalDate.parse(rs.getString("DATA_ALUGUEL"), DateTimeFormatter.BASIC_ISO_DATE));
                rent.setDataDevolucao(LocalDate.parse(rs.getString("DATA_DEVOLUCAO"), DateTimeFormatter.BASIC_ISO_DATE));
                rent.setValor(rs.getLong("VALOR"));
                rent.setPago("S".equals(rs.getString("PAGO")));
                rents.add(rent);
            }
        }
        return rents;
    }
}