/**
 */
package org.sheepy.vsand.model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Transformation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sheepy.vsand.model.Transformation#getName <em>Name</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.Transformation#getReactant <em>Reactant</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.Transformation#getCatalyst <em>Catalyst</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.Transformation#getTarget <em>Target</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.Transformation#getProbability <em>Probability</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.Transformation#getPropagation <em>Propagation</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.Transformation#isIsStaticTransformation <em>Is Static Transformation</em>}</li>
 * </ul>
 *
 * @see org.sheepy.vsand.model.VSandPackage#getTransformation()
 * @model
 * @generated
 */
public interface Transformation extends EObject
{
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.sheepy.vsand.model.VSandPackage#getTransformation_Name()
	 * @model unique="false"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.sheepy.vsand.model.Transformation#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

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

	/**
	 * Returns the value of the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' reference.
	 * @see #setTarget(Material)
	 * @see org.sheepy.vsand.model.VSandPackage#getTransformation_Target()
	 * @model
	 * @generated
	 */
	Material getTarget();

	/**
	 * Sets the value of the '{@link org.sheepy.vsand.model.Transformation#getTarget <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(Material value);

	/**
	 * Returns the value of the '<em><b>Probability</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Probability</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Probability</em>' attribute.
	 * @see #setProbability(int)
	 * @see org.sheepy.vsand.model.VSandPackage#getTransformation_Probability()
	 * @model unique="false"
	 * @generated
	 */
	int getProbability();

	/**
	 * Sets the value of the '{@link org.sheepy.vsand.model.Transformation#getProbability <em>Probability</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Probability</em>' attribute.
	 * @see #getProbability()
	 * @generated
	 */
	void setProbability(int value);

	/**
	 * Returns the value of the '<em><b>Propagation</b></em>' attribute.
	 * The default value is <code>"1"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Propagation</em>' attribute.
	 * @see #setPropagation(int)
	 * @see org.sheepy.vsand.model.VSandPackage#getTransformation_Propagation()
	 * @model default="1" unique="false"
	 * @generated
	 */
	int getPropagation();

	/**
	 * Sets the value of the '{@link org.sheepy.vsand.model.Transformation#getPropagation <em>Propagation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Propagation</em>' attribute.
	 * @see #getPropagation()
	 * @generated
	 */
	void setPropagation(int value);

	/**
	 * Returns the value of the '<em><b>Is Static Transformation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Is Static Transformation</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Static Transformation</em>' attribute.
	 * @see #setIsStaticTransformation(boolean)
	 * @see org.sheepy.vsand.model.VSandPackage#getTransformation_IsStaticTransformation()
	 * @model unique="false"
	 * @generated
	 */
	boolean isIsStaticTransformation();

	/**
	 * Sets the value of the '{@link org.sheepy.vsand.model.Transformation#isIsStaticTransformation <em>Is Static Transformation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Static Transformation</em>' attribute.
	 * @see #isIsStaticTransformation()
	 * @generated
	 */
	void setIsStaticTransformation(boolean value);

} // Transformation
