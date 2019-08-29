/**
 */
package org.sheepy.vsand.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.sheepy.lily.core.api.adapter.LilyEObject;

import org.sheepy.vsand.model.Material;
import org.sheepy.vsand.model.MaterialProvider;
import org.sheepy.vsand.model.MultipleTransformation;
import org.sheepy.vsand.model.VSandPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Multiple Transformation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.sheepy.vsand.model.impl.MultipleTransformationImpl#getProbability <em>Probability</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.impl.MultipleTransformationImpl#getPropagation <em>Propagation</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.impl.MultipleTransformationImpl#isIsStaticTransformation <em>Is Static Transformation</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.impl.MultipleTransformationImpl#getTarget <em>Target</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.impl.MultipleTransformationImpl#getReactants <em>Reactants</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.impl.MultipleTransformationImpl#getCatalysts <em>Catalysts</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.impl.MultipleTransformationImpl#getName <em>Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MultipleTransformationImpl extends LilyEObject implements MultipleTransformation
{
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
	 * The default value of the '{@link #getPropagation() <em>Propagation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPropagation()
	 * @generated
	 * @ordered
	 */
	protected static final int PROPAGATION_EDEFAULT = 1;

	/**
	 * The cached value of the '{@link #getPropagation() <em>Propagation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPropagation()
	 * @generated
	 * @ordered
	 */
	protected int propagation = PROPAGATION_EDEFAULT;

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
	 * The cached value of the '{@link #getTarget() <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTarget()
	 * @generated
	 * @ordered
	 */
	protected Material target;

	/**
	 * The cached value of the '{@link #getReactants() <em>Reactants</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReactants()
	 * @generated
	 * @ordered
	 */
	protected MaterialProvider reactants;

	/**
	 * The cached value of the '{@link #getCatalysts() <em>Catalysts</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCatalysts()
	 * @generated
	 * @ordered
	 */
	protected MaterialProvider catalysts;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = "";

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MultipleTransformationImpl()
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
		return VSandPackage.Literals.MULTIPLE_TRANSFORMATION;
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
			eNotify(new ENotificationImpl(this, Notification.SET, VSandPackage.MULTIPLE_TRANSFORMATION__PROBABILITY, oldProbability, probability));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getPropagation()
	{
		return propagation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPropagation(int newPropagation)
	{
		int oldPropagation = propagation;
		propagation = newPropagation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, VSandPackage.MULTIPLE_TRANSFORMATION__PROPAGATION, oldPropagation, propagation));
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
			eNotify(new ENotificationImpl(this, Notification.SET, VSandPackage.MULTIPLE_TRANSFORMATION__IS_STATIC_TRANSFORMATION, oldIsStaticTransformation, isStaticTransformation));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Material getTarget()
	{
		if (target != null && ((EObject)target).eIsProxy())
		{
			InternalEObject oldTarget = (InternalEObject)target;
			target = (Material)eResolveProxy(oldTarget);
			if (target != oldTarget)
			{
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, VSandPackage.MULTIPLE_TRANSFORMATION__TARGET, oldTarget, target));
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
			eNotify(new ENotificationImpl(this, Notification.SET, VSandPackage.MULTIPLE_TRANSFORMATION__TARGET, oldTarget, target));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public MaterialProvider getReactants()
	{
		return reactants;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetReactants(MaterialProvider newReactants, NotificationChain msgs)
	{
		MaterialProvider oldReactants = reactants;
		reactants = newReactants;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, VSandPackage.MULTIPLE_TRANSFORMATION__REACTANTS, oldReactants, newReactants);
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
	public void setReactants(MaterialProvider newReactants)
	{
		if (newReactants != reactants)
		{
			NotificationChain msgs = null;
			if (reactants != null)
				msgs = ((InternalEObject)reactants).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - VSandPackage.MULTIPLE_TRANSFORMATION__REACTANTS, null, msgs);
			if (newReactants != null)
				msgs = ((InternalEObject)newReactants).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - VSandPackage.MULTIPLE_TRANSFORMATION__REACTANTS, null, msgs);
			msgs = basicSetReactants(newReactants, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, VSandPackage.MULTIPLE_TRANSFORMATION__REACTANTS, newReactants, newReactants));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public MaterialProvider getCatalysts()
	{
		return catalysts;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCatalysts(MaterialProvider newCatalysts, NotificationChain msgs)
	{
		MaterialProvider oldCatalysts = catalysts;
		catalysts = newCatalysts;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, VSandPackage.MULTIPLE_TRANSFORMATION__CATALYSTS, oldCatalysts, newCatalysts);
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
	public void setCatalysts(MaterialProvider newCatalysts)
	{
		if (newCatalysts != catalysts)
		{
			NotificationChain msgs = null;
			if (catalysts != null)
				msgs = ((InternalEObject)catalysts).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - VSandPackage.MULTIPLE_TRANSFORMATION__CATALYSTS, null, msgs);
			if (newCatalysts != null)
				msgs = ((InternalEObject)newCatalysts).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - VSandPackage.MULTIPLE_TRANSFORMATION__CATALYSTS, null, msgs);
			msgs = basicSetCatalysts(newCatalysts, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, VSandPackage.MULTIPLE_TRANSFORMATION__CATALYSTS, newCatalysts, newCatalysts));
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
			eNotify(new ENotificationImpl(this, Notification.SET, VSandPackage.MULTIPLE_TRANSFORMATION__NAME, oldName, name));
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
			case VSandPackage.MULTIPLE_TRANSFORMATION__REACTANTS:
				return basicSetReactants(null, msgs);
			case VSandPackage.MULTIPLE_TRANSFORMATION__CATALYSTS:
				return basicSetCatalysts(null, msgs);
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
			case VSandPackage.MULTIPLE_TRANSFORMATION__PROBABILITY:
				return getProbability();
			case VSandPackage.MULTIPLE_TRANSFORMATION__PROPAGATION:
				return getPropagation();
			case VSandPackage.MULTIPLE_TRANSFORMATION__IS_STATIC_TRANSFORMATION:
				return isIsStaticTransformation();
			case VSandPackage.MULTIPLE_TRANSFORMATION__TARGET:
				if (resolve) return getTarget();
				return basicGetTarget();
			case VSandPackage.MULTIPLE_TRANSFORMATION__REACTANTS:
				return getReactants();
			case VSandPackage.MULTIPLE_TRANSFORMATION__CATALYSTS:
				return getCatalysts();
			case VSandPackage.MULTIPLE_TRANSFORMATION__NAME:
				return getName();
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
			case VSandPackage.MULTIPLE_TRANSFORMATION__PROBABILITY:
				setProbability((Integer)newValue);
				return;
			case VSandPackage.MULTIPLE_TRANSFORMATION__PROPAGATION:
				setPropagation((Integer)newValue);
				return;
			case VSandPackage.MULTIPLE_TRANSFORMATION__IS_STATIC_TRANSFORMATION:
				setIsStaticTransformation((Boolean)newValue);
				return;
			case VSandPackage.MULTIPLE_TRANSFORMATION__TARGET:
				setTarget((Material)newValue);
				return;
			case VSandPackage.MULTIPLE_TRANSFORMATION__REACTANTS:
				setReactants((MaterialProvider)newValue);
				return;
			case VSandPackage.MULTIPLE_TRANSFORMATION__CATALYSTS:
				setCatalysts((MaterialProvider)newValue);
				return;
			case VSandPackage.MULTIPLE_TRANSFORMATION__NAME:
				setName((String)newValue);
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
			case VSandPackage.MULTIPLE_TRANSFORMATION__PROBABILITY:
				setProbability(PROBABILITY_EDEFAULT);
				return;
			case VSandPackage.MULTIPLE_TRANSFORMATION__PROPAGATION:
				setPropagation(PROPAGATION_EDEFAULT);
				return;
			case VSandPackage.MULTIPLE_TRANSFORMATION__IS_STATIC_TRANSFORMATION:
				setIsStaticTransformation(IS_STATIC_TRANSFORMATION_EDEFAULT);
				return;
			case VSandPackage.MULTIPLE_TRANSFORMATION__TARGET:
				setTarget((Material)null);
				return;
			case VSandPackage.MULTIPLE_TRANSFORMATION__REACTANTS:
				setReactants((MaterialProvider)null);
				return;
			case VSandPackage.MULTIPLE_TRANSFORMATION__CATALYSTS:
				setCatalysts((MaterialProvider)null);
				return;
			case VSandPackage.MULTIPLE_TRANSFORMATION__NAME:
				setName(NAME_EDEFAULT);
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
			case VSandPackage.MULTIPLE_TRANSFORMATION__PROBABILITY:
				return probability != PROBABILITY_EDEFAULT;
			case VSandPackage.MULTIPLE_TRANSFORMATION__PROPAGATION:
				return propagation != PROPAGATION_EDEFAULT;
			case VSandPackage.MULTIPLE_TRANSFORMATION__IS_STATIC_TRANSFORMATION:
				return isStaticTransformation != IS_STATIC_TRANSFORMATION_EDEFAULT;
			case VSandPackage.MULTIPLE_TRANSFORMATION__TARGET:
				return target != null;
			case VSandPackage.MULTIPLE_TRANSFORMATION__REACTANTS:
				return reactants != null;
			case VSandPackage.MULTIPLE_TRANSFORMATION__CATALYSTS:
				return catalysts != null;
			case VSandPackage.MULTIPLE_TRANSFORMATION__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
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
		result.append(" (probability: ");
		result.append(probability);
		result.append(", propagation: ");
		result.append(propagation);
		result.append(", isStaticTransformation: ");
		result.append(isStaticTransformation);
		result.append(", name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} //MultipleTransformationImpl
