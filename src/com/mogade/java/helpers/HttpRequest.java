package com.mogade.java.helpers;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequest
{
   public static String execute(URL url, byte[] data, String contentType, String requestMethod) throws IOException
   {
      return execute(url, data, contentType, requestMethod, 1000, 5000);
   }
   public static String execute(URL url, byte[] data, String contentType, String requestMethod, int connectTimeout, int readTimeout) throws IOException
   {
      DataOutputStream output = null;
      try
      {
         HttpURLConnection connection = (HttpURLConnection) url.openConnection();
         connection.setConnectTimeout(connectTimeout);
         connection.setReadTimeout(readTimeout);
         connection.setUseCaches(false);
         connection.setDoInput(true);
         connection.setDoOutput(true);
         connection.setRequestProperty("Content-Type", contentType);
         connection.setRequestMethod(requestMethod);

         output = new DataOutputStream(connection.getOutputStream());
         output.write(data, 0, data.length);
         output.flush();

         int responseCode = connection.getResponseCode();
         if (responseCode == HttpURLConnection.HTTP_OK)
         {
            return readAll(connection.getInputStream());
         }
         if (responseCode == HttpURLConnection.HTTP_BAD_REQUEST || responseCode == HttpURLConnection.HTTP_UNAVAILABLE)
         {
            return readAll(connection.getErrorStream());
         }
         throw new IOException(connection.getResponseMessage());
      }
      finally
      {
         if (output != null)
         {
            output.close();
         }
      }
   }
   private static String readAll(InputStream inputStream) throws IOException
   {
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      try
      {
         byte [] buffer = new byte[1024];
         int len;

         while((len = inputStream.read(buffer)) > 0)
         {
            outputStream.write(buffer, 0, len);
         }
         return new String(outputStream.toByteArray());
      }
      finally
      {
         inputStream.close();
         outputStream.close();
      }
   }
}