/**
 */
package org.sheepy.vulkan.gameoflife.model.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.sheepy.vulkan.gameoflife.model.BoardBuffer;
import org.sheepy.vulkan.gameoflife.model.BoardImage;
import org.sheepy.vulkan.gameoflife.model.GameOfLifeFactory;
import org.sheepy.vulkan.gameoflife.model.GameOfLifePackage;

import org.sheepy.vulkan.model.enumeration.EnumerationPackage;

import org.sheepy.vulkan.model.resource.ResourcePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class GameOfLifePackageImpl extends EPackageImpl implements GameOfLifePackage
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass boardBufferEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass boardImageEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.sheepy.vulkan.gameoflife.model.GameOfLifePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private GameOfLifePackageImpl()
	{
		super(eNS_URI, GameOfLifeFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>This method is used to initialize {@link GameOfLifePackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static GameOfLifePackage init()
	{
		if (isInited) return (GameOfLifePackage)EPackage.Registry.INSTANCE.getEPackage(GameOfLifePackage.eNS_URI);

		// Obtain or create and register package
		Object registeredGameOfLifePackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		GameOfLifePackageImpl theGameOfLifePackage = registeredGameOfLifePackage instanceof GameOfLifePackageImpl ? (GameOfLifePackageImpl)registeredGameOfLifePackage : new GameOfLifePackageImpl();

		isInited = true;

		// Initialize simple dependencies
		ResourcePackage.eINSTANCE.eClass();
		EcorePackage.eINSTANCE.eClass();
		EnumerationPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theGameOfLifePackage.createPackageContents();

		// Initialize created meta-data
		theGameOfLifePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theGameOfLifePackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(GameOfLifePackage.eNS_URI, theGameOfLifePackage);
		return theGameOfLifePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBoardBuffer()
	{
		return boardBufferEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBoardImage()
	{
		return boardImageEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GameOfLifeFactory getGameOfLifeFactory()
	{
		return (GameOfLifeFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents()
	{
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		boardBufferEClass = createEClass(BOARD_BUFFER);

		boardImageEClass = createEClass(BOARD_IMAGE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents()
	{
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		ResourcePackage theResourcePackage = (ResourcePackage)EPackage.Registry.INSTANCE.getEPackage(ResourcePackage.eNS_URI);

		// Add supertypes to classes
		boardBufferEClass.getESuperTypes().add(theResourcePackage.getVulkanBuffer());
		boardBufferEClass.getESuperTypes().add(theResourcePackage.getIDescriptor());
		boardImageEClass.getESuperTypes().add(theResourcePackage.getImage());
		boardImageEClass.getESuperTypes().add(theResourcePackage.getIDescriptor());

		// Initialize classes and features; add operations and parameters
		initEClass(boardBufferEClass, BoardBuffer.class, "BoardBuffer", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(boardImageEClass, BoardImage.class, "BoardImage", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} //GameOfLifePackageImpl
