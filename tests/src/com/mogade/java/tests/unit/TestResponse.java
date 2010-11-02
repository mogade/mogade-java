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
      Response response = new SaveScoreResponse(null, null, null);

      assertNull(response.getInfo());
      assertTrue(response.isOk());
      assertFalse(response.isUnavailable());
      assertFalse(response.isError());
      assertNull(response.getStatus());
   }
   @Test
   public void testOkResponseWithInfo()
   {
      String msg = "great username";
      Response response = new SaveScoreResponse(msg, null, null);

      assertEquals(msg, response.getInfo());
      assertTrue(response.isOk());
      assertFalse(response.isUnavailable());
      assertFalse(response.isError());
   }
   @Test
   public void testUnavailableResponse()
   {
      String msg = "down for maintenance";
      Response response = new SaveScoreResponse(null, msg, null);

      assertNull(response.getInfo());
      assertFalse(response.isOk());
      assertTrue(response.isUnavailable());
      assertFalse(response.isError());
      assertEquals(msg, response.getStatus());
   }
   @Test
   public void testErrorResponse()
   {
      String msg = "error occured";
      Response response = new SaveScoreResponse(null, null, msg);

      assertNull(response.getInfo());
      assertFalse(response.isOk());
      assertFalse(response.isUnavailable());
      assertTrue(response.isError());
      assertEquals(msg, response.getStatus());
   }
}