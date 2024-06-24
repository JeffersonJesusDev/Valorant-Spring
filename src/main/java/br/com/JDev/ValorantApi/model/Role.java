package br.com.JDev.ValorantApi.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public record Role(String displayName) {
}
