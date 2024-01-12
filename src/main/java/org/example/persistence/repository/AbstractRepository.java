package org.example.persistence.repository;

import org.example.configuration.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public abstract class AbstractRepository {

    protected List<Map<String, Object>> executeSelect(String sql, List<String> parameters) throws SQLException {
        validateClause(sql, "SELECT");
        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            for (int i = 0; i < parameters.size(); i++) {
                statement.setString(i + 1, parameters.get(i));
            }
            return convertToMap(statement.executeQuery());
        }
    }

    protected Boolean executeDml(String sql, List<String> parameters) throws SQLException {
        validateClause(sql, "INSERT", "DELETE", "UPDATE");
        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            for (int i = 0; i < parameters.size(); i++) {
                statement.setString(i + 1, parameters.get(i));
            }
            return statement.execute();
        }
    }

    private List<Map<String, Object>> convertToMap(ResultSet resultSet) throws SQLException {
        var metaData = resultSet.getMetaData();
        var resultList = new ArrayList<Map<String, Object>>();
        while (resultSet.next()) {
            var row = new HashMap<String, Object>();
            for (int i = 1; i <= metaData.getColumnCount(); ++i) {
                row.put(metaData.getColumnName(i), resultSet.getObject(i));
            }
            resultList.add(row);
        }
        return resultList;
    }

    private void validateClause(String sql, String... acceptedCommands) {
        if (sql != null && !sql.isEmpty()) {
            if (Arrays.stream(acceptedCommands).noneMatch(sql.toUpperCase()::startsWith)) {
                throw new RuntimeException("Comando sql não aceito.");
            }
        }
    }

}
