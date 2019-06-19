/**
 */
package org.sheepy.vsand.model;

import org.sheepy.lily.vulkan.model.resource.ConstantBuffer;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Draw Constant Buffer</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sheepy.vsand.model.DrawConstantBuffer#getBoardConstantBuffer <em>Board Constant Buffer</em>}</li>
 * </ul>
 *
 * @see org.sheepy.vsand.model.VSandPackage#getDrawConstantBuffer()
 * @model
 * @generated
 */
public interface DrawConstantBuffer extends ConstantBuffer
{
	/**
	 * Returns the value of the '<em><b>Board Constant Buffer</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Board Constant Buffer</em>' reference.
	 * @see #setBoardConstantBuffer(BoardConstantBuffer)
	 * @see org.sheepy.vsand.model.VSandPackage#getDrawConstantBuffer_BoardConstantBuffer()
	 * @model
	 * @generated
	 */
	BoardConstantBuffer getBoardConstantBuffer();

	/**
	 * Sets the value of the '{@link org.sheepy.vsand.model.DrawConstantBuffer#getBoardConstantBuffer <em>Board Constant Buffer</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Board Constant Buffer</em>' reference.
	 * @see #getBoardConstantBuffer()
	 * @generated
	 */
	void setBoardConstantBuffer(BoardConstantBuffer value);

} // DrawConstantBuffer
