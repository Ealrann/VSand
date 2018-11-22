/**
 */
package org.sheepy.vulkan.sand.model.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.sheepy.vulkan.sand.model.Material;
import org.sheepy.vulkan.sand.model.Transformation;
import org.sheepy.vulkan.sand.model.VSandPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Transformation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.sheepy.vulkan.sand.model.impl.TransformationImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.sheepy.vulkan.sand.model.impl.TransformationImpl#getReactant <em>Reactant</em>}</li>
 *   <li>{@link org.sheepy.vulkan.sand.model.impl.TransformationImpl#getCatalyst <em>Catalyst</em>}</li>
 *   <li>{@link org.sheepy.vulkan.sand.model.impl.TransformationImpl#getTarget <em>Target</em>}</li>
 *   <li>{@link org.sheepy.vulkan.sand.model.impl.TransformationImpl#getProbability <em>Probability</em>}</li>
 *   <li>{@link org.sheepy.vulkan.sand.model.impl.TransformationImpl#isIsStaticTransformation <em>Is Static Transformation</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TransformationImpl extends MinimalEObjectImpl.Container implements Transformation
{
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getReactant() <em>Reactant</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReactant()
	 * @generated
	 * @ordered
	 */
	protected Material reactant;

	/**
	 * The cached value of the '{@link #getCatalyst() <em>Catalyst</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCatalyst()
	 * @generated
	 * @ordered
	 */
	protected Material catalyst;

	/**
	 * The cached value of the '{@link #getTarget() <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTarget()
	 * @generated
	 * @ordered
	 */
	protected Material target;

	/**
	 * The default value of the '{@link #getProbability() <em>Probability</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProbability()
	 * @generated
	 * @ordered
	 */
	protected static final int PROBABILITY_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getProbability() <em>Probability</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProbability()
	 * @generated
	 * @ordered
	 */
	protected int probability = PROBABILITY_EDEFAULT;

	/**
	 * The default value of the '{@link #isIsStaticTransformation() <em>Is Static Transformation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsStaticTransformation()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_STATIC_TRANSFORMATION_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isIsStaticTransformation() <em>Is Static Transformation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsStaticTransformation()
	 * @generated
	 * @ordered
	 */
	protected boolean isStaticTransformation = IS_STATIC_TRANSFORMATION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TransformationImpl()
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
		return VSandPackage.Literals.TRANSFORMATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName()
	{
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setName(String newName)
	{
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, VSandPackage.TRANSFORMATION__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Material getReactant()
	{
		if (reactant != null && reactant.eIsProxy())
		{
			InternalEObject oldReactant = (InternalEObject)reactant;
			reactant = (Material)eResolveProxy(oldReactant);
			if (reactant != oldReactant)
			{
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, VSandPackage.TRANSFORMATION__REACTANT, oldReactant, reactant));
			}
		}
		return reactant;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Material basicGetReactant()
	{
		return reactant;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setReactant(Material newReactant)
	{
		Material oldReactant = reactant;
		reactant = newReactant;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, VSandPackage.TRANSFORMATION__REACTANT, oldReactant, reactant));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Material getCatalyst()
	{
		if (catalyst != null && catalyst.eIsProxy())
		{
			InternalEObject oldCatalyst = (InternalEObject)catalyst;
			catalyst = (Material)eResolveProxy(oldCatalyst);
			if (catalyst != oldCatalyst)
			{
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, VSandPackage.TRANSFORMATION__CATALYST, oldCatalyst, catalyst));
			}
		}
		return catalyst;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Material basicGetCatalyst()
	{
		return catalyst;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCatalyst(Material newCatalyst)
	{
		Material oldCatalyst = catalyst;
		catalyst = newCatalyst;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, VSandPackage.TRANSFORMATION__CATALYST, oldCatalyst, catalyst));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Material getTarget()
	{
		if (target != null && target.eIsProxy())
		{
			InternalEObject oldTarget = (InternalEObject)target;
			target = (Material)eResolveProxy(oldTarget);
			if (target != oldTarget)
			{
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, VSandPackage.TRANSFORMATION__TARGET, oldTarget, target));
			}
		}
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Material basicGetTarget()
	{
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTarget(Material newTarget)
	{
		Material oldTarget = target;
		target = newTarget;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, VSandPackage.TRANSFORMATION__TARGET, oldTarget, target));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getProbability()
	{
		return probability;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setProbability(int newProbability)
	{
		int oldProbability = probability;
		probability = newProbability;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, VSandPackage.TRANSFORMATION__PROBABILITY, oldProbability, probability));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isIsStaticTransformation()
	{
		return isStaticTransformation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setIsStaticTransformation(boolean newIsStaticTransformation)
	{
		boolean oldIsStaticTransformation = isStaticTransformation;
		isStaticTransformation = newIsStaticTransformation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, VSandPackage.TRANSFORMATION__IS_STATIC_TRANSFORMATION, oldIsStaticTransformation, isStaticTransformation));
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
			case VSandPackage.TRANSFORMATION__NAME:
				return getName();
			case VSandPackage.TRANSFORMATION__REACTANT:
				if (resolve) return getReactant();
				return basicGetReactant();
			case VSandPackage.TRANSFORMATION__CATALYST:
				if (resolve) return getCatalyst();
				return basicGetCatalyst();
			case VSandPackage.TRANSFORMATION__TARGET:
				if (resolve) return getTarget();
				return basicGetTarget();
			case VSandPackage.TRANSFORMATION__PROBABILITY:
				return getProbability();
			case VSandPackage.TRANSFORMATION__IS_STATIC_TRANSFORMATION:
				return isIsStaticTransformation();
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
			case VSandPackage.TRANSFORMATION__NAME:
				setName((String)newValue);
				return;
			case VSandPackage.TRANSFORMATION__REACTANT:
				setReactant((Material)newValue);
				return;
			case VSandPackage.TRANSFORMATION__CATALYST:
				setCatalyst((Material)newValue);
				return;
			case VSandPackage.TRANSFORMATION__TARGET:
				setTarget((Material)newValue);
				return;
			case VSandPackage.TRANSFORMATION__PROBABILITY:
				setProbability((Integer)newValue);
				return;
			case VSandPackage.TRANSFORMATION__IS_STATIC_TRANSFORMATION:
				setIsStaticTransformation((Boolean)newValue);
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
			case VSandPackage.TRANSFORMATION__NAME:
				setName(NAME_EDEFAULT);
				return;
			case VSandPackage.TRANSFORMATION__REACTANT:
				setReactant((Material)null);
				return;
			case VSandPackage.TRANSFORMATION__CATALYST:
				setCatalyst((Material)null);
				return;
			case VSandPackage.TRANSFORMATION__TARGET:
				setTarget((Material)null);
				return;
			case VSandPackage.TRANSFORMATION__PROBABILITY:
				setProbability(PROBABILITY_EDEFAULT);
				return;
			case VSandPackage.TRANSFORMATION__IS_STATIC_TRANSFORMATION:
				setIsStaticTransformation(IS_STATIC_TRANSFORMATION_EDEFAULT);
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
			case VSandPackage.TRANSFORMATION__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case VSandPackage.TRANSFORMATION__REACTANT:
				return reactant != null;
			case VSandPackage.TRANSFORMATION__CATALYST:
				return catalyst != null;
			case VSandPackage.TRANSFORMATION__TARGET:
				return target != null;
			case VSandPackage.TRANSFORMATION__PROBABILITY:
				return probability != PROBABILITY_EDEFAULT;
			case VSandPackage.TRANSFORMATION__IS_STATIC_TRANSFORMATION:
				return isStaticTransformation != IS_STATIC_TRANSFORMATION_EDEFAULT;
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
		result.append(" (name: ");
		result.append(name);
		result.append(", probability: ");
		result.append(probability);
		result.append(", isStaticTransformation: ");
		result.append(isStaticTransformation);
		result.append(')');
		return result.toString();
	}

} //TransformationImpl
