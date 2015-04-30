/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import ControladorJPA.exceptions.IllegalOrphanException;
import ControladorJPA.exceptions.NonexistentEntityException;
import ControladorJPA.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Logica.Factura;
import Logica.Itempedido;
import Logica.Pizzabase;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

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

    public void create(Itempedido itempedido) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Factura facturaOrphanCheck = itempedido.getFactura();
        if (facturaOrphanCheck != null) {
            Itempedido oldItempedidoOfFactura = facturaOrphanCheck.getItempedido();
            if (oldItempedidoOfFactura != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Factura " + facturaOrphanCheck + " already has an item of type Itempedido whose factura column cannot be null. Please make another selection for the factura field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Factura factura = itempedido.getFactura();
            if (factura != null) {
                factura = em.getReference(factura.getClass(), factura.getFacturaId());
                itempedido.setFactura(factura);
            }
            Pizzabase pizzaIdIngredienteId = itempedido.getPizzaIdIngredienteId();
            if (pizzaIdIngredienteId != null) {
                pizzaIdIngredienteId = em.getReference(pizzaIdIngredienteId.getClass(), pizzaIdIngredienteId.getPizzaId());
                itempedido.setPizzaIdIngredienteId(pizzaIdIngredienteId);
            }
            em.persist(itempedido);
            if (factura != null) {
                factura.setItempedido(itempedido);
                factura = em.merge(factura);
            }
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

    public void edit(Itempedido itempedido) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Itempedido persistentItempedido = em.find(Itempedido.class, itempedido.getFacturaId());
            Factura facturaOld = persistentItempedido.getFactura();
            Factura facturaNew = itempedido.getFactura();
            Pizzabase pizzaIdIngredienteIdOld = persistentItempedido.getPizzaIdIngredienteId();
            Pizzabase pizzaIdIngredienteIdNew = itempedido.getPizzaIdIngredienteId();
            List<String> illegalOrphanMessages = null;
            if (facturaNew != null && !facturaNew.equals(facturaOld)) {
                Itempedido oldItempedidoOfFactura = facturaNew.getItempedido();
                if (oldItempedidoOfFactura != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Factura " + facturaNew + " already has an item of type Itempedido whose factura column cannot be null. Please make another selection for the factura field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (facturaNew != null) {
                facturaNew = em.getReference(facturaNew.getClass(), facturaNew.getFacturaId());
                itempedido.setFactura(facturaNew);
            }
            if (pizzaIdIngredienteIdNew != null) {
                pizzaIdIngredienteIdNew = em.getReference(pizzaIdIngredienteIdNew.getClass(), pizzaIdIngredienteIdNew.getPizzaId());
                itempedido.setPizzaIdIngredienteId(pizzaIdIngredienteIdNew);
            }
            itempedido = em.merge(itempedido);
            if (facturaOld != null && !facturaOld.equals(facturaNew)) {
                facturaOld.setItempedido(null);
                facturaOld = em.merge(facturaOld);
            }
            if (facturaNew != null && !facturaNew.equals(facturaOld)) {
                facturaNew.setItempedido(itempedido);
                facturaNew = em.merge(facturaNew);
            }
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
            Factura factura = itempedido.getFactura();
            if (factura != null) {
                factura.setItempedido(null);
                factura = em.merge(factura);
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
