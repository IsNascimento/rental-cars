package org.example.persistence.repository;

import org.example.mapper.ClientMapper;
import org.example.persistence.model.Client;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ClientRepository extends AbstractRepository {

    private static ClientRepository instance;
    private ClientMapper clientMapper = new ClientMapper();

    public static ClientRepository getInstance() {
        if (instance == null) {
            instance = new ClientRepository();
        }
        return instance;
    }

    private ClientRepository() {
    }

    public Client buscar(Integer id) throws SQLException {
        String sql = "SELECT * FROM CLIENTE WHERE ID = ?";
        List<Client> clients = clientMapper.fromResultMap(executeSelect(sql, List.of(id.toString())));
        if (clients.isEmpty()) {
            return null;
        }
        return clients.get(0);
    }
    
    public Client getById(Integer id) throws SQLException {
        String sql = "SELECT * FROM CLIENTE WHERE ID = ?";
        List<Map<String, Object>> resultMap = executeSelect(sql, List.of(id.toString()));
        if (resultMap.isEmpty()) {
            return null;
        }
        return createClientFromMap(resultMap.get(0));
    }

    private Client createClientFromMap(Map<String, Object> map) {
        Client client = new Client();
        client.setId(((Number) map.get("ID")).longValue());
        client.setNome((String) map.get("NOME"));
        client.setCpf((String) map.get("CPF"));
        client.setCnh((String) map.get("CNH"));
        client.setTelefone((String) map.get("TELEFONE"));
        return client;
    }
    
}