/**
 */
package org.sheepy.lily.vulkan.nuklear.model;

import org.sheepy.lily.vulkan.model.process.IPipeline;

import org.sheepy.lily.vulkan.model.process.graphic.IGUIPipeline;

import org.sheepy.lily.vulkan.model.resource.Font;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Pipeline</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sheepy.lily.vulkan.nuklear.model.NuklearPipeline#getIndexBuffer <em>Index Buffer</em>}</li>
 *   <li>{@link org.sheepy.lily.vulkan.nuklear.model.NuklearPipeline#getFont <em>Font</em>}</li>
 *   <li>{@link org.sheepy.lily.vulkan.nuklear.model.NuklearPipeline#getPushConstant <em>Push Constant</em>}</li>
 *   <li>{@link org.sheepy.lily.vulkan.nuklear.model.NuklearPipeline#getSubpass <em>Subpass</em>}</li>
 * </ul>
 *
 * @see org.sheepy.lily.vulkan.nuklear.model.NuklearPackage#getNuklearPipeline()
 * @model
 * @generated
 */
public interface NuklearPipeline extends IPipeline, IGUIPipeline
{
	/**
	 * Returns the value of the '<em><b>Index Buffer</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Index Buffer</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Index Buffer</em>' containment reference.
	 * @see #setIndexBuffer(NuklearIndexBuffer)
	 * @see org.sheepy.lily.vulkan.nuklear.model.NuklearPackage#getNuklearPipeline_IndexBuffer()
	 * @model containment="true" required="true"
	 * @generated
	 */
	NuklearIndexBuffer getIndexBuffer();

	/**
	 * Sets the value of the '{@link org.sheepy.lily.vulkan.nuklear.model.NuklearPipeline#getIndexBuffer <em>Index Buffer</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Index Buffer</em>' containment reference.
	 * @see #getIndexBuffer()
	 * @generated
	 */
	void setIndexBuffer(NuklearIndexBuffer value);

	/**
	 * Returns the value of the '<em><b>Font</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Font</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Font</em>' containment reference.
	 * @see #setFont(Font)
	 * @see org.sheepy.lily.vulkan.nuklear.model.NuklearPackage#getNuklearPipeline_Font()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Font getFont();

	/**
	 * Sets the value of the '{@link org.sheepy.lily.vulkan.nuklear.model.NuklearPipeline#getFont <em>Font</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Font</em>' containment reference.
	 * @see #getFont()
	 * @generated
	 */
	void setFont(Font value);

	/**
	 * Returns the value of the '<em><b>Push Constant</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Push Constant</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Push Constant</em>' containment reference.
	 * @see #setPushConstant(NuklearConstants)
	 * @see org.sheepy.lily.vulkan.nuklear.model.NuklearPackage#getNuklearPipeline_PushConstant()
	 * @model containment="true" required="true"
	 * @generated
	 */
	NuklearConstants getPushConstant();

	/**
	 * Sets the value of the '{@link org.sheepy.lily.vulkan.nuklear.model.NuklearPipeline#getPushConstant <em>Push Constant</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Push Constant</em>' containment reference.
	 * @see #getPushConstant()
	 * @generated
	 */
	void setPushConstant(NuklearConstants value);

	/**
	 * Returns the value of the '<em><b>Subpass</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Subpass</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Subpass</em>' attribute.
	 * @see #setSubpass(int)
	 * @see org.sheepy.lily.vulkan.nuklear.model.NuklearPackage#getNuklearPipeline_Subpass()
	 * @model default="0" unique="false"
	 * @generated
	 */
	int getSubpass();

	/**
	 * Sets the value of the '{@link org.sheepy.lily.vulkan.nuklear.model.NuklearPipeline#getSubpass <em>Subpass</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Subpass</em>' attribute.
	 * @see #getSubpass()
	 * @generated
	 */
	void setSubpass(int value);

} // NuklearPipeline
