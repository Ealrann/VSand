/**
 */
package org.sheepy.vsand.model.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.sheepy.lily.vulkan.model.resource.impl.AbstractConstantsImpl;
import org.sheepy.vsand.model.VSandConstants;
import org.sheepy.vsand.model.VSandPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Constants</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.sheepy.vsand.model.impl.VSandConstantsImpl#isFirstPass <em>First Pass</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.impl.VSandConstantsImpl#isShowSleepZones <em>Show Sleep Zones</em>}</li>
 * </ul>
 *
 * @generated
 */
public class VSandConstantsImpl extends AbstractConstantsImpl implements VSandConstants
{
	/**
	 * The default value of the '{@link #isFirstPass() <em>First Pass</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isFirstPass()
	 * @generated
	 * @ordered
	 */
	protected static final boolean FIRST_PASS_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isFirstPass() <em>First Pass</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isFirstPass()
	 * @generated
	 * @ordered
	 */
	protected boolean firstPass = FIRST_PASS_EDEFAULT;

	/**
	 * The default value of the '{@link #isShowSleepZones() <em>Show Sleep Zones</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isShowSleepZones()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SHOW_SLEEP_ZONES_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isShowSleepZones() <em>Show Sleep Zones</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isShowSleepZones()
	 * @generated
	 * @ordered
	 */
	protected boolean showSleepZones = SHOW_SLEEP_ZONES_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VSandConstantsImpl()
	{
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass()
	{
		return VSandPackage.Literals.VSAND_CONSTANTS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isFirstPass()
	{
		return firstPass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setFirstPass(boolean newFirstPass)
	{
		boolean oldFirstPass = firstPass;
		firstPass = newFirstPass;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, VSandPackage.VSAND_CONSTANTS__FIRST_PASS, oldFirstPass, firstPass));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isShowSleepZones()
	{
		return showSleepZones;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setShowSleepZones(boolean newShowSleepZones)
	{
		boolean oldShowSleepZones = showSleepZones;
		showSleepZones = newShowSleepZones;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, VSandPackage.VSAND_CONSTANTS__SHOW_SLEEP_ZONES, oldShowSleepZones, showSleepZones));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType)
	{
		switch (featureID)
		{
			case VSandPackage.VSAND_CONSTANTS__FIRST_PASS:
				return isFirstPass();
			case VSandPackage.VSAND_CONSTANTS__SHOW_SLEEP_ZONES:
				return isShowSleepZones();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue)
	{
		switch (featureID)
		{
			case VSandPackage.VSAND_CONSTANTS__FIRST_PASS:
				setFirstPass((Boolean)newValue);
				return;
			case VSandPackage.VSAND_CONSTANTS__SHOW_SLEEP_ZONES:
				setShowSleepZones((Boolean)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID)
	{
		switch (featureID)
		{
			case VSandPackage.VSAND_CONSTANTS__FIRST_PASS:
				setFirstPass(FIRST_PASS_EDEFAULT);
				return;
			case VSandPackage.VSAND_CONSTANTS__SHOW_SLEEP_ZONES:
				setShowSleepZones(SHOW_SLEEP_ZONES_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID)
	{
		switch (featureID)
		{
			case VSandPackage.VSAND_CONSTANTS__FIRST_PASS:
				return firstPass != FIRST_PASS_EDEFAULT;
			case VSandPackage.VSAND_CONSTANTS__SHOW_SLEEP_ZONES:
				return showSleepZones != SHOW_SLEEP_ZONES_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString()
	{
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (firstPass: ");
		result.append(firstPass);
		result.append(", showSleepZones: ");
		result.append(showSleepZones);
		result.append(')');
		return result.toString();
	}

} //VSandConstantsImpl
