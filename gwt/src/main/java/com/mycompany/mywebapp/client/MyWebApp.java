package com.mycompany.mywebapp.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.mycompany.mywebapp.shared.Transferencia;
import com.mycompany.mywebapp.shared.TransferenciaRequest;
import com.mycompany.mywebapp.shared.TransferenciaResponse;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private final TransferenciaServiceAsync transferenciaService = GWT.create(TransferenciaService.class);

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        final Button sendButton = new Button("Transferir");
        final Button consultaButton = new Button("Consultar transferencias");
        final TextBox nameField = new TextBox();
        final Label errorLabel = new Label();




        //ReservaFacil

        final TextBox contaOrigemField = new TextBox();
        final Label contaOrigemLabel = new Label("Conta Origem");

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
        RootPanel.get("consultaButtonContainer").add(consultaButton);
        RootPanel.get("errorLabelContainer").add(errorLabel);


        RootPanel.get("contaOrigemLabelContainer").add(contaOrigemLabel);
        RootPanel.get("contaOrigemFieldContainer").add(contaOrigemField);


        RootPanel.get("contaDestinoLabelContainer").add(contaDestinoLabel);
        RootPanel.get("contaDestinoFieldContainer").add(contaDestinoField);

        RootPanel.get("valorLabelContainer").add(valorLabel);
        RootPanel.get("valorFieldContainer").add(valorField);

        RootPanel.get("dataTransferenciaLabelContainer").add(dataTransferenciaLabel);
        RootPanel.get("dataTransferenciaFieldContainer").add(dataTransferenciaField);




        // Create a handler for the sendButton and nameField
        class TransferenciaHandler implements ClickHandler {
            /**
             * Fired when the user clicks on the sendButton.
             */
            public void onClick(ClickEvent event) {
                solicitarTransferencia();
            }

            /**
             * Send the name from the nameField to the server and wait for a response.
             */
            private void solicitarTransferencia() {
                // First, we validate the input.
                errorLabel.setText("");
                String textToServer = contaDestinoField.getText();

                String valor = valorField.getText();

                if (valor != null && valor.isEmpty())
                    valor = "0";

                Transferencia transferencia = new Transferencia();


                transferencia.setValor(Float.valueOf(valor));
                transferencia.setContaDestino(contaDestinoField.getText());
                transferencia.setContaOrigem(contaOrigemField.getText());
                transferencia.setDataTransferencia(dataTransferenciaField.getText());

                DateTimeFormat format = DateTimeFormat.getFormat("dd/MM/yyyy");


                transferencia.setDataAgendamento(format.format(new Date()));

                TransferenciaRequest request = new TransferenciaRequest();
                request.setTransferencia(transferencia);

                transferenciaService.post(request, new AsyncCallback<Void>() {
                    @Override
                    public void onFailure(Throwable caught) {


                    }

                    @Override
                    public void onSuccess(Void result) {

                    }
                });

            }


        }


        class ConsultaHandler implements ClickHandler{

            List<Transferencia> response = new ArrayList<Transferencia>();

            @Override
            public void onClick(ClickEvent event) {
                transferenciaService.get(new AsyncCallback<TransferenciaResponse>() {
                    @Override
                    public void onFailure(Throwable caught) {

                    }

                    @Override
                    public void onSuccess(TransferenciaResponse result) {

                        response = result.getTransferencias();
                        RootPanel.get("testLabelContainer").add(new Label(response.get(0).getContaOrigem()));


                        CellTable<Transferencia> table = new CellTable<Transferencia>();

                        TextColumn<Transferencia> contaOrigemColumn = new TextColumn<Transferencia>() {
                            @Override
                            public String getValue(Transferencia object) {
                                return object.getContaOrigem();
                            }
                        };

                        table.addColumn(contaOrigemColumn, "Conta Origem");
                        RootPanel.get().add(table);

                    }
                });
            }
        }



        // Add a handler to send the name to the server
        TransferenciaHandler transferenciaHandler = new TransferenciaHandler();
        sendButton.addClickHandler(transferenciaHandler);

        ConsultaHandler consultaHandler = new ConsultaHandler();
        consultaButton.addClickHandler(consultaHandler);



    }
}