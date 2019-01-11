package com.mycompany.mywebapp.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mycompany.mywebapp.client.TransferenciaService;
import com.mycompany.mywebapp.shared.Constants;
import com.mycompany.mywebapp.shared.FieldVerifier;
import com.mycompany.mywebapp.shared.request.TransferenciaRequest;
import com.mycompany.mywebapp.shared.request.TransferenciaResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Logger;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class TransferenciaServiceImpl extends RemoteServiceServlet implements
        TransferenciaService {

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

    public void post(TransferenciaRequest request) {

        RestTemplate restTemplate = new RestTemplate();

        URI uri = null;
        try {
            uri = new URI(Constants.TRANSFERENCIA_URI);

            ResponseEntity<TransferenciaResponse> response = restTemplate.postForEntity(uri, request, TransferenciaResponse.class);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (Exception ce) {
            ce.printStackTrace();
        }


    }

    @Override
    public TransferenciaResponse get() {

        URI uri = null;
        try {
            uri = new URI(Constants.TRANSFERENCIA_URI);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        RestTemplate restTemplate = new RestTemplate();
        TransferenciaResponse response = restTemplate.getForObject(uri, TransferenciaResponse.class);
        return response;
    }
}
