package com.mogade.java.tests.unit;

import com.mogade.java.MogadeException;
import com.mogade.java.data.Score;
import org.junit.Test;
import static junit.framework.Assert.*;

public class TestScore
{
   @Test(expected= MogadeException.class)
   public void testScoreInvalidUsername()
   {
      Score.create(null, "unique", 2000);
   }
   @Test(expected= MogadeException.class)
   public void testScoreInvalidUnique()
   {
      Score.create("brian", null, 2000);
   }
   @Test(expected=MogadeException.class)
   public void testScoreInvalidUsernameLength()
   {
      StringBuilder sb = new StringBuilder();
      for(int i = 0 ; i <= Score.USERNAME_MAXLEN ; i++)
      {
         sb.append("a");
      }

      Score.create(sb.toString(), "unique", 1);
   }
   @Test(expected=MogadeException.class)
   public void testScoreInvalidData()
   {
      StringBuilder sb = new StringBuilder();
      for(int i = 0 ; i <= Score.DATA_MAXLEN ; i++)
      {
         sb.append("a");
      }

      Score.create("brian", "unique", 1, sb.toString());
   }
   @Test
   public void testScoreCreateSuccess()
   {
      assertNotNull(Score.create("brian", "unique", 1));
      assertNotNull(Score.create("brian", "unique", 1, "data"));
   }
}