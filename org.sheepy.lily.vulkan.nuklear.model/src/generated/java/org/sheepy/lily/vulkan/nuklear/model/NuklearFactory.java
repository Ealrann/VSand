/**
 */
package org.sheepy.lily.vulkan.nuklear.model;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.sheepy.lily.vulkan.nuklear.model.NuklearPackage
 * @generated
 */
public interface NuklearFactory extends EFactory
{
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	NuklearFactory eINSTANCE = org.sheepy.lily.vulkan.nuklear.model.impl.NuklearFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Pipeline</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Pipeline</em>'.
	 * @generated
	 */
	NuklearPipeline createNuklearPipeline();

	/**
	 * Returns a new object of class '<em>Constants</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Constants</em>'.
	 * @generated
	 */
	NuklearConstants createNuklearConstants();

	/**
	 * Returns a new object of class '<em>Index Buffer</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Index Buffer</em>'.
	 * @generated
	 */
	NuklearIndexBuffer createNuklearIndexBuffer();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	NuklearPackage getNuklearPackage();

} //NuklearFactory
