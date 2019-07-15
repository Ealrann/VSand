/**
 */
package org.sheepy.vsand.model.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.sheepy.vsand.model.Material;
import org.sheepy.vsand.model.VSandPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Material</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.sheepy.vsand.model.impl.MaterialImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.impl.MaterialImpl#isIsStatic <em>Is Static</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.impl.MaterialImpl#getDensity <em>Density</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.impl.MaterialImpl#getRunoff <em>Runoff</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.impl.MaterialImpl#getR <em>R</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.impl.MaterialImpl#getG <em>G</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.impl.MaterialImpl#getB <em>B</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.impl.MaterialImpl#isUserFriendly <em>User Friendly</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MaterialImpl extends MinimalEObjectImpl.Container implements Material
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
	 * The default value of the '{@link #isIsStatic() <em>Is Static</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsStatic()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_STATIC_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isIsStatic() <em>Is Static</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsStatic()
	 * @generated
	 * @ordered
	 */
	protected boolean isStatic = IS_STATIC_EDEFAULT;

	/**
	 * The default value of the '{@link #getDensity() <em>Density</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDensity()
	 * @generated
	 * @ordered
	 */
	protected static final int DENSITY_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getDensity() <em>Density</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDensity()
	 * @generated
	 * @ordered
	 */
	protected int density = DENSITY_EDEFAULT;

	/**
	 * The default value of the '{@link #getRunoff() <em>Runoff</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRunoff()
	 * @generated
	 * @ordered
	 */
	protected static final int RUNOFF_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getRunoff() <em>Runoff</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRunoff()
	 * @generated
	 * @ordered
	 */
	protected int runoff = RUNOFF_EDEFAULT;

	/**
	 * The default value of the '{@link #getR() <em>R</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getR()
	 * @generated
	 * @ordered
	 */
	protected static final int R_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getR() <em>R</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getR()
	 * @generated
	 * @ordered
	 */
	protected int r = R_EDEFAULT;

	/**
	 * The default value of the '{@link #getG() <em>G</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getG()
	 * @generated
	 * @ordered
	 */
	protected static final int G_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getG() <em>G</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getG()
	 * @generated
	 * @ordered
	 */
	protected int g = G_EDEFAULT;

	/**
	 * The default value of the '{@link #getB() <em>B</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getB()
	 * @generated
	 * @ordered
	 */
	protected static final int B_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getB() <em>B</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getB()
	 * @generated
	 * @ordered
	 */
	protected int b = B_EDEFAULT;

	/**
	 * The default value of the '{@link #isUserFriendly() <em>User Friendly</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isUserFriendly()
	 * @generated
	 * @ordered
	 */
	protected static final boolean USER_FRIENDLY_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isUserFriendly() <em>User Friendly</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isUserFriendly()
	 * @generated
	 * @ordered
	 */
	protected boolean userFriendly = USER_FRIENDLY_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MaterialImpl()
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
		return VSandPackage.Literals.MATERIAL;
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
			eNotify(new ENotificationImpl(this, Notification.SET, VSandPackage.MATERIAL__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isIsStatic()
	{
		return isStatic;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setIsStatic(boolean newIsStatic)
	{
		boolean oldIsStatic = isStatic;
		isStatic = newIsStatic;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, VSandPackage.MATERIAL__IS_STATIC, oldIsStatic, isStatic));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getDensity()
	{
		return density;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDensity(int newDensity)
	{
		int oldDensity = density;
		density = newDensity;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, VSandPackage.MATERIAL__DENSITY, oldDensity, density));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getRunoff()
	{
		return runoff;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRunoff(int newRunoff)
	{
		int oldRunoff = runoff;
		runoff = newRunoff;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, VSandPackage.MATERIAL__RUNOFF, oldRunoff, runoff));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getR()
	{
		return r;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setR(int newR)
	{
		int oldR = r;
		r = newR;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, VSandPackage.MATERIAL__R, oldR, r));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getG()
	{
		return g;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setG(int newG)
	{
		int oldG = g;
		g = newG;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, VSandPackage.MATERIAL__G, oldG, g));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getB()
	{
		return b;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setB(int newB)
	{
		int oldB = b;
		b = newB;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, VSandPackage.MATERIAL__B, oldB, b));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isUserFriendly()
	{
		return userFriendly;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setUserFriendly(boolean newUserFriendly)
	{
		boolean oldUserFriendly = userFriendly;
		userFriendly = newUserFriendly;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, VSandPackage.MATERIAL__USER_FRIENDLY, oldUserFriendly, userFriendly));
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
			case VSandPackage.MATERIAL__NAME:
				return getName();
			case VSandPackage.MATERIAL__IS_STATIC:
				return isIsStatic();
			case VSandPackage.MATERIAL__DENSITY:
				return getDensity();
			case VSandPackage.MATERIAL__RUNOFF:
				return getRunoff();
			case VSandPackage.MATERIAL__R:
				return getR();
			case VSandPackage.MATERIAL__G:
				return getG();
			case VSandPackage.MATERIAL__B:
				return getB();
			case VSandPackage.MATERIAL__USER_FRIENDLY:
				return isUserFriendly();
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
			case VSandPackage.MATERIAL__NAME:
				setName((String)newValue);
				return;
			case VSandPackage.MATERIAL__IS_STATIC:
				setIsStatic((Boolean)newValue);
				return;
			case VSandPackage.MATERIAL__DENSITY:
				setDensity((Integer)newValue);
				return;
			case VSandPackage.MATERIAL__RUNOFF:
				setRunoff((Integer)newValue);
				return;
			case VSandPackage.MATERIAL__R:
				setR((Integer)newValue);
				return;
			case VSandPackage.MATERIAL__G:
				setG((Integer)newValue);
				return;
			case VSandPackage.MATERIAL__B:
				setB((Integer)newValue);
				return;
			case VSandPackage.MATERIAL__USER_FRIENDLY:
				setUserFriendly((Boolean)newValue);
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
			case VSandPackage.MATERIAL__NAME:
				setName(NAME_EDEFAULT);
				return;
			case VSandPackage.MATERIAL__IS_STATIC:
				setIsStatic(IS_STATIC_EDEFAULT);
				return;
			case VSandPackage.MATERIAL__DENSITY:
				setDensity(DENSITY_EDEFAULT);
				return;
			case VSandPackage.MATERIAL__RUNOFF:
				setRunoff(RUNOFF_EDEFAULT);
				return;
			case VSandPackage.MATERIAL__R:
				setR(R_EDEFAULT);
				return;
			case VSandPackage.MATERIAL__G:
				setG(G_EDEFAULT);
				return;
			case VSandPackage.MATERIAL__B:
				setB(B_EDEFAULT);
				return;
			case VSandPackage.MATERIAL__USER_FRIENDLY:
				setUserFriendly(USER_FRIENDLY_EDEFAULT);
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
			case VSandPackage.MATERIAL__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case VSandPackage.MATERIAL__IS_STATIC:
				return isStatic != IS_STATIC_EDEFAULT;
			case VSandPackage.MATERIAL__DENSITY:
				return density != DENSITY_EDEFAULT;
			case VSandPackage.MATERIAL__RUNOFF:
				return runoff != RUNOFF_EDEFAULT;
			case VSandPackage.MATERIAL__R:
				return r != R_EDEFAULT;
			case VSandPackage.MATERIAL__G:
				return g != G_EDEFAULT;
			case VSandPackage.MATERIAL__B:
				return b != B_EDEFAULT;
			case VSandPackage.MATERIAL__USER_FRIENDLY:
				return userFriendly != USER_FRIENDLY_EDEFAULT;
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
		result.append(", isStatic: ");
		result.append(isStatic);
		result.append(", density: ");
		result.append(density);
		result.append(", runoff: ");
		result.append(runoff);
		result.append(", r: ");
		result.append(r);
		result.append(", g: ");
		result.append(g);
		result.append(", b: ");
		result.append(b);
		result.append(", userFriendly: ");
		result.append(userFriendly);
		result.append(')');
		return result.toString();
	}

} //MaterialImpl
