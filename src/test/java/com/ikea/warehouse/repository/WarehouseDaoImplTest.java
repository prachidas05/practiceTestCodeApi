package com.ikea.warehouse.repository;

import com.ikea.warehouse.entity.InventoryEntity;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseDataSourceConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.SQLException;

import static com.ikea.warehouse.util.TestUtils.readFile;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Prachi Das
 */
@SpringBootTest
@Transactional
class WarehouseDaoImplTest {


        @Autowired
        private WarehouseDao warehouseDao;

        @Autowired private DataSource dataSource;

        private IDatabaseConnection dbConn;

        @BeforeEach
        public void setUp() throws Exception {
            dbConn = new DatabaseDataSourceConnection(dataSource);
        }


        @AfterEach
        public void cleanup() throws SQLException {
            dbConn.close();
        }

        @Test
        void test_SaveUpdateAndRetrieve() throws DataSetException, DatabaseUnitException, SQLException {
            final FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
            DatabaseOperation.CLEAN_INSERT.execute(dbConn,
                    builder.build(readFile("inventory-test-data.xml")));
            warehouseDao.addUpdateArticles(mockInventoryEntity());
            final InventoryEntity entity = warehouseDao.getInventoryEntity(1L);
            assertNotNull(entity);
            assertEquals(12L, entity.getStock());

        }
        private InventoryEntity mockInventoryEntity() {
        InventoryEntity entity1 = new InventoryEntity();
        entity1.setArticleId(1L);
        entity1.setArticleName("leg");
        entity1.setStock(12L);
        return entity1;
    }
}
