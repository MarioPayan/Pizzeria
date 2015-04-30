/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import ControladorJPA.exceptions.NonexistentEntityException;
import ControladorJPA.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Logica.Itempedido;
import Logica.Pizzabase;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author kazemu
 */
public class PizzabaseJpaController implements Serializable {

    public PizzabaseJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pizzabase pizzabase) throws PreexistingEntityException, Exception {
        if (pizzabase.getItempedidoCollection() == null) {
            pizzabase.setItempedidoCollection(new ArrayList<Itempedido>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Itempedido> attachedItempedidoCollection = new ArrayList<Itempedido>();
            for (Itempedido itempedidoCollectionItempedidoToAttach : pizzabase.getItempedidoCollection()) {
                itempedidoCollectionItempedidoToAttach = em.getReference(itempedidoCollectionItempedidoToAttach.getClass(), itempedidoCollectionItempedidoToAttach.getFacturaId());
                attachedItempedidoCollection.add(itempedidoCollectionItempedidoToAttach);
            }
            pizzabase.setItempedidoCollection(attachedItempedidoCollection);
            em.persist(pizzabase);
            for (Itempedido itempedidoCollectionItempedido : pizzabase.getItempedidoCollection()) {
                Pizzabase oldPizzaIdIngredienteIdOfItempedidoCollectionItempedido = itempedidoCollectionItempedido.getPizzaIdIngredienteId();
                itempedidoCollectionItempedido.setPizzaIdIngredienteId(pizzabase);
                itempedidoCollectionItempedido = em.merge(itempedidoCollectionItempedido);
                if (oldPizzaIdIngredienteIdOfItempedidoCollectionItempedido != null) {
                    oldPizzaIdIngredienteIdOfItempedidoCollectionItempedido.getItempedidoCollection().remove(itempedidoCollectionItempedido);
                    oldPizzaIdIngredienteIdOfItempedidoCollectionItempedido = em.merge(oldPizzaIdIngredienteIdOfItempedidoCollectionItempedido);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPizzabase(pizzabase.getPizzaId()) != null) {
                throw new PreexistingEntityException("Pizzabase " + pizzabase + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pizzabase pizzabase) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pizzabase persistentPizzabase = em.find(Pizzabase.class, pizzabase.getPizzaId());
            Collection<Itempedido> itempedidoCollectionOld = persistentPizzabase.getItempedidoCollection();
            Collection<Itempedido> itempedidoCollectionNew = pizzabase.getItempedidoCollection();
            Collection<Itempedido> attachedItempedidoCollectionNew = new ArrayList<Itempedido>();
            for (Itempedido itempedidoCollectionNewItempedidoToAttach : itempedidoCollectionNew) {
                itempedidoCollectionNewItempedidoToAttach = em.getReference(itempedidoCollectionNewItempedidoToAttach.getClass(), itempedidoCollectionNewItempedidoToAttach.getFacturaId());
                attachedItempedidoCollectionNew.add(itempedidoCollectionNewItempedidoToAttach);
            }
            itempedidoCollectionNew = attachedItempedidoCollectionNew;
            pizzabase.setItempedidoCollection(itempedidoCollectionNew);
            pizzabase = em.merge(pizzabase);
            for (Itempedido itempedidoCollectionOldItempedido : itempedidoCollectionOld) {
                if (!itempedidoCollectionNew.contains(itempedidoCollectionOldItempedido)) {
                    itempedidoCollectionOldItempedido.setPizzaIdIngredienteId(null);
                    itempedidoCollectionOldItempedido = em.merge(itempedidoCollectionOldItempedido);
                }
            }
            for (Itempedido itempedidoCollectionNewItempedido : itempedidoCollectionNew) {
                if (!itempedidoCollectionOld.contains(itempedidoCollectionNewItempedido)) {
                    Pizzabase oldPizzaIdIngredienteIdOfItempedidoCollectionNewItempedido = itempedidoCollectionNewItempedido.getPizzaIdIngredienteId();
                    itempedidoCollectionNewItempedido.setPizzaIdIngredienteId(pizzabase);
                    itempedidoCollectionNewItempedido = em.merge(itempedidoCollectionNewItempedido);
                    if (oldPizzaIdIngredienteIdOfItempedidoCollectionNewItempedido != null && !oldPizzaIdIngredienteIdOfItempedidoCollectionNewItempedido.equals(pizzabase)) {
                        oldPizzaIdIngredienteIdOfItempedidoCollectionNewItempedido.getItempedidoCollection().remove(itempedidoCollectionNewItempedido);
                        oldPizzaIdIngredienteIdOfItempedidoCollectionNewItempedido = em.merge(oldPizzaIdIngredienteIdOfItempedidoCollectionNewItempedido);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = pizzabase.getPizzaId();
                if (findPizzabase(id) == null) {
                    throw new NonexistentEntityException("The pizzabase with id " + id + " no longer exists.");
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
            Pizzabase pizzabase;
            try {
                pizzabase = em.getReference(Pizzabase.class, id);
                pizzabase.getPizzaId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pizzabase with id " + id + " no longer exists.", enfe);
            }
            Collection<Itempedido> itempedidoCollection = pizzabase.getItempedidoCollection();
            for (Itempedido itempedidoCollectionItempedido : itempedidoCollection) {
                itempedidoCollectionItempedido.setPizzaIdIngredienteId(null);
                itempedidoCollectionItempedido = em.merge(itempedidoCollectionItempedido);
            }
            em.remove(pizzabase);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pizzabase> findPizzabaseEntities() {
        return findPizzabaseEntities(true, -1, -1);
    }

    public List<Pizzabase> findPizzabaseEntities(int maxResults, int firstResult) {
        return findPizzabaseEntities(false, maxResults, firstResult);
    }

    private List<Pizzabase> findPizzabaseEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pizzabase.class));
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

    public Pizzabase findPizzabase(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pizzabase.class, id);
        } finally {
            em.close();
        }
    }

    public int getPizzabaseCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pizzabase> rt = cq.from(Pizzabase.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
