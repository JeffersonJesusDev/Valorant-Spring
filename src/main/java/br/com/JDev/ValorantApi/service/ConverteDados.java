package br.com.JDev.ValorantApi.service;

import br.com.JDev.ValorantApi.model.DadosList;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class ConverteDados implements IConverteDados {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T obterDados(String json, Class<T> classe) {
        try {
            return mapper.readValue(json, classe);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao converter JSON para objeto", e);
        }
    }


//    @Override
//    public <T> List<T> obterLista(String json, Class<T> classe) {
//        CollectionType lista = mapper.getTypeFactory()
//                .constructCollectionType(List.class, classe);
//        try {
//            return mapper.readValue(json, lista);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
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
