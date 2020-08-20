/**
 */
package org.sheepy.vsand.model.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;
import org.sheepy.lily.core.model.application.Application;
import org.sheepy.lily.core.model.types.LNamedElement;
import org.sheepy.lily.vulkan.extra.model.nuklear.IInputProvider;
import org.sheepy.lily.vulkan.model.IVulkanResource;
import org.sheepy.vsand.model.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.sheepy.vsand.model.VSandPackage
 * @generated
 */
public class VSandAdapterFactory extends AdapterFactoryImpl
{
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static VSandPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VSandAdapterFactory()
	{
		if (modelPackage == null)
		{
			modelPackage = VSandPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object)
	{
		if (object == modelPackage)
		{
			return true;
		}
		if (object instanceof EObject)
		{
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected VSandSwitch<Adapter> modelSwitch = new VSandSwitch<Adapter>()
		{
			@Override
			public Adapter caseVSandApplication(VSandApplication object)
			{
				return createVSandApplicationAdapter();
			}
			@Override
			public Adapter caseBoardConstantBuffer(BoardConstantBuffer object)
			{
				return createBoardConstantBufferAdapter();
			}
			@Override
			public Adapter caseDrawConstantBuffer(DrawConstantBuffer object)
			{
				return createDrawConstantBufferAdapter();
			}
			@Override
			public Adapter casePixelConstantBuffer(PixelConstantBuffer object)
			{
				return createPixelConstantBufferAdapter();
			}
			@Override
			public Adapter caseDrawCommand(DrawCommand object)
			{
				return createDrawCommandAdapter();
			}
			@Override
			public Adapter caseDrawCircle(DrawCircle object)
			{
				return createDrawCircleAdapter();
			}
			@Override
			public Adapter caseDrawSquare(DrawSquare object)
			{
				return createDrawSquareAdapter();
			}
			@Override
			public Adapter caseDrawLine(DrawLine object)
			{
				return createDrawLineAdapter();
			}
			@Override
			public Adapter caseMaterials(Materials object)
			{
				return createMaterialsAdapter();
			}
			@Override
			public Adapter caseMaterial(Material object)
			{
				return createMaterialAdapter();
			}
			@Override
			public Adapter caseTransformations(Transformations object)
			{
				return createTransformationsAdapter();
			}
			@Override
			public Adapter caseTransformation(Transformation object)
			{
				return createTransformationAdapter();
			}
			@Override
			public Adapter caseMultipleTransformation(MultipleTransformation object)
			{
				return createMultipleTransformationAdapter();
			}
			@Override
			public Adapter caseMaterialProvider(MaterialProvider object)
			{
				return createMaterialProviderAdapter();
			}
			@Override
			public Adapter caseITransformation(ITransformation object)
			{
				return createITransformationAdapter();
			}
			@Override
			public Adapter caseInputMaterialProvider(InputMaterialProvider object)
			{
				return createInputMaterialProviderAdapter();
			}
			@Override
			public Adapter caseApplication(Application object)
			{
				return createApplicationAdapter();
			}
			@Override
			public Adapter caseLNamedElement(LNamedElement object)
			{
				return createLNamedElementAdapter();
			}
			@Override
			public Adapter caseIVulkanResource(IVulkanResource object)
			{
				return createIVulkanResourceAdapter();
			}
			@Override
			public Adapter caseConstantBuffer(org.sheepy.lily.vulkan.model.vulkanresource.ConstantBuffer object)
			{
				return createConstantBufferAdapter();
			}
			@Override
			public Adapter caseIInputProvider(IInputProvider object)
			{
				return createIInputProviderAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object)
			{
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target)
	{
		return modelSwitch.doSwitch((EObject)target);
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.sheepy.vsand.model.VSandApplication <em>Application</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sheepy.vsand.model.VSandApplication
	 * @generated
	 */
	public Adapter createVSandApplicationAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.sheepy.vsand.model.BoardConstantBuffer <em>Board Constant Buffer</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sheepy.vsand.model.BoardConstantBuffer
	 * @generated
	 */
	public Adapter createBoardConstantBufferAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.sheepy.vsand.model.DrawConstantBuffer <em>Draw Constant Buffer</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sheepy.vsand.model.DrawConstantBuffer
	 * @generated
	 */
	public Adapter createDrawConstantBufferAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.sheepy.vsand.model.PixelConstantBuffer <em>Pixel Constant Buffer</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sheepy.vsand.model.PixelConstantBuffer
	 * @generated
	 */
	public Adapter createPixelConstantBufferAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.sheepy.vsand.model.DrawCommand <em>Draw Command</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sheepy.vsand.model.DrawCommand
	 * @generated
	 */
	public Adapter createDrawCommandAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.sheepy.vsand.model.DrawCircle <em>Draw Circle</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sheepy.vsand.model.DrawCircle
	 * @generated
	 */
	public Adapter createDrawCircleAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.sheepy.vsand.model.DrawSquare <em>Draw Square</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sheepy.vsand.model.DrawSquare
	 * @generated
	 */
	public Adapter createDrawSquareAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.sheepy.vsand.model.DrawLine <em>Draw Line</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sheepy.vsand.model.DrawLine
	 * @generated
	 */
	public Adapter createDrawLineAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.sheepy.vsand.model.Materials <em>Materials</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sheepy.vsand.model.Materials
	 * @generated
	 */
	public Adapter createMaterialsAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.sheepy.vsand.model.Material <em>Material</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sheepy.vsand.model.Material
	 * @generated
	 */
	public Adapter createMaterialAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.sheepy.vsand.model.Transformations <em>Transformations</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sheepy.vsand.model.Transformations
	 * @generated
	 */
	public Adapter createTransformationsAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.sheepy.vsand.model.Transformation <em>Transformation</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sheepy.vsand.model.Transformation
	 * @generated
	 */
	public Adapter createTransformationAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.sheepy.vsand.model.MultipleTransformation <em>Multiple Transformation</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sheepy.vsand.model.MultipleTransformation
	 * @generated
	 */
	public Adapter createMultipleTransformationAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.sheepy.vsand.model.MaterialProvider <em>Material Provider</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sheepy.vsand.model.MaterialProvider
	 * @generated
	 */
	public Adapter createMaterialProviderAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.sheepy.vsand.model.ITransformation <em>ITransformation</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sheepy.vsand.model.ITransformation
	 * @generated
	 */
	public Adapter createITransformationAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.sheepy.vsand.model.InputMaterialProvider <em>Input Material Provider</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sheepy.vsand.model.InputMaterialProvider
	 * @generated
	 */
	public Adapter createInputMaterialProviderAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.sheepy.lily.core.model.application.Application <em>Application</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sheepy.lily.core.model.application.Application
	 * @generated
	 */
	public Adapter createApplicationAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.sheepy.lily.core.model.types.LNamedElement <em>LNamed Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sheepy.lily.core.model.types.LNamedElement
	 * @generated
	 */
	public Adapter createLNamedElementAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.sheepy.lily.vulkan.model.IVulkanResource <em>IVulkan Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sheepy.lily.vulkan.model.IVulkanResource
	 * @generated
	 */
	public Adapter createIVulkanResourceAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.sheepy.lily.vulkan.model.vulkanresource.ConstantBuffer <em>Constant Buffer</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sheepy.lily.vulkan.model.vulkanresource.ConstantBuffer
	 * @generated
	 */
	public Adapter createConstantBufferAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.sheepy.lily.vulkan.extra.model.nuklear.IInputProvider <em>IInput Provider</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.sheepy.lily.vulkan.extra.model.nuklear.IInputProvider
	 * @generated
	 */
	public Adapter createIInputProviderAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter()
	{
		return null;
	}

} //VSandAdapterFactory
