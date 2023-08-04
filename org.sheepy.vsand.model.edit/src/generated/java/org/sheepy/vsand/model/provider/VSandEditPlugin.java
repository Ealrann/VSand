/**
 */
package org.sheepy.vsand.model.provider;

import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.ResourceLocator;
import org.sheepy.lily.core.model.resource.provider.LilyEditPlugin;
import org.sheepy.lily.vulkan.model.process.compute.provider.LilyVulkanEditPlugin;
import org.sheepy.vulkan.model.barrier.provider.VulkanEditPlugin;

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
		     org.sheepy.lily.vulkan.extra.model.nuklear.provider.ExtraEditPlugin.INSTANCE,
		     LilyEditPlugin.INSTANCE,
		     LilyVulkanEditPlugin.INSTANCE,
		     VulkanEditPlugin.INSTANCE,
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
