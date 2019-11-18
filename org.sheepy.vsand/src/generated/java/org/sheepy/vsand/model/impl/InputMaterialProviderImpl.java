/**
 */
package org.sheepy.vsand.model.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.sheepy.lily.core.api.adapter.LilyEObject;

import org.sheepy.vsand.model.InputMaterialProvider;
import org.sheepy.vsand.model.Materials;
import org.sheepy.vsand.model.VSandPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Input Material Provider</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.sheepy.vsand.model.impl.InputMaterialProviderImpl#getMaterials <em>Materials</em>}</li>
 * </ul>
 *
 * @generated
 */
public class InputMaterialProviderImpl extends LilyEObject implements InputMaterialProvider
{
	/**
	 * The cached value of the '{@link #getMaterials() <em>Materials</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaterials()
	 * @generated
	 * @ordered
	 */
	protected Materials materials;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected InputMaterialProviderImpl()
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
		return VSandPackage.Literals.INPUT_MATERIAL_PROVIDER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Materials getMaterials()
	{
		if (materials != null && ((EObject)materials).eIsProxy())
		{
			InternalEObject oldMaterials = (InternalEObject)materials;
			materials = (Materials)eResolveProxy(oldMaterials);
			if (materials != oldMaterials)
			{
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, VSandPackage.INPUT_MATERIAL_PROVIDER__MATERIALS, oldMaterials, materials));
			}
		}
		return materials;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Materials basicGetMaterials()
	{
		return materials;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMaterials(Materials newMaterials)
	{
		Materials oldMaterials = materials;
		materials = newMaterials;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, VSandPackage.INPUT_MATERIAL_PROVIDER__MATERIALS, oldMaterials, materials));
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
			case VSandPackage.INPUT_MATERIAL_PROVIDER__MATERIALS:
				if (resolve) return getMaterials();
				return basicGetMaterials();
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
			case VSandPackage.INPUT_MATERIAL_PROVIDER__MATERIALS:
				setMaterials((Materials)newValue);
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
			case VSandPackage.INPUT_MATERIAL_PROVIDER__MATERIALS:
				setMaterials((Materials)null);
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
			case VSandPackage.INPUT_MATERIAL_PROVIDER__MATERIALS:
				return materials != null;
		}
		return super.eIsSet(featureID);
	}

} //InputMaterialProviderImpl
