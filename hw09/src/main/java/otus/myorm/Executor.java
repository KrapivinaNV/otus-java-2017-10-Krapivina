package otus.myorm;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class Executor {


    static private final Connection connection = ConnectionHelper.getConnection();

    static <T extends DataSet> void save(T user) throws SQLException {
        StringBuilder createIfNotExistsString = new StringBuilder();
        StringBuilder insertString = new StringBuilder();

        String className = user.getClass().getSimpleName();
        Field[] fields = MySimpleReflectionHelper.getFields(user);

        if (fields.length != 0) {
            createIfNotExistsString.append("create table if not exists " + className + "(id bigint(20) primary key auto_increment");

            insertString.append("insert into " + className + " values(" + user.getId());

            for (Field field : fields) {
                String fieldName = field.getName();
                Object fieldValue = MySimpleReflectionHelper.getFieldValue(user, fieldName);
                Class<?> clazz = field.getType();
                String typeToString = "";
                String valueToString = "";
                if (clazz.equals(Integer.class) || clazz.equals(int.class)) {
                    typeToString = "int";
                    valueToString = fieldValue.toString();
                } else if (clazz.equals(String.class)) {
                    typeToString = "varchar(255)";
                    valueToString = "\'" + fieldValue.toString() + "\'";
                } else if (clazz.equals(Number.class)) {
                    typeToString = "int(3)";
                    valueToString = fieldValue.toString();
                }
                createIfNotExistsString.append(", " + fieldName + " " + typeToString);
                insertString.append(", " + valueToString);
            }
            createIfNotExistsString.append(")");
            insertString.append(")");

            execQuery(createIfNotExistsString.toString(), null);
            execQuery(insertString.toString(), null);

        } else System.out.println("not exists fields");

    }

    static <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException {
        StringBuilder selectString = new StringBuilder("select * from " + clazz.getSimpleName() + " where id=" + id);
        ResultHandlerGetUser resultHandlerGetUser = new ResultHandlerGetUser();
        execQuery(selectString.toString(), resultHandlerGetUser);
        Map result = resultHandlerGetUser.resultMap;

        T instantiate = MySimpleReflectionHelper.instantiate(clazz, id);
        Field[] fields = MySimpleReflectionHelper.getFields(instantiate);
        if (fields.length != 0) {

            for (Field field : fields) {
                String fieldName = field.getName();
                Class<?> type = field.getType();

                result.forEach((key, value) -> {
                    if (key.toString().equals(fieldName.toUpperCase())) {
                        if (type.equals(int.class)) {
                            MySimpleReflectionHelper.setFieldValue(instantiate, fieldName, Integer.parseInt(value.toString()));
                        } else if (type.equals(String.class)) {
                            MySimpleReflectionHelper.setFieldValue(instantiate, fieldName, (String) value);
                        } else if (type.equals(Number.class)) {
                            MySimpleReflectionHelper.setFieldValue(instantiate, fieldName, Integer.parseInt(value.toString()));
                        }
                    }
                });
            }
        }
        return instantiate;
    }

    private static void execQuery(String query, ResultHandler handler) throws SQLException {
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
        }
    }


    private static class ResultHandlerGetUser implements ResultHandler {
        private Map<String, String> resultMap = new HashMap<>();

        public void handle(ResultSet result) throws SQLException {
            System.out.println("Read: " + result.toString());
            result.first();
            ResultSetMetaData metaData = result.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int index = 1; index < columnCount + 1; index++) {
                resultMap.put(metaData.getColumnName(index), result.getString(index));
            }
        }
    }
}