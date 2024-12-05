package pt.ipp.estg.pp.vendaplanos;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.worker.JobWorker;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProvider;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProviderBuilder;
import java.time.Duration;
import org.camunda.bpm.engine.RuntimeService;
import pt.ipp.estg.pp.vendaplanos.handler.EnviarNotificacaoHandler;
import pt.ipp.estg.pp.vendaplanos.handler.ReceberReferenciaPagamentoHandler;
import pt.ipp.estg.pp.vendaplanos.handler.ValidarPagamentoServiceHandler;

/**
 * Configura os Workers para o Camunda.
 */
public class ValidarPagamento {

    private static final String ZEEBE_ADDRESS = "dfdb8d36-5bf6-4b20-be42-8205ce0805f0.bru-2.zeebe.camunda.io:443";
    private static final String ZEEBE_CLIENT_ID = "GV3L26WwwbW7dvg2Kw_tr6zyVvlN0z0_";
    private static final String ZEEBE_CLIENT_SECRET = "JQ4MX7YpU9xY8KQtgo.54YqRMyci1Cs3GQnFrRObnLFvzMtqoLKBbtyYF9xINiu2";
    private static final String ZEEBE_AUTHORIZATION_SERVER_URL = "https://login.cloud.camunda.io/oauth/token";
    private static final String ZEEBE_TOKEN_AUDIENCE = "zeebe.camunda.io";

    public static void main(String[] args) {
        final OAuthCredentialsProvider credentialsProvider
                = new OAuthCredentialsProviderBuilder()
                        .authorizationServerUrl(ZEEBE_AUTHORIZATION_SERVER_URL)
                        .audience(ZEEBE_TOKEN_AUDIENCE)
                        .clientId(ZEEBE_CLIENT_ID)
                        .clientSecret(ZEEBE_CLIENT_SECRET)
                        .build();
        

        try (final ZeebeClient client
                = ZeebeClient.newClientBuilder()
                        .gatewayAddress(ZEEBE_ADDRESS)
                        .credentialsProvider(credentialsProvider)
                        .build();) {

                    final JobWorker validarPagamentoWorker
                            = client.newWorker()
                                    .jobType("validarPagamento")
                                    .handler(new ValidarPagamentoServiceHandler())
                                    .timeout(Duration.ofSeconds(10).toMillis())
                                    .open();

                    final JobWorker enviarNotificacaoWorker
                            = client.newWorker()
                                    .jobType("enviarNotificacao")
                                    .handler(new EnviarNotificacaoHandler())
                                    .timeout(Duration.ofSeconds(10).toMillis())
                                    .open();

                    final JobWorker receberReferenciaPagamentoWorker
                            = client.newWorker()
                                    .jobType("referencia")
                                    .handler(new ReceberReferenciaPagamentoHandler())
                                    .timeout(Duration.ofSeconds(10).toMillis())
                                    .open();
            MessageCorrelationExample msg= new MessageCorrelationExample();
            
            msg.correlateMessage();
            System.out.println("Workers configurados. Aguardando tarefas...");
                    Thread.sleep(10000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
    }
}