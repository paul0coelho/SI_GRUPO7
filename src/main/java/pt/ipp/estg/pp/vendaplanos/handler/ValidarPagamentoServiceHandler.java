/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pt.ipp.estg.pp.vendaplanos.handler;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;
import pt.ipp.estg.pp.vendaplanos.service.ValidarPagamentoService;

/**
 *
 * @author paulo
 */
public class ValidarPagamentoServiceHandler implements JobHandler {
    ValidarPagamentoService validarPagamentoService = new ValidarPagamentoService();
    
    @Override
    public void handle(JobClient client, ActivatedJob job) throws Exception {
        validarPagamentoService.validarPagamento();
        client.newCompleteCommand(job.getKey()).send().join();
    }
    
}
