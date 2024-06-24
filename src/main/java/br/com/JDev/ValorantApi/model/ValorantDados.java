package br.com.JDev.ValorantApi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ValorantDados(String uuid, String displayName, Role role, List<Abilities> abilities, String category) {
}