/**
 */
package org.sheepy.lily.vulkan.nuklear.model.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.sheepy.lily.vulkan.nuklear.model.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class NuklearFactoryImpl extends EFactoryImpl implements NuklearFactory
{
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static NuklearFactory init()
	{
		try
		{
			NuklearFactory theNuklearFactory = (NuklearFactory)EPackage.Registry.INSTANCE.getEFactory(NuklearPackage.eNS_URI);
			if (theNuklearFactory != null)
			{
				return theNuklearFactory;
			}
		}
		catch (Exception exception)
		{
			EcorePlugin.INSTANCE.log(exception);
		}
		return new NuklearFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NuklearFactoryImpl()
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
			case NuklearPackage.NUKLEAR_PIPELINE: return createNuklearPipeline();
			case NuklearPackage.NUKLEAR_CONSTANTS: return createNuklearConstants();
			case NuklearPackage.NUKLEAR_INDEX_BUFFER: return createNuklearIndexBuffer();
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
	public NuklearPipeline createNuklearPipeline()
	{
		NuklearPipelineImpl nuklearPipeline = new NuklearPipelineImpl();
		return nuklearPipeline;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NuklearConstants createNuklearConstants()
	{
		NuklearConstantsImpl nuklearConstants = new NuklearConstantsImpl();
		return nuklearConstants;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NuklearIndexBuffer createNuklearIndexBuffer()
	{
		NuklearIndexBufferImpl nuklearIndexBuffer = new NuklearIndexBufferImpl();
		return nuklearIndexBuffer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NuklearPackage getNuklearPackage()
	{
		return (NuklearPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static NuklearPackage getPackage()
	{
		return NuklearPackage.eINSTANCE;
	}

} //NuklearFactoryImpl
