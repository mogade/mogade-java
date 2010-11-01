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
}