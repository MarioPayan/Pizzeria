/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControladorJPA;

import ControladorJPA.exceptions.NonexistentEntityException;
import ControladorJPA.exceptions.PreexistingEntityException;
import Logica.Salsa;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author kazemu
 */
public class SalsaJpaController implements Serializable {

    public SalsaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Salsa salsa) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(salsa);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSalsa(salsa.getSalsaId()) != null) {
                throw new PreexistingEntityException("Salsa " + salsa + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Salsa salsa) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            salsa = em.merge(salsa);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = salsa.getSalsaId();
                if (findSalsa(id) == null) {
                    throw new NonexistentEntityException("The salsa with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Salsa salsa;
            try {
                salsa = em.getReference(Salsa.class, id);
                salsa.getSalsaId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The salsa with id " + id + " no longer exists.", enfe);
            }
            em.remove(salsa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Salsa> findSalsaEntities() {
        return findSalsaEntities(true, -1, -1);
    }

    public List<Salsa> findSalsaEntities(int maxResults, int firstResult) {
        return findSalsaEntities(false, maxResults, firstResult);
    }

    private List<Salsa> findSalsaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Salsa.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Salsa findSalsa(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Salsa.class, id);
        } finally {
            em.close();
        }
    }

    public int getSalsaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Salsa> rt = cq.from(Salsa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
