package by.epam.clinic.core.service.impl;

import javax.servlet.http.Part;

import by.epam.clinic.core.model.Department;
import by.epam.clinic.core.pool.TransactionManager;
import by.epam.clinic.core.pool.TransactionManagerException;
import by.epam.clinic.core.repository.impl.DepartmentRepository;
import by.epam.clinic.core.repository.impl.RepositoryException;
import by.epam.clinic.core.service.DepartmentService;
import by.epam.clinic.core.specification.impl.FindAllDepartmentsSpecification;
import by.epam.clinic.core.specification.impl.FindDepartmentByIdSpecification;
import by.epam.clinic.util.ImageUploader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class DepartmentServiceImpl implements DepartmentService {
    private static Logger logger = LogManager.getLogger();

    private static final String UPLOAD_DIR_PATH = "/img/departments";

    public void createDepartment(String name, String description, String phone,
                                    String servletContextPath, Part imageFile) throws ServiceException {
        String uploadFileDir = servletContextPath + UPLOAD_DIR_PATH;
        ImageUploader imageUploader = new ImageUploader(uploadFileDir);
        String fileName = imageUploader.generateUniqueFileName(imageFile);
        TransactionManager transactionManager = new TransactionManager();
        try {
            transactionManager.init();
            DepartmentRepository departmentRepository = new DepartmentRepository();
            transactionManager.setConnectionToRepository(departmentRepository);
            transactionManager.beginTransaction();
            String dbFilePath = UPLOAD_DIR_PATH + "/" + fileName;
            Department department = new Department(name, description, phone, dbFilePath);
            departmentRepository.add(department);
            try {
                imageUploader.write(imageFile, fileName);
            } catch (IOException e) {
                transactionManager.rollbackTransaction();
                throw new ServiceException("Error in uploading image" ,e);
            }
        } catch (TransactionManagerException e) {
            throw new ServiceException("Transaction manager error", e);
        } catch (RepositoryException e) {
            throw new ServiceException("Repository error", e);
        } finally {
            try {
                transactionManager.releaseResources();
            } catch (TransactionManagerException e) {
                logger.error("Error in releasing connection", e);
            }
        }
    }

    public List<Department> getAllDepartments() throws ServiceException {
        TransactionManager transactionManager = new TransactionManager();
        try {
            transactionManager.init();
            DepartmentRepository departmentRepository = new DepartmentRepository();
            transactionManager.setConnectionToRepository(departmentRepository);
            FindAllDepartmentsSpecification specification = new FindAllDepartmentsSpecification();
            return departmentRepository.query(specification);
        } catch (TransactionManagerException e) {
            throw new ServiceException("Transaction manager error", e);
        } catch (RepositoryException e) {
            throw new ServiceException("Repository error", e);
        } finally {
            try {
                transactionManager.releaseResources();
            } catch (TransactionManagerException e) {
                logger.error("Error in releasing connection", e);
            }
        }
    }

    public void deleteDepartment(long id) throws ServiceException {
        TransactionManager transactionManager = new TransactionManager();
        try {
            transactionManager.init();
            DepartmentRepository repository = new DepartmentRepository();
            transactionManager.setConnectionToRepository(repository);
            repository.remove(id);
        } catch (TransactionManagerException e) {
            throw new ServiceException("Transaction manager error", e);
        } catch (RepositoryException e) {
            throw new ServiceException("Repository error", e);
        } finally {
            try {
                transactionManager.releaseResources();
            } catch (TransactionManagerException e) {
                logger.error("Error in releasing connection", e);
            }
        }
    }

    public Optional<Department> findDepartment(long id) throws ServiceException {
        TransactionManager transactionManager = new TransactionManager();
        try {
            transactionManager.init();
            DepartmentRepository repository = new DepartmentRepository();
            transactionManager.setConnectionToRepository(repository);
            FindDepartmentByIdSpecification specification
                    = new FindDepartmentByIdSpecification(id);
            List<Department> queryResult = repository.query(specification);
            if (queryResult.size() == 0) {
                return Optional.empty();
            } else {
                Department department = queryResult.get(0);
                return Optional.of(department);
            }
        } catch (TransactionManagerException e) {
            throw new ServiceException("Transaction manager error", e);
        } catch (RepositoryException e) {
            throw new ServiceException("Repository error", e);
        } finally {
            try {
                transactionManager.releaseResources();
            } catch (TransactionManagerException e) {
                logger.error("Error in releasing connection", e);
            }
        }
    }

    public void updateDepartment(Department department, String servletContextPath, Part imageFile) throws ServiceException {
        TransactionManager transactionManager = new TransactionManager();
        String uploadDir = servletContextPath + UPLOAD_DIR_PATH;
        ImageUploader uploader = new ImageUploader(uploadDir);
        String imageFileName = uploader.generateUniqueFileName(imageFile);
        try {
            transactionManager.init();
            DepartmentRepository departmentRepository = new DepartmentRepository();
            transactionManager.setConnectionToRepository(departmentRepository);
            transactionManager.beginTransaction();
            if (department.getImagePath() != null) {
                departmentRepository.update(department);
            } else {
                String dbFilePath = UPLOAD_DIR_PATH + "/" + imageFileName;
                department.setImagePath(dbFilePath);
                departmentRepository.update(department);
                uploader.write(imageFile, imageFileName);
            }
            transactionManager.commitTransaction();
        } catch (TransactionManagerException e) {
            throw new ServiceException("Transaction manager error", e);
        } catch (IOException e) {
            try {
                transactionManager.rollbackTransaction();
            } catch (TransactionManagerException ex) {
                logger.error("Error in rollback of transaction", e);
            }
            throw new ServiceException("Upload image error", e);
        } catch (RepositoryException e) {
            throw new ServiceException("Repository error", e);
        } finally {
            try {
                transactionManager.releaseResources();
            } catch (TransactionManagerException e) {
                logger.error("Error in releasing connection", e);
            }
        }
    }
}
