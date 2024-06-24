package br.com.JDev.ValorantApi.util;


import java.util.List;

public interface IConverteDados {
    <T> List<T> obterLista(String json, Class<T> classe);
}