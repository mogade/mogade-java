package com.mogade.java;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mogade.java.data.Score;
import com.mogade.java.helpers.HttpRequest;
import com.mogade.java.helpers.Utility;
import com.mogade.java.protocol.Request;
import com.mogade.java.protocol.SaveScoreRequest;
import com.mogade.java.protocol.SaveScoreResponse;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.TreeSet;

public class MogadeImpl implements Mogade
{
   public static final String VERSION = "1";
   public static final String APIURL = "http://api.mogade.com/";

   private Gson gson = new GsonBuilder().create();
   private String gameKey;
   private String secret;

   private MogadeImpl(String gameKey, String secret)
   {
      this.gameKey = gameKey;
      this.secret = secret;
   }
   public static Mogade create(String gameKey, String secret)
   {
      if (Utility.isNullOrEmpty(gameKey))
      {
         throw new IllegalArgumentException("Invalid gameKey");
      }
      if (Utility.isNullOrEmpty(secret))
      {
         throw new IllegalArgumentException("Invalid secret");
      }
      return new MogadeImpl(gameKey, secret);
   }
   public SaveScoreResponse saveScore(String leaderboardId, Score score)
   {
      if (Utility.isNullOrEmpty(leaderboardId))
      {
         throw new IllegalArgumentException("Invalid leaderboardId");
      }
      if (score == null)
      {
         throw new IllegalArgumentException("Invalid score");
      }

      try
      {
         return gson.fromJson(sendRequest(new SaveScoreRequest(gameKey, VERSION, leaderboardId, score)), SaveScoreResponse.class);
      }
      catch(IOException ex)
      {
         return new SaveScoreResponse(ex.getMessage());
      }
   }

   private String sendRequest(Request request) throws IOException
   {
      createAndSetSig(request);
      String jsonRequest = gson.toJson(request);
      return HttpRequest.execute(new URL(APIURL+request.getUrl()), URLEncoder.encode(jsonRequest,"UTF-8").getBytes(), "application/json", request.getRequestMethod().toString());
   }
   private void createAndSetSig(Request request)
   {
      Map<String,String> elements = Utility.flattenJsonElement(gson.toJsonTree(request), null);
      StringBuilder sb = new StringBuilder(100);

      for (String k : new TreeSet<String>(elements.keySet()))
      {
         sb.append(k);
         sb.append("=");
         sb.append(elements.get(k));
         sb.append("&");
      }

      sb.append(secret);
      request.setSig(Utility.md5(sb.toString()));
   }
}