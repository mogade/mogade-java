package com.mogade.java.tests.unit;

import com.mogade.java.data.Score;
import org.junit.Test;

public class TestScore
{
   @Test(expected=IllegalArgumentException.class)
   public void testScoreInvalidUsername()
   {
      Score.create(null, 2000);
   }
   @Test(expected=IllegalArgumentException.class)
   public void testScoreInvalidData()
   {
      StringBuilder sb = new StringBuilder();
      for(int i = 0 ; i <= Score.DATA_MAXLEN ; i++)
      {
         sb.append("a");
      }

      Score.create(null, 0, sb.toString());
   }
}