/**
 */
package org.sheepy.vsand.model.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import org.sheepy.lily.core.api.adapter.LilyEObject;

import org.sheepy.vsand.model.Material;
import org.sheepy.vsand.model.MaterialProvider;
import org.sheepy.vsand.model.VSandPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Material Provider</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.sheepy.vsand.model.impl.MaterialProviderImpl#getMaterials <em>Materials</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.impl.MaterialProviderImpl#isFilterMode <em>Filter Mode</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MaterialProviderImpl extends LilyEObject implements MaterialProvider
{
	/**
	 * The cached value of the '{@link #getMaterials() <em>Materials</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaterials()
	 * @generated
	 * @ordered
	 */
	protected EList<Material> materials;

	/**
	 * The default value of the '{@link #isFilterMode() <em>Filter Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isFilterMode()
	 * @generated
	 * @ordered
	 */
	protected static final boolean FILTER_MODE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isFilterMode() <em>Filter Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isFilterMode()
	 * @generated
	 * @ordered
	 */
	protected boolean filterMode = FILTER_MODE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MaterialProviderImpl()
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
		return VSandPackage.Literals.MATERIAL_PROVIDER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Material> getMaterials()
	{
		if (materials == null)
		{
			materials = new EObjectResolvingEList<Material>(Material.class, this, VSandPackage.MATERIAL_PROVIDER__MATERIALS);
		}
		return materials;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isFilterMode()
	{
		return filterMode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setFilterMode(boolean newFilterMode)
	{
		boolean oldFilterMode = filterMode;
		filterMode = newFilterMode;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, VSandPackage.MATERIAL_PROVIDER__FILTER_MODE, oldFilterMode, filterMode));
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
			case VSandPackage.MATERIAL_PROVIDER__MATERIALS:
				return getMaterials();
			case VSandPackage.MATERIAL_PROVIDER__FILTER_MODE:
				return isFilterMode();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue)
	{
		switch (featureID)
		{
			case VSandPackage.MATERIAL_PROVIDER__MATERIALS:
				getMaterials().clear();
				getMaterials().addAll((Collection<? extends Material>)newValue);
				return;
			case VSandPackage.MATERIAL_PROVIDER__FILTER_MODE:
				setFilterMode((Boolean)newValue);
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
			case VSandPackage.MATERIAL_PROVIDER__MATERIALS:
				getMaterials().clear();
				return;
			case VSandPackage.MATERIAL_PROVIDER__FILTER_MODE:
				setFilterMode(FILTER_MODE_EDEFAULT);
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
			case VSandPackage.MATERIAL_PROVIDER__MATERIALS:
				return materials != null && !materials.isEmpty();
			case VSandPackage.MATERIAL_PROVIDER__FILTER_MODE:
				return filterMode != FILTER_MODE_EDEFAULT;
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
		result.append(" (filterMode: ");
		result.append(filterMode);
		result.append(')');
		return result.toString();
	}

} //MaterialProviderImpl
