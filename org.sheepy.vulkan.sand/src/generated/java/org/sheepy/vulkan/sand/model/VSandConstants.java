/**
 */
package org.sheepy.vulkan.sand.model;

import org.sheepy.lily.vulkan.model.resource.AbstractConstants;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Constants</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sheepy.vulkan.sand.model.VSandConstants#isFirstPass <em>First Pass</em>}</li>
 * </ul>
 *
 * @see org.sheepy.vulkan.sand.model.VSandPackage#getVSandConstants()
 * @model
 * @generated
 */
public interface VSandConstants extends AbstractConstants
{
	/**
	 * Returns the value of the '<em><b>First Pass</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>First Pass</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>First Pass</em>' attribute.
	 * @see #setFirstPass(boolean)
	 * @see org.sheepy.vulkan.sand.model.VSandPackage#getVSandConstants_FirstPass()
	 * @model default="true" unique="false"
	 * @generated
	 */
	boolean isFirstPass();

	/**
	 * Sets the value of the '{@link org.sheepy.vulkan.sand.model.VSandConstants#isFirstPass <em>First Pass</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>First Pass</em>' attribute.
	 * @see #isFirstPass()
	 * @generated
	 */
	void setFirstPass(boolean value);

} // VSandConstants
