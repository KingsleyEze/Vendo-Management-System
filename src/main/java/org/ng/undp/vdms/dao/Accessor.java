package org.ng.undp.vdms.dao;

import org.ng.undp.vdms.domains.Model;
import org.ng.undp.vdms.services.EmService;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

/**
 * Created by emmanuel on 2/12/15.
 */
public class Accessor {
    public static EntityManager em = EmService.em();

    public static void saveOne(Model model) {
        if (model.getId() == null) {
            em.persist(model);
        } else {
            em.merge(model);
        }
    }


    public static void deleteOne(Model model){
        em.remove(model);
    }

    public static <T> void deleteOne(T t){
        em.remove(t);
    }

    public static <T> T findOne(Class<T> clazz, Long id) {
        try {
            return em.find(clazz, id);
        } catch(Exception e) {
            return null;
        }
    }

    public static <T> T findOne(Class<T> clazz, String key, Object value) {
        try{
            return em.createQuery(
                "SELECT t FROM " + clazz.getSimpleName() + " t " + "WHERE t." + key + "=:value", clazz)
                .setParameter("value", value)
                .getSingleResult();
        } catch(Exception e) {
            return null;
        }
    }

    public static <T> T findOne(Class<T> clazz, Filter filter) {
        try{
            String sql = "SELECT t FROM " + clazz.getSimpleName() + " t ";
            sql += filter.getSql();
            TypedQuery<T> q = em.createQuery(sql, clazz);
            addParam(q, filter);
            return q.getSingleResult();
        } catch(Exception e) {
            return null;
        }
    }

    public static <T> List<T> findList(Class<T> clazz, Filter filter) {
        String sql = "SELECT t FROM " + clazz.getSimpleName() + " t ";
        sql += filter.getSql();
        TypedQuery<T> q = em.createQuery(sql, clazz);
        addParam(q, filter);
        return q.getResultList();
    }

    public static <T> List<T> findList(Class<T> clazz, Filter filter, Param param) {
        String sql = "SELECT t FROM " + clazz.getSimpleName() + " t ";
        sql += filter.getSql() + getOrder(param);
        TypedQuery<T> q = em.createQuery(sql, clazz);
        addParam(q, filter);
        q.setFirstResult(param.getOffset()).setMaxResults(param.getSize());
        return q.getResultList();
    }

    public static <T> List<T> findList(Class<T> clazz, Filter filter, String sort) {
        String sql = "SELECT t FROM " + clazz.getSimpleName() + " t ";
        sql += filter.getSql() + " ORDER BY " + sort;
        TypedQuery<T> q = em.createQuery(sql, clazz);
        addParam(q, filter);
        return q.getResultList();
    }

    /**
     * create a query with limit, without relying on the Param Object
     * @param clazz
     * @param filter
     * @param limit
     * @param <T>
     * @return
     */
    public static <T> List<T> findList(Class<T> clazz, Filter filter, int limit) {
        String sql = "SELECT t FROM " + clazz.getSimpleName() + " t ";
        sql += filter.getSql() ;
        TypedQuery<T> q = em.createQuery(sql, clazz);
        addParam(q, filter);
        q.setMaxResults(limit);
        return q.getResultList();
    }

    public static <T> Long count(Class<T> clazz, Filter filter) {
        String sql = "SELECT COUNT(t) FROM " + clazz.getSimpleName() + " t ";
        sql += filter.getSql();
        TypedQuery<Long> q = em.createQuery(sql, Long.class);
        addParam(q, filter);
        return q.getSingleResult();
    }

    private static <T> void addParam(TypedQuery<T> q, Filter filter) {
        filter.getParams().forEach((key, value) -> {
            if (value instanceof Date) {
                q.setParameter(key, (Date) value);
            } else {
                q.setParameter(key, value);
            }
        });
    }

    private static String getOrder(Param param) {
        return StringUtils.hasText(param.getSort()) ? " ORDER BY " + param.getSort(): "";
    }


    public static <T> T findLast(Class<T> clazz) {
        try{
            String sql = "SELECT t FROM " + clazz.getSimpleName() + " t order by id desc ";
            TypedQuery<T> q = em.createQuery(sql, clazz);
            q.setMaxResults(1);
            return q.getSingleResult();
        } catch(Exception e) {
            return null;
        }
    }


    public static <T> Long lastId(Class<T> clazz) {
        //try{
            String sql = "SELECT max(t.id) FROM " + clazz.getSimpleName() + " t ";
            Query q = em.createQuery(sql);
            Long max = (Long)q.getSingleResult();
            if(max == null){
                return 0l;
            }
            return max;
        /*} catch(Exception e) {
            return 0l;
        }*/
    }
}
