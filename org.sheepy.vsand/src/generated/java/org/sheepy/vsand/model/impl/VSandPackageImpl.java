/**
 */
package org.sheepy.vsand.model.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.sheepy.lily.core.model.action.ActionPackage;
import org.sheepy.lily.core.model.application.ApplicationPackage;
import org.sheepy.lily.core.model.cadence.CadencePackage;
import org.sheepy.lily.core.model.inference.InferencePackage;
import org.sheepy.lily.core.model.maintainer.MaintainerPackage;
import org.sheepy.lily.core.model.presentation.PresentationPackage;
import org.sheepy.lily.core.model.resource.ResourcePackage;
import org.sheepy.lily.core.model.types.TypesPackage;
import org.sheepy.lily.core.model.ui.UiPackage;
import org.sheepy.lily.core.model.variable.VariablePackage;
import org.sheepy.lily.vulkan.extra.model.nuklear.NuklearPackage;
import org.sheepy.lily.vulkan.model.VulkanPackage;
import org.sheepy.lily.vulkan.model.process.ProcessPackage;
import org.sheepy.lily.vulkan.model.vulkanresource.VulkanResourcePackage;
import org.sheepy.vsand.model.BoardConstantBuffer;
import org.sheepy.vsand.model.DrawCircle;
import org.sheepy.vsand.model.DrawCommand;
import org.sheepy.vsand.model.DrawConstantBuffer;
import org.sheepy.vsand.model.DrawLine;
import org.sheepy.vsand.model.DrawSquare;
import org.sheepy.vsand.model.ITransformation;
import org.sheepy.vsand.model.InputMaterialProvider;
import org.sheepy.vsand.model.Material;
import org.sheepy.vsand.model.MaterialProvider;
import org.sheepy.vsand.model.Materials;
import org.sheepy.vsand.model.MultipleTransformation;
import org.sheepy.vsand.model.PixelConstantBuffer;
import org.sheepy.vsand.model.Transformation;
import org.sheepy.vsand.model.Transformations;
import org.sheepy.vsand.model.VSandApplication;
import org.sheepy.vsand.model.VSandFactory;
import org.sheepy.vsand.model.VSandPackage;
import org.sheepy.vulkan.model.barrier.BarrierPackage;
import org.sheepy.vulkan.model.enumeration.EnumerationPackage;
import org.sheepy.vulkan.model.image.ImagePackage;
import org.sheepy.vulkan.model.pipeline.PipelinePackage;

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
	private EClass boardConstantBufferEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass drawConstantBufferEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass pixelConstantBufferEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass drawCommandEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass drawCircleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass drawSquareEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass drawLineEClass = null;

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
	private EClass multipleTransformationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass materialProviderEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iTransformationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass inputMaterialProviderEClass = null;

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
		NuklearPackage.eINSTANCE.eClass();
		UiPackage.eINSTANCE.eClass();
		PresentationPackage.eINSTANCE.eClass();
		ApplicationPackage.eINSTANCE.eClass();
		VariablePackage.eINSTANCE.eClass();
		TypesPackage.eINSTANCE.eClass();
		InferencePackage.eINSTANCE.eClass();
		MaintainerPackage.eINSTANCE.eClass();
		CadencePackage.eINSTANCE.eClass();
		ActionPackage.eINSTANCE.eClass();
		ProcessPackage.eINSTANCE.eClass();
		VulkanPackage.eINSTANCE.eClass();
		BarrierPackage.eINSTANCE.eClass();
		EnumerationPackage.eINSTANCE.eClass();
		ImagePackage.eINSTANCE.eClass();
		PipelinePackage.eINSTANCE.eClass();
		ResourcePackage.eINSTANCE.eClass();
		VulkanResourcePackage.eINSTANCE.eClass();

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
	public EReference getVSandApplication_DrawQueue()
	{
		return (EReference)vSandApplicationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getVSandApplication_MainMaterial()
	{
		return (EReference)vSandApplicationEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getVSandApplication_SecondaryMaterial()
	{
		return (EReference)vSandApplicationEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getVSandApplication_NextMode()
	{
		return (EAttribute)vSandApplicationEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getVSandApplication_Paused()
	{
		return (EAttribute)vSandApplicationEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getVSandApplication_Speed()
	{
		return (EAttribute)vSandApplicationEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getVSandApplication_ForceClear()
	{
		return (EAttribute)vSandApplicationEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getVSandApplication_ShowSleepZones()
	{
		return (EAttribute)vSandApplicationEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getVSandApplication_BrushSize()
	{
		return (EAttribute)vSandApplicationEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getVSandApplication_BoardUpdateTask()
	{
		return (EReference)vSandApplicationEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getVSandApplication_Size()
	{
		return (EAttribute)vSandApplicationEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getBoardConstantBuffer()
	{
		return boardConstantBufferEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getBoardConstantBuffer_CurrentBoardBuffer()
	{
		return (EAttribute)boardConstantBufferEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDrawConstantBuffer()
	{
		return drawConstantBufferEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDrawConstantBuffer_BoardConstantBuffer()
	{
		return (EReference)drawConstantBufferEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getPixelConstantBuffer()
	{
		return pixelConstantBufferEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getPixelConstantBuffer_BoardConstantBuffer()
	{
		return (EReference)pixelConstantBufferEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDrawCommand()
	{
		return drawCommandEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDrawCommand_Material()
	{
		return (EReference)drawCommandEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDrawCircle()
	{
		return drawCircleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDrawCircle_X()
	{
		return (EAttribute)drawCircleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDrawCircle_Y()
	{
		return (EAttribute)drawCircleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDrawCircle_Size()
	{
		return (EAttribute)drawCircleEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDrawSquare()
	{
		return drawSquareEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDrawSquare_X()
	{
		return (EAttribute)drawSquareEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDrawSquare_Y()
	{
		return (EAttribute)drawSquareEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDrawSquare_Size()
	{
		return (EAttribute)drawSquareEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDrawLine()
	{
		return drawLineEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDrawLine_X1()
	{
		return (EAttribute)drawLineEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDrawLine_Y1()
	{
		return (EAttribute)drawLineEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDrawLine_X2()
	{
		return (EAttribute)drawLineEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDrawLine_Y2()
	{
		return (EAttribute)drawLineEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getDrawLine_Size()
	{
		return (EAttribute)drawLineEClass.getEStructuralFeatures().get(4);
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
	public EReference getMaterial_PaintSound()
	{
		return (EReference)materialEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getMaterial_Pitch()
	{
		return (EAttribute)materialEClass.getEStructuralFeatures().get(9);
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
	public EReference getTransformation_Reactant()
	{
		return (EReference)transformationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTransformation_Catalyst()
	{
		return (EReference)transformationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getMultipleTransformation()
	{
		return multipleTransformationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getMultipleTransformation_Reactants()
	{
		return (EReference)multipleTransformationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getMultipleTransformation_Catalysts()
	{
		return (EReference)multipleTransformationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getMultipleTransformation_Name()
	{
		return (EAttribute)multipleTransformationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getMaterialProvider()
	{
		return materialProviderEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getMaterialProvider_Materials()
	{
		return (EReference)materialProviderEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getMaterialProvider_FilterMode()
	{
		return (EAttribute)materialProviderEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getITransformation()
	{
		return iTransformationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getITransformation_Probability()
	{
		return (EAttribute)iTransformationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getITransformation_Propagation()
	{
		return (EAttribute)iTransformationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getITransformation_IsStaticTransformation()
	{
		return (EAttribute)iTransformationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getITransformation_Target()
	{
		return (EReference)iTransformationEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getInputMaterialProvider()
	{
		return inputMaterialProviderEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getInputMaterialProvider_Materials()
	{
		return (EReference)inputMaterialProviderEClass.getEStructuralFeatures().get(0);
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
		createEReference(vSandApplicationEClass, VSAND_APPLICATION__DRAW_QUEUE);
		createEReference(vSandApplicationEClass, VSAND_APPLICATION__MAIN_MATERIAL);
		createEReference(vSandApplicationEClass, VSAND_APPLICATION__SECONDARY_MATERIAL);
		createEAttribute(vSandApplicationEClass, VSAND_APPLICATION__NEXT_MODE);
		createEAttribute(vSandApplicationEClass, VSAND_APPLICATION__PAUSED);
		createEAttribute(vSandApplicationEClass, VSAND_APPLICATION__SPEED);
		createEAttribute(vSandApplicationEClass, VSAND_APPLICATION__FORCE_CLEAR);
		createEAttribute(vSandApplicationEClass, VSAND_APPLICATION__SHOW_SLEEP_ZONES);
		createEAttribute(vSandApplicationEClass, VSAND_APPLICATION__BRUSH_SIZE);
		createEReference(vSandApplicationEClass, VSAND_APPLICATION__BOARD_UPDATE_TASK);
		createEAttribute(vSandApplicationEClass, VSAND_APPLICATION__SIZE);

		boardConstantBufferEClass = createEClass(BOARD_CONSTANT_BUFFER);
		createEAttribute(boardConstantBufferEClass, BOARD_CONSTANT_BUFFER__CURRENT_BOARD_BUFFER);

		drawConstantBufferEClass = createEClass(DRAW_CONSTANT_BUFFER);
		createEReference(drawConstantBufferEClass, DRAW_CONSTANT_BUFFER__BOARD_CONSTANT_BUFFER);

		pixelConstantBufferEClass = createEClass(PIXEL_CONSTANT_BUFFER);
		createEReference(pixelConstantBufferEClass, PIXEL_CONSTANT_BUFFER__BOARD_CONSTANT_BUFFER);

		drawCommandEClass = createEClass(DRAW_COMMAND);
		createEReference(drawCommandEClass, DRAW_COMMAND__MATERIAL);

		drawCircleEClass = createEClass(DRAW_CIRCLE);
		createEAttribute(drawCircleEClass, DRAW_CIRCLE__X);
		createEAttribute(drawCircleEClass, DRAW_CIRCLE__Y);
		createEAttribute(drawCircleEClass, DRAW_CIRCLE__SIZE);

		drawSquareEClass = createEClass(DRAW_SQUARE);
		createEAttribute(drawSquareEClass, DRAW_SQUARE__X);
		createEAttribute(drawSquareEClass, DRAW_SQUARE__Y);
		createEAttribute(drawSquareEClass, DRAW_SQUARE__SIZE);

		drawLineEClass = createEClass(DRAW_LINE);
		createEAttribute(drawLineEClass, DRAW_LINE__X1);
		createEAttribute(drawLineEClass, DRAW_LINE__Y1);
		createEAttribute(drawLineEClass, DRAW_LINE__X2);
		createEAttribute(drawLineEClass, DRAW_LINE__Y2);
		createEAttribute(drawLineEClass, DRAW_LINE__SIZE);

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
		createEReference(materialEClass, MATERIAL__PAINT_SOUND);
		createEAttribute(materialEClass, MATERIAL__PITCH);

		transformationsEClass = createEClass(TRANSFORMATIONS);
		createEReference(transformationsEClass, TRANSFORMATIONS__TRANSFORMATIONS);

		transformationEClass = createEClass(TRANSFORMATION);
		createEReference(transformationEClass, TRANSFORMATION__REACTANT);
		createEReference(transformationEClass, TRANSFORMATION__CATALYST);

		multipleTransformationEClass = createEClass(MULTIPLE_TRANSFORMATION);
		createEReference(multipleTransformationEClass, MULTIPLE_TRANSFORMATION__REACTANTS);
		createEReference(multipleTransformationEClass, MULTIPLE_TRANSFORMATION__CATALYSTS);
		createEAttribute(multipleTransformationEClass, MULTIPLE_TRANSFORMATION__NAME);

		materialProviderEClass = createEClass(MATERIAL_PROVIDER);
		createEReference(materialProviderEClass, MATERIAL_PROVIDER__MATERIALS);
		createEAttribute(materialProviderEClass, MATERIAL_PROVIDER__FILTER_MODE);

		iTransformationEClass = createEClass(ITRANSFORMATION);
		createEAttribute(iTransformationEClass, ITRANSFORMATION__PROBABILITY);
		createEAttribute(iTransformationEClass, ITRANSFORMATION__PROPAGATION);
		createEAttribute(iTransformationEClass, ITRANSFORMATION__IS_STATIC_TRANSFORMATION);
		createEReference(iTransformationEClass, ITRANSFORMATION__TARGET);

		inputMaterialProviderEClass = createEClass(INPUT_MATERIAL_PROVIDER);
		createEReference(inputMaterialProviderEClass, INPUT_MATERIAL_PROVIDER__MATERIALS);
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
		ProcessPackage theProcessPackage = (ProcessPackage)EPackage.Registry.INSTANCE.getEPackage(ProcessPackage.eNS_URI);
		TypesPackage theTypesPackage = (TypesPackage)EPackage.Registry.INSTANCE.getEPackage(TypesPackage.eNS_URI);
		VulkanResourcePackage theVulkanResourcePackage = (VulkanResourcePackage)EPackage.Registry.INSTANCE.getEPackage(VulkanResourcePackage.eNS_URI);
		ResourcePackage theResourcePackage = (ResourcePackage)EPackage.Registry.INSTANCE.getEPackage(ResourcePackage.eNS_URI);
		NuklearPackage theNuklearPackage = (NuklearPackage)EPackage.Registry.INSTANCE.getEPackage(NuklearPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		vSandApplicationEClass.getESuperTypes().add(theApplicationPackage.getApplication());
		boardConstantBufferEClass.getESuperTypes().add(theVulkanResourcePackage.getConstantBuffer());
		drawConstantBufferEClass.getESuperTypes().add(this.getBoardConstantBuffer());
		pixelConstantBufferEClass.getESuperTypes().add(this.getBoardConstantBuffer());
		drawCircleEClass.getESuperTypes().add(this.getDrawCommand());
		drawSquareEClass.getESuperTypes().add(this.getDrawCommand());
		drawLineEClass.getESuperTypes().add(this.getDrawCommand());
		transformationEClass.getESuperTypes().add(this.getITransformation());
		multipleTransformationEClass.getESuperTypes().add(this.getITransformation());
		inputMaterialProviderEClass.getESuperTypes().add(theNuklearPackage.getIInputProvider());

		// Initialize classes, features, and operations; add parameters
		initEClass(vSandApplicationEClass, VSandApplication.class, "VSandApplication", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getVSandApplication_Materials(), this.getMaterials(), null, "materials", null, 1, 1, VSandApplication.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVSandApplication_Transformations(), this.getTransformations(), null, "transformations", null, 1, 1, VSandApplication.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVSandApplication_DrawQueue(), this.getDrawCommand(), null, "drawQueue", null, 0, -1, VSandApplication.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVSandApplication_MainMaterial(), this.getMaterial(), null, "mainMaterial", null, 0, 1, VSandApplication.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVSandApplication_SecondaryMaterial(), this.getMaterial(), null, "secondaryMaterial", null, 0, 1, VSandApplication.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVSandApplication_NextMode(), ecorePackage.getEBoolean(), "nextMode", "false", 0, 1, VSandApplication.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVSandApplication_Paused(), ecorePackage.getEBoolean(), "paused", "false", 0, 1, VSandApplication.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVSandApplication_Speed(), ecorePackage.getEInt(), "speed", "1", 0, 1, VSandApplication.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVSandApplication_ForceClear(), ecorePackage.getEBoolean(), "forceClear", "false", 0, 1, VSandApplication.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVSandApplication_ShowSleepZones(), ecorePackage.getEBoolean(), "showSleepZones", "false", 0, 1, VSandApplication.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVSandApplication_BrushSize(), ecorePackage.getEInt(), "brushSize", "4", 0, 1, VSandApplication.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVSandApplication_BoardUpdateTask(), theProcessPackage.getCompositeTask(), null, "boardUpdateTask", null, 0, 1, VSandApplication.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVSandApplication_Size(), theTypesPackage.getVector2i(), "size", null, 1, 1, VSandApplication.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(boardConstantBufferEClass, BoardConstantBuffer.class, "BoardConstantBuffer", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getBoardConstantBuffer_CurrentBoardBuffer(), ecorePackage.getEInt(), "currentBoardBuffer", "0", 0, 1, BoardConstantBuffer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(drawConstantBufferEClass, DrawConstantBuffer.class, "DrawConstantBuffer", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDrawConstantBuffer_BoardConstantBuffer(), this.getBoardConstantBuffer(), null, "boardConstantBuffer", null, 0, 1, DrawConstantBuffer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(pixelConstantBufferEClass, PixelConstantBuffer.class, "PixelConstantBuffer", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPixelConstantBuffer_BoardConstantBuffer(), this.getBoardConstantBuffer(), null, "boardConstantBuffer", null, 0, 1, PixelConstantBuffer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(drawCommandEClass, DrawCommand.class, "DrawCommand", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDrawCommand_Material(), this.getMaterial(), null, "material", null, 0, 1, DrawCommand.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(drawCircleEClass, DrawCircle.class, "DrawCircle", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDrawCircle_X(), ecorePackage.getEInt(), "x", null, 0, 1, DrawCircle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDrawCircle_Y(), ecorePackage.getEInt(), "y", null, 0, 1, DrawCircle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDrawCircle_Size(), ecorePackage.getEInt(), "size", null, 0, 1, DrawCircle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(drawSquareEClass, DrawSquare.class, "DrawSquare", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDrawSquare_X(), ecorePackage.getEInt(), "x", null, 0, 1, DrawSquare.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDrawSquare_Y(), ecorePackage.getEInt(), "y", null, 0, 1, DrawSquare.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDrawSquare_Size(), ecorePackage.getEInt(), "size", null, 0, 1, DrawSquare.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(drawLineEClass, DrawLine.class, "DrawLine", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDrawLine_X1(), ecorePackage.getEInt(), "x1", null, 0, 1, DrawLine.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDrawLine_Y1(), ecorePackage.getEInt(), "y1", null, 0, 1, DrawLine.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDrawLine_X2(), ecorePackage.getEInt(), "x2", null, 0, 1, DrawLine.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDrawLine_Y2(), ecorePackage.getEInt(), "y2", null, 0, 1, DrawLine.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDrawLine_Size(), ecorePackage.getEInt(), "size", null, 0, 1, DrawLine.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(materialsEClass, Materials.class, "Materials", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMaterials_Materials(), this.getMaterial(), null, "materials", null, 0, -1, Materials.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(materialEClass, Material.class, "Material", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMaterial_Name(), ecorePackage.getEString(), "name", null, 0, 1, Material.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMaterial_IsStatic(), ecorePackage.getEBoolean(), "isStatic", null, 0, 1, Material.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMaterial_Density(), ecorePackage.getEInt(), "density", null, 0, 1, Material.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMaterial_Runoff(), ecorePackage.getEInt(), "runoff", null, 0, 1, Material.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMaterial_R(), ecorePackage.getEInt(), "r", "0", 0, 1, Material.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMaterial_G(), ecorePackage.getEInt(), "g", "0", 0, 1, Material.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMaterial_B(), ecorePackage.getEInt(), "b", "0", 0, 1, Material.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMaterial_UserFriendly(), ecorePackage.getEBoolean(), "userFriendly", "true", 0, 1, Material.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMaterial_PaintSound(), theResourcePackage.getSound(), null, "paintSound", null, 0, 1, Material.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMaterial_Pitch(), ecorePackage.getEFloat(), "pitch", "1", 1, 1, Material.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(transformationsEClass, Transformations.class, "Transformations", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTransformations_Transformations(), this.getITransformation(), null, "transformations", null, 0, -1, Transformations.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(transformationEClass, Transformation.class, "Transformation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTransformation_Reactant(), this.getMaterial(), null, "reactant", null, 0, 1, Transformation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTransformation_Catalyst(), this.getMaterial(), null, "catalyst", null, 0, 1, Transformation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(multipleTransformationEClass, MultipleTransformation.class, "MultipleTransformation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMultipleTransformation_Reactants(), this.getMaterialProvider(), null, "reactants", null, 1, 1, MultipleTransformation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMultipleTransformation_Catalysts(), this.getMaterialProvider(), null, "catalysts", null, 1, 1, MultipleTransformation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMultipleTransformation_Name(), ecorePackage.getEString(), "name", "", 0, 1, MultipleTransformation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(materialProviderEClass, MaterialProvider.class, "MaterialProvider", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMaterialProvider_Materials(), this.getMaterial(), null, "materials", null, 0, -1, MaterialProvider.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMaterialProvider_FilterMode(), ecorePackage.getEBoolean(), "filterMode", "false", 1, 1, MaterialProvider.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iTransformationEClass, ITransformation.class, "ITransformation", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getITransformation_Probability(), ecorePackage.getEInt(), "probability", null, 0, 1, ITransformation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getITransformation_Propagation(), ecorePackage.getEInt(), "propagation", "1", 0, 1, ITransformation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getITransformation_IsStaticTransformation(), ecorePackage.getEBoolean(), "isStaticTransformation", null, 0, 1, ITransformation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getITransformation_Target(), this.getMaterial(), null, "target", null, 0, 1, ITransformation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(inputMaterialProviderEClass, InputMaterialProvider.class, "InputMaterialProvider", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getInputMaterialProvider_Materials(), this.getMaterials(), null, "materials", null, 1, 1, InputMaterialProvider.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //VSandPackageImpl
