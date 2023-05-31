package com.projectX.requests;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.projectX.models.FieldsData;
import com.projectX.utils.JsonHelper;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ApiRequests {
    private static final FieldsData fieldsData = JsonHelper.getJsonData(JsonHelper.getValueFromJson("pathToFieldsData"), FieldsData.class);

    public static String getToken(String url, int variant) {
        try {
            return Unirest.post(url)
                    .field(fieldsData.getFieldVariant(), variant)
                    .asString().getBody();
        } catch (UnirestException e) {
            throw new IllegalStateException(e);
        }
    }
}