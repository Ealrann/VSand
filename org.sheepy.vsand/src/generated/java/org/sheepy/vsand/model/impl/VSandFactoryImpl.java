/**
 */
package org.sheepy.vsand.model.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.sheepy.vsand.model.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class VSandFactoryImpl extends EFactoryImpl implements VSandFactory
{
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static VSandFactory init()
	{
		try
		{
			VSandFactory theVSandFactory = (VSandFactory)EPackage.Registry.INSTANCE.getEFactory(VSandPackage.eNS_URI);
			if (theVSandFactory != null)
			{
				return theVSandFactory;
			}
		}
		catch (Exception exception)
		{
			EcorePlugin.INSTANCE.log(exception);
		}
		return new VSandFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VSandFactoryImpl()
	{
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass)
	{
		switch (eClass.getClassifierID())
		{
			case VSandPackage.VSAND_APPLICATION: return createVSandApplication();
			case VSandPackage.MATERIALS: return createMaterials();
			case VSandPackage.MATERIAL: return createMaterial();
			case VSandPackage.TRANSFORMATIONS: return createTransformations();
			case VSandPackage.TRANSFORMATION: return createTransformation();
			case VSandPackage.REPEAT_COMPUTE_PIPELINE: return createRepeatComputePipeline();
			case VSandPackage.VSAND_CONSTANTS: return createVSandConstants();
			case VSandPackage.MATERIAL_SELECTOR_PANEL: return createMaterialSelectorPanel();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public VSandApplication createVSandApplication()
	{
		VSandApplicationImpl vSandApplication = new VSandApplicationImpl();
		return vSandApplication;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Materials createMaterials()
	{
		MaterialsImpl materials = new MaterialsImpl();
		return materials;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Material createMaterial()
	{
		MaterialImpl material = new MaterialImpl();
		return material;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Transformations createTransformations()
	{
		TransformationsImpl transformations = new TransformationsImpl();
		return transformations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Transformation createTransformation()
	{
		TransformationImpl transformation = new TransformationImpl();
		return transformation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public RepeatComputePipeline createRepeatComputePipeline()
	{
		RepeatComputePipelineImpl repeatComputePipeline = new RepeatComputePipelineImpl();
		return repeatComputePipeline;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public VSandConstants createVSandConstants()
	{
		VSandConstantsImpl vSandConstants = new VSandConstantsImpl();
		return vSandConstants;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public MaterialSelectorPanel createMaterialSelectorPanel()
	{
		MaterialSelectorPanelImpl materialSelectorPanel = new MaterialSelectorPanelImpl();
		return materialSelectorPanel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public VSandPackage getVSandPackage()
	{
		return (VSandPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static VSandPackage getPackage()
	{
		return VSandPackage.eINSTANCE;
	}

} //VSandFactoryImpl
