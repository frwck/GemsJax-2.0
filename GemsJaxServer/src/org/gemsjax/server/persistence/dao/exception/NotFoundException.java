package org.gemsjax.server.persistence.dao.exception;

/**
 * Is thrown, when a DAO try to access a persistence object (by quering the database with the persistence objects unique id)
 * but no data record was found.
 * @author Hannes Dorfmann
 *
 */
public class NotFoundException extends Exception {

}
