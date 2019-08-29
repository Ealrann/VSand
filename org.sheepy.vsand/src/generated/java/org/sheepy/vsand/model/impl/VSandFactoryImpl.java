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
			case VSandPackage.BOARD_CONSTANT_BUFFER: return createBoardConstantBuffer();
			case VSandPackage.DRAW_CONSTANT_BUFFER: return createDrawConstantBuffer();
			case VSandPackage.PIXEL_CONSTANT_BUFFER: return createPixelConstantBuffer();
			case VSandPackage.DRAW_CIRCLE: return createDrawCircle();
			case VSandPackage.DRAW_SQUARE: return createDrawSquare();
			case VSandPackage.DRAW_LINE: return createDrawLine();
			case VSandPackage.MATERIALS: return createMaterials();
			case VSandPackage.MATERIAL: return createMaterial();
			case VSandPackage.TRANSFORMATIONS: return createTransformations();
			case VSandPackage.TRANSFORMATION: return createTransformation();
			case VSandPackage.MATERIAL_SELECTOR_PANEL: return createMaterialSelectorPanel();
			case VSandPackage.MULTIPLE_TRANSFORMATION: return createMultipleTransformation();
			case VSandPackage.MATERIAL_PROVIDER: return createMaterialProvider();
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
	public BoardConstantBuffer createBoardConstantBuffer()
	{
		BoardConstantBufferImpl boardConstantBuffer = new BoardConstantBufferImpl();
		return boardConstantBuffer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DrawConstantBuffer createDrawConstantBuffer()
	{
		DrawConstantBufferImpl drawConstantBuffer = new DrawConstantBufferImpl();
		return drawConstantBuffer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public PixelConstantBuffer createPixelConstantBuffer()
	{
		PixelConstantBufferImpl pixelConstantBuffer = new PixelConstantBufferImpl();
		return pixelConstantBuffer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DrawCircle createDrawCircle()
	{
		DrawCircleImpl drawCircle = new DrawCircleImpl();
		return drawCircle;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DrawSquare createDrawSquare()
	{
		DrawSquareImpl drawSquare = new DrawSquareImpl();
		return drawSquare;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DrawLine createDrawLine()
	{
		DrawLineImpl drawLine = new DrawLineImpl();
		return drawLine;
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
	public MultipleTransformation createMultipleTransformation()
	{
		MultipleTransformationImpl multipleTransformation = new MultipleTransformationImpl();
		return multipleTransformation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public MaterialProvider createMaterialProvider()
	{
		MaterialProviderImpl materialProvider = new MaterialProviderImpl();
		return materialProvider;
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
