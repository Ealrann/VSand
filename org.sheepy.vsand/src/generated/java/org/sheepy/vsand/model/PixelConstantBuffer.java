/**
 */
package org.sheepy.vsand.model;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Pixel Constant Buffer</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sheepy.vsand.model.PixelConstantBuffer#getBoardConstantBuffer <em>Board Constant Buffer</em>}</li>
 * </ul>
 *
 * @see org.sheepy.vsand.model.VSandPackage#getPixelConstantBuffer()
 * @model
 * @generated
 */
public interface PixelConstantBuffer extends BoardConstantBuffer
{
	/**
	 * Returns the value of the '<em><b>Board Constant Buffer</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Board Constant Buffer</em>' reference.
	 * @see #setBoardConstantBuffer(BoardConstantBuffer)
	 * @see org.sheepy.vsand.model.VSandPackage#getPixelConstantBuffer_BoardConstantBuffer()
	 * @model
	 * @generated
	 */
	BoardConstantBuffer getBoardConstantBuffer();

	/**
	 * Sets the value of the '{@link org.sheepy.vsand.model.PixelConstantBuffer#getBoardConstantBuffer <em>Board Constant Buffer</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Board Constant Buffer</em>' reference.
	 * @see #getBoardConstantBuffer()
	 * @generated
	 */
	void setBoardConstantBuffer(BoardConstantBuffer value);

} // PixelConstantBuffer
