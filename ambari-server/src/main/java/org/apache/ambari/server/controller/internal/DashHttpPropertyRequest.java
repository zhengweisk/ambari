/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.ambari.server.controller.internal;
import org.apache.ambari.server.controller.spi.SystemException;
import org.apache.ambari.server.state.Cluster;

import java.util.Collections;
import java.util.Map;

public class DashHttpPropertyRequest extends JsonHttpPropertyRequest {

  private static String DashApplicationsUrl = "http://localhost:9191/services/v1/applications";

  /**
   * Create a property request.
   *
   * @param propertyMappings the property name mapping
   */
  public DashHttpPropertyRequest(Map<String, String> propertyMappings) {
    super(propertyMappings);
  }

  @Override
  public String getUrl(Cluster cluster, String hostName) throws SystemException {
    return DashApplicationsUrl;
  }

  public String getUrl(Cluster cluster, String hostName, String yarnAppName) throws SystemException {
    return DashApplicationsUrl + "/" + yarnAppName;
  }
}