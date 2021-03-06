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
package org.apache.ambari.view.huetoambarimigration.migration.configuration;

import org.apache.ambari.view.ViewContext;
import org.apache.ambari.view.huetoambarimigration.datasource.DataSourceAmbariDatabase;
import org.apache.ambari.view.huetoambarimigration.resources.scripts.models.InstanceModel;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.ambari.view.huetoambarimigration.datasource.queryset.ambariqueryset.pig.instancedetail.*;

/**
 * Utility class to fetch Pig Instance details
 */

public class PigInstanceDetailsUtility {

  public List<InstanceModel> getInstancedetails(ViewContext view) throws PropertyVetoException, SQLException, IOException {

    List<InstanceModel> instancelist = new ArrayList<>();
    Connection conn = null;
    Statement stmt = null;
    PreparedStatement prSt;
    conn = DataSourceAmbariDatabase.getInstance(view.getProperties().get("ambaridrivername"), view.getProperties().get("ambarijdbcurl"), view.getProperties().get("ambaridbusername"), view.getProperties().get("ambaridbpassword")).getConnection();
    conn.setAutoCommit(false);
    stmt = conn.createStatement();
    int i = 0;

    QuerySetAmbariDB ambaridatabase = null;

    if (view.getProperties().get("ambaridrivername").contains("mysql")) {
      ambaridatabase = new MysqlQuerySetAmbariDB();
    } else if (view.getProperties().get("ambaridrivername").contains("postgresql")) {
      ambaridatabase = new PostgressQuerySetAmbariDB();
    } else if (view.getProperties().get("ambaridrivername").contains("oracle")) {
      ambaridatabase = new OracleQuerySetAmbariDB();
    }

    ResultSet rs1 = null;

    prSt = ambaridatabase.getAllPigInstance(conn);

    rs1 = prSt.executeQuery();

    while (rs1.next()) {
      InstanceModel I = new InstanceModel();
      I.setInstanceName(rs1.getString(1));
      I.setId(i);
      instancelist.add(I);
      i++;
    }
    rs1.close();
    stmt.close();
    return instancelist;

  }


}
