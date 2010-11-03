package com.mogade.java.tests.unit;

import com.mogade.java.MogadeException;
import com.mogade.java.data.Leaderboard;
import org.junit.Test;
import static junit.framework.Assert.*;

public class TestLeaderboard
{
   @Test(expected=MogadeException.class)
   public void testLeaderboardInvalidId()
   {
      Leaderboard.create(null, 1, Leaderboard.Scope.DAILY);
   }
   @Test(expected=MogadeException.class)
   public void testLeaderboardInvalidPage()
   {
      Leaderboard.create("1cc425bf5346ed081f0000ef", 0, Leaderboard.Scope.DAILY);
   }
   @Test(expected=MogadeException.class)
   public void testLeaderboardInvalidScope()
   {
      Leaderboard.create("1cc425bf5346ed081f0000ef", 1, null);
   }
   @Test
   public void testLeaderboardCreateSuccess()
   {
      assertNotNull(Leaderboard.create("1cc425bf5346ed081f0000ef", 1, Leaderboard.Scope.WEEKLY));
   }
}