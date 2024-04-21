package mr2.meetingroom02.dojosession.base.dao;


import mr2.meetingroom02.dojosession.base.entity.BaseEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

//@RequiredArgsConstructor
public abstract class BaseDAO<T extends BaseEntity> {
    @PersistenceContext
    protected EntityManager entityManager;
    private final Class<T> entityClass;

    public BaseDAO (Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public List<T> findAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<T> q = cb.createQuery(entityClass);
        Root<T> c = q.from(entityClass);

        return entityManager.createQuery(q).getResultList();
    }

    public T insert(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    public List<T> insertAll(List<T> entities) {
        entities.forEach(e -> entityManager.persist(e));
        return entities;
    }

    public T update(T entity) {
        entityManager.merge(entity);
        return entity;
    }

    public Optional<T> findById(long id) {
        return Optional.ofNullable(entityManager.find(entityClass, id));
    }

    public void delete(long id) {
        T entity = this.findById(id).get();
        entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
    }
}
