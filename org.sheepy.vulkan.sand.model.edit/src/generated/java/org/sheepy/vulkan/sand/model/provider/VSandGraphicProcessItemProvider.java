/**
 */
package org.sheepy.lily.vulkan.sand.model.provider;


import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;

import org.sheepy.lily.vulkan.model.process.ProcessPackage;

import org.sheepy.lily.vulkan.model.process.compute.ComputeFactory;

import org.sheepy.lily.vulkan.model.process.graphic.GraphicPackage;

import org.sheepy.lily.vulkan.model.process.graphic.provider.GraphicProcessItemProvider;
import org.sheepy.lily.vulkan.nuklear.model.NuklearFactory;
import org.sheepy.lily.vulkan.sand.model.VSandFactory;
import org.sheepy.lily.vulkan.sand.model.VSandGraphicProcess;

/**
 * This is the item provider adapter for a {@link org.sheepy.lily.vulkan.sand.model.VSandGraphicProcess} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class VSandGraphicProcessItemProvider extends GraphicProcessItemProvider
{
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VSandGraphicProcessItemProvider(AdapterFactory adapterFactory)
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
	 * This returns VSandGraphicProcess.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object)
	{
		return overlayImage(object, getResourceLocator().getImage("full/obj16/VSandGraphicProcess"));
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
		String label = ((VSandGraphicProcess)object).getName();
		return label == null || label.length() == 0 ?
			getString("_UI_VSandGraphicProcess_type") :
			getString("_UI_VSandGraphicProcess_type") + " " + label;
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
				 ComputeFactory.eINSTANCE.createComputePipeline()));

		newChildDescriptors.add
			(createChildParameter
				(ProcessPackage.Literals.ABSTRACT_PROCESS__UNITS,
				 NuklearFactory.eINSTANCE.createNuklearPipeline()));
	}

	/**
	 * This returns the label text for {@link org.eclipse.emf.edit.command.CreateChildCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getCreateChildText(Object owner, Object feature, Object child, Collection<?> selection)
	{
		Object childFeature = feature;
		Object childObject = child;

		boolean qualify =
			childFeature == ProcessPackage.Literals.ABSTRACT_PROCESS__RESOURCES ||
			childFeature == GraphicPackage.Literals.GRAPHIC_PROCESS__DEPTH_IMAGE;

		if (qualify)
		{
			return getString
				("_UI_CreateChild_text2",
				 new Object[] { getTypeText(childObject), getFeatureText(childFeature), getTypeText(owner) });
		}
		return super.getCreateChildText(owner, feature, child, selection);
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
