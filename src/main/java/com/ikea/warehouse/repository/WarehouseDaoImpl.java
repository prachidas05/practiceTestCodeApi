package com.ikea.warehouse.repository;

import com.ikea.warehouse.entity.BillOfMaterialEntity;
import com.ikea.warehouse.entity.InventoryEntity;
import com.ikea.warehouse.entity.ProductEntity;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Prachi Das
 */
@Repository
@Transactional
@Log4j2
public class WarehouseDaoImpl implements WarehouseDao{

    /**
     * the session factory instance provided by Hibernate
     */
    @Autowired
    protected SessionFactory sessionFactory;


    /**
     * Saves new articles to the warehouse from Inventory.json/
     * Updates articles when a product is sold
     * @param entity {@link InventoryEntity} instance
     */
    @Override
    @Transactional
    public void addUpdateArticles(final InventoryEntity entity) {
        sessionFactory.getCurrentSession().merge(entity);
     }

    /**
     * Saves new products to the warehouse from Product.json
     *
     * @param entity {@link ProductEntity} instance
     */
    @Override
    @Transactional
    public void addProducts(ProductEntity entity) {
        sessionFactory.getCurrentSession().merge(entity);
    }

    /**
     * gets all products from the DB
     * @return   List<String></String>
     */

    @Override
    @Transactional
    public List<String> retrieveAllProducts() {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select productName from ProductEntity ");
        log.debug("Query--->"+query);
        return query.getResultList();
    }


    /**
     * gets an inventory entity from the DB
     * @param artId {@link Long} instance
     * @return   InventoryEntity
     */

    @Override
    @Transactional
    public InventoryEntity getInventoryEntity(Long artId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from InventoryEntity where articleId = "+artId);
        log.debug("Query--->"+query);
        return (InventoryEntity) query.getResultList().get(0);
    }


    /**
     * Saves bill of Materials when a new product is added
     * against the corresponding articles and amount of articles
     * @param bomEntity {@link BillOfMaterialEntity} instance
     */

    @Override
    @Transactional
    public void addBillOfMaterials(BillOfMaterialEntity bomEntity) {
        sessionFactory.getCurrentSession().merge(bomEntity);
    }

    @Override
    @Transactional
    public List<BillOfMaterialEntity> getBillOfMaterialEntity(String productName) {

        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(" from BillOfMaterialEntity where productName =:productName");
        query.setParameter("productName",productName);
        log.debug("Query--->"+query);
        return query.getResultList();
    }
}
