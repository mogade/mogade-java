package com.mogade.java;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.mogade.java.data.Leaderboard;
import com.mogade.java.data.Score;
import com.mogade.java.helpers.HttpRequest;
import com.mogade.java.helpers.Utility;
import com.mogade.java.protocol.*;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.TreeSet;

public class MogadeImpl implements Mogade
{
   public static final int VERSION = 1;
   private static final String CONTENT_TYPE = "application/json";

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

   public int getApiVersion()
   {
      return VERSION;
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
      catch(JsonParseException ex)
      {
         return new SaveScoreResponse(null, null, "json parse exception:" + ex.getMessage());
      }
      catch(IOException ex)
      {
         return new SaveScoreResponse(null, null, ex.getMessage());
      }
   }
   public GetLeaderboardResponse getLeaderboard(Leaderboard leaderboard)
   {
      if (leaderboard == null)
      {
         throw new IllegalArgumentException("Invalid leaderboard");
      }

      try
      {
         return gson.fromJson(sendRequest(new GetLeaderboardRequest(gameKey, getApiVersion(), leaderboard)), GetLeaderboardResponse.class);
      }
      catch(JsonParseException ex)
      {
         return new GetLeaderboardResponse(null, null, "json parse exception:" + ex.getMessage());
      }
      catch(IOException ex)
      {
         return new GetLeaderboardResponse(null, null, ex.getMessage());
      }
   }
   private String sendRequest(Request request) throws IOException
   {
      request.setSig(request.calculateSignature(secret));
      String jsonRequest = gson.toJson(request);
      MogadeConfiguration config = MogadeConfigurationImpl.instance();
      System.setProperty("http.keepAlive", String.valueOf(config.getKeepAlive()));
      return HttpRequest.execute(new URL(config.getApiUrl()+request.getUrl()), jsonRequest.getBytes(), CONTENT_TYPE, request.getRequestMethod().toString(), config.getConnectTimeout(), config.getReadTimeout());
   }
}