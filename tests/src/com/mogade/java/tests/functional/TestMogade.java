package com.mogade.java.tests.functional;

import static junit.framework.Assert.*;
import static junit.framework.Assert.assertEquals;

import com.mogade.java.Mogade;
import com.mogade.java.MogadeConfigurationImpl;
import com.mogade.java.MogadeImpl;
import com.mogade.java.data.Achievement;
import com.mogade.java.data.Leaderboard;
import com.mogade.java.data.Score;
import com.mogade.java.helpers.Utility;
import com.mogade.java.protocol.*;
import com.mogade.java.tests.FakeServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.HttpURLConnection;

public class TestMogade
{
   private FakeServer fakeServer;
   private String fakeServerIp = "127.0.0.1";
   private int fakeServerPort = 9500;

   @Before
   public void startFakeServer() throws IOException
   {
      fakeServer = new FakeServer(fakeServerIp, fakeServerPort);
      MogadeConfigurationImpl.instance().setApiUrl(Utility.format("http://{0}:{1}/", fakeServerIp, fakeServerPort));
   }
   @After
   public void stopFakeServer() throws IOException
   {
      if (fakeServer != null)
      {
         fakeServer.stop();
      }
      MogadeConfigurationImpl.instance().resetDefaults();
   }

   @Test
   public void testUnavailable()
   {
      fakeServer.addResponse(new FakeServer.FakeResponse(HttpURLConnection.HTTP_OK, "{\"maintenance\":\"down for maintenance\"}"));
      Mogade mogade = MogadeImpl.create("GAMEKEY", "SECRET");
      SaveScoreResponse response = mogade.saveScore("LEADER", Score.create("brian", "unique", 2000));

      assertFalse(response.isOk());
      assertTrue(response.isUnavailable());
      assertFalse(response.isError());
      assertEquals("down for maintenance", response.getStatus());
   }
   @Test
   public void testSaveScoreUnknownGameKey()
   {
      fakeServer.addResponse(new FakeServer.FakeResponse(HttpURLConnection.HTTP_OK, "{\"error\":\"unknown game key\"}"));
      Mogade mogade = MogadeImpl.create("UNKNOWNGAMEKEY", "SECRET");
      SaveScoreResponse response = mogade.saveScore("LEADER", Score.create("brian", "unique", 2000));

      assertFalse(response.isOk());
      assertFalse(response.isUnavailable());
      assertTrue(response.isError());
      assertEquals("unknown game key", response.getStatus());
      assertEquals(0, response.getDaily());
      assertEquals(0, response.getWeekly());
      assertEquals(0, response.getOverall());
   }
   @Test
   public void testSaveScoreWrongSecret()
   {
      fakeServer.addResponse(new FakeServer.FakeResponse(HttpURLConnection.HTTP_OK, "{\"error\":\"invalid sig\"}"));
      Mogade mogade = MogadeImpl.create("GAMEKEY", "WRONGSECRET");
      SaveScoreResponse response = mogade.saveScore("LEADER", Score.create("brian", "unique", 2000));

      assertFalse(response.isOk());
      assertFalse(response.isUnavailable());
      assertTrue(response.isError());
      assertEquals("invalid sig", response.getStatus());
      assertEquals(0, response.getDaily());
      assertEquals(0, response.getWeekly());
      assertEquals(0, response.getOverall());
   }
   @Test
   public void testSaveScoreSuccessNotAllRanksReturned()
   {
      fakeServer.addResponse(new FakeServer.FakeResponse(HttpURLConnection.HTTP_OK, "{\"daily\":25}"));
      Mogade mogade = MogadeImpl.create("GAMEKEY", "SECRET");
      SaveScoreResponse response = mogade.saveScore("lid", Score.create("brian", "unique", System.currentTimeMillis()));

      assertTrue(response.isOk());
      assertFalse(response.isUnavailable());
      assertFalse(response.isError());

      assertEquals(25, response.getDaily());
      assertEquals(0, response.getWeekly());
      assertEquals(0, response.getOverall());
   }
   @Test
   public void testSaveScoreInvalidUsername()
   {
      fakeServer.addResponse(new FakeServer.FakeResponse(HttpURLConnection.HTTP_OK, "{\"error\":\"invalid data\",\"info\":\"username is a badword\"}"));
      Mogade mogade = MogadeImpl.create("GAMEKEY", "SECRET");
      SaveScoreResponse response = mogade.saveScore("lid", Score.create("brian", "unique", System.currentTimeMillis()));

      assertFalse(response.isOk());
      assertFalse(response.isUnavailable());
      assertTrue(response.isError());
      assertEquals("invalid data", response.getStatus());
      assertEquals("username is a badword", response.getInfo());

      assertEquals(0, response.getDaily());
      assertEquals(0, response.getWeekly());
      assertEquals(0, response.getOverall());
   }
   @Test
   public void testSaveScoreInvalidUnique()
   {
      fakeServer.addResponse(new FakeServer.FakeResponse(HttpURLConnection.HTTP_OK, "{\"error\":\"missing unique\"}"));
      Mogade mogade = MogadeImpl.create("GAMEKEY", "SECRET");
      SaveScoreResponse response = mogade.saveScore("lid", Score.create("brian", "unique", System.currentTimeMillis()));

      assertFalse(response.isOk());
      assertFalse(response.isUnavailable());
      assertTrue(response.isError());
      assertEquals("missing unique", response.getStatus());

      assertEquals(0, response.getDaily());
      assertEquals(0, response.getWeekly());
      assertEquals(0, response.getOverall());
   }
   @Test
   public void testSaveScoreJsonParseException()
   {
      fakeServer.addResponse(new FakeServer.FakeResponse(HttpURLConnection.HTTP_OK, "not json"));
      Mogade mogade = MogadeImpl.create("GAMEKEY", "SECRET");
      SaveScoreResponse response = mogade.saveScore("lid", Score.create("brian", "unique", System.currentTimeMillis()));

      assertFalse(response.isOk());
      assertFalse(response.isUnavailable());
      assertTrue(response.isError());
      assertTrue(response.getStatus().startsWith("json parse exception"));
   }
   @Test
   public void testSaveScoreSuccess()
   {
      fakeServer.addResponse(new FakeServer.FakeResponse(HttpURLConnection.HTTP_OK, "{\"daily\":5,\"weekly\":10,\"overall\":171}"));
      Mogade mogade = MogadeImpl.create("GAMEKEY", "SECRET");
      SaveScoreResponse response = mogade.saveScore("lid", Score.create("brian", "unique", System.currentTimeMillis()));

      assertTrue(response.isOk());
      assertFalse(response.isUnavailable());
      assertFalse(response.isError());

      assertEquals(5, response.getDaily());
      assertEquals(10, response.getWeekly());
      assertEquals(171, response.getOverall());
   }

   @Test
   public void testGetLeaderboardUnknownleaderboardId()
   {
      fakeServer.addResponse(new FakeServer.FakeResponse(HttpURLConnection.HTTP_OK, "{\"error\":\"invalid leaderboard id\"}"));
      Mogade mogade = MogadeImpl.create("GAMEKEY", "SECRET");
      GetLeaderboardResponse response = mogade.getLeaderboard(Leaderboard.create("invalid", 1, Leaderboard.Scope.OVERALL));

      assertFalse(response.isOk());
      assertFalse(response.isUnavailable());
      assertTrue(response.isError());
      assertEquals("invalid leaderboard id", response.getStatus());
      assertNull(response.getScores());
   }
   @Test
   public void testGetLeaderboardSuccess()
   {
      fakeServer.addResponse(new FakeServer.FakeResponse(HttpURLConnection.HTTP_OK, "{\"info\":\"wicked leaderboard\",\"scores\":[{\"points\":1288645059419,\"data\":null,\"username\":\"brian\"},{\"points\":1288644912581,\"data\":\"some data\",\"username\":\"brian2\"}]}"));
      Mogade mogade = MogadeImpl.create("GAMEKEY", "SECRET");
      GetLeaderboardResponse response = mogade.getLeaderboard(Leaderboard.create("lid", 1, Leaderboard.Scope.OVERALL));

      assertEquals("wicked leaderboard", response.getInfo());
      assertTrue(response.isOk());
      assertFalse(response.isUnavailable());
      assertFalse(response.isError());

      assertTrue(response.getScores().size() == 2);

      Score score = response.getScores().get(0);
      assertEquals(1288645059419L, score.getPoints());
      assertNull(score.getData());
      assertEquals("brian", score.getUsername());

      score = response.getScores().get(1);
      assertEquals(1288644912581L, score.getPoints());
      assertEquals("some data", score.getData());
      assertEquals("brian2", score.getUsername());
   }

   @Test
   public void testGetConfigVersionSuccess()
   {
      fakeServer.addResponse(new FakeServer.FakeResponse(HttpURLConnection.HTTP_OK, "{\"version\":10}"));
      Mogade mogade = MogadeImpl.create("GAMEKEY", "SECRET");
      GetConfigVersionResponse response = mogade.getConfigVersion();

      assertTrue(response.isOk());
      assertFalse(response.isUnavailable());
      assertFalse(response.isError());

      assertEquals(10, response.getVersion());
   }
   @Test
   public void testGetConfigNoAchievements()
   {
      fakeServer.addResponse(new FakeServer.FakeResponse(HttpURLConnection.HTTP_OK, "{\"version\":10}"));
      Mogade mogade = MogadeImpl.create("GAMEKEY", "SECRET");
      GetConfigResponse response = mogade.getConfig();

      assertTrue(response.isOk());
      assertFalse(response.isUnavailable());
      assertFalse(response.isError());

      assertEquals(10, response.getVersion());
      assertFalse(response.hasAchievements());
      assertNull(response.getAchievements());
   }
   @Test
   public void testGetConfigEmptyAchievements()
   {
      fakeServer.addResponse(new FakeServer.FakeResponse(HttpURLConnection.HTTP_OK, "{\"version\":10,\"achievements\":[]}"));
      Mogade mogade = MogadeImpl.create("GAMEKEY", "SECRET");
      GetConfigResponse response = mogade.getConfig();

      assertTrue(response.isOk());
      assertFalse(response.isUnavailable());
      assertFalse(response.isError());

      assertEquals(10, response.getVersion());
      assertNotNull(response.getAchievements());
      assertFalse(response.hasAchievements());
      assertEquals(0, response.getAchievements().size());
   }
   @Test
   public void testGetConfigSuccess()
   {
      fakeServer.addResponse(new FakeServer.FakeResponse(HttpURLConnection.HTTP_OK, "{\"version\":10,\"achievements\":[{\"id\":\"9sdf923sdfsfd9\",\"name\":\"Level 1 expert\",\"desc\":\"finish first level in 20 secs\",\"points\":90210},{\"id\":\"2l3k23kj2l3jkl3k\",\"name\":\"Level 5 expert\", \"desc\":\"finish fifth level in 120 secs\",\"points\":450578}]}"));
      Mogade mogade = MogadeImpl.create("GAMEKEY", "SECRET");
      GetConfigResponse response = mogade.getConfig();

      assertTrue(response.isOk());
      assertFalse(response.isUnavailable());
      assertFalse(response.isError());

      assertEquals(10, response.getVersion());

      assertTrue(response.hasAchievements());
      assertTrue(response.getAchievements().size() == 2);

      Achievement achievement = response.getAchievements().get(0);
      assertEquals("9sdf923sdfsfd9", achievement.getId());
      assertEquals("Level 1 expert", achievement.getName());
      assertEquals("finish first level in 20 secs", achievement.getDescription());
      assertEquals(90210, achievement.getPoints());

      achievement = response.getAchievements().get(1);
      assertEquals("2l3k23kj2l3jkl3k", achievement.getId());
      assertEquals("Level 5 expert", achievement.getName());
      assertEquals("finish fifth level in 120 secs", achievement.getDescription());
      assertEquals(450578, achievement.getPoints());
   }
   @Test
   public void testGetUserGameDataNoEarnedAchievements()
   {
      fakeServer.addResponse(new FakeServer.FakeResponse(HttpURLConnection.HTTP_OK, "{\"leaderboards\":[{\"id\":\"4cd70dcd5a740858ad000007\",\"points\":1289704288638}]}"));
      Mogade mogade = MogadeImpl.create("GAMEKEY", "SECRET");
      GetUserGameDataResponse response = mogade.getUserGameData("brian", "1");

      assertTrue(response.isOk());
      assertFalse(response.isUnavailable());
      assertFalse(response.isError());

      assertFalse(response.hasAchievements());
      assertNull(response.getAchievements());
   }
   @Test
   public void testGetUserGameDataEmptyEarnedAchievements()
   {
      fakeServer.addResponse(new FakeServer.FakeResponse(HttpURLConnection.HTTP_OK, "{\"achievements\":[],\"{leaderboards\":[{\"id\":\"4cd70dcd5a740858ad000007\",\"points\":1289704288638}]}\"}"));
      Mogade mogade = MogadeImpl.create("GAMEKEY", "SECRET");
      GetUserGameDataResponse response = mogade.getUserGameData("brian", "1");

      assertTrue(response.isOk());
      assertFalse(response.isUnavailable());
      assertFalse(response.isError());

      assertFalse(response.hasAchievements());
      assertNotNull(response.getAchievements());
      assertEquals(0, response.getAchievements().size());
   }
   @Test
   public void testGetUserGameDataNoHighScoresAchievements()
   {
      fakeServer.addResponse(new FakeServer.FakeResponse(HttpURLConnection.HTTP_OK, "{\"achievements\":[\"achieveid1\",\"achieveid2\",\"achieveid3\"]}"));
      Mogade mogade = MogadeImpl.create("GAMEKEY", "SECRET");
      GetUserGameDataResponse response = mogade.getUserGameData("brian", "1");

      assertTrue(response.isOk());
      assertFalse(response.isUnavailable());
      assertFalse(response.isError());

      assertFalse(response.hasHighScores());
      assertNull(response.getHighScores());
   }
   @Test
   public void testGetUserGameDataEmptyHighScores()
   {
      fakeServer.addResponse(new FakeServer.FakeResponse(HttpURLConnection.HTTP_OK, "{\"achievements\":[\"achieveid1\",\"achieveid2\",\"achieveid3\"],\"leaderboards\":[]}"));
      Mogade mogade = MogadeImpl.create("GAMEKEY", "SECRET");
      GetUserGameDataResponse response = mogade.getUserGameData("brian", "1");

      assertTrue(response.isOk());
      assertFalse(response.isUnavailable());
      assertFalse(response.isError());

      assertFalse(response.hasHighScores());
      assertNotNull(response.getHighScores());
      assertEquals(0, response.getHighScores().size());
   }
   @Test
   public void testGetUserGameDataSuccess()
   {
      fakeServer.addResponse(new FakeServer.FakeResponse(HttpURLConnection.HTTP_OK, "{\"achievements\":[\"achieveid1\",\"achieveid2\",\"achieveid3\"],\"leaderboards\":[{\"id\":\"lid\",\"points\":1289704288638}]}\"}"));
      Mogade mogade = MogadeImpl.create("GAMEKEY", "SECRET");
      GetUserGameDataResponse response = mogade.getUserGameData("brian", "1");

      assertTrue(response.isOk());
      assertFalse(response.isUnavailable());
      assertFalse(response.isError());

      assertTrue(response.hasAchievements());
      assertNotNull(response.getAchievements());

      assertEquals("achieveid1", response.getAchievements().get(0));
      assertEquals("achieveid2", response.getAchievements().get(1));
      assertEquals("achieveid3", response.getAchievements().get(2));

      assertTrue(response.hasHighScores());
      assertNotNull(response.getHighScores());

      assertEquals("lid", response.getHighScores().get(0).getLeaderboardId());
      assertEquals(1289704288638L, response.getHighScores().get(0).getPoints());
   }
}