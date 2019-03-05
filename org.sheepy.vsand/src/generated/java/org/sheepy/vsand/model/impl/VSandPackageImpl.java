/**
 */
package org.sheepy.vsand.model.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.sheepy.lily.core.model.action.ActionPackage;
import org.sheepy.lily.core.model.application.ApplicationPackage;
import org.sheepy.lily.core.model.inference.InferencePackage;
import org.sheepy.lily.core.model.presentation.PresentationPackage;
import org.sheepy.lily.core.model.root.RootPackage;
import org.sheepy.lily.core.model.types.TypesPackage;
import org.sheepy.lily.vulkan.model.VulkanPackage;

import org.sheepy.lily.vulkan.model.enumeration.EnumerationPackage;

import org.sheepy.lily.vulkan.model.process.ProcessPackage;

import org.sheepy.lily.vulkan.model.process.compute.ComputePackage;
import org.sheepy.lily.vulkan.model.resource.ResourcePackage;
import org.sheepy.vsand.model.Material;
import org.sheepy.vsand.model.MaterialSelectorPanel;
import org.sheepy.vsand.model.Materials;
import org.sheepy.vsand.model.RepeatComputePipeline;
import org.sheepy.vsand.model.Transformation;
import org.sheepy.vsand.model.Transformations;
import org.sheepy.vsand.model.VSandApplication;
import org.sheepy.vsand.model.VSandConstants;
import org.sheepy.vsand.model.VSandFactory;
import org.sheepy.vsand.model.VSandPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class VSandPackageImpl extends EPackageImpl implements VSandPackage
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass vSandApplicationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass materialsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass materialEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass transformationsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass transformationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass repeatComputePipelineEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass vSandConstantsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass materialSelectorPanelEClass = null;

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
	 * @see org.sheepy.vsand.model.VSandPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private VSandPackageImpl()
	{
		super(eNS_URI, VSandFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link VSandPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static VSandPackage init()
	{
		if (isInited) return (VSandPackage)EPackage.Registry.INSTANCE.getEPackage(VSandPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredVSandPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		VSandPackageImpl theVSandPackage = registeredVSandPackage instanceof VSandPackageImpl ? (VSandPackageImpl)registeredVSandPackage : new VSandPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		ApplicationPackage.eINSTANCE.eClass();
		RootPackage.eINSTANCE.eClass();
		InferencePackage.eINSTANCE.eClass();
		EcorePackage.eINSTANCE.eClass();
		ComputePackage.eINSTANCE.eClass();
		TypesPackage.eINSTANCE.eClass();
		ProcessPackage.eINSTANCE.eClass();
		VulkanPackage.eINSTANCE.eClass();
		ResourcePackage.eINSTANCE.eClass();
		PresentationPackage.eINSTANCE.eClass();
		ActionPackage.eINSTANCE.eClass();
		EnumerationPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theVSandPackage.createPackageContents();

		// Initialize created meta-data
		theVSandPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theVSandPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(VSandPackage.eNS_URI, theVSandPackage);
		return theVSandPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getVSandApplication()
	{
		return vSandApplicationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getVSandApplication_Materials()
	{
		return (EReference)vSandApplicationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getVSandApplication_Transformations()
	{
		return (EReference)vSandApplicationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getVSandApplication_MainMaterial()
	{
		return (EReference)vSandApplicationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getVSandApplication_SecondaryMaterial()
	{
		return (EReference)vSandApplicationEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getVSandApplication_NextMode()
	{
		return (EAttribute)vSandApplicationEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getVSandApplication_BrushSize()
	{
		return (EAttribute)vSandApplicationEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getMaterials()
	{
		return materialsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getMaterials_Materials()
	{
		return (EReference)materialsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getMaterial()
	{
		return materialEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getMaterial_Name()
	{
		return (EAttribute)materialEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getMaterial_IsStatic()
	{
		return (EAttribute)materialEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getMaterial_Density()
	{
		return (EAttribute)materialEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getMaterial_Runoff()
	{
		return (EAttribute)materialEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getMaterial_R()
	{
		return (EAttribute)materialEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getMaterial_G()
	{
		return (EAttribute)materialEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getMaterial_B()
	{
		return (EAttribute)materialEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getMaterial_UserFriendly()
	{
		return (EAttribute)materialEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTransformations()
	{
		return transformationsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTransformations_Transformations()
	{
		return (EReference)transformationsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTransformation()
	{
		return transformationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTransformation_Name()
	{
		return (EAttribute)transformationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTransformation_Reactant()
	{
		return (EReference)transformationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTransformation_Catalyst()
	{
		return (EReference)transformationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTransformation_Target()
	{
		return (EReference)transformationEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTransformation_Probability()
	{
		return (EAttribute)transformationEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTransformation_IsStaticTransformation()
	{
		return (EAttribute)transformationEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getRepeatComputePipeline()
	{
		return repeatComputePipelineEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getRepeatComputePipeline_RepeatCount()
	{
		return (EAttribute)repeatComputePipelineEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getVSandConstants()
	{
		return vSandConstantsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getVSandConstants_FirstPass()
	{
		return (EAttribute)vSandConstantsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getVSandConstants_ShowSleepZones()
	{
		return (EAttribute)vSandConstantsEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getMaterialSelectorPanel()
	{
		return materialSelectorPanelEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getMaterialSelectorPanel_LineHeight()
	{
		return (EAttribute)materialSelectorPanelEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getMaterialSelectorPanel_PrimaryR()
	{
		return (EAttribute)materialSelectorPanelEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getMaterialSelectorPanel_PrimaryG()
	{
		return (EAttribute)materialSelectorPanelEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getMaterialSelectorPanel_PrimaryB()
	{
		return (EAttribute)materialSelectorPanelEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getMaterialSelectorPanel_SecondaryR()
	{
		return (EAttribute)materialSelectorPanelEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getMaterialSelectorPanel_SecondaryG()
	{
		return (EAttribute)materialSelectorPanelEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getMaterialSelectorPanel_SecondaryB()
	{
		return (EAttribute)materialSelectorPanelEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public VSandFactory getVSandFactory()
	{
		return (VSandFactory)getEFactoryInstance();
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
		vSandApplicationEClass = createEClass(VSAND_APPLICATION);
		createEReference(vSandApplicationEClass, VSAND_APPLICATION__MATERIALS);
		createEReference(vSandApplicationEClass, VSAND_APPLICATION__TRANSFORMATIONS);
		createEReference(vSandApplicationEClass, VSAND_APPLICATION__MAIN_MATERIAL);
		createEReference(vSandApplicationEClass, VSAND_APPLICATION__SECONDARY_MATERIAL);
		createEAttribute(vSandApplicationEClass, VSAND_APPLICATION__NEXT_MODE);
		createEAttribute(vSandApplicationEClass, VSAND_APPLICATION__BRUSH_SIZE);

		materialsEClass = createEClass(MATERIALS);
		createEReference(materialsEClass, MATERIALS__MATERIALS);

		materialEClass = createEClass(MATERIAL);
		createEAttribute(materialEClass, MATERIAL__NAME);
		createEAttribute(materialEClass, MATERIAL__IS_STATIC);
		createEAttribute(materialEClass, MATERIAL__DENSITY);
		createEAttribute(materialEClass, MATERIAL__RUNOFF);
		createEAttribute(materialEClass, MATERIAL__R);
		createEAttribute(materialEClass, MATERIAL__G);
		createEAttribute(materialEClass, MATERIAL__B);
		createEAttribute(materialEClass, MATERIAL__USER_FRIENDLY);

		transformationsEClass = createEClass(TRANSFORMATIONS);
		createEReference(transformationsEClass, TRANSFORMATIONS__TRANSFORMATIONS);

		transformationEClass = createEClass(TRANSFORMATION);
		createEAttribute(transformationEClass, TRANSFORMATION__NAME);
		createEReference(transformationEClass, TRANSFORMATION__REACTANT);
		createEReference(transformationEClass, TRANSFORMATION__CATALYST);
		createEReference(transformationEClass, TRANSFORMATION__TARGET);
		createEAttribute(transformationEClass, TRANSFORMATION__PROBABILITY);
		createEAttribute(transformationEClass, TRANSFORMATION__IS_STATIC_TRANSFORMATION);

		repeatComputePipelineEClass = createEClass(REPEAT_COMPUTE_PIPELINE);
		createEAttribute(repeatComputePipelineEClass, REPEAT_COMPUTE_PIPELINE__REPEAT_COUNT);

		vSandConstantsEClass = createEClass(VSAND_CONSTANTS);
		createEAttribute(vSandConstantsEClass, VSAND_CONSTANTS__FIRST_PASS);
		createEAttribute(vSandConstantsEClass, VSAND_CONSTANTS__SHOW_SLEEP_ZONES);

		materialSelectorPanelEClass = createEClass(MATERIAL_SELECTOR_PANEL);
		createEAttribute(materialSelectorPanelEClass, MATERIAL_SELECTOR_PANEL__LINE_HEIGHT);
		createEAttribute(materialSelectorPanelEClass, MATERIAL_SELECTOR_PANEL__PRIMARY_R);
		createEAttribute(materialSelectorPanelEClass, MATERIAL_SELECTOR_PANEL__PRIMARY_G);
		createEAttribute(materialSelectorPanelEClass, MATERIAL_SELECTOR_PANEL__PRIMARY_B);
		createEAttribute(materialSelectorPanelEClass, MATERIAL_SELECTOR_PANEL__SECONDARY_R);
		createEAttribute(materialSelectorPanelEClass, MATERIAL_SELECTOR_PANEL__SECONDARY_G);
		createEAttribute(materialSelectorPanelEClass, MATERIAL_SELECTOR_PANEL__SECONDARY_B);
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
		ApplicationPackage theApplicationPackage = (ApplicationPackage)EPackage.Registry.INSTANCE.getEPackage(ApplicationPackage.eNS_URI);
		EcorePackage theEcorePackage = (EcorePackage)EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);
		ComputePackage theComputePackage = (ComputePackage)EPackage.Registry.INSTANCE.getEPackage(ComputePackage.eNS_URI);
		ResourcePackage theResourcePackage = (ResourcePackage)EPackage.Registry.INSTANCE.getEPackage(ResourcePackage.eNS_URI);
		PresentationPackage thePresentationPackage = (PresentationPackage)EPackage.Registry.INSTANCE.getEPackage(PresentationPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		vSandApplicationEClass.getESuperTypes().add(theApplicationPackage.getApplication());
		repeatComputePipelineEClass.getESuperTypes().add(theComputePackage.getComputePipeline());
		vSandConstantsEClass.getESuperTypes().add(theResourcePackage.getAbstractConstants());
		materialSelectorPanelEClass.getESuperTypes().add(thePresentationPackage.getIPanel());

		// Initialize classes, features, and operations; add parameters
		initEClass(vSandApplicationEClass, VSandApplication.class, "VSandApplication", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getVSandApplication_Materials(), this.getMaterials(), null, "materials", null, 1, 1, VSandApplication.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVSandApplication_Transformations(), this.getTransformations(), null, "transformations", null, 1, 1, VSandApplication.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVSandApplication_MainMaterial(), this.getMaterial(), null, "mainMaterial", null, 0, 1, VSandApplication.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVSandApplication_SecondaryMaterial(), this.getMaterial(), null, "secondaryMaterial", null, 0, 1, VSandApplication.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVSandApplication_NextMode(), theEcorePackage.getEBoolean(), "nextMode", "false", 0, 1, VSandApplication.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVSandApplication_BrushSize(), theEcorePackage.getEInt(), "brushSize", "4", 0, 1, VSandApplication.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(materialsEClass, Materials.class, "Materials", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMaterials_Materials(), this.getMaterial(), null, "materials", null, 0, -1, Materials.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(materialEClass, Material.class, "Material", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMaterial_Name(), theEcorePackage.getEString(), "name", null, 0, 1, Material.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMaterial_IsStatic(), theEcorePackage.getEBoolean(), "isStatic", null, 0, 1, Material.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMaterial_Density(), theEcorePackage.getEInt(), "density", null, 0, 1, Material.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMaterial_Runoff(), theEcorePackage.getEInt(), "runoff", null, 0, 1, Material.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMaterial_R(), theEcorePackage.getEInt(), "r", "0", 0, 1, Material.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMaterial_G(), theEcorePackage.getEInt(), "g", "0", 0, 1, Material.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMaterial_B(), theEcorePackage.getEInt(), "b", "0", 0, 1, Material.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMaterial_UserFriendly(), theEcorePackage.getEBoolean(), "userFriendly", "true", 0, 1, Material.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(transformationsEClass, Transformations.class, "Transformations", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTransformations_Transformations(), this.getTransformation(), null, "transformations", null, 0, -1, Transformations.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(transformationEClass, Transformation.class, "Transformation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTransformation_Name(), theEcorePackage.getEString(), "name", null, 0, 1, Transformation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTransformation_Reactant(), this.getMaterial(), null, "reactant", null, 0, 1, Transformation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTransformation_Catalyst(), this.getMaterial(), null, "catalyst", null, 0, 1, Transformation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTransformation_Target(), this.getMaterial(), null, "target", null, 0, 1, Transformation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTransformation_Probability(), theEcorePackage.getEInt(), "probability", null, 0, 1, Transformation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTransformation_IsStaticTransformation(), theEcorePackage.getEBoolean(), "isStaticTransformation", null, 0, 1, Transformation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(repeatComputePipelineEClass, RepeatComputePipeline.class, "RepeatComputePipeline", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRepeatComputePipeline_RepeatCount(), theEcorePackage.getEInt(), "repeatCount", "1", 0, 1, RepeatComputePipeline.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(vSandConstantsEClass, VSandConstants.class, "VSandConstants", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getVSandConstants_FirstPass(), theEcorePackage.getEBoolean(), "firstPass", "true", 0, 1, VSandConstants.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVSandConstants_ShowSleepZones(), theEcorePackage.getEBoolean(), "showSleepZones", "false", 0, 1, VSandConstants.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(materialSelectorPanelEClass, MaterialSelectorPanel.class, "MaterialSelectorPanel", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMaterialSelectorPanel_LineHeight(), theEcorePackage.getEInt(), "lineHeight", "32", 0, 1, MaterialSelectorPanel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMaterialSelectorPanel_PrimaryR(), theEcorePackage.getEInt(), "primaryR", "255", 0, 1, MaterialSelectorPanel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMaterialSelectorPanel_PrimaryG(), theEcorePackage.getEInt(), "primaryG", "50", 0, 1, MaterialSelectorPanel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMaterialSelectorPanel_PrimaryB(), theEcorePackage.getEInt(), "primaryB", "50", 0, 1, MaterialSelectorPanel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMaterialSelectorPanel_SecondaryR(), theEcorePackage.getEInt(), "secondaryR", "50", 0, 1, MaterialSelectorPanel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMaterialSelectorPanel_SecondaryG(), theEcorePackage.getEInt(), "secondaryG", "50", 0, 1, MaterialSelectorPanel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMaterialSelectorPanel_SecondaryB(), theEcorePackage.getEInt(), "secondaryB", "255", 0, 1, MaterialSelectorPanel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //VSandPackageImpl
