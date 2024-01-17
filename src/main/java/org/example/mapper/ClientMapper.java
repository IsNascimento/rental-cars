package org.example.mapper;

import org.example.persistence.model.Client;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ClientMapper {

    public List<Client> fromResultMap(List<Map<String, Object>> resultMap) {
        return resultMap.stream().map(this::createClient).collect(Collectors.toList());
    }

    private Client createClient(Map<String, Object> objectMap) {
        var client = new Client();
        client.setId(Long.valueOf(objectMap.get("ID").toString()));
        client.setNome(objectMap.get("NOME").toString());
        client.setCpf(objectMap.get("CPF").toString());
        client.setCnh(objectMap.get("CNH").toString());
        client.setTelefone(objectMap.get("TELEFONE").toString());
        return client;
    }
}