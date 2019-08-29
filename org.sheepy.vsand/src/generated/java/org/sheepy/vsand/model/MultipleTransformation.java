/**
 */
package org.sheepy.vsand.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Multiple Transformation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sheepy.vsand.model.MultipleTransformation#getReactants <em>Reactants</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.MultipleTransformation#getCatalysts <em>Catalysts</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.MultipleTransformation#getName <em>Name</em>}</li>
 * </ul>
 *
 * @see org.sheepy.vsand.model.VSandPackage#getMultipleTransformation()
 * @model
 * @generated
 */
public interface MultipleTransformation extends ITransformation
{
	/**
	 * Returns the value of the '<em><b>Reactants</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Reactants</em>' containment reference.
	 * @see #setReactants(MaterialProvider)
	 * @see org.sheepy.vsand.model.VSandPackage#getMultipleTransformation_Reactants()
	 * @model containment="true" required="true"
	 * @generated
	 */
	MaterialProvider getReactants();

	/**
	 * Sets the value of the '{@link org.sheepy.vsand.model.MultipleTransformation#getReactants <em>Reactants</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Reactants</em>' containment reference.
	 * @see #getReactants()
	 * @generated
	 */
	void setReactants(MaterialProvider value);

	/**
	 * Returns the value of the '<em><b>Catalysts</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Catalysts</em>' containment reference.
	 * @see #setCatalysts(MaterialProvider)
	 * @see org.sheepy.vsand.model.VSandPackage#getMultipleTransformation_Catalysts()
	 * @model containment="true" required="true"
	 * @generated
	 */
	MaterialProvider getCatalysts();

	/**
	 * Sets the value of the '{@link org.sheepy.vsand.model.MultipleTransformation#getCatalysts <em>Catalysts</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Catalysts</em>' containment reference.
	 * @see #getCatalysts()
	 * @generated
	 */
	void setCatalysts(MaterialProvider value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.sheepy.vsand.model.VSandPackage#getMultipleTransformation_Name()
	 * @model default=""
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.sheepy.vsand.model.MultipleTransformation#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

} // MultipleTransformation
