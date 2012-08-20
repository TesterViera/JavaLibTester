package tester;

/**
 * Copyright 2007 Viera K. Proulx
 * This program is distributed under the terms of the 
 * GNU Lesser General Public License (LGPL)
 */

/**
 *  An interface that defines a functional iterator for traversing datasets
 * 
 * @author Viera K. Proulx
 * @since 30 May 2007 
 */
public interface Traversal<T> {

	/** 
	 * Produce true if this <CODE>{@link Traversal Traversal}</CODE> 
	 * represents an empty dataset 
	 * 
	 * @return true if the dataset is empty
	 */
	public boolean isEmpty();

	/** 
	 * <P>Produce the first element in the dataset represented by this 
	 * <CODE>{@link Traversal Traversal}</CODE> </P>
	 * <P>Throws <code>IllegalUseOfTraversalException</code> 
	 * if the dataset is empty.</P>
	 * 
	 * @return the first element if available -- otherwise 
	 * throws <code>IllegalUseOfTraversalException</code>
	 */
	public T getFirst();

	/** 
	 * <P>Produce a <CODE>{@link Traversal Traversal}</CODE> 
	 * for the rest of the dataset </P>
	 * <P>Throws <code>IllegalUseOfTraversalException</code> 
	 * if the dataset is empty.</P>
	 * 
	 * @return the <CODE>{@link Traversal Traversal}</CODE> for
	 * the rest of this dataset if available -- otherwise 
	 * throws <code>IllegalUseOfTraversalException</code>
	 */
	public Traversal<T> getRest();
}
