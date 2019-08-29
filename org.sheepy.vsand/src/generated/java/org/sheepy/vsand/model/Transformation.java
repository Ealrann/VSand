/**
 */
package org.sheepy.vsand.model;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Transformation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sheepy.vsand.model.Transformation#getReactant <em>Reactant</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.Transformation#getCatalyst <em>Catalyst</em>}</li>
 * </ul>
 *
 * @see org.sheepy.vsand.model.VSandPackage#getTransformation()
 * @model
 * @generated
 */
public interface Transformation extends ITransformation
{
	/**
	 * Returns the value of the '<em><b>Reactant</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Reactant</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Reactant</em>' reference.
	 * @see #setReactant(Material)
	 * @see org.sheepy.vsand.model.VSandPackage#getTransformation_Reactant()
	 * @model
	 * @generated
	 */
	Material getReactant();

	/**
	 * Sets the value of the '{@link org.sheepy.vsand.model.Transformation#getReactant <em>Reactant</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Reactant</em>' reference.
	 * @see #getReactant()
	 * @generated
	 */
	void setReactant(Material value);

	/**
	 * Returns the value of the '<em><b>Catalyst</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Catalyst</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Catalyst</em>' reference.
	 * @see #setCatalyst(Material)
	 * @see org.sheepy.vsand.model.VSandPackage#getTransformation_Catalyst()
	 * @model
	 * @generated
	 */
	Material getCatalyst();

	/**
	 * Sets the value of the '{@link org.sheepy.vsand.model.Transformation#getCatalyst <em>Catalyst</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Catalyst</em>' reference.
	 * @see #getCatalyst()
	 * @generated
	 */
	void setCatalyst(Material value);

} // Transformation
