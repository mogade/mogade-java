package com.mogade.java;

import com.mogade.java.data.Score;
import com.mogade.java.protocol.SaveScoreResponse;

public interface Mogade
{
   public int getApiVersion();
   public int getConnectTimeout();
   public void setConnectTimeout(int connectTimeout);
   public int getReadTimeout();
   public void setReadTimeout(int readTimeout);

   public SaveScoreResponse saveScore(String leaderboardId, Score score);
}