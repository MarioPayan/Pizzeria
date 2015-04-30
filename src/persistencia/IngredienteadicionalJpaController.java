/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import ControladorJPA.exceptions.NonexistentEntityException;
import ControladorJPA.exceptions.PreexistingEntityException;
import Logica.Carne;
import Logica.Ingredienteadicional;
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
public class IngredienteadicionalJpaController implements Serializable {

    public IngredienteadicionalJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ingredienteadicional ingredienteadicional) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(ingredienteadicional);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findIngredienteadicional(ingredienteadicional.getIngredienteId()) != null) {
                throw new PreexistingEntityException("Ingredienteadicional " + ingredienteadicional + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ingredienteadicional ingredienteadicional) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ingredienteadicional = em.merge(ingredienteadicional);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = ingredienteadicional.getIngredienteId();
                if (findIngredienteadicional(id) == null) {
                    throw new NonexistentEntityException("The ingredienteadicional with id " + id + " no longer exists.");
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
            Ingredienteadicional ingredienteadicional;
            try {
                ingredienteadicional = em.getReference(Ingredienteadicional.class, id);
                ingredienteadicional.getIngredienteId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ingredienteadicional with id " + id + " no longer exists.", enfe);
            }
            em.remove(ingredienteadicional);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ingredienteadicional> findIngredienteadicionalEntities() {
        return findIngredienteadicionalEntities(true, -1, -1);
    }

    public List<Ingredienteadicional> findIngredienteadicionalEntities(int maxResults, int firstResult) {
        return findIngredienteadicionalEntities(false, maxResults, firstResult);
    }

    private List<Ingredienteadicional> findIngredienteadicionalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ingredienteadicional.class));
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

    public Ingredienteadicional findIngredienteadicional(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ingredienteadicional.class, id);
        } finally {
            em.close();
        }
    }

    public int getIngredienteadicionalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ingredienteadicional> rt = cq.from(Ingredienteadicional.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    
    
}
