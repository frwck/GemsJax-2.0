package org.gemsjax.shared.metamodel.exception;

import org.gemsjax.shared.metamodel.MetaClass;
import org.gemsjax.shared.metamodel.MetaContainmentRelation;

/**
 * This Exception is thrown, when you try to add a {@link MetaContainmentRelation} to a MetaClass ( {@link #container},
 * which already has a {@link MetaContainmentRelation} for the same MetaClass destination ({@link #toContain}.
 * @author Hannes Dorfmann
 *
 */
public class MetaContainmentRelationException extends Exception {

	private MetaClass container;
	private MetaClass toContain;
	
	public MetaContainmentRelationException(MetaClass container, MetaClass toContain)
	{
		this.container = container;
		this.toContain = toContain;
	}

	/**
	 * The {@link MetaClass} which should add a new {@link MetaContainmentRelation} but this is not allowed
	 * @return
	 */
	public MetaClass getContainer() {
		return container;
	}

	/**
	 * The {@link MetaClass} which should be added to the container ({@link #getContainer()) as new MetaContainmentRelation
	 * @return
	 */
	public MetaClass getToContain() {
		return toContain;
	}
}
