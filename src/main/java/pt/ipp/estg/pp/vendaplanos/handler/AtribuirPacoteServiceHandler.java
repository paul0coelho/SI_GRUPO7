package pt.ipp.estg.pp.vendaplanos.handler;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;
import pt.ipp.estg.pp.vendaplanos.service.ValidarPagamentoService;


/**
 *
 * @author paulo
 */
public class AtribuirPacoteServiceHandler implements JobHandler {
    ValidarPagamentoService validarPagamentoService = new ValidarPagamentoService();
    
    @Override
    public void handle(JobClient client, ActivatedJob job) throws Exception {
        client.newCompleteCommand(job.getKey()).variables("{\"pagamento\": \"" + "valido" + "\"}").send().join();
    }
    
}