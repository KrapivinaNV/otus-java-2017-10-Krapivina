package otus.myorm;

import otus.data.DataSet;
import otus.myorm.annotations.MyManyToOne;
import otus.myorm.annotations.MyOneToMany;
import otus.myorm.annotations.MyOneToOne;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.*;
import java.util.*;

public class UsersDAO {

    private Connection connection;

    UsersDAO(Connection connection) {
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
            } else if (MySimpleReflectionHelper.ifFieldAnnotated(myClazz, field.getName(), MyOneToOne.class)
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

    <T extends DataSet> void save(T object) throws SQLException {
        StringBuilder insertString = new StringBuilder();
        insertString.append("insert into ")
                .append(object.getClass().getSimpleName())
                .append(" values(")
                .append("NULL");
        Object collection = null;

        for (Field field : MySimpleReflectionHelper.getFieldsByObject(object)) {
            String fieldName = field.getName();
            Object fieldValue = MySimpleReflectionHelper.getFieldValue(object, fieldName);
            Class<?> clazz = field.getType();
            String valueToString = "";
            if (clazz.equals(String.class)) {
                valueToString = "\'" + fieldValue.toString() + "\'";
            } else if (clazz.equals(Number.class)) {
                valueToString = fieldValue.toString();
            } else if (Collection.class.isAssignableFrom(clazz)
                    && MySimpleReflectionHelper.ifFieldAnnotated(object.getClass(), field.getName(), MyOneToMany.class)) {
                collection = MySimpleReflectionHelper.getFieldValue(object, fieldName);
            } else if (MySimpleReflectionHelper.ifFieldAnnotated(object.getClass(), field.getName(), MyOneToOne.class)) {
                Object internalObject = MySimpleReflectionHelper.getFieldValue(object, fieldName);
                save((T) internalObject);
                long id = ((T) internalObject).getId();
                valueToString = String.valueOf(id);
            } else if (MySimpleReflectionHelper.ifFieldAnnotated(object.getClass(), field.getName(), MyManyToOne.class)) {
                valueToString = "NULL";
            }

            insertString.append(", ").append(valueToString);
        }
        insertString.append(")");

        ResultHandlerGetId resultHandlerGetId = new ResultHandlerGetId();
        execQuery(insertString.toString(), resultHandlerGetId);
        object.setId(resultHandlerGetId.id);

        if (collection != null) {
            for (Object element : ((Collection) collection).toArray()) {
                save((T) element);
                setOneToManyConnection((T) element, object);
            }
        }

    }

    private <T extends DataSet> void setOneToManyConnection(T child, T parent) throws SQLException {
        StringBuilder updateString = new StringBuilder();
        updateString.append("update ")
                .append(child.getClass().getSimpleName())
                .append(" set ")
                .append(parent.getClass().getSimpleName().replace("DataSet", "_id"))
                .append(" = ")
                .append(parent.getId())
                .append(" where ID = ")
                .append(child.getId());
        execQuery(updateString.toString(), null);

    }


    <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException {
        ResultHandlerGetUser resultHandlerGetUser = new ResultHandlerGetUser();
        execQuery("select * from " + clazz.getSimpleName() + " where id=" + id, resultHandlerGetUser);
        Map<String, String> result = resultHandlerGetUser.getResultMap();

        T instantiate = MySimpleReflectionHelper.instantiate(clazz, id);
        fillNewObject(instantiate, result);

        //one to many
        for (Field field : MySimpleReflectionHelper.getFieldsByObject(instantiate)) {
            Class<T> type = (Class<T>) field.getType();
            String fieldName = field.getName();
            if (Collection.class.isAssignableFrom(type)
                    && MySimpleReflectionHelper.ifFieldAnnotated(instantiate.getClass(), fieldName, MyOneToMany.class)) {
                Object collection = MySimpleReflectionHelper.getFieldValue(instantiate, fieldName);
                if (collection == null) {
                    Class<T> elementType = (Class<T>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
                    Class<T> collectionType = (Class<T>) ((ParameterizedType) field.getGenericType()).getRawType();

                    StringBuilder oneToManyString = new StringBuilder();
                    oneToManyString.append("select * from ")
                            .append(elementType.getSimpleName())
                            .append(" where ")
                            .append(clazz.getSimpleName().replace("DataSet", "_id = "))
                            .append(id);
                    ResultHandlerGetPhones resultHandlerGetPhones = new ResultHandlerGetPhones();
                    execQuery(oneToManyString.toString(), resultHandlerGetPhones);
                    List<Map<String, String>> resultList = resultHandlerGetPhones.resultList;

                    if (resultList != null) {
                        collection = new HashSet<>();
                        for (Map<String, String> myResult : resultList) {
                            T element = MySimpleReflectionHelper.instantiate((Class<T>) elementType, Long.valueOf(myResult.get("ID")), instantiate);
                            T fillElement = fillNewObject(element, myResult);
                            ((Collection) collection).add(fillElement);
                        }
                        MySimpleReflectionHelper.setFieldValue(instantiate, fieldName, collection);
                    }
                }
            }
        }
        return instantiate;
    }

    private <T extends DataSet> T fillNewObject(T object, Map<String, String> result) {
        Field[] fields = MySimpleReflectionHelper.getFieldsByObject(object);
        if (fields.length != 0) {
            for (Field field : fields) {

                String fieldName = field.getName();
                Class<T> type = (Class<T>) field.getType();
                if (!MySimpleReflectionHelper.ifFieldHasValue(object, fieldName)) {
                    result.forEach((String key, String value) -> {
                        if (key.equals(fieldName.toUpperCase())) {
                            if (type.equals(String.class)) {
                                MySimpleReflectionHelper.setFieldValue(object, fieldName, value);
                            } else if (type.equals(Number.class)) {
                                MySimpleReflectionHelper.setFieldValue(object, fieldName, Integer.parseInt(value));
                            }
                        } else if (key.equals((fieldName + "_id").toUpperCase())) {
                            T internalLoad = null;
                            try {
                                internalLoad = load(Long.valueOf(value), type);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            MySimpleReflectionHelper.setFieldValue(object, fieldName, internalLoad);
                        }
                    });
                }
            }
        }
        return object;
    }

    private void execQuery(String query, ResultHandler handler) throws SQLException {
        System.out.println(query);
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.execute();
            if (handler != null) {
                if (handler.getClass().equals(ResultHandlerGetId.class)) {
                    handler.handle(preparedStatement.getGeneratedKeys());
                } else if (handler.getClass().equals(ResultHandlerGetUser.class)
                        || handler.getClass().equals(ResultHandlerGetPhones.class)) {
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

    private class ResultHandlerGetPhones implements ResultHandler {
        private List<Map<String, String>> resultList = new ArrayList<>();

        List<Map<String, String>> getResultList() {
            return resultList;
        }

        public void handle(ResultSet result) throws SQLException {
            if (result.isBeforeFirst()) {
                while (!result.isLast()) {
                    result.next();
                    ResultSetMetaData metaData = result.getMetaData();
                    int columnCount = metaData.getColumnCount();

                    Map<String, String> resultPhones = new HashMap<>();
                    for (int index = 1; index < columnCount + 1; index++) {
                        resultPhones.put(metaData.getColumnName(index), result.getString(index));
                    }
                    resultList.add(resultPhones);


                }
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