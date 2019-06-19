/**
 */
package org.sheepy.vsand.model;

import org.sheepy.lily.vulkan.model.resource.ConstantBuffer;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Board Constant Buffer</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sheepy.vsand.model.BoardConstantBuffer#getCurrentBoardBuffer <em>Current Board Buffer</em>}</li>
 * </ul>
 *
 * @see org.sheepy.vsand.model.VSandPackage#getBoardConstantBuffer()
 * @model
 * @generated
 */
public interface BoardConstantBuffer extends ConstantBuffer
{
	/**
	 * Returns the value of the '<em><b>Current Board Buffer</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Current Board Buffer</em>' attribute.
	 * @see #setCurrentBoardBuffer(int)
	 * @see org.sheepy.vsand.model.VSandPackage#getBoardConstantBuffer_CurrentBoardBuffer()
	 * @model default="0" unique="false"
	 * @generated
	 */
	int getCurrentBoardBuffer();

	/**
	 * Sets the value of the '{@link org.sheepy.vsand.model.BoardConstantBuffer#getCurrentBoardBuffer <em>Current Board Buffer</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Current Board Buffer</em>' attribute.
	 * @see #getCurrentBoardBuffer()
	 * @generated
	 */
	void setCurrentBoardBuffer(int value);

} // BoardConstantBuffer
