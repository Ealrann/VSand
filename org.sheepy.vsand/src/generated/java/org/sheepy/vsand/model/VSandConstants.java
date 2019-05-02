/**
 */
package org.sheepy.vsand.model;

import org.sheepy.lily.vulkan.model.resource.AbstractConstants;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Constants</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sheepy.vsand.model.VSandConstants#isForceClear <em>Force Clear</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.VSandConstants#isShowSleepZones <em>Show Sleep Zones</em>}</li>
 * </ul>
 *
 * @see org.sheepy.vsand.model.VSandPackage#getVSandConstants()
 * @model
 * @generated
 */
public interface VSandConstants extends AbstractConstants
{
	/**
	 * Returns the value of the '<em><b>Force Clear</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Force Clear</em>' attribute.
	 * @see #setForceClear(boolean)
	 * @see org.sheepy.vsand.model.VSandPackage#getVSandConstants_ForceClear()
	 * @model default="false" unique="false"
	 * @generated
	 */
	boolean isForceClear();

	/**
	 * Sets the value of the '{@link org.sheepy.vsand.model.VSandConstants#isForceClear <em>Force Clear</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Force Clear</em>' attribute.
	 * @see #isForceClear()
	 * @generated
	 */
	void setForceClear(boolean value);

	/**
	 * Returns the value of the '<em><b>Show Sleep Zones</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Show Sleep Zones</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Show Sleep Zones</em>' attribute.
	 * @see #setShowSleepZones(boolean)
	 * @see org.sheepy.vsand.model.VSandPackage#getVSandConstants_ShowSleepZones()
	 * @model default="false" unique="false"
	 * @generated
	 */
	boolean isShowSleepZones();

	/**
	 * Sets the value of the '{@link org.sheepy.vsand.model.VSandConstants#isShowSleepZones <em>Show Sleep Zones</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Show Sleep Zones</em>' attribute.
	 * @see #isShowSleepZones()
	 * @generated
	 */
	void setShowSleepZones(boolean value);

} // VSandConstants
