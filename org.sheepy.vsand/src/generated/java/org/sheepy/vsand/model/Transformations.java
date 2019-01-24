/**
 */
package org.sheepy.vulkan.sand.model;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Transformations</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sheepy.vulkan.sand.model.Transformations#getTransformations <em>Transformations</em>}</li>
 * </ul>
 *
 * @see org.sheepy.vulkan.sand.model.VSandPackage#getTransformations()
 * @model
 * @generated
 */
public interface Transformations extends EObject
{
	/**
	 * Returns the value of the '<em><b>Transformations</b></em>' containment reference list.
	 * The list contents are of type {@link org.sheepy.vulkan.sand.model.Transformation}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Transformations</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Transformations</em>' containment reference list.
	 * @see org.sheepy.vulkan.sand.model.VSandPackage#getTransformations_Transformations()
	 * @model containment="true"
	 * @generated
	 */
	EList<Transformation> getTransformations();

} // Transformations
