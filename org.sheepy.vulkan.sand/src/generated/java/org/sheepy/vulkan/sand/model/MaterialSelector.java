/**
 */
package org.sheepy.vulkan.sand.model;

import org.sheepy.lily.core.model.ui.Widget;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Material Selector</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sheepy.vulkan.sand.model.MaterialSelector#getLineHeight <em>Line Height</em>}</li>
 * </ul>
 *
 * @see org.sheepy.vulkan.sand.model.VSandPackage#getMaterialSelector()
 * @model
 * @generated
 */
public interface MaterialSelector extends Widget
{

	/**
	 * Returns the value of the '<em><b>Line Height</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Line Height</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Line Height</em>' attribute.
	 * @see #setLineHeight(int)
	 * @see org.sheepy.vulkan.sand.model.VSandPackage#getMaterialSelector_LineHeight()
	 * @model unique="false"
	 * @generated
	 */
	int getLineHeight();

	/**
	 * Sets the value of the '{@link org.sheepy.vulkan.sand.model.MaterialSelector#getLineHeight <em>Line Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Line Height</em>' attribute.
	 * @see #getLineHeight()
	 * @generated
	 */
	void setLineHeight(int value);
} // MaterialSelector
