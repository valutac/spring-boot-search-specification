package com.valutac.spring.boot.jpa.search.specification;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;

import static com.valutac.spring.boot.jpa.search.specification.SearchCriteria.Operator.*;

public class SearchSpecification<T> implements Specification<T> {

    private SearchCriteria searchCriteria;

    public SearchSpecification(SearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        final String key = searchCriteria.getKey();
        final String operation = searchCriteria.getOperation();
        final Object value = searchCriteria.getValue();

        switch (operation) {
            case EQUALS:
                return cb.equal(root.get(key), value);
            case NOT_EQUALS:
                return cb.notEqual(root.get(key), value);
            case LESS_THAN:
                switch (searchCriteria.getDataType()) {
                    case "Integer":
                        return cb.lessThan(root.get(key), Integer.valueOf(value.toString()));
                    case "Date":
                        return cb.lessThan(root.get(key), Timestamp.valueOf(value.toString()));
                    default:
                        return cb.lessThan(root.get(key), value.toString());
                }
            case LESS_THAN_EQUALS:
                switch (searchCriteria.getDataType()) {
                    case "Integer":
                        return cb.lessThanOrEqualTo(root.get(key), Integer.valueOf(value.toString()));
                    case "Date":
                        return cb.lessThanOrEqualTo(root.get(key), Timestamp.valueOf(value.toString()));
                    default:
                        return cb.lessThanOrEqualTo(root.get(key), value.toString());
                }
            case GREATER_THAN:
                switch (searchCriteria.getDataType()) {
                    case "Integer":
                        return cb.greaterThan(root.get(key), Integer.valueOf(value.toString()));
                    case "Date":
                        return cb.greaterThan(root.get(key), Timestamp.valueOf(value.toString()));
                    default:
                        return cb.greaterThan(root.get(key), value.toString());
                }
            case GREATER_THAN_EQUALS:
                switch (searchCriteria.getDataType()) {
                    case "Integer":
                        return cb.greaterThanOrEqualTo(root.get(key), Integer.valueOf(value.toString()));
                    case "Date":
                        return cb.greaterThanOrEqualTo(root.get(key), Timestamp.valueOf(value.toString()));
                    default:
                        return cb.greaterThanOrEqualTo(root.get(key), value.toString());
                }
            case IN:
                switch (searchCriteria.getDataType()) {
                    case "String":
                        return getStringInclauseExpression(root, cb);
                    case "Integer":
                        return getIntegerInclauseExpression(root, cb);
                    case "Long":
                        return getLongInclauseExpression(root, cb);
                    default:
                        return null;
                }
            case LIKE:
                return cb.like(root.get(key), "%" + value + "%");
            case NULL:
                return cb.isNull(root.get(key));
            case NOT_NULL:
                return cb.isNotNull(root.get(key));
            default:
                return null;
        }
    }

    private Predicate getStringInclauseExpression(Root<T> root, CriteriaBuilder cb) {
        CriteriaBuilder.In<String> inClause = cb.in(root.get(searchCriteria.getKey()));
        String[] list = (String[]) searchCriteria.getValue();
        for (String x : list) {
            inClause.value(x);
        }
        return inClause;
    }

    private Predicate getIntegerInclauseExpression(Root<T> root, CriteriaBuilder cb) {
        CriteriaBuilder.In<Integer> inClause = cb.in(root.get(searchCriteria.getKey()));
        Integer[] list = (Integer[]) searchCriteria.getValue();
        for (Integer x : list) {
            inClause.value(x);
        }
        return inClause;
    }

    private Predicate getLongInclauseExpression(Root<T> root, CriteriaBuilder cb) {
        CriteriaBuilder.In<Long> inClause = cb.in(root.get(searchCriteria.getKey()));
        Long[] list = (Long[]) searchCriteria.getValue();
        for (Long x : list) {
            inClause.value(x);
        }
        return inClause;
    }

}
