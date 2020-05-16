package com.htec.vojinpesalj.dakarrally.repository.specification;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchCriteria {
    private List<String> keys;
    private Object value;
    private String sortBy;
}
