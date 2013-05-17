/*
 * Copyright 2013 selendroid committers.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package io.selendroid.server.support;

import java.util.Properties;

import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selendroid.server.Response;

/**
 * Implementations are able to listen to the {@link SelendroidDeviceServerStub} lifecycle.
 * 
 * @author ddary
 * 
 */
public abstract class TestSessionListener {
  public static final String BROWSER_NAME = "browserName";
  public static final String PLATFORM = "platform";
  public static final String TAKES_SCREENSHOT = "takesScreenshot";
  public static final String VERSION = "version";
  public static final String ROTATABLE = "rotatable";

  public final String sessionId;
  public final JSONObject status;
  public final String uriMapping;

  public TestSessionListener(String sessionId, String uriMapping) throws JSONException {
    this.sessionId = sessionId;
    status = new JSONObject();
    JSONObject build = new JSONObject();
    build.put("browserName", "selendroid");
    build.put("version", "dev");

    JSONObject os = new JSONObject();
    os.put("arch", "x86");
    os.put("locale", "en_US");
    os.put("version", 16);
    os.put("name", "Android");
    status.put("build", build);
    status.put("os", os);

    this.uriMapping = uriMapping;
  }

  public Response deleteSession(Properties params) {
    return defaultResponseWithMessage("");
  }

  public Response status(Properties params) throws JSONException {
    return defaultResponseWithMessage(status);
  }

  public Response createSession(Properties params) throws JSONException {

    JSONObject sessionCap = new JSONObject();
    sessionCap.put(TAKES_SCREENSHOT, true);
    sessionCap.put(BROWSER_NAME, "selendroid");
    sessionCap.put(ROTATABLE, false);
    sessionCap.put(PLATFORM, "android");
    sessionCap.put(VERSION, "16");
    return defaultResponseWithMessage(sessionCap);
  }

  public abstract Response executeSelendroidRequest(Properties params);

  protected Response defaultResponseWithMessage(Object message) {
    return new Response(sessionId, message);
  }

  protected Response defaultResponse() {
    return defaultResponseWithMessage("");
  }
}
