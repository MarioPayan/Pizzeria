/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import ControladorJPA.exceptions.NonexistentEntityException;
import ControladorJPA.exceptions.PreexistingEntityException;
import Logica.Vegetal;
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
public class VegetalJpaController implements Serializable {

    public VegetalJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Vegetal vegetal) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(vegetal);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findVegetal(vegetal.getVegetalId()) != null) {
                throw new PreexistingEntityException("Vegetal " + vegetal + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Vegetal vegetal) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            vegetal = em.merge(vegetal);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = vegetal.getVegetalId();
                if (findVegetal(id) == null) {
                    throw new NonexistentEntityException("The vegetal with id " + id + " no longer exists.");
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
            Vegetal vegetal;
            try {
                vegetal = em.getReference(Vegetal.class, id);
                vegetal.getVegetalId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The vegetal with id " + id + " no longer exists.", enfe);
            }
            em.remove(vegetal);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Vegetal> findVegetalEntities() {
        return findVegetalEntities(true, -1, -1);
    }

    public List<Vegetal> findVegetalEntities(int maxResults, int firstResult) {
        return findVegetalEntities(false, maxResults, firstResult);
    }

    private List<Vegetal> findVegetalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Vegetal.class));
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

    public Vegetal findVegetal(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Vegetal.class, id);
        } finally {
            em.close();
        }
    }

    public int getVegetalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Vegetal> rt = cq.from(Vegetal.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
