package org.example.configuration;

import org.example.log.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class DatabaseConnection {

    private static Connection connection;

    Logger logger = Logger.getInstance();

    protected DatabaseConnection() {
    }

    public static Connection getConnection() throws SQLException {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection("jdbc:h2:mem:rentalcars");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    protected void createDataBase() {
        logger.info("Criando banco de dados em memória...");
        try {
            try (var statement = getConnection().createStatement()) {

                for (String sql : getDatabaseCreationSql()) {
                    statement.execute(sql);
                }
                for (String sql : getDatabaseLoadSql()) {
                    statement.executeUpdate(sql);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        logger.info("Banco de dados criado e populado em memória com sucesso.");
    }

    private List<String> getDatabaseCreationSql() {
        return List.of("CREATE TABLE CARRO (ID int primary key, MODELO varchar(50), ANO varchar(4), QTD_PASSAGEIROS int, KM int, FABRICANTE varchar(50), VLR_DIARIA int)",
                "CREATE TABLE CLIENTE (ID int primary key, NOME varchar(100), CPF varchar(11), CNH varchar(11), TELEFONE varchar(13))",
                "CREATE TABLE ALUGUEL (ID int primary key, CARRO_ID int, foreign key (CARRO_ID) references CARRO(ID), CLIENTE_ID int, foreign key (CLIENTE_ID) references CLIENTE(ID), DATA_ALUGUEL varchar(8), DATA_DEVOLUCAO varchar(8), VALOR int, PAGO char(1))");
    }

    private List<String> getDatabaseLoadSql() {
        return Arrays.asList("INSERT INTO CARRO (ID, MODELO, ANO, QTD_PASSAGEIROS, KM, FABRICANTE, VLR_DIARIA) VALUES (1, 'GOL', '2020', 5, 154748, 'Volkswagen', 18500)",
                "INSERT INTO CARRO (ID, MODELO, ANO, QTD_PASSAGEIROS, KM, FABRICANTE, VLR_DIARIA) VALUES (2, 'GOL', '2022', 5, 87554, 'Volkswagen', 19985)",
                "INSERT INTO CARRO (ID, MODELO, ANO, QTD_PASSAGEIROS, KM, FABRICANTE, VLR_DIARIA) VALUES (3, 'POLO', '2019', 5, 225426, 'Volkswagen', 22500)",
                "INSERT INTO CARRO (ID, MODELO, ANO, QTD_PASSAGEIROS, KM, FABRICANTE, VLR_DIARIA) VALUES (4, 'VITRUS', '2023', 5, 24551, 'Volkswagen', 24487)",
                "INSERT INTO CARRO (ID, MODELO, ANO, QTD_PASSAGEIROS, KM, FABRICANTE, VLR_DIARIA) VALUES (5, 'JETTA', '2024', 5, 0, 'Volkswagen', 29889)",
                "INSERT INTO CARRO (ID, MODELO, ANO, QTD_PASSAGEIROS, KM, FABRICANTE, VLR_DIARIA) VALUES (6, 'COROLLA', '2020', 5, 387449, 'Toyota', 25227)",
                "INSERT INTO CARRO (ID, MODELO, ANO, QTD_PASSAGEIROS, KM, FABRICANTE, VLR_DIARIA) VALUES (7, 'RAV4', '2023', 7, 198966, 'Toyota', 34250)",
                "INSERT INTO CARRO (ID, MODELO, ANO, QTD_PASSAGEIROS, KM, FABRICANTE, VLR_DIARIA) VALUES (8, 'FOCUS', '2018', 5, 301302, 'Ford', 30000)",
                "INSERT INTO CARRO (ID, MODELO, ANO, QTD_PASSAGEIROS, KM, FABRICANTE, VLR_DIARIA) VALUES (9, 'UNO', '2022', 5, 445580, 'Fiat', 15000)",
                "INSERT INTO CARRO (ID, MODELO, ANO, QTD_PASSAGEIROS, KM, FABRICANTE, VLR_DIARIA) VALUES (10, 'MOBI', '2021', 5, 388750, 'Fiat', 17790)",
                "INSERT INTO CARRO (ID, MODELO, ANO, QTD_PASSAGEIROS, KM, FABRICANTE, VLR_DIARIA) VALUES (11, 'ARGO', '2023', 5, 268498, 'Fiat', 19999)",
                "INSERT INTO CLIENTE (ID, NOME, CPF, CNH, TELEFONE) VALUES (1, 'Lucas Joaquim Almada', '43374922759', '29677677708', '65991842240')",
                "INSERT INTO CLIENTE (ID, NOME, CPF, CNH, TELEFONE) VALUES (2, 'Carlos Eduardo Anthony Isaac de Paula', '83413989073', '01981669301', '44992220053')",
                "INSERT INTO CLIENTE (ID, NOME, CPF, CNH, TELEFONE) VALUES (3, 'Priscila Márcia Sebastiana Costa', '06154891091', '13079541770', '49997839265')",
                "INSERT INTO CLIENTE (ID, NOME, CPF, CNH, TELEFONE) VALUES (4, 'Luciana Isis Maitê Ferreira', '85234015041', '97655795804', '14994676214')",
                "INSERT INTO CLIENTE (ID, NOME, CPF, CNH, TELEFONE) VALUES (5, 'Pedro Henrique Thales Lopes', '09878066029', '17851584547', '67981141924')",
                "INSERT INTO CLIENTE (ID, NOME, CPF, CNH, TELEFONE) VALUES (6, 'Ricardo Mateus Ribeiro', '58783795030', '04708033928', '61994669121')",
                "INSERT INTO CLIENTE (ID, NOME, CPF, CNH, TELEFONE) VALUES (7, 'Alícia Fabiana Pires', '39198924028', '38882828503', '63987092349')",
                "INSERT INTO CLIENTE (ID, NOME, CPF, CNH, TELEFONE) VALUES (8, 'Bryan Thales Erick Moura', '37886426029', '82393099363', '79999244913')",
                "INSERT INTO CLIENTE (ID, NOME, CPF, CNH, TELEFONE) VALUES (9, 'Geraldo Henry Bernardo da Conceição', '40168500027', '82393909363', '51986656408')",
                "INSERT INTO CLIENTE (ID, NOME, CPF, CNH, TELEFONE) VALUES (10, 'Teresinha Nair Campos', '19797478068', '39388472407', '49992792615')",
                "INSERT INTO CLIENTE (ID, NOME, CPF, CNH, TELEFONE) VALUES (11, 'Marcos Vinicius Erick Henry Peixoto', '90883468018', '16344178208', '79989157613')",
                "INSERT INTO CLIENTE (ID, NOME, CPF, CNH, TELEFONE) VALUES (12, 'Carla Sabrina Melissa Fogaça', '48948467050', '65468577774', '68987657032')",
                "INSERT INTO CLIENTE (ID, NOME, CPF, CNH, TELEFONE) VALUES (13, 'Sebastião Martin Teixeira', '09045710099', '16100326509', '69984198300')",
                "INSERT INTO CLIENTE (ID, NOME, CPF, CNH, TELEFONE) VALUES (14, 'Tiago Vinicius Gomes', '92804928020', '74619860950', '31991953336')",
                "INSERT INTO CLIENTE (ID, NOME, CPF, CNH, TELEFONE) VALUES (15, 'Luiz Thomas Peixoto', '19519417052', '36938544931', '48984907165')",
                "INSERT INTO ALUGUEL (ID, CARRO_ID, CLIENTE_ID, DATA_ALUGUEL, DATA_DEVOLUCAO, VALOR, PAGO) VALUES (1, 2, 4, '20220110', '20220112', 39970, 'S')",
                "INSERT INTO ALUGUEL (ID, CARRO_ID, CLIENTE_ID, DATA_ALUGUEL, DATA_DEVOLUCAO, VALOR, PAGO) VALUES (2, 7, 15, '20221229', '20230106', 274000, 'S')",
                "INSERT INTO ALUGUEL (ID, CARRO_ID, CLIENTE_ID, DATA_ALUGUEL, DATA_DEVOLUCAO, VALOR, PAGO) VALUES (3, 9, 9, '20230102', '20230104', 30000, 'N')");
    }
}
