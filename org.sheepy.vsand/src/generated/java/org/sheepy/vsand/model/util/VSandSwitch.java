/**
 */
package org.sheepy.vsand.model.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;
import org.sheepy.lily.core.model.application.Application;
import org.sheepy.lily.core.model.types.LNamedElement;
import org.sheepy.lily.vulkan.extra.model.nuklear.IInputProvider;
import org.sheepy.lily.vulkan.model.IVulkanResource;
import org.sheepy.lily.vulkan.model.resource.ConstantBuffer;
import org.sheepy.vsand.model.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.sheepy.vsand.model.VSandPackage
 * @generated
 */
public class VSandSwitch<T> extends Switch<T>
{
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static VSandPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VSandSwitch()
	{
		if (modelPackage == null)
		{
			modelPackage = VSandPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage)
	{
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject)
	{
		switch (classifierID)
		{
			case VSandPackage.VSAND_APPLICATION:
			{
				VSandApplication vSandApplication = (VSandApplication)theEObject;
				T result = caseVSandApplication(vSandApplication);
				if (result == null) result = caseApplication(vSandApplication);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case VSandPackage.BOARD_CONSTANT_BUFFER:
			{
				BoardConstantBuffer boardConstantBuffer = (BoardConstantBuffer)theEObject;
				T result = caseBoardConstantBuffer(boardConstantBuffer);
				if (result == null) result = caseConstantBuffer(boardConstantBuffer);
				if (result == null) result = caseIVulkanResource(boardConstantBuffer);
				if (result == null) result = caseLNamedElement(boardConstantBuffer);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case VSandPackage.DRAW_CONSTANT_BUFFER:
			{
				DrawConstantBuffer drawConstantBuffer = (DrawConstantBuffer)theEObject;
				T result = caseDrawConstantBuffer(drawConstantBuffer);
				if (result == null) result = caseBoardConstantBuffer(drawConstantBuffer);
				if (result == null) result = caseConstantBuffer(drawConstantBuffer);
				if (result == null) result = caseIVulkanResource(drawConstantBuffer);
				if (result == null) result = caseLNamedElement(drawConstantBuffer);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case VSandPackage.PIXEL_CONSTANT_BUFFER:
			{
				PixelConstantBuffer pixelConstantBuffer = (PixelConstantBuffer)theEObject;
				T result = casePixelConstantBuffer(pixelConstantBuffer);
				if (result == null) result = caseBoardConstantBuffer(pixelConstantBuffer);
				if (result == null) result = caseConstantBuffer(pixelConstantBuffer);
				if (result == null) result = caseIVulkanResource(pixelConstantBuffer);
				if (result == null) result = caseLNamedElement(pixelConstantBuffer);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case VSandPackage.DRAW_COMMAND:
			{
				DrawCommand drawCommand = (DrawCommand)theEObject;
				T result = caseDrawCommand(drawCommand);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case VSandPackage.DRAW_CIRCLE:
			{
				DrawCircle drawCircle = (DrawCircle)theEObject;
				T result = caseDrawCircle(drawCircle);
				if (result == null) result = caseDrawCommand(drawCircle);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case VSandPackage.DRAW_SQUARE:
			{
				DrawSquare drawSquare = (DrawSquare)theEObject;
				T result = caseDrawSquare(drawSquare);
				if (result == null) result = caseDrawCommand(drawSquare);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case VSandPackage.DRAW_LINE:
			{
				DrawLine drawLine = (DrawLine)theEObject;
				T result = caseDrawLine(drawLine);
				if (result == null) result = caseDrawCommand(drawLine);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case VSandPackage.MATERIALS:
			{
				Materials materials = (Materials)theEObject;
				T result = caseMaterials(materials);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case VSandPackage.MATERIAL:
			{
				Material material = (Material)theEObject;
				T result = caseMaterial(material);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case VSandPackage.TRANSFORMATIONS:
			{
				Transformations transformations = (Transformations)theEObject;
				T result = caseTransformations(transformations);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case VSandPackage.TRANSFORMATION:
			{
				Transformation transformation = (Transformation)theEObject;
				T result = caseTransformation(transformation);
				if (result == null) result = caseITransformation(transformation);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case VSandPackage.MULTIPLE_TRANSFORMATION:
			{
				MultipleTransformation multipleTransformation = (MultipleTransformation)theEObject;
				T result = caseMultipleTransformation(multipleTransformation);
				if (result == null) result = caseITransformation(multipleTransformation);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case VSandPackage.MATERIAL_PROVIDER:
			{
				MaterialProvider materialProvider = (MaterialProvider)theEObject;
				T result = caseMaterialProvider(materialProvider);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case VSandPackage.ITRANSFORMATION:
			{
				ITransformation iTransformation = (ITransformation)theEObject;
				T result = caseITransformation(iTransformation);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case VSandPackage.INPUT_MATERIAL_PROVIDER:
			{
				InputMaterialProvider inputMaterialProvider = (InputMaterialProvider)theEObject;
				T result = caseInputMaterialProvider(inputMaterialProvider);
				if (result == null) result = caseIInputProvider(inputMaterialProvider);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Application</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Application</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVSandApplication(VSandApplication object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Board Constant Buffer</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Board Constant Buffer</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBoardConstantBuffer(BoardConstantBuffer object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Draw Constant Buffer</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Draw Constant Buffer</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDrawConstantBuffer(DrawConstantBuffer object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Pixel Constant Buffer</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Pixel Constant Buffer</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePixelConstantBuffer(PixelConstantBuffer object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Draw Command</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Draw Command</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDrawCommand(DrawCommand object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Draw Circle</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Draw Circle</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDrawCircle(DrawCircle object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Draw Square</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Draw Square</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDrawSquare(DrawSquare object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Draw Line</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Draw Line</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDrawLine(DrawLine object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Materials</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Materials</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMaterials(Materials object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Material</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Material</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMaterial(Material object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Transformations</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Transformations</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTransformations(Transformations object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Transformation</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Transformation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTransformation(Transformation object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Multiple Transformation</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Multiple Transformation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMultipleTransformation(MultipleTransformation object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Material Provider</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Material Provider</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMaterialProvider(MaterialProvider object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ITransformation</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ITransformation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseITransformation(ITransformation object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Input Material Provider</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Input Material Provider</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseInputMaterialProvider(InputMaterialProvider object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Application</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Application</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseApplication(Application object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>LNamed Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>LNamed Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLNamedElement(LNamedElement object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IVulkan Resource</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IVulkan Resource</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIVulkanResource(IVulkanResource object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Constant Buffer</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Constant Buffer</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseConstantBuffer(ConstantBuffer object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IInput Provider</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IInput Provider</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIInputProvider(IInputProvider object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object)
	{
		return null;
	}

} //VSandSwitch
