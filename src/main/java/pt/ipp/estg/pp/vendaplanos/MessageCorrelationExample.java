package pt.ipp.estg.pp.vendaplanos;

import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageCorrelationExample {

    @Autowired
    private RuntimeService runtimeService;

    public void correlateMessage() {
        String businessKey = "12345789";
        String approvalStatus = "APPROVED";

        // Correlacionar a mensagem
        runtimeService.createMessageCorrelation("Referencia")
                      .processInstanceVariableEquals("referencia", "123456789")
                      .correlate();
    }
}
