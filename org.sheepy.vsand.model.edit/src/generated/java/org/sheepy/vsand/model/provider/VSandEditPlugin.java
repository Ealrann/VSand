/**
 */
package org.sheepy.vsand.model.provider;

import org.eclipse.emf.common.EMFPlugin;

import org.eclipse.emf.common.util.ResourceLocator;
import org.sheepy.lily.core.model.action.provider.ActionEditPlugin;
import org.sheepy.lily.core.model.application.provider.ApplicationEditPlugin;
import org.sheepy.lily.core.model.inference.provider.InferenceEditPlugin;
import org.sheepy.lily.core.model.presentation.provider.PresentationEditPlugin;
import org.sheepy.lily.core.model.root.provider.RootEditPlugin;
import org.sheepy.lily.core.model.types.provider.TypesEditPlugin;
import org.sheepy.lily.vulkan.model.process.compute.provider.ComputeEditPlugin;
import org.sheepy.lily.vulkan.model.process.provider.ProcessEditPlugin;
import org.sheepy.lily.vulkan.model.provider.VulkanEditPlugin;
import org.sheepy.lily.vulkan.model.resource.provider.ResourceEditPlugin;
import org.sheepy.vulkan.model.barrier.provider.BarrierEditPlugin;
import org.sheepy.vulkan.model.enumeration.provider.EnumerationEditPlugin;

/**
 * This is the central singleton for the VSand edit plugin.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public final class VSandEditPlugin extends EMFPlugin
{
	/**
	 * Keep track of the singleton.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final VSandEditPlugin INSTANCE = new VSandEditPlugin();

	/**
	 * Keep track of the singleton.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static Implementation plugin;

	/**
	 * Create the instance.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VSandEditPlugin()
	{
		super
		  (new ResourceLocator [] 
		   {
		     ApplicationEditPlugin.INSTANCE,
		     RootEditPlugin.INSTANCE,
		     InferenceEditPlugin.INSTANCE,
		     ComputeEditPlugin.INSTANCE,
		     TypesEditPlugin.INSTANCE,
		     ProcessEditPlugin.INSTANCE,
		     VulkanEditPlugin.INSTANCE,
		     ResourceEditPlugin.INSTANCE,
		     PresentationEditPlugin.INSTANCE,
		     ActionEditPlugin.INSTANCE,
		     BarrierEditPlugin.INSTANCE,
		     EnumerationEditPlugin.INSTANCE,
		   });
	}

	/**
	 * Returns the singleton instance of the Eclipse plugin.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the singleton instance.
	 * @generated
	 */
	@Override
	public ResourceLocator getPluginResourceLocator()
	{
		return plugin;
	}

	/**
	 * Returns the singleton instance of the Eclipse plugin.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the singleton instance.
	 * @generated
	 */
	public static Implementation getPlugin()
	{
		return plugin;
	}

	/**
	 * The actual implementation of the Eclipse <b>Plugin</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static class Implementation extends EclipsePlugin
	{
		/**
		 * Creates an instance.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public Implementation()
		{
			super();

			// Remember the static instance.
			//
			plugin = this;
		}
	}

}
