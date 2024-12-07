package handlers;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;
import Services.ValidarPagamentoService;


/**
 *
 * @author paulo
 */
public class atribuirPacoteHandler implements JobHandler {
    ValidarPagamentoService validarPagamentoService = new ValidarPagamentoService();
    
    @Override
    public void handle(JobClient client, ActivatedJob job) throws Exception {
        validarPagamentoService.validarPagamento();
        client.newCompleteCommand(job.getKey()).variables("{\"pagamento\": \"" + "invalido" + "\"}").send().join();
    }
    
}