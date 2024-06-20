package br.com.JDev.ValorantApi.model.subModel;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public record Role(String displayName) {
}
