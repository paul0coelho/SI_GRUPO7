package pt.ipp.estg.pp.vendaplanos.handler;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;

/**
 
Handler para a SendTask "Enviar Notificação"
Simula o envio de uma notificação para o cliente.*/
public class EnviarNotificacaoHandler implements JobHandler {

    @Override
    public void handle(JobClient client, ActivatedJob job) throws Exception {
        // Completa a tarefa no Zeebe
        client.newCompleteCommand(job.getKey()).send().join();

    }
}