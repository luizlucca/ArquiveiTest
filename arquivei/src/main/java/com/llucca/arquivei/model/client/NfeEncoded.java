package com.llucca.arquivei.model.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NfeEncoded {

    @JsonProperty("access_key")
    private String accessKey;

    private String xml;
}
