package pt.ipp.estg.pp.vendaplanos.handler;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;

/**
 
Handler para o ReceiveTask "Receber Referência de Pagamento"
Este handler processa a referência de pagamento recebida no ReceiveTask.*/
public class ReceberReferenciaPagamentoHandler implements JobHandler {

    @Override
    public void handle(JobClient client, ActivatedJob job) throws Exception {
        String referenciaPagamento = (String) job.getVariablesAsMap().get("referencia");

        System.out.println("Referência de pagamento recebida: " + referenciaPagamento);

        client.newCompleteCommand(job.getKey()).send().join();
        System.out.println("Processamento da referência de pagamento concluído.");
    }
}