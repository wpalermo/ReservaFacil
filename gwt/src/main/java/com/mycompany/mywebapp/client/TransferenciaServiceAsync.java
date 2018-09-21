package com.mycompany.mywebapp.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mycompany.mywebapp.shared.TransferenciaRequest;
import com.mycompany.mywebapp.shared.TransferenciaResponse;

import java.util.List;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface TransferenciaServiceAsync {
    void greetServer(String input, AsyncCallback<String> callback)
            throws IllegalArgumentException;


    void post(TransferenciaRequest request, AsyncCallback<Void> async);

    void get(AsyncCallback<TransferenciaResponse> async);
}
