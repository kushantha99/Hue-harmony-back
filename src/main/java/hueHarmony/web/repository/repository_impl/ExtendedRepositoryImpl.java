package hueHarmony.web.repository.repository_impl;

import hueHarmony.web.repository.ExtendedRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.jpa.repository.query.QueryUtils.toOrders;

public class ExtendedRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements ExtendedRepository<T, ID> {

    private final EntityManager entityManager;

    public ExtendedRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public Page<?> filterAndSelectFieldsBySpecsAndPage(Specification<T> specification, Pageable pageable, List<String> fields, Class<?> clazz) {
        Assert.notNull(pageable, "Pageable must be not null!");
        Assert.notEmpty(fields, "Fields must not be empty!");

        // Create query
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<?> query = builder.createQuery(clazz);

        // Define FROM clause
        Root<T> root = applySpecToCriteria(query, builder, specification);

        // Define selecting expression
        List<Selection<?>> selections = getSelections(fields, root);
        query.multiselect(selections.toArray(new Selection<?>[0]));

        //Define ORDER BY clause
        applySorting(builder, query, root, pageable);
        System.out.println(entityManager.createQuery(query).unwrap(org.hibernate.query.Query.class).getQueryString());

        List<?> content = getPageableResultList(query, pageable);
        return new PageImpl<>(content, pageable, count(specification));
    }

    private <R> Root<T> applySpecToCriteria(CriteriaQuery<R> query, CriteriaBuilder builder, Specification<T> specs) {
        Assert.notNull(query, "CriteriaQuery must not be null!");

        Root<T> root = query.from(getDomainClass());

        if (specs == null) {
            return root;
        }

        Predicate predicate = specs.toPredicate(root, query, builder);

        if (predicate != null) {
            query.where(predicate);
        }

        return root;
    }

    private List<Selection<?>> getSelections(List<String> fields, Root<T> root) {
        List<Selection<?>> selections = new ArrayList<>();

        for (String field : fields) {
            selections.add(root.get(field));
        }

        return selections;
    }

    private <R> void applySorting(CriteriaBuilder builder, CriteriaQuery<R> query, Root<T> root, Pageable pageable) {
        Sort sort = pageable.isPaged() ? pageable.getSort() : Sort.unsorted();
        if (sort.isSorted()) {
            query.orderBy(toOrders(sort, root, builder));
        }
    }

    private <R> List<R> getPageableResultList(CriteriaQuery<R> query, Pageable pageable) {

        TypedQuery<R> typedQuery = entityManager.createQuery(query);

        // Apply pagination
        if (pageable.isPaged()) {
            typedQuery.setFirstResult((int) pageable.getOffset());
            typedQuery.setMaxResults(pageable.getPageSize());
        }

        return typedQuery.getResultList();
    }

    public long count(Specification<T> specification) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
        Root<T> root = applySpecToCriteria(countQuery, builder, specification);
        countQuery.select(builder.count(root));
        return entityManager.createQuery(countQuery).getSingleResult();
    }

}
