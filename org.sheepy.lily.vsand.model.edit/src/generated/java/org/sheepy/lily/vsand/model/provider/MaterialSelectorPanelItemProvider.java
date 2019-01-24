/**
 */
package org.sheepy.vsand.model.provider;


import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;

import org.sheepy.lily.core.model.presentation.PresentationPackage;

import org.sheepy.lily.core.model.types.TypesPackage;

import org.sheepy.vsand.model.MaterialSelectorPanel;
import org.sheepy.vsand.model.VSandPackage;

/**
 * This is the item provider adapter for a {@link org.sheepy.vsand.model.MaterialSelectorPanel} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class MaterialSelectorPanelItemProvider 
	extends ItemProviderAdapter
	implements
		IEditingDomainItemProvider,
		IStructuredItemContentProvider,
		ITreeItemContentProvider,
		IItemLabelProvider,
		IItemPropertySource
{
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MaterialSelectorPanelItemProvider(AdapterFactory adapterFactory)
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

			addNamePropertyDescriptor(object);
			addPositionPropertyDescriptor(object);
			addVerticalRelativePropertyDescriptor(object);
			addHorizontalRelativePropertyDescriptor(object);
			addLineHeightPropertyDescriptor(object);
			addPrimaryRPropertyDescriptor(object);
			addPrimaryGPropertyDescriptor(object);
			addPrimaryBPropertyDescriptor(object);
			addSecondaryRPropertyDescriptor(object);
			addSecondaryGPropertyDescriptor(object);
			addSecondaryBPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Name feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addNamePropertyDescriptor(Object object)
	{
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_LNamedElement_name_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_LNamedElement_name_feature", "_UI_LNamedElement_type"),
				 TypesPackage.Literals.LNAMED_ELEMENT__NAME,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Position feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addPositionPropertyDescriptor(Object object)
	{
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_IPositionElement_position_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_IPositionElement_position_feature", "_UI_IPositionElement_type"),
				 PresentationPackage.Literals.IPOSITION_ELEMENT__POSITION,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Vertical Relative feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addVerticalRelativePropertyDescriptor(Object object)
	{
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_IPositionElement_verticalRelative_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_IPositionElement_verticalRelative_feature", "_UI_IPositionElement_type"),
				 PresentationPackage.Literals.IPOSITION_ELEMENT__VERTICAL_RELATIVE,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Horizontal Relative feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addHorizontalRelativePropertyDescriptor(Object object)
	{
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_IPositionElement_horizontalRelative_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_IPositionElement_horizontalRelative_feature", "_UI_IPositionElement_type"),
				 PresentationPackage.Literals.IPOSITION_ELEMENT__HORIZONTAL_RELATIVE,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Line Height feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addLineHeightPropertyDescriptor(Object object)
	{
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_MaterialSelectorPanel_lineHeight_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_MaterialSelectorPanel_lineHeight_feature", "_UI_MaterialSelectorPanel_type"),
				 VSandPackage.Literals.MATERIAL_SELECTOR_PANEL__LINE_HEIGHT,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				 getString("_UI_UIPropertyCategory"),
				 null));
	}

	/**
	 * This adds a property descriptor for the Primary R feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addPrimaryRPropertyDescriptor(Object object)
	{
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_MaterialSelectorPanel_primaryR_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_MaterialSelectorPanel_primaryR_feature", "_UI_MaterialSelectorPanel_type"),
				 VSandPackage.Literals.MATERIAL_SELECTOR_PANEL__PRIMARY_R,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				 getString("_UI_PrimaryColorPropertyCategory"),
				 null));
	}

	/**
	 * This adds a property descriptor for the Primary G feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addPrimaryGPropertyDescriptor(Object object)
	{
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_MaterialSelectorPanel_primaryG_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_MaterialSelectorPanel_primaryG_feature", "_UI_MaterialSelectorPanel_type"),
				 VSandPackage.Literals.MATERIAL_SELECTOR_PANEL__PRIMARY_G,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				 getString("_UI_PrimaryColorPropertyCategory"),
				 null));
	}

	/**
	 * This adds a property descriptor for the Primary B feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addPrimaryBPropertyDescriptor(Object object)
	{
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_MaterialSelectorPanel_primaryB_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_MaterialSelectorPanel_primaryB_feature", "_UI_MaterialSelectorPanel_type"),
				 VSandPackage.Literals.MATERIAL_SELECTOR_PANEL__PRIMARY_B,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				 getString("_UI_PrimaryColorPropertyCategory"),
				 null));
	}

	/**
	 * This adds a property descriptor for the Secondary R feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addSecondaryRPropertyDescriptor(Object object)
	{
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_MaterialSelectorPanel_secondaryR_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_MaterialSelectorPanel_secondaryR_feature", "_UI_MaterialSelectorPanel_type"),
				 VSandPackage.Literals.MATERIAL_SELECTOR_PANEL__SECONDARY_R,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				 getString("_UI_SecondaryColorPropertyCategory"),
				 null));
	}

	/**
	 * This adds a property descriptor for the Secondary G feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addSecondaryGPropertyDescriptor(Object object)
	{
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_MaterialSelectorPanel_secondaryG_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_MaterialSelectorPanel_secondaryG_feature", "_UI_MaterialSelectorPanel_type"),
				 VSandPackage.Literals.MATERIAL_SELECTOR_PANEL__SECONDARY_G,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				 getString("_UI_SecondaryColorPropertyCategory"),
				 null));
	}

	/**
	 * This adds a property descriptor for the Secondary B feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addSecondaryBPropertyDescriptor(Object object)
	{
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_MaterialSelectorPanel_secondaryB_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_MaterialSelectorPanel_secondaryB_feature", "_UI_MaterialSelectorPanel_type"),
				 VSandPackage.Literals.MATERIAL_SELECTOR_PANEL__SECONDARY_B,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				 getString("_UI_SecondaryColorPropertyCategory"),
				 null));
	}

	/**
	 * This returns MaterialSelectorPanel.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object)
	{
		return overlayImage(object, getResourceLocator().getImage("full/obj16/MaterialSelectorPanel"));
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
		String label = ((MaterialSelectorPanel)object).getName();
		return label == null || label.length() == 0 ?
			getString("_UI_MaterialSelectorPanel_type") :
			getString("_UI_MaterialSelectorPanel_type") + " " + label;
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

		switch (notification.getFeatureID(MaterialSelectorPanel.class))
		{
			case VSandPackage.MATERIAL_SELECTOR_PANEL__NAME:
			case VSandPackage.MATERIAL_SELECTOR_PANEL__CONTENT_OBJECTS:
			case VSandPackage.MATERIAL_SELECTOR_PANEL__POSITION:
			case VSandPackage.MATERIAL_SELECTOR_PANEL__VERTICAL_RELATIVE:
			case VSandPackage.MATERIAL_SELECTOR_PANEL__HORIZONTAL_RELATIVE:
			case VSandPackage.MATERIAL_SELECTOR_PANEL__LINE_HEIGHT:
			case VSandPackage.MATERIAL_SELECTOR_PANEL__PRIMARY_R:
			case VSandPackage.MATERIAL_SELECTOR_PANEL__PRIMARY_G:
			case VSandPackage.MATERIAL_SELECTOR_PANEL__PRIMARY_B:
			case VSandPackage.MATERIAL_SELECTOR_PANEL__SECONDARY_R:
			case VSandPackage.MATERIAL_SELECTOR_PANEL__SECONDARY_G:
			case VSandPackage.MATERIAL_SELECTOR_PANEL__SECONDARY_B:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
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
