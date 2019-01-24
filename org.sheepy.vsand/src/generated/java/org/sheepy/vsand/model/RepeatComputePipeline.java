/**
 */
package org.sheepy.vulkan.sand.model;

import org.sheepy.lily.vulkan.model.process.compute.ComputePipeline;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Repeat Compute Pipeline</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sheepy.vulkan.sand.model.RepeatComputePipeline#getRepeatCount <em>Repeat Count</em>}</li>
 * </ul>
 *
 * @see org.sheepy.vulkan.sand.model.VSandPackage#getRepeatComputePipeline()
 * @model
 * @generated
 */
public interface RepeatComputePipeline extends ComputePipeline
{
	/**
	 * Returns the value of the '<em><b>Repeat Count</b></em>' attribute.
	 * The default value is <code>"1"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Repeat Count</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Repeat Count</em>' attribute.
	 * @see #setRepeatCount(int)
	 * @see org.sheepy.vulkan.sand.model.VSandPackage#getRepeatComputePipeline_RepeatCount()
	 * @model default="1" unique="false"
	 * @generated
	 */
	int getRepeatCount();

	/**
	 * Sets the value of the '{@link org.sheepy.vulkan.sand.model.RepeatComputePipeline#getRepeatCount <em>Repeat Count</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Repeat Count</em>' attribute.
	 * @see #getRepeatCount()
	 * @generated
	 */
	void setRepeatCount(int value);

} // RepeatComputePipeline
