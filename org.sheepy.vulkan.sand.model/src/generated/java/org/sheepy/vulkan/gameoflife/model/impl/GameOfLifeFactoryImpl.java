/**
 */
package org.sheepy.vulkan.gameoflife.model.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.sheepy.vulkan.gameoflife.model.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class GameOfLifeFactoryImpl extends EFactoryImpl implements GameOfLifeFactory
{
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static GameOfLifeFactory init()
	{
		try
		{
			GameOfLifeFactory theGameOfLifeFactory = (GameOfLifeFactory)EPackage.Registry.INSTANCE.getEFactory(GameOfLifePackage.eNS_URI);
			if (theGameOfLifeFactory != null)
			{
				return theGameOfLifeFactory;
			}
		}
		catch (Exception exception)
		{
			EcorePlugin.INSTANCE.log(exception);
		}
		return new GameOfLifeFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GameOfLifeFactoryImpl()
	{
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject create(EClass eClass)
	{
		switch (eClass.getClassifierID())
		{
			case GameOfLifePackage.BOARD_BUFFER: return createBoardBuffer();
			case GameOfLifePackage.BOARD_IMAGE: return createBoardImage();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BoardBuffer createBoardBuffer()
	{
		BoardBufferImpl boardBuffer = new BoardBufferImpl();
		return boardBuffer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BoardImage createBoardImage()
	{
		BoardImageImpl boardImage = new BoardImageImpl();
		return boardImage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GameOfLifePackage getGameOfLifePackage()
	{
		return (GameOfLifePackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	public static GameOfLifePackage getPackage()
	{
		return GameOfLifePackage.eINSTANCE;
	}

} //GameOfLifeFactoryImpl
