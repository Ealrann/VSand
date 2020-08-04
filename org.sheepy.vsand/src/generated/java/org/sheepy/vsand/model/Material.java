/**
 */
package org.sheepy.vsand.model;

import org.sheepy.lily.core.api.model.ILilyEObject;
import org.sheepy.lily.core.model.resource.Sound;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Material</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sheepy.vsand.model.Material#getName <em>Name</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.Material#isIsStatic <em>Is Static</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.Material#getDensity <em>Density</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.Material#getRunoff <em>Runoff</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.Material#getR <em>R</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.Material#getG <em>G</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.Material#getB <em>B</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.Material#isUserFriendly <em>User Friendly</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.Material#getPaintSound <em>Paint Sound</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.Material#getPitch <em>Pitch</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.Material#getType <em>Type</em>}</li>
 * </ul>
 *
 * @see org.sheepy.vsand.model.VSandPackage#getMaterial()
 * @model
 * @extends ILilyEObject
 * @generated
 */
public interface Material extends ILilyEObject
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
	 * @see org.sheepy.vsand.model.VSandPackage#getMaterial_Name()
	 * @model unique="false"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.sheepy.vsand.model.Material#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Is Static</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Is Static</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Static</em>' attribute.
	 * @see #setIsStatic(boolean)
	 * @see org.sheepy.vsand.model.VSandPackage#getMaterial_IsStatic()
	 * @model unique="false"
	 * @generated
	 */
	boolean isIsStatic();

	/**
	 * Sets the value of the '{@link org.sheepy.vsand.model.Material#isIsStatic <em>Is Static</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Static</em>' attribute.
	 * @see #isIsStatic()
	 * @generated
	 */
	void setIsStatic(boolean value);

	/**
	 * Returns the value of the '<em><b>Density</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Density</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Density</em>' attribute.
	 * @see #setDensity(float)
	 * @see org.sheepy.vsand.model.VSandPackage#getMaterial_Density()
	 * @model unique="false"
	 * @generated
	 */
	float getDensity();

	/**
	 * Sets the value of the '{@link org.sheepy.vsand.model.Material#getDensity <em>Density</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Density</em>' attribute.
	 * @see #getDensity()
	 * @generated
	 */
	void setDensity(float value);

	/**
	 * Returns the value of the '<em><b>Runoff</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Runoff</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Runoff</em>' attribute.
	 * @see #setRunoff(int)
	 * @see org.sheepy.vsand.model.VSandPackage#getMaterial_Runoff()
	 * @model unique="false"
	 * @generated
	 */
	int getRunoff();

	/**
	 * Sets the value of the '{@link org.sheepy.vsand.model.Material#getRunoff <em>Runoff</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Runoff</em>' attribute.
	 * @see #getRunoff()
	 * @generated
	 */
	void setRunoff(int value);

	/**
	 * Returns the value of the '<em><b>R</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>R</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>R</em>' attribute.
	 * @see #setR(int)
	 * @see org.sheepy.vsand.model.VSandPackage#getMaterial_R()
	 * @model default="0" unique="false"
	 * @generated
	 */
	int getR();

	/**
	 * Sets the value of the '{@link org.sheepy.vsand.model.Material#getR <em>R</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>R</em>' attribute.
	 * @see #getR()
	 * @generated
	 */
	void setR(int value);

	/**
	 * Returns the value of the '<em><b>G</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>G</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>G</em>' attribute.
	 * @see #setG(int)
	 * @see org.sheepy.vsand.model.VSandPackage#getMaterial_G()
	 * @model default="0" unique="false"
	 * @generated
	 */
	int getG();

	/**
	 * Sets the value of the '{@link org.sheepy.vsand.model.Material#getG <em>G</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>G</em>' attribute.
	 * @see #getG()
	 * @generated
	 */
	void setG(int value);

	/**
	 * Returns the value of the '<em><b>B</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>B</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>B</em>' attribute.
	 * @see #setB(int)
	 * @see org.sheepy.vsand.model.VSandPackage#getMaterial_B()
	 * @model default="0" unique="false"
	 * @generated
	 */
	int getB();

	/**
	 * Sets the value of the '{@link org.sheepy.vsand.model.Material#getB <em>B</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>B</em>' attribute.
	 * @see #getB()
	 * @generated
	 */
	void setB(int value);

	/**
	 * Returns the value of the '<em><b>User Friendly</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>User Friendly</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>User Friendly</em>' attribute.
	 * @see #setUserFriendly(boolean)
	 * @see org.sheepy.vsand.model.VSandPackage#getMaterial_UserFriendly()
	 * @model default="true" unique="false"
	 * @generated
	 */
	boolean isUserFriendly();

	/**
	 * Sets the value of the '{@link org.sheepy.vsand.model.Material#isUserFriendly <em>User Friendly</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>User Friendly</em>' attribute.
	 * @see #isUserFriendly()
	 * @generated
	 */
	void setUserFriendly(boolean value);

	/**
	 * Returns the value of the '<em><b>Paint Sound</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Paint Sound</em>' reference.
	 * @see #setPaintSound(Sound)
	 * @see org.sheepy.vsand.model.VSandPackage#getMaterial_PaintSound()
	 * @model
	 * @generated
	 */
	Sound getPaintSound();

	/**
	 * Sets the value of the '{@link org.sheepy.vsand.model.Material#getPaintSound <em>Paint Sound</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Paint Sound</em>' reference.
	 * @see #getPaintSound()
	 * @generated
	 */
	void setPaintSound(Sound value);

	/**
	 * Returns the value of the '<em><b>Pitch</b></em>' attribute.
	 * The default value is <code>"1"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pitch</em>' attribute.
	 * @see #setPitch(float)
	 * @see org.sheepy.vsand.model.VSandPackage#getMaterial_Pitch()
	 * @model default="1" required="true"
	 * @generated
	 */
	float getPitch();

	/**
	 * Sets the value of the '{@link org.sheepy.vsand.model.Material#getPitch <em>Pitch</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pitch</em>' attribute.
	 * @see #getPitch()
	 * @generated
	 */
	void setPitch(float value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The default value is <code>"Solid"</code>.
	 * The literals are from the enumeration {@link org.sheepy.vsand.model.EMaterialType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see org.sheepy.vsand.model.EMaterialType
	 * @see #setType(EMaterialType)
	 * @see org.sheepy.vsand.model.VSandPackage#getMaterial_Type()
	 * @model default="Solid"
	 * @generated
	 */
	EMaterialType getType();

	/**
	 * Sets the value of the '{@link org.sheepy.vsand.model.Material#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see org.sheepy.vsand.model.EMaterialType
	 * @see #getType()
	 * @generated
	 */
	void setType(EMaterialType value);

} // Material
