/* Copyright 2004-2006 Brian McCallister
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.skife.jdbi.v2;

import org.skife.jdbi.v2.exceptions.UnableToCloseResourceException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public class BasicHandle implements Handle
{
    private final Connection connection;

    public BasicHandle(Connection connection)
    {
        this.connection = connection;
    }

    public Query<Map<String, Object>> createQuery(String sql)
    {
        return new Query<Map<String, Object>>(new DefaultMapper(),
                                              connection,
                                              sql);
    }



    public void close()
    {
        try
        {
            connection.close();
        }
        catch (SQLException e)
        {
            throw new UnableToCloseResourceException("Unable to close Connection", e);
        }
    }

    public SQLStatement createStatement(String sql)
    {
        return new SQLStatement(connection, sql);
    }

    public int insert(String sql)
    {
        return update(sql);
    }

    public int update(String sql)
    {
        return createStatement(sql).execute();
    }
}