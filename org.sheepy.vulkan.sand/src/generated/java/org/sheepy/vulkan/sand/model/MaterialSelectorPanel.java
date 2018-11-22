/**
 */
package org.sheepy.vulkan.sand.model;

import org.sheepy.common.model.presentation.IPanel;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Material Selector Panel</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sheepy.vulkan.sand.model.MaterialSelectorPanel#getLineHeight <em>Line Height</em>}</li>
 *   <li>{@link org.sheepy.vulkan.sand.model.MaterialSelectorPanel#getPrimaryR <em>Primary R</em>}</li>
 *   <li>{@link org.sheepy.vulkan.sand.model.MaterialSelectorPanel#getPrimaryG <em>Primary G</em>}</li>
 *   <li>{@link org.sheepy.vulkan.sand.model.MaterialSelectorPanel#getPrimaryB <em>Primary B</em>}</li>
 *   <li>{@link org.sheepy.vulkan.sand.model.MaterialSelectorPanel#getSecondaryR <em>Secondary R</em>}</li>
 *   <li>{@link org.sheepy.vulkan.sand.model.MaterialSelectorPanel#getSecondaryG <em>Secondary G</em>}</li>
 *   <li>{@link org.sheepy.vulkan.sand.model.MaterialSelectorPanel#getSecondaryB <em>Secondary B</em>}</li>
 * </ul>
 *
 * @see org.sheepy.vulkan.sand.model.VSandPackage#getMaterialSelectorPanel()
 * @model
 * @generated
 */
public interface MaterialSelectorPanel extends IPanel
{
	/**
	 * Returns the value of the '<em><b>Line Height</b></em>' attribute.
	 * The default value is <code>"32"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Line Height</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Line Height</em>' attribute.
	 * @see #setLineHeight(int)
	 * @see org.sheepy.vulkan.sand.model.VSandPackage#getMaterialSelectorPanel_LineHeight()
	 * @model default="32" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel propertyCategory='UI'"
	 * @generated
	 */
	int getLineHeight();

	/**
	 * Sets the value of the '{@link org.sheepy.vulkan.sand.model.MaterialSelectorPanel#getLineHeight <em>Line Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Line Height</em>' attribute.
	 * @see #getLineHeight()
	 * @generated
	 */
	void setLineHeight(int value);

	/**
	 * Returns the value of the '<em><b>Primary R</b></em>' attribute.
	 * The default value is <code>"255"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Primary R</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Primary R</em>' attribute.
	 * @see #setPrimaryR(int)
	 * @see org.sheepy.vulkan.sand.model.VSandPackage#getMaterialSelectorPanel_PrimaryR()
	 * @model default="255" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel propertyCategory='Primary Color'"
	 * @generated
	 */
	int getPrimaryR();

	/**
	 * Sets the value of the '{@link org.sheepy.vulkan.sand.model.MaterialSelectorPanel#getPrimaryR <em>Primary R</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Primary R</em>' attribute.
	 * @see #getPrimaryR()
	 * @generated
	 */
	void setPrimaryR(int value);

	/**
	 * Returns the value of the '<em><b>Primary G</b></em>' attribute.
	 * The default value is <code>"50"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Primary G</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Primary G</em>' attribute.
	 * @see #setPrimaryG(int)
	 * @see org.sheepy.vulkan.sand.model.VSandPackage#getMaterialSelectorPanel_PrimaryG()
	 * @model default="50" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel propertyCategory='Primary Color'"
	 * @generated
	 */
	int getPrimaryG();

	/**
	 * Sets the value of the '{@link org.sheepy.vulkan.sand.model.MaterialSelectorPanel#getPrimaryG <em>Primary G</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Primary G</em>' attribute.
	 * @see #getPrimaryG()
	 * @generated
	 */
	void setPrimaryG(int value);

	/**
	 * Returns the value of the '<em><b>Primary B</b></em>' attribute.
	 * The default value is <code>"50"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Primary B</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Primary B</em>' attribute.
	 * @see #setPrimaryB(int)
	 * @see org.sheepy.vulkan.sand.model.VSandPackage#getMaterialSelectorPanel_PrimaryB()
	 * @model default="50" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel propertyCategory='Primary Color'"
	 * @generated
	 */
	int getPrimaryB();

	/**
	 * Sets the value of the '{@link org.sheepy.vulkan.sand.model.MaterialSelectorPanel#getPrimaryB <em>Primary B</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Primary B</em>' attribute.
	 * @see #getPrimaryB()
	 * @generated
	 */
	void setPrimaryB(int value);

	/**
	 * Returns the value of the '<em><b>Secondary R</b></em>' attribute.
	 * The default value is <code>"50"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Secondary R</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Secondary R</em>' attribute.
	 * @see #setSecondaryR(int)
	 * @see org.sheepy.vulkan.sand.model.VSandPackage#getMaterialSelectorPanel_SecondaryR()
	 * @model default="50" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel propertyCategory='Secondary Color'"
	 * @generated
	 */
	int getSecondaryR();

	/**
	 * Sets the value of the '{@link org.sheepy.vulkan.sand.model.MaterialSelectorPanel#getSecondaryR <em>Secondary R</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Secondary R</em>' attribute.
	 * @see #getSecondaryR()
	 * @generated
	 */
	void setSecondaryR(int value);

	/**
	 * Returns the value of the '<em><b>Secondary G</b></em>' attribute.
	 * The default value is <code>"50"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Secondary G</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Secondary G</em>' attribute.
	 * @see #setSecondaryG(int)
	 * @see org.sheepy.vulkan.sand.model.VSandPackage#getMaterialSelectorPanel_SecondaryG()
	 * @model default="50" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel propertyCategory='Secondary Color'"
	 * @generated
	 */
	int getSecondaryG();

	/**
	 * Sets the value of the '{@link org.sheepy.vulkan.sand.model.MaterialSelectorPanel#getSecondaryG <em>Secondary G</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Secondary G</em>' attribute.
	 * @see #getSecondaryG()
	 * @generated
	 */
	void setSecondaryG(int value);

	/**
	 * Returns the value of the '<em><b>Secondary B</b></em>' attribute.
	 * The default value is <code>"255"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Secondary B</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Secondary B</em>' attribute.
	 * @see #setSecondaryB(int)
	 * @see org.sheepy.vulkan.sand.model.VSandPackage#getMaterialSelectorPanel_SecondaryB()
	 * @model default="255" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel propertyCategory='Secondary Color'"
	 * @generated
	 */
	int getSecondaryB();

	/**
	 * Sets the value of the '{@link org.sheepy.vulkan.sand.model.MaterialSelectorPanel#getSecondaryB <em>Secondary B</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Secondary B</em>' attribute.
	 * @see #getSecondaryB()
	 * @generated
	 */
	void setSecondaryB(int value);

} // MaterialSelectorPanel
