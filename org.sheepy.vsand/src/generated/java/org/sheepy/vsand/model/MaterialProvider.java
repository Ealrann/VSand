/**
 */
package org.sheepy.vsand.model;

import org.eclipse.emf.common.util.EList;

import org.sheepy.lily.core.api.adapter.ILilyEObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Material Provider</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sheepy.vsand.model.MaterialProvider#getMaterials <em>Materials</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.MaterialProvider#isFilterMode <em>Filter Mode</em>}</li>
 * </ul>
 *
 * @see org.sheepy.vsand.model.VSandPackage#getMaterialProvider()
 * @model
 * @extends ILilyEObject
 * @generated
 */
public interface MaterialProvider extends ILilyEObject
{
	/**
	 * Returns the value of the '<em><b>Materials</b></em>' reference list.
	 * The list contents are of type {@link org.sheepy.vsand.model.Material}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Materials</em>' reference list.
	 * @see org.sheepy.vsand.model.VSandPackage#getMaterialProvider_Materials()
	 * @model
	 * @generated
	 */
	EList<Material> getMaterials();

	/**
	 * Returns the value of the '<em><b>Filter Mode</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Filter Mode</em>' attribute.
	 * @see #setFilterMode(boolean)
	 * @see org.sheepy.vsand.model.VSandPackage#getMaterialProvider_FilterMode()
	 * @model default="false" required="true"
	 * @generated
	 */
	boolean isFilterMode();

	/**
	 * Sets the value of the '{@link org.sheepy.vsand.model.MaterialProvider#isFilterMode <em>Filter Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Filter Mode</em>' attribute.
	 * @see #isFilterMode()
	 * @generated
	 */
	void setFilterMode(boolean value);

} // MaterialProvider
