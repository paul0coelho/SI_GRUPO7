/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pt.ipp.estg.pp.vendaplanos;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.worker.JobWorker;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProvider;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProviderBuilder;
import java.time.Duration;


/**
 *
 * @author duart
 */
public class ReceberNotificacao {

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
                 

                    client.newPublishMessageCommand()
                            .messageName("Pagamento") // Nome da mensagem (igual ao definido no BPMN)
                            .correlationKey("invalido") // Chave de correlação (deve coincidir com a variável "referencia")
                            .variables("{\"Pagamento\": \"invalido\"}") // Variáveis adicionais
                            .send()
                            .join();
         }catch(Exception e){
             System.out.println(e.toString());
         }
    }
    
}