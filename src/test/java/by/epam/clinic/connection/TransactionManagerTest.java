package by.epam.clinic.connection;

import by.epam.clinic.core.pool.TransactionManager;
import by.epam.clinic.core.pool.TransactionManagerException;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TransactionManagerTest {
    private TransactionManager transactionManager;

    @BeforeClass
    public void setUp() {
        transactionManager = new TransactionManager();
    }

    @AfterClass
    public void setDown() {
        transactionManager = null;
    }

    @Test(expectedExceptions = TransactionManagerException.class)
    public void negativeBeginTransactionTest() throws TransactionManagerException {
        transactionManager.beginTransaction();
    }

    @Test(expectedExceptions = TransactionManagerException.class)
    public void negativeRollbackTransactionTest() throws TransactionManagerException {
        transactionManager.rollbackTransaction();
    }

    @Test(expectedExceptions = TransactionManagerException.class)
    public void negativeCommitTransactionTest() throws TransactionManagerException {
        transactionManager.commitTransaction();
    }

    @Test(expectedExceptions = TransactionManagerException.class)
    public void negativeReleaseResourcesTest() throws TransactionManagerException {
        transactionManager.releaseResources();
    }
}
