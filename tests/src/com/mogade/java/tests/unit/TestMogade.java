package com.mogade.java.tests.unit;

import com.mogade.java.Mogade;
import com.mogade.java.MogadeImpl;
import static junit.framework.Assert.*;

import com.mogade.java.data.Score;
import org.junit.Test;

public class TestMogade
{
   @Test(expected=IllegalArgumentException.class)
   public void testCreateInvalidGameKey()
   {
      MogadeImpl.create(null,"SECRET");
   }
   @Test(expected=IllegalArgumentException.class)
   public void testCreateInvalidGameKey2()
   {
      MogadeImpl.create("","SECRET");
   }
   @Test(expected=IllegalArgumentException.class)
   public void testCreateInvalidSecret()
   {
      MogadeImpl.create("GAMEKEY",null);
   }
   @Test(expected=IllegalArgumentException.class)
   public void testCreateInvalidSecret2()
   {
      MogadeImpl.create("GAMEKEY","");
   }
   @Test(expected=IllegalArgumentException.class)
   public void testCreateInvalidConnectTimeout()
   {
      MogadeImpl.create("GAMEKEY","", -1, 5000);
   }
   @Test(expected=IllegalArgumentException.class)
   public void testCreateInvalidReadTimeout()
   {
      MogadeImpl.create("GAMEKEY","", 5000, -1);
   }
   @Test
   public void testCreateSuccess()
   {
      Mogade mogade = MogadeImpl.create("GAMEKEY","SECRET", 0, 0);
      assertNotNull(mogade);
   }

   @Test
   public void testGetApiVersion()
   {
      Mogade mogade = MogadeImpl.create("GAMEKEY","SECRET");
      assertTrue(mogade.getApiVersion() > 0);
   }
   @Test
   public void testGetSetConnectTimeout()
   {
      int timeout = 2;
      Mogade mogade = MogadeImpl.create("GAMEKEY","SECRET", timeout, 5000);

      assertEquals(timeout, mogade.getConnectTimeout());

      timeout++;

      mogade.setConnectTimeout(timeout);
      assertEquals(timeout, mogade.getConnectTimeout());
   }
   @Test
   public void testGetSetReadTimeout()
   {
      int timeout = 5;
      Mogade mogade = MogadeImpl.create("GAMEKEY","SECRET", 5000, timeout);

      assertEquals(timeout, mogade.getReadTimeout());

      timeout++;

      mogade.setReadTimeout(timeout);
      assertEquals(timeout, mogade.getReadTimeout());
   }

   @Test(expected=IllegalArgumentException.class)
   public void testSaveScoreInvalidLeaderboardId()
   {
      Mogade mogade = MogadeImpl.create("GAMEKEY","SECRET");
      mogade.saveScore("", Score.create("brian", 2000));
   }
   @Test(expected=IllegalArgumentException.class)
   public void testSaveScoreInvalidScore()
   {
      Mogade mogade = MogadeImpl.create("GAMEKEY","SECRET");
      mogade.saveScore("LEADER", null);
   }

   @Test(expected=IllegalArgumentException.class)
   public void testGetLeaderboardInvalidLeaderboard()
   {
      Mogade mogade = MogadeImpl.create("GAMEKEY","SECRET");
      mogade.getLeaderboard(null);
   }
}