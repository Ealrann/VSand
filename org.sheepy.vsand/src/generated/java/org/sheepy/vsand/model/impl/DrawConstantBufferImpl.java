/**
 */
package org.sheepy.vsand.model.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.sheepy.vsand.model.BoardConstantBuffer;
import org.sheepy.vsand.model.DrawConstantBuffer;
import org.sheepy.vsand.model.VSandPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Draw Constant Buffer</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.sheepy.vsand.model.impl.DrawConstantBufferImpl#getBoardConstantBuffer <em>Board Constant Buffer</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DrawConstantBufferImpl extends BoardConstantBufferImpl implements DrawConstantBuffer
{
	/**
	 * The cached value of the '{@link #getBoardConstantBuffer() <em>Board Constant Buffer</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBoardConstantBuffer()
	 * @generated
	 * @ordered
	 */
	protected BoardConstantBuffer boardConstantBuffer;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DrawConstantBufferImpl()
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
		return VSandPackage.Literals.DRAW_CONSTANT_BUFFER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BoardConstantBuffer getBoardConstantBuffer()
	{
		if (boardConstantBuffer != null && boardConstantBuffer.eIsProxy())
		{
			InternalEObject oldBoardConstantBuffer = (InternalEObject)boardConstantBuffer;
			boardConstantBuffer = (BoardConstantBuffer)eResolveProxy(oldBoardConstantBuffer);
			if (boardConstantBuffer != oldBoardConstantBuffer)
			{
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, VSandPackage.DRAW_CONSTANT_BUFFER__BOARD_CONSTANT_BUFFER, oldBoardConstantBuffer, boardConstantBuffer));
			}
		}
		return boardConstantBuffer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BoardConstantBuffer basicGetBoardConstantBuffer()
	{
		return boardConstantBuffer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setBoardConstantBuffer(BoardConstantBuffer newBoardConstantBuffer)
	{
		BoardConstantBuffer oldBoardConstantBuffer = boardConstantBuffer;
		boardConstantBuffer = newBoardConstantBuffer;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, VSandPackage.DRAW_CONSTANT_BUFFER__BOARD_CONSTANT_BUFFER, oldBoardConstantBuffer, boardConstantBuffer));
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
			case VSandPackage.DRAW_CONSTANT_BUFFER__BOARD_CONSTANT_BUFFER:
				if (resolve) return getBoardConstantBuffer();
				return basicGetBoardConstantBuffer();
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
			case VSandPackage.DRAW_CONSTANT_BUFFER__BOARD_CONSTANT_BUFFER:
				setBoardConstantBuffer((BoardConstantBuffer)newValue);
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
			case VSandPackage.DRAW_CONSTANT_BUFFER__BOARD_CONSTANT_BUFFER:
				setBoardConstantBuffer((BoardConstantBuffer)null);
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
			case VSandPackage.DRAW_CONSTANT_BUFFER__BOARD_CONSTANT_BUFFER:
				return boardConstantBuffer != null;
		}
		return super.eIsSet(featureID);
	}

} //DrawConstantBufferImpl
