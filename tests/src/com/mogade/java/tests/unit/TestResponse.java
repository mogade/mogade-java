package com.mogade.java.tests.unit;

import com.mogade.java.protocol.Response;
import com.mogade.java.protocol.SaveScoreResponse;
import org.junit.Test;
import static junit.framework.Assert.*;

public class TestResponse
{
   @Test
   public void testOkResponse()
   {
      Response response = new SaveScoreResponse(null, null);

      assertTrue(response.isOk());
      assertFalse(response.isUnavailable());
      assertFalse(response.isError());
      assertNull(response.getStatus());
   }
   @Test
   public void testUnavailableResponse()
   {
      String msg = "down for maintenacne";
      Response response = new SaveScoreResponse(msg, null);

      assertFalse(response.isOk());
      assertTrue(response.isUnavailable());
      assertFalse(response.isError());
      assertEquals(msg, response.getStatus());
   }
   @Test
   public void testErrorResponse()
   {
      String msg = "error occured";
      Response response = new SaveScoreResponse(null, msg);

      assertFalse(response.isOk());
      assertFalse(response.isUnavailable());
      assertTrue(response.isError());
      assertEquals(msg, response.getStatus());
   }
}