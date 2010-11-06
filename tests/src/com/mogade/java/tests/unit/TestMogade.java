package com.mogade.java.tests.unit;

import com.mogade.java.Mogade;
import com.mogade.java.MogadeException;
import com.mogade.java.MogadeImpl;
import static junit.framework.Assert.*;

import com.mogade.java.data.Score;
import org.junit.Test;

public class TestMogade
{
   @Test(expected= MogadeException.class)
   public void testCreateInvalidGameKey()
   {
      MogadeImpl.create(null,"SECRET");
   }
   @Test(expected=MogadeException.class)
   public void testCreateInvalidGameKey2()
   {
      MogadeImpl.create("","SECRET");
   }
   @Test(expected=MogadeException.class)
   public void testCreateInvalidSecret()
   {
      MogadeImpl.create("GAMEKEY",null);
   }
   @Test(expected=MogadeException.class)
   public void testCreateInvalidSecret2()
   {
      MogadeImpl.create("GAMEKEY","");
   }
   @Test
   public void testCreateSuccess()
   {
      Mogade mogade = MogadeImpl.create("GAMEKEY","SECRET");
      assertNotNull(mogade);
   }

   @Test
   public void testGetApiVersion()
   {
      Mogade mogade = MogadeImpl.create("GAMEKEY","SECRET");
      assertTrue(mogade.getApiVersion() > 0);
   }

   @Test(expected=MogadeException.class)
   public void testSaveScoreInvalidLeaderboardId()
   {
      Mogade mogade = MogadeImpl.create("GAMEKEY","SECRET");
      mogade.saveScore("", Score.create("brian", 2000));
   }
   @Test(expected=MogadeException.class)
   public void testSaveScoreInvalidScore()
   {
      Mogade mogade = MogadeImpl.create("GAMEKEY","SECRET");
      mogade.saveScore("LEADER", null);
   }

   @Test(expected=MogadeException.class)
   public void testGetLeaderboardInvalidLeaderboard()
   {
      Mogade mogade = MogadeImpl.create("GAMEKEY","SECRET");
      mogade.getLeaderboard(null);
   }

   @Test(expected=MogadeException.class)
   public void testGetUserGameDataInvalidUsername()
   {
      Mogade mogade = MogadeImpl.create("GAMEKEY","SECRET");
      mogade.getUserGameData(null, "1");
   }
   @Test(expected=MogadeException.class)
   public void testGetUserGameDataInvalidUnique()
   {
      Mogade mogade = MogadeImpl.create("GAMEKEY","SECRET");
      mogade.getUserGameData("brian", "");
   }
}