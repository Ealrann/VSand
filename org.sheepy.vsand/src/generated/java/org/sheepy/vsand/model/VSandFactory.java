/**
 */
package org.sheepy.vsand.model;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.sheepy.vsand.model.VSandPackage
 * @generated
 */
public interface VSandFactory extends EFactory
{
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	VSandFactory eINSTANCE = org.sheepy.vsand.model.impl.VSandFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Application</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Application</em>'.
	 * @generated
	 */
	VSandApplication createVSandApplication();

	/**
	 * Returns a new object of class '<em>Board Constant Buffer</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Board Constant Buffer</em>'.
	 * @generated
	 */
	BoardConstantBuffer createBoardConstantBuffer();

	/**
	 * Returns a new object of class '<em>Draw Constant Buffer</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Draw Constant Buffer</em>'.
	 * @generated
	 */
	DrawConstantBuffer createDrawConstantBuffer();

	/**
	 * Returns a new object of class '<em>Pixel Constant Buffer</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Pixel Constant Buffer</em>'.
	 * @generated
	 */
	PixelConstantBuffer createPixelConstantBuffer();

	/**
	 * Returns a new object of class '<em>Draw Circle</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Draw Circle</em>'.
	 * @generated
	 */
	DrawCircle createDrawCircle();

	/**
	 * Returns a new object of class '<em>Draw Square</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Draw Square</em>'.
	 * @generated
	 */
	DrawSquare createDrawSquare();

	/**
	 * Returns a new object of class '<em>Draw Line</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Draw Line</em>'.
	 * @generated
	 */
	DrawLine createDrawLine();

	/**
	 * Returns a new object of class '<em>Materials</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Materials</em>'.
	 * @generated
	 */
	Materials createMaterials();

	/**
	 * Returns a new object of class '<em>Material</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Material</em>'.
	 * @generated
	 */
	Material createMaterial();

	/**
	 * Returns a new object of class '<em>Transformations</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Transformations</em>'.
	 * @generated
	 */
	Transformations createTransformations();

	/**
	 * Returns a new object of class '<em>Transformation</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Transformation</em>'.
	 * @generated
	 */
	Transformation createTransformation();

	/**
	 * Returns a new object of class '<em>Material Selector Panel</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Material Selector Panel</em>'.
	 * @generated
	 */
	MaterialSelectorPanel createMaterialSelectorPanel();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	VSandPackage getVSandPackage();

} //VSandFactory
