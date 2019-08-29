/**
 */
package org.sheepy.vsand.model;

import org.sheepy.lily.core.api.adapter.ILilyEObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>ITransformation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sheepy.vsand.model.ITransformation#getProbability <em>Probability</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.ITransformation#getPropagation <em>Propagation</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.ITransformation#isIsStaticTransformation <em>Is Static Transformation</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.ITransformation#getTarget <em>Target</em>}</li>
 * </ul>
 *
 * @see org.sheepy.vsand.model.VSandPackage#getITransformation()
 * @model interface="true" abstract="true"
 * @extends ILilyEObject
 * @generated
 */
public interface ITransformation extends ILilyEObject
{

	/**
	 * Returns the value of the '<em><b>Probability</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Probability</em>' attribute.
	 * @see #setProbability(int)
	 * @see org.sheepy.vsand.model.VSandPackage#getITransformation_Probability()
	 * @model unique="false"
	 * @generated
	 */
	int getProbability();

	/**
	 * Sets the value of the '{@link org.sheepy.vsand.model.ITransformation#getProbability <em>Probability</em>}' attribute.
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
	 * @see org.sheepy.vsand.model.VSandPackage#getITransformation_Propagation()
	 * @model default="1" unique="false"
	 * @generated
	 */
	int getPropagation();

	/**
	 * Sets the value of the '{@link org.sheepy.vsand.model.ITransformation#getPropagation <em>Propagation</em>}' attribute.
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
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Static Transformation</em>' attribute.
	 * @see #setIsStaticTransformation(boolean)
	 * @see org.sheepy.vsand.model.VSandPackage#getITransformation_IsStaticTransformation()
	 * @model unique="false"
	 * @generated
	 */
	boolean isIsStaticTransformation();

	/**
	 * Sets the value of the '{@link org.sheepy.vsand.model.ITransformation#isIsStaticTransformation <em>Is Static Transformation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Static Transformation</em>' attribute.
	 * @see #isIsStaticTransformation()
	 * @generated
	 */
	void setIsStaticTransformation(boolean value);

	/**
	 * Returns the value of the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' reference.
	 * @see #setTarget(Material)
	 * @see org.sheepy.vsand.model.VSandPackage#getITransformation_Target()
	 * @model
	 * @generated
	 */
	Material getTarget();

	/**
	 * Sets the value of the '{@link org.sheepy.vsand.model.ITransformation#getTarget <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(Material value);
} // ITransformation
