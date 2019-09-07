package com.valutac.spring.boot.jpa.search.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unused", "WeakerAccess"})
public class SearchSpecificationBuilder<T> {

    private final List<Specification<T>> specs;
    private final boolean isOrPredicate;

    public SearchSpecificationBuilder(boolean isOrPredicate) {
        this.isOrPredicate = isOrPredicate;
        specs = new ArrayList<>();
    }

    public SearchSpecificationBuilder() {
        this.isOrPredicate = false;
        specs = new ArrayList<>();
    }

    public SearchSpecificationBuilder<T> with(SearchCriteria criteria) {
        specs.add(new SearchSpecification<>(criteria));
        return this;
    }

    public SearchSpecificationBuilder<T> with(Specification<T> spec) {
        specs.add(spec);
        return this;
    }

    public Specification<T> build() {
        if (specs.size() == 0) {
            return null;
        }

        return build(specs);
    }

    public Specification<T> build(List<Specification<T>> specs) {
        Specification<T> result = specs.get(0);
        for (int i = 1; i < specs.size(); i++) {
            result = isOrPredicate ?
                    Specifications.where(result).or(specs.get(i)) : Specifications.where(result).and(specs.get(i));
        }

        return result;
    }

}
