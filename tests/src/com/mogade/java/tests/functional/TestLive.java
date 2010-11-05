package com.mogade.java.tests.functional;

import com.mogade.java.Mogade;
import com.mogade.java.MogadeImpl;
import com.mogade.java.data.Leaderboard;
import com.mogade.java.data.Score;
import com.mogade.java.protocol.GetConfigVersionResponse;
import com.mogade.java.protocol.GetLeaderboardResponse;
import com.mogade.java.protocol.SaveScoreResponse;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.*;

public class TestLive
{
   private final static String gameKey = "4cce25151d9517161400000e";
   private final static String secret = "qRA]:A;28q]V?UU";
   private final static String leaderboardId = "4cceda2a563d8a335a000008";

   @Before
   public void setupProxy()
   {
      System.setProperty("http.proxyHost", "127.0.0.1");
      System.setProperty("http.proxyPort", "8888");
   }
   
   @Test
   public void testSaveScoreUnknownGameKey()
   {
      Mogade mogade = MogadeImpl.create("UNKNOWNGAMEKEY", secret);
      SaveScoreResponse response = mogade.saveScore("LEADER", Score.create("brian", 2000));

      assertFalse(response.isOk());
      assertFalse(response.isUnavailable());
      assertTrue(response.isError());
      assertEquals(0, response.getDaily());
      assertEquals(0, response.getWeekly());
      assertEquals(0, response.getOverall());
   }
   @Test
   public void testSaveScoreWrongSecret()
   {
      Mogade mogade = MogadeImpl.create(gameKey, "SECRET");
      SaveScoreResponse response = mogade.saveScore("LEADER", Score.create("brian", 2000));

      assertFalse(response.isOk());
      assertFalse(response.isUnavailable());
      assertTrue(response.isError());
      assertEquals(0, response.getDaily());
      assertEquals(0, response.getWeekly());
      assertEquals(0, response.getOverall());
   }
   @Test
   public void testSaveScoreSuccess()
   {
      Mogade mogade = MogadeImpl.create(gameKey, secret);
      SaveScoreResponse response = mogade.saveScore(leaderboardId, Score.create("brian", System.currentTimeMillis()));

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
      Mogade mogade = MogadeImpl.create(gameKey, secret);
      GetLeaderboardResponse response = mogade.getLeaderboard(Leaderboard.create("invalid", 1, Leaderboard.Scope.OVERALL));

      assertFalse(response.isOk());
      assertFalse(response.isUnavailable());
      assertTrue(response.isError());

      assertNull(response.getScores());
   }
   @Test
   public void testGetLeaderboardSuccess()
   {
      Mogade mogade = MogadeImpl.create(gameKey, secret);
      GetLeaderboardResponse response = mogade.getLeaderboard(Leaderboard.create(leaderboardId, 1, Leaderboard.Scope.OVERALL));

      assertTrue(response.isOk());
      assertFalse(response.isUnavailable());
      assertFalse(response.isError());

      assertTrue(response.getScores().size() > 0);
   }

   @Test
   public void testConfigVersionSuccess()
   {
      Mogade mogade = MogadeImpl.create(gameKey, secret);
      GetConfigVersionResponse response = mogade.getConfigVersion();

      assertTrue(response.isOk());
      assertFalse(response.isUnavailable());
      assertFalse(response.isError());

      assertTrue(response.getVersion() > 0);
   }
}