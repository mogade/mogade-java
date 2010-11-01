package com.mogade.java.tests.functional;

import static junit.framework.Assert.*;

import com.mogade.java.Mogade;
import com.mogade.java.MogadeImpl;
import com.mogade.java.data.Score;
import com.mogade.java.protocol.SaveScoreResponse;
import org.junit.Test;

public class TestMogade
{
   @Test
   public void testSaveScoreUnknownGameKey()
   {
      Mogade mogade = MogadeImpl.create("UNKNOWNGAMEKEY","SECRET");
      SaveScoreResponse response = mogade.saveScore("LEADER", Score.create("brian", 2000));

      assertFalse(response.isOk());
      assertFalse(response.isUnavailable());
      assertTrue(response.isError());
      assertEquals(response.getDaily(), 0);
      assertEquals(response.getWeekly(), 0);
      assertEquals(response.getOverall(), 0);
   }
}