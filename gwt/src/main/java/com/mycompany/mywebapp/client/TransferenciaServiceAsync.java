package com.mycompany.mywebapp.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mycompany.mywebapp.shared.request.TransferenciaRequest;
import com.mycompany.mywebapp.shared.request.TransferenciaResponse;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface TransferenciaServiceAsync {
    void greetServer(String input, AsyncCallback<String> callback)
            throws IllegalArgumentException;


    void post(TransferenciaRequest request, AsyncCallback<Void> async);

    void get(AsyncCallback<TransferenciaResponse> async);
}
