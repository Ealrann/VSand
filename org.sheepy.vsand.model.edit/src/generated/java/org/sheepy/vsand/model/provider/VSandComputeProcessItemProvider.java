/**
 */
package org.sheepy.vsand.model.provider;


import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;

import org.sheepy.lily.vulkan.model.process.ProcessPackage;

import org.sheepy.lily.vulkan.model.process.compute.provider.ComputeProcessItemProvider;

import org.sheepy.lily.vulkan.model.process.graphic.GraphicFactory;
import org.sheepy.lily.vulkan.nuklear.model.NuklearFactory;
import org.sheepy.vsand.model.VSandComputeProcess;
import org.sheepy.vsand.model.VSandFactory;

/**
 * This is the item provider adapter for a {@link org.sheepy.vsand.model.VSandComputeProcess} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class VSandComputeProcessItemProvider extends ComputeProcessItemProvider
{
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VSandComputeProcessItemProvider(AdapterFactory adapterFactory)
	{
		super(adapterFactory);
	}

	/**
	 * This returns the property descriptors for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object)
	{
		if (itemPropertyDescriptors == null)
		{
			super.getPropertyDescriptors(object);

		}
		return itemPropertyDescriptors;
	}

	/**
	 * This returns VSandComputeProcess.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object)
	{
		return overlayImage(object, getResourceLocator().getImage("full/obj16/VSandComputeProcess"));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object)
	{
		String label = ((VSandComputeProcess)object).getName();
		return label == null || label.length() == 0 ?
			getString("_UI_VSandComputeProcess_type") :
			getString("_UI_VSandComputeProcess_type") + " " + label;
	}


	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification)
	{
		updateChildren(notification);
		super.notifyChanged(notification);
	}

	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
	 * that can be created under this object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object)
	{
		super.collectNewChildDescriptors(newChildDescriptors, object);

		newChildDescriptors.add
			(createChildParameter
				(ProcessPackage.Literals.ABSTRACT_PROCESS__RESOURCES,
				 VSandFactory.eINSTANCE.createVSandConstants()));

		newChildDescriptors.add
			(createChildParameter
				(ProcessPackage.Literals.ABSTRACT_PROCESS__RESOURCES,
				 NuklearFactory.eINSTANCE.createNuklearConstants()));

		newChildDescriptors.add
			(createChildParameter
				(ProcessPackage.Literals.ABSTRACT_PROCESS__RESOURCES,
				 NuklearFactory.eINSTANCE.createNuklearIndexBuffer()));

		newChildDescriptors.add
			(createChildParameter
				(ProcessPackage.Literals.ABSTRACT_PROCESS__UNITS,
				 VSandFactory.eINSTANCE.createRepeatComputePipeline()));

		newChildDescriptors.add
			(createChildParameter
				(ProcessPackage.Literals.ABSTRACT_PROCESS__UNITS,
				 NuklearFactory.eINSTANCE.createNuklearPipeline()));

		newChildDescriptors.add
			(createChildParameter
				(ProcessPackage.Literals.ABSTRACT_PROCESS__UNITS,
				 GraphicFactory.eINSTANCE.createImagePipeline()));
	}

	/**
	 * Return the resource locator for this item provider's resources.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator()
	{
		return VSandEditPlugin.INSTANCE;
	}

}
