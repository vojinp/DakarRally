package com.htec.vojinpesalj.dakarrally.repository.specification;

import com.htec.vojinpesalj.dakarrally.repository.domain.Vehicle;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class VehicleSpecification implements Specification<Vehicle> {

    private final SearchCriteria searchCriteria;

    public VehicleSpecification(SearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate(
            Root<Vehicle> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get(searchCriteria.getSortBy())));
        return criteriaBuilder.equal(getPath(root), searchCriteria.getValue());
    }

    private Path getPath(Root<Vehicle> root) {
        Path path = root;
        for (String key : searchCriteria.getKeys()) {
            path = path.get(key);
        }

        return path;
    }
}
