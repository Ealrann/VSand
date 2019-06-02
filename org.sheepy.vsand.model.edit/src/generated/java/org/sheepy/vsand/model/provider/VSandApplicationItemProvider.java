/**
 */
package org.sheepy.vsand.model.provider;


import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.sheepy.lily.core.model.application.ApplicationPackage;
import org.sheepy.lily.core.model.application.provider.ApplicationItemProvider;
import org.sheepy.lily.core.model.presentation.PresentationFactory;
import org.sheepy.lily.core.model.root.LObject;
import org.sheepy.vsand.model.VSandApplication;
import org.sheepy.vsand.model.VSandFactory;
import org.sheepy.vsand.model.VSandPackage;

/**
 * This is the item provider adapter for a {@link org.sheepy.vsand.model.VSandApplication} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class VSandApplicationItemProvider extends ApplicationItemProvider
{
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VSandApplicationItemProvider(AdapterFactory adapterFactory)
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

			addMainMaterialPropertyDescriptor(object);
			addSecondaryMaterialPropertyDescriptor(object);
			addNextModePropertyDescriptor(object);
			addForceClearPropertyDescriptor(object);
			addShowSleepZonesPropertyDescriptor(object);
			addBrushSizePropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Main Material feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addMainMaterialPropertyDescriptor(Object object)
	{
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_VSandApplication_mainMaterial_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_VSandApplication_mainMaterial_feature", "_UI_VSandApplication_type"),
				 VSandPackage.Literals.VSAND_APPLICATION__MAIN_MATERIAL,
				 true,
				 false,
				 true,
				 null,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Secondary Material feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addSecondaryMaterialPropertyDescriptor(Object object)
	{
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_VSandApplication_secondaryMaterial_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_VSandApplication_secondaryMaterial_feature", "_UI_VSandApplication_type"),
				 VSandPackage.Literals.VSAND_APPLICATION__SECONDARY_MATERIAL,
				 true,
				 false,
				 true,
				 null,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Next Mode feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addNextModePropertyDescriptor(Object object)
	{
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_VSandApplication_nextMode_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_VSandApplication_nextMode_feature", "_UI_VSandApplication_type"),
				 VSandPackage.Literals.VSAND_APPLICATION__NEXT_MODE,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Force Clear feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addForceClearPropertyDescriptor(Object object)
	{
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_VSandApplication_forceClear_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_VSandApplication_forceClear_feature", "_UI_VSandApplication_type"),
				 VSandPackage.Literals.VSAND_APPLICATION__FORCE_CLEAR,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Show Sleep Zones feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addShowSleepZonesPropertyDescriptor(Object object)
	{
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_VSandApplication_showSleepZones_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_VSandApplication_showSleepZones_feature", "_UI_VSandApplication_type"),
				 VSandPackage.Literals.VSAND_APPLICATION__SHOW_SLEEP_ZONES,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Brush Size feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addBrushSizePropertyDescriptor(Object object)
	{
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_VSandApplication_brushSize_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_VSandApplication_brushSize_feature", "_UI_VSandApplication_type"),
				 VSandPackage.Literals.VSAND_APPLICATION__BRUSH_SIZE,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
	 * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
	 * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object)
	{
		if (childrenFeatures == null)
		{
			super.getChildrenFeatures(object);
			childrenFeatures.add(VSandPackage.Literals.VSAND_APPLICATION__MATERIALS);
			childrenFeatures.add(VSandPackage.Literals.VSAND_APPLICATION__TRANSFORMATIONS);
			childrenFeatures.add(VSandPackage.Literals.VSAND_APPLICATION__DRAW_QUEUE);
		}
		return childrenFeatures;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EStructuralFeature getChildFeature(Object object, Object child)
	{
		// Check the type of the specified child object and return the proper feature to use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildFeature(object, child);
	}

	/**
	 * This returns VSandApplication.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object)
	{
		return overlayImage(object, getResourceLocator().getImage("full/obj16/VSandApplication"));
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
		EList<LObject> labelValue = ((VSandApplication)object).getContentObjects();
		String label = labelValue == null ? null : labelValue.toString();
		return label == null || label.length() == 0 ?
			getString("_UI_VSandApplication_type") :
			getString("_UI_VSandApplication_type") + " " + label;
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

		switch (notification.getFeatureID(VSandApplication.class))
		{
			case VSandPackage.VSAND_APPLICATION__NEXT_MODE:
			case VSandPackage.VSAND_APPLICATION__FORCE_CLEAR:
			case VSandPackage.VSAND_APPLICATION__SHOW_SLEEP_ZONES:
			case VSandPackage.VSAND_APPLICATION__BRUSH_SIZE:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case VSandPackage.VSAND_APPLICATION__MATERIALS:
			case VSandPackage.VSAND_APPLICATION__TRANSFORMATIONS:
			case VSandPackage.VSAND_APPLICATION__DRAW_QUEUE:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
				return;
		}
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
				(ApplicationPackage.Literals.APPLICATION__VIEWS,
				 PresentationFactory.eINSTANCE.createTranparentUIView()));

		newChildDescriptors.add
			(createChildParameter
				(VSandPackage.Literals.VSAND_APPLICATION__MATERIALS,
				 VSandFactory.eINSTANCE.createMaterials()));

		newChildDescriptors.add
			(createChildParameter
				(VSandPackage.Literals.VSAND_APPLICATION__TRANSFORMATIONS,
				 VSandFactory.eINSTANCE.createTransformations()));

		newChildDescriptors.add
			(createChildParameter
				(VSandPackage.Literals.VSAND_APPLICATION__DRAW_QUEUE,
				 VSandFactory.eINSTANCE.createDrawCircle()));

		newChildDescriptors.add
			(createChildParameter
				(VSandPackage.Literals.VSAND_APPLICATION__DRAW_QUEUE,
				 VSandFactory.eINSTANCE.createDrawSquare()));

		newChildDescriptors.add
			(createChildParameter
				(VSandPackage.Literals.VSAND_APPLICATION__DRAW_QUEUE,
				 VSandFactory.eINSTANCE.createDrawLine()));
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
