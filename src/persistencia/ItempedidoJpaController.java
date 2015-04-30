/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import Logica.Itempedido;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Logica.Pizzabase;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import persistencia.exceptions.NonexistentEntityException;
import persistencia.exceptions.PreexistingEntityException;

/**
 *
 * @author kazemu
 */
public class ItempedidoJpaController implements Serializable {

    public ItempedidoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Itempedido itempedido) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pizzabase pizzaIdIngredienteId = itempedido.getPizzaIdIngredienteId();
            if (pizzaIdIngredienteId != null) {
                pizzaIdIngredienteId = em.getReference(pizzaIdIngredienteId.getClass(), pizzaIdIngredienteId.getPizzaId());
                itempedido.setPizzaIdIngredienteId(pizzaIdIngredienteId);
            }
            em.persist(itempedido);
            if (pizzaIdIngredienteId != null) {
                pizzaIdIngredienteId.getItempedidoCollection().add(itempedido);
                pizzaIdIngredienteId = em.merge(pizzaIdIngredienteId);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findItempedido(itempedido.getFacturaId()) != null) {
                throw new PreexistingEntityException("Itempedido " + itempedido + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Itempedido itempedido) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Itempedido persistentItempedido = em.find(Itempedido.class, itempedido.getFacturaId());
            Pizzabase pizzaIdIngredienteIdOld = persistentItempedido.getPizzaIdIngredienteId();
            Pizzabase pizzaIdIngredienteIdNew = itempedido.getPizzaIdIngredienteId();
            if (pizzaIdIngredienteIdNew != null) {
                pizzaIdIngredienteIdNew = em.getReference(pizzaIdIngredienteIdNew.getClass(), pizzaIdIngredienteIdNew.getPizzaId());
                itempedido.setPizzaIdIngredienteId(pizzaIdIngredienteIdNew);
            }
            itempedido = em.merge(itempedido);
            if (pizzaIdIngredienteIdOld != null && !pizzaIdIngredienteIdOld.equals(pizzaIdIngredienteIdNew)) {
                pizzaIdIngredienteIdOld.getItempedidoCollection().remove(itempedido);
                pizzaIdIngredienteIdOld = em.merge(pizzaIdIngredienteIdOld);
            }
            if (pizzaIdIngredienteIdNew != null && !pizzaIdIngredienteIdNew.equals(pizzaIdIngredienteIdOld)) {
                pizzaIdIngredienteIdNew.getItempedidoCollection().add(itempedido);
                pizzaIdIngredienteIdNew = em.merge(pizzaIdIngredienteIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = itempedido.getFacturaId();
                if (findItempedido(id) == null) {
                    throw new NonexistentEntityException("The itempedido with id " + id + " no longer exists.");
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
            Itempedido itempedido;
            try {
                itempedido = em.getReference(Itempedido.class, id);
                itempedido.getFacturaId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The itempedido with id " + id + " no longer exists.", enfe);
            }
            Pizzabase pizzaIdIngredienteId = itempedido.getPizzaIdIngredienteId();
            if (pizzaIdIngredienteId != null) {
                pizzaIdIngredienteId.getItempedidoCollection().remove(itempedido);
                pizzaIdIngredienteId = em.merge(pizzaIdIngredienteId);
            }
            em.remove(itempedido);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Itempedido> findItempedidoEntities() {
        return findItempedidoEntities(true, -1, -1);
    }

    public List<Itempedido> findItempedidoEntities(int maxResults, int firstResult) {
        return findItempedidoEntities(false, maxResults, firstResult);
    }

    private List<Itempedido> findItempedidoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Itempedido.class));
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

    public Itempedido findItempedido(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Itempedido.class, id);
        } finally {
            em.close();
        }
    }

    public int getItempedidoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Itempedido> rt = cq.from(Itempedido.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
