package com.mogade.java;

import com.mogade.java.data.Score;
import com.mogade.java.protocol.SaveScoreResponse;

public interface Mogade
{
   public SaveScoreResponse saveScore(String leaderboardId, Score score);
}