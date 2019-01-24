/**
 */
package org.sheepy.vsand.model;

import org.sheepy.lily.core.model.application.Application;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Application</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sheepy.vsand.model.VSandApplication#getMaterials <em>Materials</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.VSandApplication#getTransformations <em>Transformations</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.VSandApplication#getMainMaterial <em>Main Material</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.VSandApplication#getSecondaryMaterial <em>Secondary Material</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.VSandApplication#isNextMode <em>Next Mode</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.VSandApplication#getBrushSize <em>Brush Size</em>}</li>
 * </ul>
 *
 * @see org.sheepy.vsand.model.VSandPackage#getVSandApplication()
 * @model
 * @generated
 */
public interface VSandApplication extends Application
{

	/**
	 * Returns the value of the '<em><b>Materials</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Materials</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Materials</em>' containment reference.
	 * @see #setMaterials(Materials)
	 * @see org.sheepy.vsand.model.VSandPackage#getVSandApplication_Materials()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Materials getMaterials();

	/**
	 * Sets the value of the '{@link org.sheepy.vsand.model.VSandApplication#getMaterials <em>Materials</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Materials</em>' containment reference.
	 * @see #getMaterials()
	 * @generated
	 */
	void setMaterials(Materials value);

	/**
	 * Returns the value of the '<em><b>Transformations</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Transformations</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Transformations</em>' containment reference.
	 * @see #setTransformations(Transformations)
	 * @see org.sheepy.vsand.model.VSandPackage#getVSandApplication_Transformations()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Transformations getTransformations();

	/**
	 * Sets the value of the '{@link org.sheepy.vsand.model.VSandApplication#getTransformations <em>Transformations</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Transformations</em>' containment reference.
	 * @see #getTransformations()
	 * @generated
	 */
	void setTransformations(Transformations value);

	/**
	 * Returns the value of the '<em><b>Main Material</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Main Material</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Main Material</em>' reference.
	 * @see #setMainMaterial(Material)
	 * @see org.sheepy.vsand.model.VSandPackage#getVSandApplication_MainMaterial()
	 * @model
	 * @generated
	 */
	Material getMainMaterial();

	/**
	 * Sets the value of the '{@link org.sheepy.vsand.model.VSandApplication#getMainMaterial <em>Main Material</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Main Material</em>' reference.
	 * @see #getMainMaterial()
	 * @generated
	 */
	void setMainMaterial(Material value);

	/**
	 * Returns the value of the '<em><b>Secondary Material</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Secondary Material</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Secondary Material</em>' reference.
	 * @see #setSecondaryMaterial(Material)
	 * @see org.sheepy.vsand.model.VSandPackage#getVSandApplication_SecondaryMaterial()
	 * @model
	 * @generated
	 */
	Material getSecondaryMaterial();

	/**
	 * Sets the value of the '{@link org.sheepy.vsand.model.VSandApplication#getSecondaryMaterial <em>Secondary Material</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Secondary Material</em>' reference.
	 * @see #getSecondaryMaterial()
	 * @generated
	 */
	void setSecondaryMaterial(Material value);

	/**
	 * Returns the value of the '<em><b>Next Mode</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Next Mode</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Next Mode</em>' attribute.
	 * @see #setNextMode(boolean)
	 * @see org.sheepy.vsand.model.VSandPackage#getVSandApplication_NextMode()
	 * @model default="false" unique="false"
	 * @generated
	 */
	boolean isNextMode();

	/**
	 * Sets the value of the '{@link org.sheepy.vsand.model.VSandApplication#isNextMode <em>Next Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Next Mode</em>' attribute.
	 * @see #isNextMode()
	 * @generated
	 */
	void setNextMode(boolean value);

	/**
	 * Returns the value of the '<em><b>Brush Size</b></em>' attribute.
	 * The default value is <code>"4"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Brush Size</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Brush Size</em>' attribute.
	 * @see #setBrushSize(int)
	 * @see org.sheepy.vsand.model.VSandPackage#getVSandApplication_BrushSize()
	 * @model default="4" unique="false"
	 * @generated
	 */
	int getBrushSize();

	/**
	 * Sets the value of the '{@link org.sheepy.vsand.model.VSandApplication#getBrushSize <em>Brush Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Brush Size</em>' attribute.
	 * @see #getBrushSize()
	 * @generated
	 */
	void setBrushSize(int value);
} // VSandApplication
