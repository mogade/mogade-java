package com.mogade.java;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.mogade.java.data.Leaderboard;
import com.mogade.java.data.Score;
import com.mogade.java.helpers.HttpRequest;
import com.mogade.java.helpers.Validator;
import com.mogade.java.protocol.*;

import java.io.IOException;
import java.net.URL;

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
      Validator.assertNotNullOrEmpty(gameKey, "Invalid gameKey");
      Validator.assertNotNullOrEmpty(secret, "Invalid secret");
      return new MogadeImpl(gameKey, secret);
   }

   public int getApiVersion()
   {
      return VERSION;
   }

   public SaveScoreResponse saveScore(String leaderboardId, Score score)
   {
      Validator.assertNotNullOrEmpty(leaderboardId, "Invalid leaderboardId");
      Validator.assertNotNull(score, "Invalid score");

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
      Validator.assertNotNull(leaderboard, "Invalid leaderboard");

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
   public GetConfigVersionResponse getConfigVersion()
   {
      try
      {
         return gson.fromJson(sendRequest(new GetConfigVersionRequest(gameKey, getApiVersion())), GetConfigVersionResponse.class);
      }
      catch(JsonParseException ex)
      {
         return new GetConfigVersionResponse(null, null, "json parse exception:" + ex.getMessage());
      }
      catch(IOException ex)
      {
         return new GetConfigVersionResponse(null, null, ex.getMessage());
      }
   }
   public GetConfigResponse getConfig()
   {
      try
      {
         return gson.fromJson(sendRequest(new GetConfigRequest(gameKey, getApiVersion())), GetConfigResponse.class);
      }
      catch(JsonParseException ex)
      {
         return new GetConfigResponse(null, null, "json parse exception:" + ex.getMessage());
      }
      catch(IOException ex)
      {
         return new GetConfigResponse(null, null, ex.getMessage());
      }
   }
   public GetUserGameDataResponse getUserGameData(String username, String unique)
   {
      Validator.assertNotNullOrEmpty(username, "Invalid username");
      Validator.assertNotNullOrEmpty(unique, "Invalid unique");
      try
      {
         return gson.fromJson(sendRequest(new GetUserGameDataRequest(gameKey, getApiVersion(), username, unique)), GetUserGameDataResponse.class);
      }
      catch(JsonParseException ex)
      {
         return new GetUserGameDataResponse(null, null, "json parse exception:" + ex.getMessage());
      }
      catch(IOException ex)
      {
         return new GetUserGameDataResponse(null, null, ex.getMessage());
      }
   }
   public SaveAchievementResponse saveAchievement(String achievementId, String username, String unique)
   {
      Validator.assertNotNullOrEmpty(username, "Invalid achievementId");
      Validator.assertNotNullOrEmpty(unique, "Invalid username");
      Validator.assertNotNullOrEmpty(unique, "Invalid unique");
      try
      {
         return gson.fromJson(sendRequest(new SaveAchievementRequest(gameKey, getApiVersion(), achievementId, username, unique)), SaveAchievementResponse.class);
      }
      catch(JsonParseException ex)
      {
         return new SaveAchievementResponse(null, null, "json parse exception:" + ex.getMessage());
      }
      catch(IOException ex)
      {
         return new SaveAchievementResponse(null, null, ex.getMessage());
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