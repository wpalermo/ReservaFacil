package com.mycompany.mywebapp.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.mycompany.mywebapp.shared.request.TransferenciaRequest;
import com.mycompany.mywebapp.shared.request.TransferenciaResponse;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("transferencia")
public interface TransferenciaService extends RemoteService {
  String greetServer(String name) throws IllegalArgumentException;
  void post(TransferenciaRequest request);
  TransferenciaResponse get();



}
