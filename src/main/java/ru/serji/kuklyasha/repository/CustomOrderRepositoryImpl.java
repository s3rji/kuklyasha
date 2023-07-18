package ru.serji.kuklyasha.repository;

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import ru.serji.kuklyasha.model.Order;
import ru.serji.kuklyasha.model.*;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.util.*;

@Repository
@Transactional(readOnly = true)
public class CustomOrderRepositoryImpl implements CustomOrderRepository {

    private final OrderRepository repository;

    private final EntityManager entityManager;

    @Autowired
    public CustomOrderRepositoryImpl(OrderRepository repository, EntityManager entityManager) {
        this.repository = repository;
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Order> findByIdAndUser(int id, User user) {
        return repository.findByIdAndUser(id, user);
    }

    @Override
    public List<Order> findAllFetchUser() {
        return repository.findAllFetchUser();
    }

    @Override
    public List<Order> findAllFetchUser(Pageable pageable) {
        return repository.findAllFetchUser(pageable);
    }

    @Override
    public List<Order> findAllByFilterFetchUser(int page, int limit, String sort, String direction, String field, String filter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = cb.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);
        root.fetch("user", JoinType.LEFT);
        CriteriaQuery<Order> select = criteriaQuery.select(root);

        switch (field.toLowerCase()) {
            case "id" -> select.where(cb.equal(root.get("id"), filter));
            case "user.name" ->
                    select.where(cb.like(cb.lower(root.get("user").get("name")), "%" + filter.toLowerCase() + "%"));
            case "status" ->
                    select.where(cb.equal(root.get("status").get("type"), StatusType.valueOf(filter.toUpperCase())));
        }

        switch (direction) {
            case "asc" -> {
                if (sort.equals("user.name")) {
                    select.orderBy(cb.asc(root.get("user").get("name")));
                } else {
                    select.orderBy(cb.asc(root.get(sort)));
                }
            }
            case "desc" -> {
                if (sort.equals("user")) {
                    select.orderBy(cb.desc(root.get("user").get("name")));
                } else {
                    select.orderBy(cb.desc(root.get(sort)));
                }
            }
        }

        TypedQuery<Order> typedQuery = entityManager.createQuery(select);
        typedQuery.setFirstResult(page * limit);
        typedQuery.setMaxResults(limit);
        return typedQuery.getResultList();
    }

    @Override
    public List<Order> findAllByUser(User user) {
        return repository.findAllByUser(user);
    }

    @Override
    public int deleteByIdAndUser(int id, User user) {
        return repository.deleteByIdAndUser(id, user);
    }

    @Override
    public Order save(Order order) {
        return repository.save(order);
    }
}
