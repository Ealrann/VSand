/**
 */
package org.sheepy.vsand.model.provider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.edit.command.CommandParameter;

import org.eclipse.emf.edit.domain.EditingDomain;

import org.eclipse.emf.edit.provider.ChangeNotifier;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IChangeNotifier;
import org.eclipse.emf.edit.provider.IChildCreationExtender;
import org.eclipse.emf.edit.provider.IDisposable;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;

import org.sheepy.lily.core.model.presentation.PresentationPackage;
import org.sheepy.lily.core.model.presentation.UIPage;

import org.sheepy.lily.core.model.presentation.util.PresentationSwitch;

import org.sheepy.lily.vulkan.model.ResourcePkg;
import org.sheepy.lily.vulkan.model.VulkanPackage;

import org.sheepy.lily.vulkan.model.util.VulkanSwitch;

import org.sheepy.vsand.model.VSandFactory;

import org.sheepy.vsand.model.util.VSandAdapterFactory;

/**
 * This is the factory that is used to provide the interfaces needed to support Viewers.
 * The adapters generated by this factory convert EMF adapter notifications into calls to {@link #fireNotifyChanged fireNotifyChanged}.
 * The adapters also support Eclipse property sheets.
 * Note that most of the adapters are shared among multiple instances.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class VSandItemProviderAdapterFactory extends VSandAdapterFactory
		implements ComposeableAdapterFactory, IChangeNotifier, IDisposable
{
	/**
	 * This keeps track of the root adapter factory that delegates to this adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ComposedAdapterFactory parentAdapterFactory;

	/**
	 * This is used to implement {@link org.eclipse.emf.edit.provider.IChangeNotifier}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IChangeNotifier changeNotifier = new ChangeNotifier();

	/**
	 * This keeps track of all the supported types checked by {@link #isFactoryForType isFactoryForType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Collection<Object> supportedTypes = new ArrayList<Object>();

	/**
	 * This constructs an instance.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VSandItemProviderAdapterFactory()
	{
		supportedTypes.add(IEditingDomainItemProvider.class);
		supportedTypes.add(IStructuredItemContentProvider.class);
		supportedTypes.add(ITreeItemContentProvider.class);
		supportedTypes.add(IItemLabelProvider.class);
		supportedTypes.add(IItemPropertySource.class);
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.sheepy.vsand.model.VSandApplication} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected VSandApplicationItemProvider vSandApplicationItemProvider;

	/**
	 * This creates an adapter for a {@link org.sheepy.vsand.model.VSandApplication}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createVSandApplicationAdapter()
	{
		if (vSandApplicationItemProvider == null)
		{
			vSandApplicationItemProvider = new VSandApplicationItemProvider(this);
		}

		return vSandApplicationItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.sheepy.vsand.model.BoardConstantBuffer} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BoardConstantBufferItemProvider boardConstantBufferItemProvider;

	/**
	 * This creates an adapter for a {@link org.sheepy.vsand.model.BoardConstantBuffer}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createBoardConstantBufferAdapter()
	{
		if (boardConstantBufferItemProvider == null)
		{
			boardConstantBufferItemProvider = new BoardConstantBufferItemProvider(this);
		}

		return boardConstantBufferItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.sheepy.vsand.model.DrawConstantBuffer} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DrawConstantBufferItemProvider drawConstantBufferItemProvider;

	/**
	 * This creates an adapter for a {@link org.sheepy.vsand.model.DrawConstantBuffer}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createDrawConstantBufferAdapter()
	{
		if (drawConstantBufferItemProvider == null)
		{
			drawConstantBufferItemProvider = new DrawConstantBufferItemProvider(this);
		}

		return drawConstantBufferItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.sheepy.vsand.model.PixelConstantBuffer} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PixelConstantBufferItemProvider pixelConstantBufferItemProvider;

	/**
	 * This creates an adapter for a {@link org.sheepy.vsand.model.PixelConstantBuffer}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createPixelConstantBufferAdapter()
	{
		if (pixelConstantBufferItemProvider == null)
		{
			pixelConstantBufferItemProvider = new PixelConstantBufferItemProvider(this);
		}

		return pixelConstantBufferItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.sheepy.vsand.model.DrawCircle} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DrawCircleItemProvider drawCircleItemProvider;

	/**
	 * This creates an adapter for a {@link org.sheepy.vsand.model.DrawCircle}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createDrawCircleAdapter()
	{
		if (drawCircleItemProvider == null)
		{
			drawCircleItemProvider = new DrawCircleItemProvider(this);
		}

		return drawCircleItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.sheepy.vsand.model.DrawSquare} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DrawSquareItemProvider drawSquareItemProvider;

	/**
	 * This creates an adapter for a {@link org.sheepy.vsand.model.DrawSquare}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createDrawSquareAdapter()
	{
		if (drawSquareItemProvider == null)
		{
			drawSquareItemProvider = new DrawSquareItemProvider(this);
		}

		return drawSquareItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.sheepy.vsand.model.DrawLine} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DrawLineItemProvider drawLineItemProvider;

	/**
	 * This creates an adapter for a {@link org.sheepy.vsand.model.DrawLine}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createDrawLineAdapter()
	{
		if (drawLineItemProvider == null)
		{
			drawLineItemProvider = new DrawLineItemProvider(this);
		}

		return drawLineItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.sheepy.vsand.model.Materials} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MaterialsItemProvider materialsItemProvider;

	/**
	 * This creates an adapter for a {@link org.sheepy.vsand.model.Materials}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createMaterialsAdapter()
	{
		if (materialsItemProvider == null)
		{
			materialsItemProvider = new MaterialsItemProvider(this);
		}

		return materialsItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.sheepy.vsand.model.Material} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MaterialItemProvider materialItemProvider;

	/**
	 * This creates an adapter for a {@link org.sheepy.vsand.model.Material}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createMaterialAdapter()
	{
		if (materialItemProvider == null)
		{
			materialItemProvider = new MaterialItemProvider(this);
		}

		return materialItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.sheepy.vsand.model.Transformations} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TransformationsItemProvider transformationsItemProvider;

	/**
	 * This creates an adapter for a {@link org.sheepy.vsand.model.Transformations}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createTransformationsAdapter()
	{
		if (transformationsItemProvider == null)
		{
			transformationsItemProvider = new TransformationsItemProvider(this);
		}

		return transformationsItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.sheepy.vsand.model.Transformation} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TransformationItemProvider transformationItemProvider;

	/**
	 * This creates an adapter for a {@link org.sheepy.vsand.model.Transformation}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createTransformationAdapter()
	{
		if (transformationItemProvider == null)
		{
			transformationItemProvider = new TransformationItemProvider(this);
		}

		return transformationItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.sheepy.vsand.model.MaterialSelectorPanel} instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MaterialSelectorPanelItemProvider materialSelectorPanelItemProvider;

	/**
	 * This creates an adapter for a {@link org.sheepy.vsand.model.MaterialSelectorPanel}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter createMaterialSelectorPanelAdapter()
	{
		if (materialSelectorPanelItemProvider == null)
		{
			materialSelectorPanelItemProvider = new MaterialSelectorPanelItemProvider(this);
		}

		return materialSelectorPanelItemProvider;
	}

	/**
	 * This returns the root adapter factory that contains this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ComposeableAdapterFactory getRootAdapterFactory()
	{
		return parentAdapterFactory == null ? this : parentAdapterFactory.getRootAdapterFactory();
	}

	/**
	 * This sets the composed adapter factory that contains this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setParentAdapterFactory(ComposedAdapterFactory parentAdapterFactory)
	{
		this.parentAdapterFactory = parentAdapterFactory;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object type)
	{
		return supportedTypes.contains(type) || super.isFactoryForType(type);
	}

	/**
	 * This implementation substitutes the factory itself as the key for the adapter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Adapter adapt(Notifier notifier, Object type)
	{
		return super.adapt(notifier, this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object adapt(Object object, Object type)
	{
		if (isFactoryForType(type))
		{
			Object adapter = super.adapt(object, type);
			if (!(type instanceof Class<?>) || (((Class<?>)type).isInstance(adapter)))
			{
				return adapter;
			}
		}

		return null;
	}

	/**
	 * This adds a listener.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void addListener(INotifyChangedListener notifyChangedListener)
	{
		changeNotifier.addListener(notifyChangedListener);
	}

	/**
	 * This removes a listener.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void removeListener(INotifyChangedListener notifyChangedListener)
	{
		changeNotifier.removeListener(notifyChangedListener);
	}

	/**
	 * This delegates to {@link #changeNotifier} and to {@link #parentAdapterFactory}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void fireNotifyChanged(Notification notification)
	{
		changeNotifier.fireNotifyChanged(notification);

		if (parentAdapterFactory != null)
		{
			parentAdapterFactory.fireNotifyChanged(notification);
		}
	}

	/**
	 * This disposes all of the item providers created by this factory. 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void dispose()
	{
		if (vSandApplicationItemProvider != null) vSandApplicationItemProvider.dispose();
		if (boardConstantBufferItemProvider != null) boardConstantBufferItemProvider.dispose();
		if (drawConstantBufferItemProvider != null) drawConstantBufferItemProvider.dispose();
		if (pixelConstantBufferItemProvider != null) pixelConstantBufferItemProvider.dispose();
		if (drawCircleItemProvider != null) drawCircleItemProvider.dispose();
		if (drawSquareItemProvider != null) drawSquareItemProvider.dispose();
		if (drawLineItemProvider != null) drawLineItemProvider.dispose();
		if (materialsItemProvider != null) materialsItemProvider.dispose();
		if (materialItemProvider != null) materialItemProvider.dispose();
		if (transformationsItemProvider != null) transformationsItemProvider.dispose();
		if (transformationItemProvider != null) transformationItemProvider.dispose();
		if (materialSelectorPanelItemProvider != null) materialSelectorPanelItemProvider.dispose();
	}

	/**
	 * A child creation extender for the {@link PresentationPackage}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static class PresentationChildCreationExtender implements IChildCreationExtender
	{
		/**
		 * The switch for creating child descriptors specific to each extended class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		protected static class CreationSwitch extends PresentationSwitch<Object>
		{
			/**
			 * The child descriptors being populated.
			 * <!-- begin-user-doc -->
			 * <!-- end-user-doc -->
			 * @generated
			 */
			protected List<Object> newChildDescriptors;

			/**
			 * The domain in which to create the children.
			 * <!-- begin-user-doc -->
			 * <!-- end-user-doc -->
			 * @generated
			 */
			protected EditingDomain editingDomain;

			/**
			 * Creates the a switch for populating child descriptors in the given domain.
			 * <!-- begin-user-doc -->
			 * <!-- end-user-doc -->
			 * @generated
			 */
			CreationSwitch(List<Object> newChildDescriptors, EditingDomain editingDomain)
			{
				this.newChildDescriptors = newChildDescriptors;
				this.editingDomain = editingDomain;
			}
			/**
			 * <!-- begin-user-doc -->
			 * <!-- end-user-doc -->
			 * @generated
			 */
			@Override
			public Object caseUIPage(UIPage object)
			{
				newChildDescriptors.add
					(createChildParameter
						(PresentationPackage.Literals.UI_PAGE__PANELS,
						 VSandFactory.eINSTANCE.createMaterialSelectorPanel()));

				return null;
			}

			/**
			 * <!-- begin-user-doc -->
			 * <!-- end-user-doc -->
			 * @generated
			 */
			protected CommandParameter createChildParameter(Object feature, Object child)
			{
				return new CommandParameter(null, feature, child);
			}

		}

		/**
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		@Override
		public Collection<Object> getNewChildDescriptors(Object object, EditingDomain editingDomain)
		{
			ArrayList<Object> result = new ArrayList<Object>();
			new CreationSwitch(result, editingDomain).doSwitch((EObject)object);
			return result;
		}

		/**
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

	/**
	 * A child creation extender for the {@link VulkanPackage}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static class VulkanChildCreationExtender implements IChildCreationExtender
	{
		/**
		 * The switch for creating child descriptors specific to each extended class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		protected static class CreationSwitch extends VulkanSwitch<Object>
		{
			/**
			 * The child descriptors being populated.
			 * <!-- begin-user-doc -->
			 * <!-- end-user-doc -->
			 * @generated
			 */
			protected List<Object> newChildDescriptors;

			/**
			 * The domain in which to create the children.
			 * <!-- begin-user-doc -->
			 * <!-- end-user-doc -->
			 * @generated
			 */
			protected EditingDomain editingDomain;

			/**
			 * Creates the a switch for populating child descriptors in the given domain.
			 * <!-- begin-user-doc -->
			 * <!-- end-user-doc -->
			 * @generated
			 */
			CreationSwitch(List<Object> newChildDescriptors, EditingDomain editingDomain)
			{
				this.newChildDescriptors = newChildDescriptors;
				this.editingDomain = editingDomain;
			}
			/**
			 * <!-- begin-user-doc -->
			 * <!-- end-user-doc -->
			 * @generated
			 */
			@Override
			public Object caseResourcePkg(ResourcePkg object)
			{
				newChildDescriptors.add
					(createChildParameter
						(VulkanPackage.Literals.RESOURCE_PKG__RESOURCES,
						 VSandFactory.eINSTANCE.createBoardConstantBuffer()));

				newChildDescriptors.add
					(createChildParameter
						(VulkanPackage.Literals.RESOURCE_PKG__RESOURCES,
						 VSandFactory.eINSTANCE.createDrawConstantBuffer()));

				newChildDescriptors.add
					(createChildParameter
						(VulkanPackage.Literals.RESOURCE_PKG__RESOURCES,
						 VSandFactory.eINSTANCE.createPixelConstantBuffer()));

				return null;
			}

			/**
			 * <!-- begin-user-doc -->
			 * <!-- end-user-doc -->
			 * @generated
			 */
			protected CommandParameter createChildParameter(Object feature, Object child)
			{
				return new CommandParameter(null, feature, child);
			}

		}

		/**
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		@Override
		public Collection<Object> getNewChildDescriptors(Object object, EditingDomain editingDomain)
		{
			ArrayList<Object> result = new ArrayList<Object>();
			new CreationSwitch(result, editingDomain).doSwitch((EObject)object);
			return result;
		}

		/**
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

}
