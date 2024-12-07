package handlers;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;

/**
 
Handler para a SendTask "Enviar Notificação"
Simula o envio de uma notificação para o cliente.*/
public class EnviarRelatorioHandler implements JobHandler {

    
    
    @Override
    public void handle(JobClient client, ActivatedJob job) {
        String to = "8220160@estg.ipp.pt";
        String from = "outworksuporte@gmail.com";
        String host = "smtp.gmail.com";
        String emailPassword = "gajlsfwkrfuhdgvr";

        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, emailPassword);
            }
        });

        // Async email sending
        new Thread(() -> {
            try {
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(from));
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
                message.setSubject("Relatório recebido");
                message.setText("Aceda à aplicação para ver o relatório do cliente que lhe foi atribuido.");

                Transport.send(message);
                System.out.println("Email enviado com sucesso!");

                // Complete the job
                client.newCompleteCommand(job.getKey()).send().join();
            } catch (MessagingException e) {
                e.printStackTrace();
                client.newFailCommand(job.getKey()).retries(job.getRetries() - 1).send().join();
            }
        }).start();
    }
}