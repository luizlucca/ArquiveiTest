package com.llucca.arquivei.controller;

import com.llucca.arquivei.service.NfeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller que expoe a api de busca de nota fiscal e de processamento dos dados vindos da api da arquivei
 * Nao eh necessario chamar o endpoint -> /api/nfe/process. O processamento das notas ja eh feito quando a aplicacao sobe
 * Use o o endpoint -> /api/nfe/process para forcar a consulta de dados da api da Arquivei
 */

@RestController
@RequestMapping("/api/nfe")
public class NfeController {

    @Autowired
    private NfeService nfeService;

    @GetMapping("/{accessKey}")
    @ApiOperation(value = "Busca do valor de uma nota fiscal através da chave de acesso")
    public Double findNfe(@PathVariable("accessKey") String accessKey) {
        return nfeService.findByNfe(accessKey);
    }

    @GetMapping("/process")
    @ApiOperation(value = "Consulta a api da Arquivei para buscar as notas e processá-las, baseado nas chaves de acesso fornecidas para o teste")
    public void processNewEntriesFromArquiveiApi() {
        //forca a busca dos dados da api da arquivei. Note que ja busco os dados quando a aplicacao inicia
        nfeService.fetchFromArquiveiApi();
    }
}
