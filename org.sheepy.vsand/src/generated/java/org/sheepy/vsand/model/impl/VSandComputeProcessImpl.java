/**
 */
package org.sheepy.vsand.model.impl;

import org.eclipse.emf.ecore.EClass;

import org.sheepy.lily.vulkan.model.process.compute.impl.ComputeProcessImpl;

import org.sheepy.vsand.model.VSandComputeProcess;
import org.sheepy.vsand.model.VSandPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Compute Process</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class VSandComputeProcessImpl extends ComputeProcessImpl implements VSandComputeProcess
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VSandComputeProcessImpl()
	{
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass()
	{
		return VSandPackage.Literals.VSAND_COMPUTE_PROCESS;
	}

} //VSandComputeProcessImpl
