package com.bootone.boot_one.filter;


import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Properties;

@Component
@Intercepts(
        {
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
        }
)
public class QueryByAppInterceptor implements Interceptor {

    private static final Logger log = LoggerFactory.getLogger(QueryByAppInterceptor.class);
    static int MAPPED_STATEMENT_INDEX = 0;// 这是对应上面的args的序号


    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object result = null;


        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        //id为执行的mapper方法的全路径名，如com.uv.dao.UserMapper.insertUser
        String mId = ms.getId();
        //sql语句类型 select、delete、insert、update
        String sqlCommandType = ms.getSqlCommandType().toString();
        Object parameter = args[1];
        BoundSql boundSql = ms.getBoundSql(parameter);
        ;

        //获取到原始sql语句
        String sql = boundSql.getSql();

        //注解逻辑判断  添加注解了才拦截
        Class<?> classType = Class.forName(mId.substring(0, mId.lastIndexOf(".")));
        String mName = mId.substring(mId.lastIndexOf(".") + 1, mId.length());


        //组装新的sql
        String mSql = this.process(sql, "", "");
        // 重新new一个查询语句对像
        BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), mSql, boundSql.getParameterMappings(), boundSql.getParameterObject());

        // 把新的查询放到statement里
        MappedStatement newMs = copyFromMappedStatement(ms, new BoundSqlSqlSource(newBoundSql));
        for (ParameterMapping mapping : boundSql.getParameterMappings()) {
            String prop = mapping.getProperty();
            if (boundSql.hasAdditionalParameter(prop)) {
                newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
            }
        }
        final Object[] queryArgs = invocation.getArgs();
        queryArgs[MAPPED_STATEMENT_INDEX] = newMs;

//
//       Field field = boundSql.getClass().getDeclaredField("sql");
//       field.setAccessible(true);
//       field.set(boundSql, mSql);


        return invocation.proceed();
    }


    private String process(String sql, String appCode, String version) {
        String msql =  sql + "  and cityName='北京' ";
        //此处隐去与拦截器不相关的sql处理相关逻辑
        return msql;

    }

    public static class BoundSqlSqlSource implements SqlSource {
        private BoundSql boundSql;

        public BoundSqlSqlSource(BoundSql boundSql) {
            this.boundSql = boundSql;
        }

        public BoundSql getBoundSql(Object parameterObject) {
            return boundSql;
        }
    }

    private MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
        MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());
        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        if (ms.getKeyProperties() != null && ms.getKeyProperties().length > 0) {
            builder.keyProperty(ms.getKeyProperties()[0]);
        }
        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());
        builder.resultMaps(ms.getResultMaps());
        builder.resultSetType(ms.getResultSetType());
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());
        return builder.build();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

}
