/**
 */
package org.sheepy.vsand.model.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.sheepy.lily.vulkan.model.resource.impl.ConstantBufferImpl;

import org.sheepy.vsand.model.BoardConstantBuffer;
import org.sheepy.vsand.model.VSandPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Board Constant Buffer</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.sheepy.vsand.model.impl.BoardConstantBufferImpl#getCurrentBoardBuffer <em>Current Board Buffer</em>}</li>
 * </ul>
 *
 * @generated
 */
public class BoardConstantBufferImpl extends ConstantBufferImpl implements BoardConstantBuffer
{
	/**
	 * The default value of the '{@link #getCurrentBoardBuffer() <em>Current Board Buffer</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCurrentBoardBuffer()
	 * @generated
	 * @ordered
	 */
	protected static final int CURRENT_BOARD_BUFFER_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getCurrentBoardBuffer() <em>Current Board Buffer</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCurrentBoardBuffer()
	 * @generated
	 * @ordered
	 */
	protected int currentBoardBuffer = CURRENT_BOARD_BUFFER_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BoardConstantBufferImpl()
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
		return VSandPackage.Literals.BOARD_CONSTANT_BUFFER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getCurrentBoardBuffer()
	{
		return currentBoardBuffer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCurrentBoardBuffer(int newCurrentBoardBuffer)
	{
		int oldCurrentBoardBuffer = currentBoardBuffer;
		currentBoardBuffer = newCurrentBoardBuffer;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, VSandPackage.BOARD_CONSTANT_BUFFER__CURRENT_BOARD_BUFFER, oldCurrentBoardBuffer, currentBoardBuffer));
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
			case VSandPackage.BOARD_CONSTANT_BUFFER__CURRENT_BOARD_BUFFER:
				return getCurrentBoardBuffer();
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
			case VSandPackage.BOARD_CONSTANT_BUFFER__CURRENT_BOARD_BUFFER:
				setCurrentBoardBuffer((Integer)newValue);
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
			case VSandPackage.BOARD_CONSTANT_BUFFER__CURRENT_BOARD_BUFFER:
				setCurrentBoardBuffer(CURRENT_BOARD_BUFFER_EDEFAULT);
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
			case VSandPackage.BOARD_CONSTANT_BUFFER__CURRENT_BOARD_BUFFER:
				return currentBoardBuffer != CURRENT_BOARD_BUFFER_EDEFAULT;
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
		result.append(" (currentBoardBuffer: ");
		result.append(currentBoardBuffer);
		result.append(')');
		return result.toString();
	}

} //BoardConstantBufferImpl
