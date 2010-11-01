package com.mogade.java.tests.functional;

import static junit.framework.Assert.*;
import static junit.framework.Assert.assertEquals;

import com.mogade.java.Mogade;
import com.mogade.java.MogadeConfigurationImpl;
import com.mogade.java.MogadeImpl;
import com.mogade.java.data.Leaderboard;
import com.mogade.java.data.Score;
import com.mogade.java.helpers.Utility;
import com.mogade.java.protocol.GetLeaderboardResponse;
import com.mogade.java.protocol.SaveScoreResponse;
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
      SaveScoreResponse response = mogade.saveScore("LEADER", Score.create("brian", 2000));

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
      SaveScoreResponse response = mogade.saveScore("LEADER", Score.create("brian", 2000));

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
      SaveScoreResponse response = mogade.saveScore("LEADER", Score.create("brian", 2000));

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
      fakeServer.addResponse(new FakeServer.FakeResponse(HttpURLConnection.HTTP_OK, "{\"daily\":1}"));
      Mogade mogade = MogadeImpl.create("GAMEKEY", "SECRET");
      SaveScoreResponse response = mogade.saveScore("lid", Score.create("brian", System.currentTimeMillis()));

      assertTrue(response.isOk());
      assertFalse(response.isUnavailable());
      assertFalse(response.isError());

      if (response.getDaily() > 0)
      {
         assertEquals(1, response.getDaily());
      }
      if (response.getWeekly() > 0)
      {
         assertEquals(1, response.getWeekly());
      }
      if (response.getOverall() > 0)
      {
         assertEquals(1, response.getOverall());
      }
   }
   @Test
   public void testSaveScoreSuccess()
   {
      fakeServer.addResponse(new FakeServer.FakeResponse(HttpURLConnection.HTTP_OK, "{\"daily\":1,\"weekly\":1,\"overall\":1}"));
      Mogade mogade = MogadeImpl.create("GAMEKEY", "SECRET");
      SaveScoreResponse response = mogade.saveScore("lid", Score.create("brian", System.currentTimeMillis()));

      assertTrue(response.isOk());
      assertFalse(response.isUnavailable());
      assertFalse(response.isError());

      if (response.getDaily() > 0)
      {
         assertEquals(1, response.getDaily());
      }
      if (response.getWeekly() > 0)
      {
         assertEquals(1, response.getWeekly());
      }
      if (response.getOverall() > 0)
      {
         assertEquals(1, response.getOverall());
      }
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
      fakeServer.addResponse(new FakeServer.FakeResponse(HttpURLConnection.HTTP_OK, "{\"scores\":[{\"points\":1288645059419,\"data\":null,\"username\":\"brian\"},{\"points\":1288644912581,\"data\":null,\"username\":\"brian\"},{\"points\":1288644811199,\"data\":null,\"username\":\"brian\"},{\"points\":1288644808116,\"data\":null,\"username\":\"brian\"},{\"points\":1288644804249,\"data\":null,\"username\":\"brian\"},{\"points\":1288644788102,\"data\":null,\"username\":\"brian\"},{\"points\":1288644777987,\"data\":null,\"username\":\"brian\"},{\"points\":1288644766700,\"data\":null,\"username\":\"brian\"},{\"points\":1288640696617,\"data\":null,\"username\":\"brian\"},{\"points\":1288640603765,\"data\":null,\"username\":\"brian\"}]}"));
      Mogade mogade = MogadeImpl.create("GAMEKEY", "SECRET");
      GetLeaderboardResponse response = mogade.getLeaderboard(Leaderboard.create("lid", 1, Leaderboard.Scope.OVERALL));

      assertTrue(response.isOk());
      assertFalse(response.isUnavailable());
      assertFalse(response.isError());

      assertTrue(response.getScores().size() > 0);
   }
}