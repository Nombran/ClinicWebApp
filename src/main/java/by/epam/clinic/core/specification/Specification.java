package by.epam.clinic.core.specification;


/**
 * The interface Specification describe process of creating sql query.
 */
public interface Specification {
    /**
     * Method defines logic of creating sql query as string.
     *
     * @return the string representation of sql query.
     */
    String toSqlQuery();
}
