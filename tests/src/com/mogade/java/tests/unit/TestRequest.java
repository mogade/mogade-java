package com.mogade.java.tests.unit;

import com.mogade.java.data.Score;
import com.mogade.java.helpers.Utility;
import com.mogade.java.protocol.SaveScoreRequest;
import org.junit.Test;

import static junit.framework.Assert.*;

public class TestRequest
{
   @Test
   public void testSigCalculation()
   {
      String calcSig = Utility.md5("key=KEY&leaderboard_id=lid&points=100&unique=unique&username=brian&v=1&mysecret");
      SaveScoreRequest request = new SaveScoreRequest("KEY", 1, "lid", Score.create("brian", "unique", 100));

      assertEquals(calcSig, request.calculateSignature("mysecret"));

      calcSig = Utility.md5("data=data&key=KEY&leaderboard_id=lid&points=100&unique=unique&username=brian&v=1&mysecret2");
      request = new SaveScoreRequest("KEY", 1, "lid", Score.create("brian", "unique", 100, "data"));

      assertEquals(calcSig, request.calculateSignature("mysecret2"));
   }
}