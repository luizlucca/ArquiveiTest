package com.llucca.arquivei.model.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Response esperada da api da Arquivei.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NfeResponse {
    private Status status;
    private List<NfeEncoded> data;
    private Page page;
    private Integer count;
    private String signature;
}
