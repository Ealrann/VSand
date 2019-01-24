/**
 */
package org.sheepy.lily.vulkan.gameoflife.model;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

import org.sheepy.lily.vulkan.model.resource.ResourcePackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.sheepy.lily.vulkan.gameoflife.model.GameOfLifeFactory
 * @model kind="package"
 *        annotation="http://www.eclipse.org/emf/2002/GenModel modelDirectory='/org.sheepy.lily.vulkan.gameoflife.model/src/generated/java' modelName='GameOfLife' prefix='GameOfLife' publicConstructors='true' basePackage='org.sheepy.lily.vulkan.gameoflife'"
 * @generated
 */
public interface GameOfLifePackage extends EPackage
{
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "model";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "org.sheepy.lily.vulkan.gameoflife.model";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "model";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	GameOfLifePackage eINSTANCE = org.sheepy.lily.vulkan.gameoflife.model.impl.GameOfLifePackageImpl.init();

	/**
	 * The meta object id for the '{@link org.sheepy.lily.vulkan.gameoflife.model.impl.BoardBufferImpl <em>Board Buffer</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sheepy.lily.vulkan.gameoflife.model.impl.BoardBufferImpl
	 * @see org.sheepy.lily.vulkan.gameoflife.model.impl.GameOfLifePackageImpl#getBoardBuffer()
	 * @generated
	 */
	int BOARD_BUFFER = 0;

	/**
	 * The number of structural features of the '<em>Board Buffer</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOARD_BUFFER_FEATURE_COUNT = ResourcePackage.VULKAN_BUFFER_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.sheepy.lily.vulkan.gameoflife.model.impl.BoardImageImpl <em>Board Image</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sheepy.lily.vulkan.gameoflife.model.impl.BoardImageImpl
	 * @see org.sheepy.lily.vulkan.gameoflife.model.impl.GameOfLifePackageImpl#getBoardImage()
	 * @generated
	 */
	int BOARD_IMAGE = 1;

	/**
	 * The number of structural features of the '<em>Board Image</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOARD_IMAGE_FEATURE_COUNT = ResourcePackage.IMAGE_FEATURE_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link org.sheepy.lily.vulkan.gameoflife.model.BoardBuffer <em>Board Buffer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Board Buffer</em>'.
	 * @see org.sheepy.lily.vulkan.gameoflife.model.BoardBuffer
	 * @generated
	 */
	EClass getBoardBuffer();

	/**
	 * Returns the meta object for class '{@link org.sheepy.lily.vulkan.gameoflife.model.BoardImage <em>Board Image</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Board Image</em>'.
	 * @see org.sheepy.lily.vulkan.gameoflife.model.BoardImage
	 * @generated
	 */
	EClass getBoardImage();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	GameOfLifeFactory getGameOfLifeFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals
	{
		/**
		 * The meta object literal for the '{@link org.sheepy.lily.vulkan.gameoflife.model.impl.BoardBufferImpl <em>Board Buffer</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sheepy.lily.vulkan.gameoflife.model.impl.BoardBufferImpl
		 * @see org.sheepy.lily.vulkan.gameoflife.model.impl.GameOfLifePackageImpl#getBoardBuffer()
		 * @generated
		 */
		EClass BOARD_BUFFER = eINSTANCE.getBoardBuffer();

		/**
		 * The meta object literal for the '{@link org.sheepy.lily.vulkan.gameoflife.model.impl.BoardImageImpl <em>Board Image</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sheepy.lily.vulkan.gameoflife.model.impl.BoardImageImpl
		 * @see org.sheepy.lily.vulkan.gameoflife.model.impl.GameOfLifePackageImpl#getBoardImage()
		 * @generated
		 */
		EClass BOARD_IMAGE = eINSTANCE.getBoardImage();

	}

} //GameOfLifePackage
