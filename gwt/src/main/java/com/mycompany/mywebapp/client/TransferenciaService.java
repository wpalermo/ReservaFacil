package com.mycompany.mywebapp.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.mycompany.mywebapp.shared.TransferenciaRequest;
import com.mycompany.mywebapp.shared.TransferenciaResponse;

import java.util.List;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("transferencia")
public interface TransferenciaService extends RemoteService {
  String greetServer(String name) throws IllegalArgumentException;
  void post(TransferenciaRequest request);
  TransferenciaResponse get();



}
