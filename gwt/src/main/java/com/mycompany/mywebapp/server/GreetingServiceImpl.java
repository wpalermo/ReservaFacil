package com.mycompany.mywebapp.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mycompany.mywebapp.client.GreetingService;
import com.mycompany.mywebapp.shared.FieldVerifier;
import com.mycompany.mywebapp.shared.Transferencia;
import com.mycompany.mywebapp.shared.TransferenciaRequest;
import com.mycompany.mywebapp.shared.TransferenciaResponse;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.ConnectException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Logger;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
        GreetingService {

    Logger logger = Logger.getLogger(this.getClass().toString());

  public String greetServer(String input) throws IllegalArgumentException {
    // Verify that the input is valid.

      RestTemplate restTemplate = new RestTemplate();

      if (!FieldVerifier.isValidName(input)) {
      // If the input is not valid, throw an IllegalArgumentException back to
      // the client.
      throw new IllegalArgumentException(
          "Name must be at least 4 characters long");
    }

    String serverInfo = getServletContext().getServerInfo();
    String userAgent = getThreadLocalRequest().getHeader("User-Agent");

    // Escape data from the client to avoid cross-site script vulnerabilities.
    input = escapeHtml(input);
    userAgent = escapeHtml(userAgent);

    return "Hello, " + input + "!<br><br>I am running " + serverInfo
        + ".<br><br>It looks like you are using:<br>" + userAgent;
  }

  /**
   * Escape an html string. Escaping data received from the client helps to
   * prevent cross-site script vulnerabilities.
   * 
   * @param html the html string to escape
   * @return the escaped string
   */
  private String escapeHtml(String html) {
    if (html == null) {
      return null;
    }
    return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(
        ">", "&gt;");
  }

  public void post(TransferenciaRequest request){

      System.out.println("post1");
      RestTemplate restTemplate = new RestTemplate();

      URI uri = null;
      try {
          uri = new URI("http://localhost:8080/transferencia-app/transferencia");


          logger.info(uri.getHost() + uri.getPath());


          ResponseEntity<TransferenciaResponse> response = restTemplate.postForEntity(uri, request, TransferenciaResponse.class);

      } catch (URISyntaxException e) {
          e.printStackTrace();
      } catch (Exception ce){
          ce.printStackTrace();
          logger.severe("Não foi possivel conectar no servidor " + ce.getLocalizedMessage());
      }


      System.out.println("post2");

  }
}
