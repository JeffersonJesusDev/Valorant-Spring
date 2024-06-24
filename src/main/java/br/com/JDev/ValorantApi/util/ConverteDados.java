package br.com.JDev.ValorantApi.util;

import br.com.JDev.ValorantApi.model.DadosList;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConverteDados implements IConverteDados {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> List<T> obterLista(String json, Class<T> classe) {
        try {
            DadosList dadosList = mapper.readValue(json, DadosList.class);
            return (List<T>) dadosList.data();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao converter JSON para lista de objetos", e);
        }
    }
}