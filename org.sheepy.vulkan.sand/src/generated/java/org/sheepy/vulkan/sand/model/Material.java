/**
 */
package org.sheepy.vulkan.sand.model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Material</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sheepy.vulkan.sand.model.Material#getName <em>Name</em>}</li>
 *   <li>{@link org.sheepy.vulkan.sand.model.Material#isIsStatic <em>Is Static</em>}</li>
 *   <li>{@link org.sheepy.vulkan.sand.model.Material#getDensity <em>Density</em>}</li>
 *   <li>{@link org.sheepy.vulkan.sand.model.Material#getRunoff <em>Runoff</em>}</li>
 *   <li>{@link org.sheepy.vulkan.sand.model.Material#getR <em>R</em>}</li>
 *   <li>{@link org.sheepy.vulkan.sand.model.Material#getG <em>G</em>}</li>
 *   <li>{@link org.sheepy.vulkan.sand.model.Material#getB <em>B</em>}</li>
 *   <li>{@link org.sheepy.vulkan.sand.model.Material#isUserFriendly <em>User Friendly</em>}</li>
 * </ul>
 *
 * @see org.sheepy.vulkan.sand.model.VSandPackage#getMaterial()
 * @model
 * @generated
 */
public interface Material extends EObject
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
	 * @see org.sheepy.vulkan.sand.model.VSandPackage#getMaterial_Name()
	 * @model unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel propertyCategory='Basic'"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.sheepy.vulkan.sand.model.Material#getName <em>Name</em>}' attribute.
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
	 * @see org.sheepy.vulkan.sand.model.VSandPackage#getMaterial_IsStatic()
	 * @model unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel propertyCategory='Basic'"
	 * @generated
	 */
	boolean isIsStatic();

	/**
	 * Sets the value of the '{@link org.sheepy.vulkan.sand.model.Material#isIsStatic <em>Is Static</em>}' attribute.
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
	 * @see #setDensity(int)
	 * @see org.sheepy.vulkan.sand.model.VSandPackage#getMaterial_Density()
	 * @model unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel propertyCategory='Basic'"
	 * @generated
	 */
	int getDensity();

	/**
	 * Sets the value of the '{@link org.sheepy.vulkan.sand.model.Material#getDensity <em>Density</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Density</em>' attribute.
	 * @see #getDensity()
	 * @generated
	 */
	void setDensity(int value);

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
	 * @see org.sheepy.vulkan.sand.model.VSandPackage#getMaterial_Runoff()
	 * @model unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel propertyCategory='Basic'"
	 * @generated
	 */
	int getRunoff();

	/**
	 * Sets the value of the '{@link org.sheepy.vulkan.sand.model.Material#getRunoff <em>Runoff</em>}' attribute.
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
	 * @see org.sheepy.vulkan.sand.model.VSandPackage#getMaterial_R()
	 * @model default="0" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel propertyCategory='Color'"
	 * @generated
	 */
	int getR();

	/**
	 * Sets the value of the '{@link org.sheepy.vulkan.sand.model.Material#getR <em>R</em>}' attribute.
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
	 * @see org.sheepy.vulkan.sand.model.VSandPackage#getMaterial_G()
	 * @model default="0" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel propertyCategory='Color'"
	 * @generated
	 */
	int getG();

	/**
	 * Sets the value of the '{@link org.sheepy.vulkan.sand.model.Material#getG <em>G</em>}' attribute.
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
	 * @see org.sheepy.vulkan.sand.model.VSandPackage#getMaterial_B()
	 * @model default="0" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel propertyCategory='Color'"
	 * @generated
	 */
	int getB();

	/**
	 * Sets the value of the '{@link org.sheepy.vulkan.sand.model.Material#getB <em>B</em>}' attribute.
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
	 * @see org.sheepy.vulkan.sand.model.VSandPackage#getMaterial_UserFriendly()
	 * @model default="true" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel propertyCategory='UI'"
	 * @generated
	 */
	boolean isUserFriendly();

	/**
	 * Sets the value of the '{@link org.sheepy.vulkan.sand.model.Material#isUserFriendly <em>User Friendly</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>User Friendly</em>' attribute.
	 * @see #isUserFriendly()
	 * @generated
	 */
	void setUserFriendly(boolean value);

} // Material