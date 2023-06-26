package com.ngocluy.cucumber.common.transformers;

import com.ngocluy.cucumber.common.contexts.CachedStringContext;
import com.ngocluy.cucumber.common.contexts.RestApiContext;
import io.cucumber.cucumberexpressions.Transformer;
import io.cucumber.datatable.DataTable;
import io.cucumber.spring.ScenarioScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@ScenarioScope
public class TransformStringContext {
    private final List<Transformer<String>> transformers;
    public TransformStringContext(
            @Autowired CachedStringContext cachedStringContext,
            @Autowired RestApiContext restApiContext
    ) {
        transformers = Arrays.asList(
                new RandomAlphabeticStringTransformer(),
                new RandomUUIDTransformer(),
                new RandomNumericStringTransformer(),
                new RandomLongNumberTransformer(),
                new RandomFloatNumberTransformer(),
                new RandomAlphaNumericStringTransformer(),
                new CachedStringVariableTransformer(cachedStringContext),
                new HttpResponseJsonPathVariableTransformer(restApiContext)
        );
    }

    public DataTable transform(DataTable dataTable) throws Throwable {
        List<List<String>> newResult = new ArrayList<>();

        List<List<String>> tableValues = dataTable.asLists();
        for (List<String> list : tableValues) {
            List<String> newList = new ArrayList<>();
            newResult.add(newList);
            for (String str : list) {
                newList.add(this.transform(str));
            }
        }
        return DataTable.create(newResult, dataTable.getTableConverter());
    }

    public String transform(String input) throws Throwable {
        String result = input;
        for (Transformer<String> transformer: transformers) {
            result = transformer.transform(result);
        }
        return result;
    }

    public static void main(String[] args) throws Throwable {
        CachedStringContext cachedStringContext = new CachedStringContext();
        RestApiContext restApiContext = new RestApiContext();

        TransformStringContext context = new TransformStringContext(cachedStringContext, restApiContext);
        cachedStringContext.addCache("hello", "world");
        cachedStringContext.addCache("foo", "bar");
        restApiContext.callPostApi("https://hungnt.free.beeceptor.com/todos", "{}", null);

        restApiContext.callGetApi("https://hungnt.free.beeceptor.com/todos");
        String result = context.transform("${random_length_12_alphabetic_1} a ${random_length_12_alphabetic_1} b ${random_length_12_alphanumeric_1}a ${random_from_0_to_200000000_float_number_1} ${random_from_0_to_200000000_long_number_1} ${random_length_12_numeric_1} ${random_uuid_1}!!!${variable.hello}${variable.foo}!!!${responses[-1].[0].id}${responses[0].id}");
        System.out.println(result);
    }
}
