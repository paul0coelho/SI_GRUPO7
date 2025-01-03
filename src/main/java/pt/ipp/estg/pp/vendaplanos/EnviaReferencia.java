package pt.ipp.estg.pp.vendaplanos;

import pt.ipp.estg.pp.vendaplanos.handler.ValidarPagamentoServiceHandler;
import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.worker.JobWorker;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProvider;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProviderBuilder;
import java.time.Duration;
import org.camunda.bpm.engine.RuntimeService;
import pt.ipp.estg.pp.vendaplanos.handler.EnviarReferenciaHandler;

/**
 * Configura os Workers para o Camunda.
 */
public class EnviaReferencia {

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
                    //enviar o email
                    final JobWorker enviarNotificacaoWorker
                            = client.newWorker()
                                    .jobType("enviarNotificacao")
                                    .handler(new EnviarReferenciaHandler())
                                    .timeout(Duration.ofSeconds(10).toMillis())
                                    .open();

              client.newPublishMessageCommand()
                            .messageName("Referencia") // Nome da mensagem (igual ao definido no BPMN)
                            .correlationKey("123456789") // Chave de correlação (deve coincidir com a variável "referencia")
                            .variables("{\"Referencia\": \"123456789\"}") // Variáveis adicionais
                            .send()
                            .join();
                    
                    
                    System.out.println("Workers configurados. Aguardando tarefas...");
                    Thread.sleep(30000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
    }
}
