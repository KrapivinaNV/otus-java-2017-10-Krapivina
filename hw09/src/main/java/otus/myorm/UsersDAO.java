package otus.myorm;

import otus.data.DataSet;
import otus.myorm.annotations.MyManyToOne;
import otus.myorm.annotations.MyOneToOne;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UsersDAO {

    private Connection connection;

    public UsersDAO(Connection connection) {
        this.connection = connection;
    }

    <T extends DataSet> void prepareTables(Class<T> myClazz) throws SQLException {
        StringBuilder createIfNotExistsString = new StringBuilder();
        StringBuilder alterTableString = new StringBuilder();

        createIfNotExistsString.append("create table if not exists ")
                .append(myClazz.getSimpleName())
                .append("(id bigint(20) NOT NULL auto_increment");

        for (Field field : MySimpleReflectionHelper.getFieldsByClass(myClazz)) {
            String fieldName = "";
            Class<?> clazz = field.getType();
            String typeToString = "";
            if (clazz.equals(String.class)) {
                typeToString = "varchar(255)";
                fieldName = field.getName();
            } else if (clazz.equals(Number.class)) {
                typeToString = "int(3)";
                fieldName = field.getName();
            }else if (!Collection.class.isAssignableFrom(clazz)) {
             if(MySimpleReflectionHelper.ifFieldAnnotated(myClazz,field.getName(), MyOneToOne.class)
                     || MySimpleReflectionHelper.ifFieldAnnotated(myClazz,field.getName(), MyManyToOne.class)) {
                    typeToString = "bigint(20)";
                    fieldName = field.getName() + "_id";
                    alterTableString.append("alter table ")
                            .append(myClazz.getSimpleName())
                            .append(" add foreign key(")
                            .append(fieldName)
                            .append(") references ")
                            .append(clazz.getSimpleName())
                            .append("(id)");
                }
            }
            createIfNotExistsString.append(", ")
                    .append(fieldName)
                    .append(" ")
                    .append(typeToString);
        }
        execQuery(createIfNotExistsString.append(")").toString(), null);

        if (alterTableString.length() != 0) {
            execQuery(alterTableString.toString(), null);
        }
    }

    public <T extends DataSet> void save(T object) throws SQLException {
        StringBuilder insertString = new StringBuilder();
        insertString.append("insert into ")
                .append(object.getClass().getSimpleName())
                .append(" values(")
                .append("NULL");

        for (Field field : MySimpleReflectionHelper.getFieldsByObject(object)) {
            String fieldName = field.getName();
            Object fieldValue = MySimpleReflectionHelper.getFieldValue(object, fieldName);
            Class<?> clazz = field.getType();
            String valueToString = "";
            if (clazz.equals(String.class)) {
                valueToString = "\'" + fieldValue.toString() + "\'";
            } else if (clazz.equals(Number.class)) {
                valueToString = fieldValue.toString();
            } else {
                Object internalObject = MySimpleReflectionHelper.getFieldValue(object, fieldName);
                save((T) internalObject);
                long id = ((T) internalObject).getId();
                valueToString = String.valueOf(id);
            }
            insertString.append(", ").append(valueToString);
        }
        insertString.append(")");

        ResultHandlerGetId resultHandlerGetId = new ResultHandlerGetId();
        execQuery(insertString.toString(), resultHandlerGetId);
        object.setId(resultHandlerGetId.id);
    }

    public <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException {
        ResultHandlerGetUser resultHandlerGetUser = new ResultHandlerGetUser();
        execQuery("select * from " + clazz.getSimpleName() + " where id=" + id, resultHandlerGetUser);
        Map<String, String> result = resultHandlerGetUser.getResultMap();

        T instantiate = MySimpleReflectionHelper.instantiate(clazz, id);
        Field[] fields = MySimpleReflectionHelper.getFieldsByObject(instantiate);
        if (fields.length != 0) {
            for (Field field : fields) {
                String fieldName = field.getName();
                Class<T> type = (Class<T>) field.getType();

                result.forEach((String key, String value) -> {
                    if (key.equals(fieldName.toUpperCase())) {
                        if (type.equals(String.class)) {
                            MySimpleReflectionHelper.setFieldValue(instantiate, fieldName, value);
                        } else if (type.equals(Number.class)) {
                            MySimpleReflectionHelper.setFieldValue(instantiate, fieldName, Integer.parseInt(value));
                        }
                    } else if (key.equals((fieldName + "_id").toUpperCase())) {
                        T internalLoad = null;
                        try {
                            internalLoad = load(Long.valueOf(value), type);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        MySimpleReflectionHelper.setFieldValue(instantiate, fieldName, internalLoad);
                    }
                });
            }
        }
        return instantiate;
    }

    private void execQuery(String query, ResultHandler handler) throws SQLException {
        System.out.println(query);
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.execute();
            if (handler != null) {
                if (handler.getClass().equals(ResultHandlerGetId.class)) {
                    handler.handle(preparedStatement.getGeneratedKeys());
                } else if (handler.getClass().equals(ResultHandlerGetUser.class)) {
                    handler.handle(preparedStatement.getResultSet());
                }
            }
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