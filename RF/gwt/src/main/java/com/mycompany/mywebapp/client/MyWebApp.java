package com.mycompany.mywebapp.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.datepicker.client.DateBox;
import com.mycompany.mywebapp.shared.bean.Transferencia;
import com.mycompany.mywebapp.shared.request.TransferenciaRequest;
import com.mycompany.mywebapp.shared.request.TransferenciaResponse;
import org.springframework.util.StringUtils;

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
    @SuppressWarnings("Duplicates")
    public void onModuleLoad() {
        final Button sendButton = new Button("Transferir");
        final Button consultaButton = new Button("Consultar transferencias");
        final TextBox nameField = new TextBox();
        final Label errorLabel = new Label();




        //ReservaFacil

        final TextBox contaOrigemField = new TextBox();
        final Label contaOrigemLabel = new Label("Conta Origem");



        contaOrigemField.addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent event) {
                if(!contaOrigemField.getText().matches("[0-9]*")) {
                    Window.alert("Caracter inválido " + contaOrigemField.getText());

                    while(!contaOrigemField.getText().substring(contaOrigemField.getText().length()-1).matches("[0-9]*")){
                        contaOrigemField.setText(contaOrigemField.getText().substring(0, contaOrigemField.getText().length()-1));
                    }

                }

                if(contaOrigemField.getText().length() > 4){
                    Window.alert("Quantidade de carcateres excedeu limite (Maximo 4)");
                    contaOrigemField.setText(contaOrigemField.getText().substring(0, 4));
                }

            }
        });

        final TextBox contaDestinoField = new TextBox();


        contaDestinoField.addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent event) {
                if(!contaDestinoField.getText().matches("[0-9]*")) {
                    Window.alert("Caracter inválido " + contaDestinoField.getText());

                    while(!contaDestinoField.getText().substring(contaDestinoField.getText().length()-1).matches("[0-9]*")){
                        contaDestinoField.setText(contaDestinoField.getText().substring(0, contaDestinoField.getText().length()-1));
                    }

                }

                if(contaDestinoField.getText().length() > 4){
                    Window.alert("Quantidade de carcateres excedeu limite (Maximo 4)");
                    contaDestinoField.setText(contaDestinoField.getText().substring(0, 4));
                }

            }
        });


        final Label contaDestinoLabel = new Label("Conta Destino");

        final TextBox valorField = new TextBox();
        final Label valorLabel = new Label("Valor Transacao");

        final DateBox dataTransferenciaField = new DateBox();
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

                DateTimeFormat format = DateTimeFormat.getFormat("dd/MM/yyyy");


                transferencia.setDataTransferencia(format.format(dataTransferenciaField.getValue()));
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

                        CellTable<Transferencia> table = new CellTable<Transferencia>();

                        TextColumn<Transferencia> contaOrigemColumn = new TextColumn<Transferencia>() {
                            @Override
                            public String getValue(Transferencia object) {
                                return object.getContaOrigem();
                            }
                        };


                        TextColumn<Transferencia> contaDestinoColumn = new TextColumn<Transferencia>() {
                            @Override
                            public String getValue(Transferencia object) {
                                return object.getContaDestino();
                            }
                        };

                        TextColumn<Transferencia> valorColumn = new TextColumn<Transferencia>() {
                            @Override
                            public String getValue(Transferencia object) {
                                return "R$ " + object.getValor().toString();
                            }
                        };

                        TextColumn<Transferencia> taxaColumn = new TextColumn<Transferencia>() {
                            @Override
                            public String getValue(Transferencia object) {
                                return "R$ " + object.getTaxa().toString();
                            }
                        };

                        TextColumn<Transferencia> dataTransferenciaColumn = new TextColumn<Transferencia>() {
                            @Override
                            public String getValue(Transferencia object) {
                                return object.getDataTransferencia();
                            }
                        };

                        TextColumn<Transferencia> dataAgendamentoColumn = new TextColumn<Transferencia>() {
                            @Override
                            public String getValue(Transferencia object) {
                                return object.getDataAgendamento();
                            }
                        };

                        TextColumn<Transferencia> statusColumn = new TextColumn<Transferencia>() {
                            @Override
                            public String getValue(Transferencia object) {
                                return object.getStatus();
                            }
                        };



                        table.addColumn(contaOrigemColumn, "Conta Origem");
                        table.addColumn(contaDestinoColumn, "Conta Destino");
                        table.addColumn(valorColumn, "Valor Transferencia");
                        table.addColumn(taxaColumn, "Valor Taxa");
                        table.addColumn(dataTransferenciaColumn, "Data Transferencia");
                        table.addColumn(dataAgendamentoColumn, "Data Agendamento");
                        table.addColumn(statusColumn, "Status Transferencia");


                        table.setRowCount(response.size());
                        table.setRowData(0, response);

                        RootPanel.get().clear();
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