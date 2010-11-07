package com.mogade.java.tests.functional;

import com.mogade.java.Mogade;
import com.mogade.java.MogadeConfigurationImpl;
import com.mogade.java.MogadeImpl;
import com.mogade.java.data.Achievement;
import com.mogade.java.data.Leaderboard;
import com.mogade.java.data.Score;
import com.mogade.java.helpers.Utility;
import com.mogade.java.protocol.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.*;

public class TestLive
{
   private final static String gameKey = "4cd70c955a740858ad000005";
   private final static String secret = "tY10w^l@a0s<F<EZ\\c";
   private final static String leaderboardId = "4cd70dcd5a740858ad000007";
   private final static String achievementId = "4cd70df95a740858ad000009";
   private final static long achievementPoints = 451;

   @Before
   public void setupProxy()
   {
      System.setProperty("http.proxyHost", "127.0.0.1");
      System.setProperty("http.proxyPort", "8888");
      MogadeConfigurationImpl.instance().setApiUrl("http://testing.mogade.com/api/");
   }

   @After
   public void teardown()
   {
      MogadeConfigurationImpl.instance().resetDefaults();
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
   @Test
   public void testConfigSuccess()
   {
      Mogade mogade = MogadeImpl.create(gameKey, secret);
      GetConfigResponse response = mogade.getConfig();

      assertTrue(response.isOk());
      assertFalse(response.isUnavailable());
      assertFalse(response.isError());

      assertTrue(response.getVersion() > 0);
      assertTrue(response.hasAchievements());
      assertNotNull(response.getAchievements());
      assertTrue(response.getAchievements().size() > 0);

      assertNotNull(response.getAchievements().get(0).getId());
      assertNotNull(response.getAchievements().get(0).getName());
      assertNotNull(response.getAchievements().get(0).getDescription());
      assertTrue(response.getAchievements().get(0).getPoints() > 0);
   }
   @Test
   public void testGetUserGameDataResponse()
   {
      Mogade mogade = MogadeImpl.create(gameKey, secret);
      GetUserGameDataResponse response = mogade.getUserGameData("brian", "1");

      assertTrue(response.isOk());
      assertFalse(response.isUnavailable());
      assertFalse(response.isError());

      assertTrue(response.hasAchievements());
      assertNotNull(response.getAchievements());
      assertTrue(response.getAchievements().size() > 0);

      GetConfigResponse config = mogade.getConfig();

      for(String myAchievement : response.getAchievements())
      {
         for(Achievement achievement : config.getAchievements())
         {
            if (achievement.getId().equals(myAchievement))
            {
               break;
            }
            fail("Achievement earned was not in the achievement config");
         }
      }
   }
   @Test
   public void testSaveAchievementInvalidAchievementId()
   {
      Mogade mogade = MogadeImpl.create(gameKey, secret);
      SaveAchievementResponse response = mogade.saveAchievement("invalid", "brian", "1");

      assertFalse(response.isOk());
      assertFalse(response.isUnavailable());
      assertTrue(response.isError());

      assertEquals(0, response.getPoints());
   }
   @Test
   public void testSaveAchievementSuccess()
   {
      Mogade mogade = MogadeImpl.create(gameKey, secret);
      SaveAchievementResponse response = mogade.saveAchievement(achievementId, "brian", "1");

      assertTrue(response.isOk());
      assertFalse(response.isUnavailable());
      assertFalse(response.isError());

      assertEquals(achievementPoints, response.getPoints());
   }
}