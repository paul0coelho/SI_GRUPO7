package pt.ipp.estg.pp.vendaplanos.handler;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;

/**
 
Handler para a SendTask "Enviar Notificação"
Simula o envio de uma notificação para o cliente.*/
public class EnviarReferenciaHandler implements JobHandler {

    @Override
    public void handle(JobClient client, ActivatedJob job) throws Exception {
        // Obtém dados da tarefa (caso existam variáveis)
        String referenciaPagamento = (String) job.getVariablesAsMap().get("Referencia");

        // Simula o envio de uma notificação
        System.out.println("Enviando notificação para cliente...");
        System.out.println("Referência de Pagamento: " + referenciaPagamento);
        // Completa a tarefa no Zeebe
        client.newCompleteCommand(job.getKey()).send().join();
        System.out.println("Notificação enviada com sucesso.");
    }
}