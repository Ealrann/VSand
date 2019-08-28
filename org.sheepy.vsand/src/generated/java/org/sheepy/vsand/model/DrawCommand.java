/**
 */
package org.sheepy.vsand.model;

import org.sheepy.lily.core.api.adapter.ILilyEObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Draw Command</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sheepy.vsand.model.DrawCommand#getMaterial <em>Material</em>}</li>
 * </ul>
 *
 * @see org.sheepy.vsand.model.VSandPackage#getDrawCommand()
 * @model interface="true" abstract="true"
 * @extends ILilyEObject
 * @generated
 */
public interface DrawCommand extends ILilyEObject
{

	/**
	 * Returns the value of the '<em><b>Material</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Material</em>' reference.
	 * @see #setMaterial(Material)
	 * @see org.sheepy.vsand.model.VSandPackage#getDrawCommand_Material()
	 * @model
	 * @generated
	 */
	Material getMaterial();

	/**
	 * Sets the value of the '{@link org.sheepy.vsand.model.DrawCommand#getMaterial <em>Material</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Material</em>' reference.
	 * @see #getMaterial()
	 * @generated
	 */
	void setMaterial(Material value);
} // DrawCommand
