package otus.myorm;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import otus.common.ResultHandler;
import otus.data.DataSet;

public class UsersDAO {

    private Connection connection;

    public UsersDAO(Connection connection) {
        this.connection = connection;
    }

    public <T extends DataSet> void save(T user) throws SQLException {
        StringBuilder createIfNotExistsString = new StringBuilder();
        StringBuilder insertEndString = new StringBuilder();
        StringBuilder insertString = new StringBuilder();

        String className = user.getClass().getSimpleName();
        Field[] fields = MySimpleReflectionHelper.getFields(user);

        createIfNotExistsString.append("create table if not exists ")
                .append(className)
                .append("(id bigint(20) default id_seq.nextval not null");

        for (Field field : fields) {
            String fieldName = field.getName();
            Object fieldValue = MySimpleReflectionHelper.getFieldValue(user, fieldName);
            Class<?> clazz = field.getType();
            String typeToString = "";
            String valueToString = "";
            if (clazz.equals(String.class)) {
                typeToString = "varchar(255)";
                valueToString = "\'" + fieldValue.toString() + "\'";
            } else if (clazz.equals(Number.class)) {
                typeToString = "int(3)";
                valueToString = fieldValue.toString();
            }
            createIfNotExistsString.append(", ").append(fieldName).append(" ").append(typeToString);
            insertEndString.append(", ").append(valueToString);
        }
        createIfNotExistsString.append(")");
        insertEndString.append(")");

        execQuery("CREATE SEQUENCE IF NOT EXISTS id_seq START WITH 0 INCREMENT BY 1 nomaxvalue", null);

        execQuery(createIfNotExistsString.toString(), null);

        ResultHandlerGetId resultHandlerGetId = new ResultHandlerGetId();
        execQuery("call NEXT VALUE FOR id_seq", resultHandlerGetId);

        user.setId(resultHandlerGetId.id);
        insertString.append("insert into ")
                .append(className)
                .append(" values(")
                .append(resultHandlerGetId.id)
                .append(insertEndString.toString());

        execQuery(insertString.toString(), null);
    }

    public <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException {
        ResultHandlerGetUser resultHandlerGetUser = new ResultHandlerGetUser();
        execQuery("select * from " + clazz.getSimpleName() + " where id=" + id, resultHandlerGetUser);
        Map<String, String> result = resultHandlerGetUser.getResultMap();

        T instantiate = MySimpleReflectionHelper.instantiate(clazz, id);
        Field[] fields = MySimpleReflectionHelper.getFields(instantiate);
        if (fields.length != 0) {
            for (Field field : fields) {
                String fieldName = field.getName();
                Class<?> type = field.getType();

                result.forEach((String key, String value) -> {
                    if (key.equals(fieldName.toUpperCase())) {
                        if (type.equals(String.class)) {
                            MySimpleReflectionHelper.setFieldValue(instantiate, fieldName, value);
                        } else if (type.equals(Number.class)) {
                            MySimpleReflectionHelper.setFieldValue(instantiate, fieldName, Integer.parseInt(value));
                        }
                    }
                });
            }
        }
        return instantiate;
    }

    private void execQuery(String query, ResultHandler handler) throws SQLException {
        System.out.println(query);
        try (Statement stat = connection.createStatement()) {
            stat.execute(query);
            ResultSet result = stat.getResultSet();
            if (handler != null) {
                handler.handle(result);
            }
            stat.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    private class ResultHandlerGetUser implements ResultHandler {

        private Map<String, String> resultMap = new HashMap<>();

        Map<String, String> getResultMap() {
            return resultMap;
        }

        public void handle(ResultSet result) throws SQLException {
            result.first();
            ResultSetMetaData metaData = result.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int index = 1; index < columnCount + 1; index++) {
                resultMap.put(metaData.getColumnName(index), result.getString(index));
            }
        }
    }

    private static class ResultHandlerGetId implements ResultHandler {

        private long id;

        public void handle(ResultSet result) throws SQLException {
            result.next();
            id = result.getLong(result.getMetaData().getColumnLabel(1));
        }
    }

}
