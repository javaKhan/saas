/*
 * Copyright 2018 onwards - Sunit Katkar (sunitkatkar@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mmtap.cma.demo.saas.util;

import com.mmtap.cma.demo.saas.entiy.Synctargeturi;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public final class DataSourceUtil {

    private static final Logger LOG = LoggerFactory.getLogger(DataSourceUtil.class);

    /**
     * Utility method to create and configure a data source
     *
     * @param dbInfo
     * @return
     */
    public static DataSource createAndConfigureDataSource(Map dbInfo) {
        HikariDataSource ds = new HikariDataSource();
//        ds.setUsername(dbInfo.getUsername());
        ds.setUsername(dbInfo.get("User").toString());
//        ds.setPassword(dbInfo.getPassword());
        ds.setPassword(dbInfo.get("Password").toString());
//        ds.setJdbcUrl(dbInfo.getUrl());
        ds.setJdbcUrl(dbInfo.get("dbUrl").toString());
//        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");

        // HikariCP settings - could come from the master_tenant table but
        // hardcoded here for brevity
        // Maximum waiting time for a connection from the pool
        ds.setConnectionTimeout(20000);

        // Minimum number of idle connections in the pool
        ds.setMinimumIdle(10);

        // Maximum number of actual connection in the pool
        ds.setMaximumPoolSize(20);

        // Maximum time that a connection is allowed to sit idle in the pool
        ds.setIdleTimeout(300000);
        ds.setConnectionTimeout(20000);

        // Setting up a pool name for each tenant datasource
        String tenantId = dbInfo.get("insCode").toString();
        String tenantConnectionPoolName = tenantId + "-connection-pool";
        ds.setPoolName(tenantConnectionPoolName);
        LOG.info("Configured datasource:" + tenantId + ". Connection poolname:" + tenantConnectionPoolName);
        return ds;
    }


    /**
     * 数据库连接信息:结构化
     *
     * @return
     */
    public static Map analysisDataBaseInfo(Synctargeturi conf) {
        Map map = new HashMap();
        map.put("sysnid", conf.getSyncID());
        map.put("mapId", conf.getMappingID());
        map.put("mapName", conf.getMappingName());
        map.put("insName", conf.getSyncInstanceName());
        map.put("insCode", conf.getSyncInstanceCode());
        map.put("synRule", conf.getSyncRule());
        String connUrlInfo = conf.getTargetUri();
        String[] info = connUrlInfo.split("[;]");
        for (String item : info) {
            String[] temp = item.split("[=]");
            if (temp.length == 2) {
                map.put(temp[0], temp[1]);
            }
        }
        String dbUrl = createDbUrl(map);
        map.put("dbUrl", dbUrl);
        return map;
    }

    /**
     * 根据DB 记录拼接jdbc连接串
     * jdbc:mysql://123.57.64.57:3307/wb_db_dev?useUnicode=true&characterEncoding=utf-8&useSSL=false
     *
     * @param map
     * @return
     */
    private static String createDbUrl(Map map) {
        StringBuilder sb = new StringBuilder();
        sb.append("jdbc:mysql://").append(map.get("Server"))
                .append(":" + map.get("Port"))
                .append("/" + map.get("DataBase") + "?")
                .append("characterEncoding" + map.get("CharSet") + "&")
                .append("useUnicode=true&useSSL=false");
        return sb.toString();
    }
}
