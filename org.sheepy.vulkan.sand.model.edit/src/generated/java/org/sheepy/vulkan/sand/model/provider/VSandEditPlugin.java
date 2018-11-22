/**
 */
package org.sheepy.vulkan.sand.model.provider;

import org.eclipse.emf.common.EMFPlugin;

import org.eclipse.emf.common.util.ResourceLocator;
import org.sheepy.common.model.action.provider.ActionEditPlugin;
import org.sheepy.common.model.application.provider.ApplicationEditPlugin;
import org.sheepy.common.model.inference.provider.InferenceEditPlugin;
import org.sheepy.common.model.presentation.provider.PresentationEditPlugin;
import org.sheepy.common.model.root.provider.RootEditPlugin;
import org.sheepy.common.model.types.provider.TypesEditPlugin;
import org.sheepy.common.model.ui.provider.UiEditPlugin;
import org.sheepy.common.model.variable.provider.VariableEditPlugin;
import org.sheepy.vulkan.model.enumeration.provider.EnumerationEditPlugin;

import org.sheepy.vulkan.model.process.compute.provider.ComputeEditPlugin;

import org.sheepy.vulkan.model.process.graphic.provider.GraphicEditPlugin;
import org.sheepy.vulkan.model.process.provider.ProcessEditPlugin;

import org.sheepy.vulkan.model.provider.VulkanEditPlugin;

import org.sheepy.vulkan.model.resource.provider.ResourceEditPlugin;
import org.sheepy.vulkan.nuklear.model.provider.NuklearEditPlugin;

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
		     ProcessEditPlugin.INSTANCE,
		     TypesEditPlugin.INSTANCE,
		     ResourceEditPlugin.INSTANCE,
		     VulkanEditPlugin.INSTANCE,
		     PresentationEditPlugin.INSTANCE,
		     UiEditPlugin.INSTANCE,
		     NuklearEditPlugin.INSTANCE,
		     GraphicEditPlugin.INSTANCE,
		     ActionEditPlugin.INSTANCE,
		     EnumerationEditPlugin.INSTANCE,
		     VariableEditPlugin.INSTANCE,
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
