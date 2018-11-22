/**
 */
package org.sheepy.vulkan.nuklear.model.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.sheepy.common.model.action.ActionPackage;
import org.sheepy.common.model.application.ApplicationPackage;
import org.sheepy.common.model.inference.InferencePackage;
import org.sheepy.common.model.root.RootPackage;
import org.sheepy.common.model.types.TypesPackage;
import org.sheepy.vulkan.model.VulkanPackage;

import org.sheepy.vulkan.model.enumeration.EnumerationPackage;

import org.sheepy.vulkan.model.process.ProcessPackage;
import org.sheepy.vulkan.model.process.graphic.GraphicPackage;
import org.sheepy.vulkan.model.resource.ResourcePackage;

import org.sheepy.vulkan.nuklear.model.NuklearConstants;
import org.sheepy.vulkan.nuklear.model.NuklearFactory;
import org.sheepy.vulkan.nuklear.model.NuklearIndexBuffer;
import org.sheepy.vulkan.nuklear.model.NuklearPackage;
import org.sheepy.vulkan.nuklear.model.NuklearPipeline;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class NuklearPackageImpl extends EPackageImpl implements NuklearPackage
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass nuklearPipelineEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass nuklearConstantsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass nuklearIndexBufferEClass = null;

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
	 * @see org.sheepy.vulkan.nuklear.model.NuklearPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private NuklearPackageImpl()
	{
		super(eNS_URI, NuklearFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link NuklearPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static NuklearPackage init()
	{
		if (isInited) return (NuklearPackage)EPackage.Registry.INSTANCE.getEPackage(NuklearPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredNuklearPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		NuklearPackageImpl theNuklearPackage = registeredNuklearPackage instanceof NuklearPackageImpl ? (NuklearPackageImpl)registeredNuklearPackage : new NuklearPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		ProcessPackage.eINSTANCE.eClass();
		GraphicPackage.eINSTANCE.eClass();
		RootPackage.eINSTANCE.eClass();
		TypesPackage.eINSTANCE.eClass();
		InferencePackage.eINSTANCE.eClass();
		ResourcePackage.eINSTANCE.eClass();
		EcorePackage.eINSTANCE.eClass();
		VulkanPackage.eINSTANCE.eClass();
		EnumerationPackage.eINSTANCE.eClass();
		ActionPackage.eINSTANCE.eClass();
		ApplicationPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theNuklearPackage.createPackageContents();

		// Initialize created meta-data
		theNuklearPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theNuklearPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(NuklearPackage.eNS_URI, theNuklearPackage);
		return theNuklearPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getNuklearPipeline()
	{
		return nuklearPipelineEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getNuklearPipeline_IndexBuffer()
	{
		return (EReference)nuklearPipelineEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getNuklearPipeline_Font()
	{
		return (EReference)nuklearPipelineEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getNuklearPipeline_PushConstant()
	{
		return (EReference)nuklearPipelineEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getNuklearPipeline_Subpass()
	{
		return (EAttribute)nuklearPipelineEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getNuklearConstants()
	{
		return nuklearConstantsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getNuklearIndexBuffer()
	{
		return nuklearIndexBufferEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NuklearFactory getNuklearFactory()
	{
		return (NuklearFactory)getEFactoryInstance();
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
		nuklearPipelineEClass = createEClass(NUKLEAR_PIPELINE);
		createEReference(nuklearPipelineEClass, NUKLEAR_PIPELINE__INDEX_BUFFER);
		createEReference(nuklearPipelineEClass, NUKLEAR_PIPELINE__FONT);
		createEReference(nuklearPipelineEClass, NUKLEAR_PIPELINE__PUSH_CONSTANT);
		createEAttribute(nuklearPipelineEClass, NUKLEAR_PIPELINE__SUBPASS);

		nuklearConstantsEClass = createEClass(NUKLEAR_CONSTANTS);

		nuklearIndexBufferEClass = createEClass(NUKLEAR_INDEX_BUFFER);
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
		ProcessPackage theProcessPackage = (ProcessPackage)EPackage.Registry.INSTANCE.getEPackage(ProcessPackage.eNS_URI);
		GraphicPackage theGraphicPackage = (GraphicPackage)EPackage.Registry.INSTANCE.getEPackage(GraphicPackage.eNS_URI);
		ResourcePackage theResourcePackage = (ResourcePackage)EPackage.Registry.INSTANCE.getEPackage(ResourcePackage.eNS_URI);
		EcorePackage theEcorePackage = (EcorePackage)EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		nuklearPipelineEClass.getESuperTypes().add(theProcessPackage.getIPipeline());
		nuklearPipelineEClass.getESuperTypes().add(theGraphicPackage.getIGUIPipeline());
		nuklearConstantsEClass.getESuperTypes().add(theResourcePackage.getConstants());
		nuklearIndexBufferEClass.getESuperTypes().add(theResourcePackage.getIndexedBuffer());

		// Initialize classes, features, and operations; add parameters
		initEClass(nuklearPipelineEClass, NuklearPipeline.class, "NuklearPipeline", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getNuklearPipeline_IndexBuffer(), this.getNuklearIndexBuffer(), null, "indexBuffer", null, 1, 1, NuklearPipeline.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getNuklearPipeline_Font(), theResourcePackage.getFont(), null, "font", null, 1, 1, NuklearPipeline.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getNuklearPipeline_PushConstant(), this.getNuklearConstants(), null, "pushConstant", null, 1, 1, NuklearPipeline.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNuklearPipeline_Subpass(), theEcorePackage.getEInt(), "subpass", "0", 0, 1, NuklearPipeline.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(nuklearConstantsEClass, NuklearConstants.class, "NuklearConstants", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(nuklearIndexBufferEClass, NuklearIndexBuffer.class, "NuklearIndexBuffer", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} //NuklearPackageImpl
