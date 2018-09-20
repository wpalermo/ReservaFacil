package com.mycompany.mywebapp.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mycompany.mywebapp.shared.TransferenciaRequest;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
  void greetServer(String input, AsyncCallback<String> callback)
      throws IllegalArgumentException;


  void post(TransferenciaRequest request, AsyncCallback<Void> async);
}
