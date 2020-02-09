package by.epam.clinic.servlet;

import by.epam.clinic.core.pool.ConnectionPool;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ApplicationServletContext implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent ev) {
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        ConnectionPool.INSTANCE.destroyPool();
    }
}
