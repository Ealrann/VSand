/**
 */
package org.sheepy.vulkan.sand.model;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.sheepy.vulkan.sand.model.VSandPackage
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
	VSandFactory eINSTANCE = org.sheepy.vulkan.sand.model.impl.VSandFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Application</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Application</em>'.
	 * @generated
	 */
	VSandApplication createVSandApplication();

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
	 * Returns a new object of class '<em>Repeat Compute Pipeline</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Repeat Compute Pipeline</em>'.
	 * @generated
	 */
	RepeatComputePipeline createRepeatComputePipeline();

	/**
	 * Returns a new object of class '<em>Constants</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Constants</em>'.
	 * @generated
	 */
	VSandConstants createVSandConstants();

	/**
	 * Returns a new object of class '<em>Material Selector Panel</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Material Selector Panel</em>'.
	 * @generated
	 */
	MaterialSelectorPanel createMaterialSelectorPanel();

	/**
	 * Returns a new object of class '<em>Graphic Process</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Graphic Process</em>'.
	 * @generated
	 */
	VSandGraphicProcess createVSandGraphicProcess();

	/**
	 * Returns a new object of class '<em>Compute Process</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Compute Process</em>'.
	 * @generated
	 */
	VSandComputeProcess createVSandComputeProcess();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	VSandPackage getVSandPackage();

} //VSandFactory
