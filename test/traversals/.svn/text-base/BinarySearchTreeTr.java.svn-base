package traversals;

import tester.Traversal;

/**
 * A class to represent a binary search tree of objects of the type <code>T</code>
 * that can be accessible through the <CODE>{@link Traversal Traversal} T</CODE>
 * interface.
 */
public class BinarySearchTreeTr<T> implements Traversal<T> {
	/** the pointer to the left sub-tree*/
	BinarySearchTreeTr<T> leftBranch;
	/** the object in the current node */
	T node;
	/** the pointer to the right sub-tree */
	BinarySearchTreeTr<T> rightBranch;

	/**
	 * Constructor
	 *
	 * @param leftBranch the left sub-tree
	 * @param node the object in the node
	 * @param rightBranch the right sub-tree
	 */
	public BinarySearchTreeTr (BinarySearchTreeTr<T> leftBranch, T node, BinarySearchTreeTr<T> rightBranch) {
		this.leftBranch = leftBranch;
		this.node = node;
		this.rightBranch = rightBranch;
	}

	/**
	 * Construct an empty binary search tree
	 */
	public BinarySearchTreeTr () {
	}

	/**
	 * Construct a binary search tree with no children sub-trees
	 *
	 * @param node the object in the node
	 */
	public BinarySearchTreeTr (T node) {
		this(new BinarySearchTreeTr<T>(), node, new BinarySearchTreeTr<T>());
	}

	/**
	 * Check whether this tree is empty
	 */
	public boolean isEmpty() {
		return (leftBranch == null || leftBranch.isEmpty()) &&  // the left branch is empty
		node == null &&                                 // and the node is empty
		(rightBranch == null || rightBranch.isEmpty()); // and the right branch is empty
	}

	/**
	 * Get the first element according to the tree inorder
	 */
	public T getFirst() {
		if (leftBranch != null && !leftBranch.isEmpty()) {     // if there's non-empty left sub-tree
			return leftBranch.getFirst();                          // return its first element
		}
		else if (node != null) {                               // if the node is not empty
			return node;                                            // return it
		}
		else if (rightBranch != null && !rightBranch.isEmpty()) { // if there's non-empty right sub-tree
			return leftBranch.getFirst();                           // return its first element
		}
		else {
			throw new UnsupportedOperationException(
			"Cannot access the first element of an empty data set"); // the tree is empty
		}
	}

	/**
	 * Get all the elements ot the tree but the first
	 */
	public Traversal<T> getRest() {
		if (isEmpty()) {
			throw new UnsupportedOperationException(
			"Cannot advance to the rest of an empty data set"); // empty tree
		}

		if (leftBranch != null && !leftBranch.isEmpty()) {    // if there's non-empty left sub-tree
			Traversal<T> lbRest = leftBranch.getRest();          // get its elements but the first
			if (!lbRest.isEmpty()) {
				// construct resulting tree with the rest of elements in the left sub-tree and keeping
				// the node and right sub-tree intact
				return new BinarySearchTreeTr<T>((BinarySearchTreeTr<T>) lbRest, node, rightBranch);
			}
			else {
				// construct resulting tree with an empty left sub-tree and keeping
				// the node and right sub-tree intact
				return new BinarySearchTreeTr<T>(new BinarySearchTreeTr<T>(), node, rightBranch);
			}
		}
		else if (node != null) { // if the node is not empty
			return rightBranch;  // return the right sub-tree
		}
		else {
			return rightBranch.getRest();  // return all the elements of the right sub-tree but the first
		}
	}
}