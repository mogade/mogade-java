package com.mogade.java.tests;

import com.mogade.java.helpers.Utility;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class FakeServer implements HttpHandler
{
   private Queue<FakeResponse> responses = new ConcurrentLinkedQueue<FakeResponse>();
   HttpServer server;

   public FakeServer(String ip, int port) throws IOException
   {
      server = HttpServer.create(new InetSocketAddress(InetAddress.getByName(ip), port), 5);
      server.createContext("/", this);
      server.start();
   }

   public void addResponse(FakeResponse response)
   {
      responses.add(response);
   }
   public void waitForResponse() throws InterruptedException
   {
      FakeResponse response = responses.peek();

      while (response != null)
      {
         Thread.sleep(200);
         response = responses.peek();
      }
   }
   public void stop()
   {
      server.stop(0);
   }

   public void handle(HttpExchange httpExchange) throws IOException
   {
      // check for application/json
      FakeResponse response = null;

      String contentType = httpExchange.getRequestHeaders().getFirst("Content-Type");
      if (Utility.isNullOrEmpty(contentType) || !contentType.equals("application/json"))
      {
         response = new FakeResponse(HttpURLConnection.HTTP_BAD_REQUEST, "{\"error\":\"bad content type\"}");
      }

      httpExchange.getRequestBody().close();

      Headers responseHeaders = httpExchange.getResponseHeaders();
      responseHeaders.set("Content-Type", "application/json");

      if (response != null)
      {
         send(httpExchange, response);
         return;
      }

      response = responses.peek();

      if (response != null)
      {
         send(httpExchange, response);
         return;
      }

      response = new FakeResponse(HttpURLConnection.HTTP_BAD_REQUEST, "{\"error\":\"no test responses to send\"}");
      send(httpExchange, response);
   }
   private static void send(HttpExchange httpExchange, FakeResponse response) throws IOException
   {
      httpExchange.sendResponseHeaders(response.responseCode, 0);

      OutputStream out = httpExchange.getResponseBody();

      out.write(response.body.getBytes());
      out.close();
   }

   public static class FakeResponse
   {
      private int responseCode;
      private String body;

      public FakeResponse(int responseCode, String body)
      {
         this.responseCode = responseCode;
         this.body = body;
      }
   }
}