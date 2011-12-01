package org.gemsjax.server.persistence.dao.exception;

import org.gemsjax.shared.request.Request;


/**
 * To inform that the same object is already stored in the database.
 * This is used for expample for {@link Request}s to ensure, that its not possible to create a second {@link Request} for the same thing
 * @author Hannes Dorfmann
 *
 */
public class AlreadyExistException extends Exception{

}
