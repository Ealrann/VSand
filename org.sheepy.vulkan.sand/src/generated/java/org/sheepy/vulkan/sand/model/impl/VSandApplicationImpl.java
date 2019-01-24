/**
 */
package org.sheepy.vulkan.sand.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.sheepy.lily.core.model.application.impl.ApplicationImpl;
import org.sheepy.vulkan.sand.model.Material;
import org.sheepy.vulkan.sand.model.Materials;
import org.sheepy.vulkan.sand.model.Transformations;
import org.sheepy.vulkan.sand.model.VSandApplication;
import org.sheepy.vulkan.sand.model.VSandPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Application</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.sheepy.vulkan.sand.model.impl.VSandApplicationImpl#getMaterials <em>Materials</em>}</li>
 *   <li>{@link org.sheepy.vulkan.sand.model.impl.VSandApplicationImpl#getTransformations <em>Transformations</em>}</li>
 *   <li>{@link org.sheepy.vulkan.sand.model.impl.VSandApplicationImpl#getMainMaterial <em>Main Material</em>}</li>
 *   <li>{@link org.sheepy.vulkan.sand.model.impl.VSandApplicationImpl#getSecondaryMaterial <em>Secondary Material</em>}</li>
 *   <li>{@link org.sheepy.vulkan.sand.model.impl.VSandApplicationImpl#isNextMode <em>Next Mode</em>}</li>
 *   <li>{@link org.sheepy.vulkan.sand.model.impl.VSandApplicationImpl#getBrushSize <em>Brush Size</em>}</li>
 * </ul>
 *
 * @generated
 */
public class VSandApplicationImpl extends ApplicationImpl implements VSandApplication
{
	/**
	 * The cached value of the '{@link #getMaterials() <em>Materials</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaterials()
	 * @generated
	 * @ordered
	 */
	protected Materials materials;

	/**
	 * The cached value of the '{@link #getTransformations() <em>Transformations</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTransformations()
	 * @generated
	 * @ordered
	 */
	protected Transformations transformations;

	/**
	 * The cached value of the '{@link #getMainMaterial() <em>Main Material</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMainMaterial()
	 * @generated
	 * @ordered
	 */
	protected Material mainMaterial;

	/**
	 * The cached value of the '{@link #getSecondaryMaterial() <em>Secondary Material</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSecondaryMaterial()
	 * @generated
	 * @ordered
	 */
	protected Material secondaryMaterial;

	/**
	 * The default value of the '{@link #isNextMode() <em>Next Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isNextMode()
	 * @generated
	 * @ordered
	 */
	protected static final boolean NEXT_MODE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isNextMode() <em>Next Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isNextMode()
	 * @generated
	 * @ordered
	 */
	protected boolean nextMode = NEXT_MODE_EDEFAULT;

	/**
	 * The default value of the '{@link #getBrushSize() <em>Brush Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBrushSize()
	 * @generated
	 * @ordered
	 */
	protected static final int BRUSH_SIZE_EDEFAULT = 4;

	/**
	 * The cached value of the '{@link #getBrushSize() <em>Brush Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBrushSize()
	 * @generated
	 * @ordered
	 */
	protected int brushSize = BRUSH_SIZE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VSandApplicationImpl()
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
		return VSandPackage.Literals.VSAND_APPLICATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Materials getMaterials()
	{
		return materials;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMaterials(Materials newMaterials, NotificationChain msgs)
	{
		Materials oldMaterials = materials;
		materials = newMaterials;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, VSandPackage.VSAND_APPLICATION__MATERIALS, oldMaterials, newMaterials);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMaterials(Materials newMaterials)
	{
		if (newMaterials != materials)
		{
			NotificationChain msgs = null;
			if (materials != null)
				msgs = ((InternalEObject)materials).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - VSandPackage.VSAND_APPLICATION__MATERIALS, null, msgs);
			if (newMaterials != null)
				msgs = ((InternalEObject)newMaterials).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - VSandPackage.VSAND_APPLICATION__MATERIALS, null, msgs);
			msgs = basicSetMaterials(newMaterials, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, VSandPackage.VSAND_APPLICATION__MATERIALS, newMaterials, newMaterials));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Transformations getTransformations()
	{
		return transformations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTransformations(Transformations newTransformations, NotificationChain msgs)
	{
		Transformations oldTransformations = transformations;
		transformations = newTransformations;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, VSandPackage.VSAND_APPLICATION__TRANSFORMATIONS, oldTransformations, newTransformations);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTransformations(Transformations newTransformations)
	{
		if (newTransformations != transformations)
		{
			NotificationChain msgs = null;
			if (transformations != null)
				msgs = ((InternalEObject)transformations).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - VSandPackage.VSAND_APPLICATION__TRANSFORMATIONS, null, msgs);
			if (newTransformations != null)
				msgs = ((InternalEObject)newTransformations).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - VSandPackage.VSAND_APPLICATION__TRANSFORMATIONS, null, msgs);
			msgs = basicSetTransformations(newTransformations, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, VSandPackage.VSAND_APPLICATION__TRANSFORMATIONS, newTransformations, newTransformations));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Material getMainMaterial()
	{
		if (mainMaterial != null && mainMaterial.eIsProxy())
		{
			InternalEObject oldMainMaterial = (InternalEObject)mainMaterial;
			mainMaterial = (Material)eResolveProxy(oldMainMaterial);
			if (mainMaterial != oldMainMaterial)
			{
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, VSandPackage.VSAND_APPLICATION__MAIN_MATERIAL, oldMainMaterial, mainMaterial));
			}
		}
		return mainMaterial;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Material basicGetMainMaterial()
	{
		return mainMaterial;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMainMaterial(Material newMainMaterial)
	{
		Material oldMainMaterial = mainMaterial;
		mainMaterial = newMainMaterial;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, VSandPackage.VSAND_APPLICATION__MAIN_MATERIAL, oldMainMaterial, mainMaterial));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Material getSecondaryMaterial()
	{
		if (secondaryMaterial != null && secondaryMaterial.eIsProxy())
		{
			InternalEObject oldSecondaryMaterial = (InternalEObject)secondaryMaterial;
			secondaryMaterial = (Material)eResolveProxy(oldSecondaryMaterial);
			if (secondaryMaterial != oldSecondaryMaterial)
			{
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, VSandPackage.VSAND_APPLICATION__SECONDARY_MATERIAL, oldSecondaryMaterial, secondaryMaterial));
			}
		}
		return secondaryMaterial;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Material basicGetSecondaryMaterial()
	{
		return secondaryMaterial;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSecondaryMaterial(Material newSecondaryMaterial)
	{
		Material oldSecondaryMaterial = secondaryMaterial;
		secondaryMaterial = newSecondaryMaterial;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, VSandPackage.VSAND_APPLICATION__SECONDARY_MATERIAL, oldSecondaryMaterial, secondaryMaterial));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isNextMode()
	{
		return nextMode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setNextMode(boolean newNextMode)
	{
		boolean oldNextMode = nextMode;
		nextMode = newNextMode;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, VSandPackage.VSAND_APPLICATION__NEXT_MODE, oldNextMode, nextMode));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getBrushSize()
	{
		return brushSize;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setBrushSize(int newBrushSize)
	{
		int oldBrushSize = brushSize;
		brushSize = newBrushSize;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, VSandPackage.VSAND_APPLICATION__BRUSH_SIZE, oldBrushSize, brushSize));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
	{
		switch (featureID)
		{
			case VSandPackage.VSAND_APPLICATION__MATERIALS:
				return basicSetMaterials(null, msgs);
			case VSandPackage.VSAND_APPLICATION__TRANSFORMATIONS:
				return basicSetTransformations(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
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
			case VSandPackage.VSAND_APPLICATION__MATERIALS:
				return getMaterials();
			case VSandPackage.VSAND_APPLICATION__TRANSFORMATIONS:
				return getTransformations();
			case VSandPackage.VSAND_APPLICATION__MAIN_MATERIAL:
				if (resolve) return getMainMaterial();
				return basicGetMainMaterial();
			case VSandPackage.VSAND_APPLICATION__SECONDARY_MATERIAL:
				if (resolve) return getSecondaryMaterial();
				return basicGetSecondaryMaterial();
			case VSandPackage.VSAND_APPLICATION__NEXT_MODE:
				return isNextMode();
			case VSandPackage.VSAND_APPLICATION__BRUSH_SIZE:
				return getBrushSize();
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
			case VSandPackage.VSAND_APPLICATION__MATERIALS:
				setMaterials((Materials)newValue);
				return;
			case VSandPackage.VSAND_APPLICATION__TRANSFORMATIONS:
				setTransformations((Transformations)newValue);
				return;
			case VSandPackage.VSAND_APPLICATION__MAIN_MATERIAL:
				setMainMaterial((Material)newValue);
				return;
			case VSandPackage.VSAND_APPLICATION__SECONDARY_MATERIAL:
				setSecondaryMaterial((Material)newValue);
				return;
			case VSandPackage.VSAND_APPLICATION__NEXT_MODE:
				setNextMode((Boolean)newValue);
				return;
			case VSandPackage.VSAND_APPLICATION__BRUSH_SIZE:
				setBrushSize((Integer)newValue);
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
			case VSandPackage.VSAND_APPLICATION__MATERIALS:
				setMaterials((Materials)null);
				return;
			case VSandPackage.VSAND_APPLICATION__TRANSFORMATIONS:
				setTransformations((Transformations)null);
				return;
			case VSandPackage.VSAND_APPLICATION__MAIN_MATERIAL:
				setMainMaterial((Material)null);
				return;
			case VSandPackage.VSAND_APPLICATION__SECONDARY_MATERIAL:
				setSecondaryMaterial((Material)null);
				return;
			case VSandPackage.VSAND_APPLICATION__NEXT_MODE:
				setNextMode(NEXT_MODE_EDEFAULT);
				return;
			case VSandPackage.VSAND_APPLICATION__BRUSH_SIZE:
				setBrushSize(BRUSH_SIZE_EDEFAULT);
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
			case VSandPackage.VSAND_APPLICATION__MATERIALS:
				return materials != null;
			case VSandPackage.VSAND_APPLICATION__TRANSFORMATIONS:
				return transformations != null;
			case VSandPackage.VSAND_APPLICATION__MAIN_MATERIAL:
				return mainMaterial != null;
			case VSandPackage.VSAND_APPLICATION__SECONDARY_MATERIAL:
				return secondaryMaterial != null;
			case VSandPackage.VSAND_APPLICATION__NEXT_MODE:
				return nextMode != NEXT_MODE_EDEFAULT;
			case VSandPackage.VSAND_APPLICATION__BRUSH_SIZE:
				return brushSize != BRUSH_SIZE_EDEFAULT;
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
		result.append(" (nextMode: ");
		result.append(nextMode);
		result.append(", brushSize: ");
		result.append(brushSize);
		result.append(')');
		return result.toString();
	}

} //VSandApplicationImpl
