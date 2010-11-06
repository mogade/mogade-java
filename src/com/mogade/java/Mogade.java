package com.mogade.java;

import com.mogade.java.data.Leaderboard;
import com.mogade.java.data.Score;
import com.mogade.java.protocol.*;

public interface Mogade
{
   public int getApiVersion();

   public GetConfigVersionResponse getConfigVersion();
   public GetConfigResponse getConfig();
   public GetUserGameDataResponse getUserGameData(String username, String unique);
   public SaveScoreResponse saveScore(String leaderboardId, Score score);
   public GetLeaderboardResponse getLeaderboard(Leaderboard leaderboard);
   public SaveAchievementResponse saveAchievement(String achievementId, String username, String unique);
}