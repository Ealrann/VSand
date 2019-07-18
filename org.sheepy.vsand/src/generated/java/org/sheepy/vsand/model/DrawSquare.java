/**
 */
package org.sheepy.vsand.model;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Draw Square</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sheepy.vsand.model.DrawSquare#getX <em>X</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.DrawSquare#getY <em>Y</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.DrawSquare#getSize <em>Size</em>}</li>
 * </ul>
 *
 * @see org.sheepy.vsand.model.VSandPackage#getDrawSquare()
 * @model
 * @generated
 */
public interface DrawSquare extends DrawCommand
{

	/**
	 * Returns the value of the '<em><b>X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>X</em>' attribute.
	 * @see #setX(int)
	 * @see org.sheepy.vsand.model.VSandPackage#getDrawSquare_X()
	 * @model unique="false"
	 * @generated
	 */
	int getX();

	/**
	 * Sets the value of the '{@link org.sheepy.vsand.model.DrawSquare#getX <em>X</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>X</em>' attribute.
	 * @see #getX()
	 * @generated
	 */
	void setX(int value);

	/**
	 * Returns the value of the '<em><b>Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Y</em>' attribute.
	 * @see #setY(int)
	 * @see org.sheepy.vsand.model.VSandPackage#getDrawSquare_Y()
	 * @model unique="false"
	 * @generated
	 */
	int getY();

	/**
	 * Sets the value of the '{@link org.sheepy.vsand.model.DrawSquare#getY <em>Y</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Y</em>' attribute.
	 * @see #getY()
	 * @generated
	 */
	void setY(int value);

	/**
	 * Returns the value of the '<em><b>Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Size</em>' attribute.
	 * @see #setSize(int)
	 * @see org.sheepy.vsand.model.VSandPackage#getDrawSquare_Size()
	 * @model unique="false"
	 * @generated
	 */
	int getSize();

	/**
	 * Sets the value of the '{@link org.sheepy.vsand.model.DrawSquare#getSize <em>Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Size</em>' attribute.
	 * @see #getSize()
	 * @generated
	 */
	void setSize(int value);
} // DrawSquare
