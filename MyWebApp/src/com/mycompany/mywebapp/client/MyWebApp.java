package com.mycompany.mywebapp.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.mycompany.mywebapp.shared.FieldVerifier;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class MyWebApp implements EntryPoint {
    /**
     * The message displayed to the user when the server cannot be reached or
     * returns an error.
     */
    private static final String SERVER_ERROR = "An error occurred while "
            + "attempting to contact the server. Please check your network "
            + "connection and try again.";

    /**
     * Create a remote service proxy to talk to the server-side Greeting service.
     */
    private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        final Button sendButton = new Button("Send to server");
        final TextBox nameField = new TextBox();
        final Label errorLabel = new Label();



        //ReservaFacil
        final TextBox agenciaOrigemField = new TextBox();
        final Label agenciaOrigemLabel = new Label("Agencia Origem");

        final TextBox contaOrigemField = new TextBox();
        final Label contaOrigemLabel = new Label("Conta Origem");

        final TextBox agenciaDestinoField = new TextBox();
        final Label agenciaDestinoLabel = new Label("Agencia Destino");

        final TextBox contaDestinoField = new TextBox();
        final Label contaDestinoLabel = new Label("Conta Destino");

        final TextBox valorField = new TextBox();
        final Label valorLabel = new Label("Valor Transacao");

        final TextBox dataTransferenciaField = new TextBox();
        final Label dataTransferenciaLabel = new Label("Data da transferencia");


        // We can add style names to widgets
        sendButton.addStyleName("sendButton");

        // Add the nameField and sendButton to the RootPanel
        // Use RootPanel.get() to get the entire body element
        RootPanel.get("sendButtonContainer").add(sendButton);
        RootPanel.get("errorLabelContainer").add(errorLabel);


        RootPanel.get("agenciaOrigemFieldContainer").add(agenciaOrigemField);
        RootPanel.get("agenciaOrigemLabelContainer").add(agenciaOrigemLabel);

        RootPanel.get("contaOrigemLabelContainer").add(contaOrigemLabel);
        RootPanel.get("contaOrigemFieldContainer").add(contaOrigemField);

        RootPanel.get("agenciaDestinoLabelContainer").add(agenciaDestinoLabel);
        RootPanel.get("agenciaDestinoFieldContainer").add(agenciaDestinoField);


        RootPanel.get("contaDestinoLabelContainer").add(contaDestinoLabel);
        RootPanel.get("contaDestinoFieldContainer").add(contaDestinoField);

        RootPanel.get("valorLabelContainer").add(valorLabel);
        RootPanel.get("valorFieldContainer").add(valorField);

        RootPanel.get("dataTransferenciaLabelContainer").add(dataTransferenciaLabel);
        RootPanel.get("dataTransferenciaFieldContainer").add(dataTransferenciaField);


        // Focus the cursor on the name field when the app loads
        nameField.setFocus(true);
        nameField.selectAll();

        // Create the popup dialog box
        final DialogBox dialogBox = new DialogBox();
        dialogBox.setText("Remote Procedure Call");
        dialogBox.setAnimationEnabled(true);
        final Button closeButton = new Button("Close");
        // We can set the id of a widget by accessing its Element
        closeButton.getElement().setId("closeButton");
        final Label textToServerLabel = new Label();
        final HTML serverResponseLabel = new HTML();
        VerticalPanel dialogVPanel = new VerticalPanel();
        dialogVPanel.addStyleName("dialogVPanel");
        dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
        dialogVPanel.add(textToServerLabel);
        dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
        dialogVPanel.add(serverResponseLabel);
        dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
        dialogVPanel.add(closeButton);
        dialogBox.setWidget(dialogVPanel);

        // Add a handler to close the DialogBox
        closeButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                dialogBox.hide();
                sendButton.setEnabled(true);
                sendButton.setFocus(true);
            }
        });

        // Create a handler for the sendButton and nameField
        class MyHandler implements ClickHandler, KeyUpHandler {
            /**
             * Fired when the user clicks on the sendButton.
             */
            public void onClick(ClickEvent event) {
                sendNameToServer();
            }

            /**
             * Fired when the user types in the nameField.
             */
            public void onKeyUp(KeyUpEvent event) {
                if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                    sendNameToServer();
                }
            }

            /**
             * Send the name from the nameField to the server and wait for a response.
             */
            private void sendNameToServer() {
                // First, we validate the input.
                errorLabel.setText("");
                String agenciaOrigem = agenciaOrigemField.getText();




                // Then, we send the input to the server.
                sendButton.setEnabled(false);
                textToServerLabel.setText(textToServer);
                serverResponseLabel.setText("");
                greetingService.greetServer(textToServer, new AsyncCallback<String>() {
                    public void onFailure(Throwable caught) {
                        // Show the RPC error message to the user
                        dialogBox.setText("Remote Procedure Call - Failure");
                        serverResponseLabel.addStyleName("serverResponseLabelError");
                        serverResponseLabel.setHTML(SERVER_ERROR);
                        dialogBox.center();
                        closeButton.setFocus(true);
                    }

                    public void onSuccess(String result) {
                        dialogBox.setText("Remote Procedure Call");
                        serverResponseLabel.removeStyleName("serverResponseLabelError");
                        serverResponseLabel.setHTML(result);
                        dialogBox.center();
                        closeButton.setFocus(true);
                    }
                });
            }
        }

        // Add a handler to send the name to the server
        MyHandler handler = new MyHandler();
        sendButton.addClickHandler(handler);
        nameField.addKeyUpHandler(handler);
    }
}
