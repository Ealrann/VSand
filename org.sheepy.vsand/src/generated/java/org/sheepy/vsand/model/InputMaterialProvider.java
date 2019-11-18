/**
 */
package org.sheepy.vsand.model;

import org.sheepy.lily.vulkan.extra.model.nuklear.IInputProvider;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Input Material Provider</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sheepy.vsand.model.InputMaterialProvider#getMaterials <em>Materials</em>}</li>
 * </ul>
 *
 * @see org.sheepy.vsand.model.VSandPackage#getInputMaterialProvider()
 * @model
 * @generated
 */
public interface InputMaterialProvider extends IInputProvider
{
	/**
	 * Returns the value of the '<em><b>Materials</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Materials</em>' reference.
	 * @see #setMaterials(Materials)
	 * @see org.sheepy.vsand.model.VSandPackage#getInputMaterialProvider_Materials()
	 * @model required="true"
	 * @generated
	 */
	Materials getMaterials();

	/**
	 * Sets the value of the '{@link org.sheepy.vsand.model.InputMaterialProvider#getMaterials <em>Materials</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Materials</em>' reference.
	 * @see #getMaterials()
	 * @generated
	 */
	void setMaterials(Materials value);

} // InputMaterialProvider
