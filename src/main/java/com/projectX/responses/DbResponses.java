package com.projectX.responses;

import com.projectX.models.CompanyTests;
import com.projectX.utils.Enum;
import java.util.ArrayList;
import java.util.List;

public class DbResponses {

    public List<CompanyTests> transformData(List<ArrayList<String>> data) {
        List<CompanyTests> transformedData = new ArrayList<>();
        for (ArrayList<String> row : data) {
            CompanyTests test = CompanyTests.builder()
                    .name(row.get(Enum.ZERO.getValue()))
                    .method(row.get(Enum.ONE.getValue()))
                    .status(row.get(Enum.TWO.getValue()))
                    .startTime(row.get(Enum.THREE.getValue()))
                    .endTime(row.get(Enum.FOUR.getValue()))
                    .duration(row.get(Enum.FIVE.getValue()))
                    .build();
            transformedData.add(test);
        }
        return transformedData;
    }

}