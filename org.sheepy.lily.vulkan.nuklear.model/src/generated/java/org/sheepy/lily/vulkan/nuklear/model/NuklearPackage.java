/**
 */
package org.sheepy.lily.vulkan.nuklear.model;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.sheepy.lily.vulkan.model.process.ProcessPackage;

import org.sheepy.lily.vulkan.model.resource.ResourcePackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.sheepy.lily.vulkan.nuklear.model.NuklearFactory
 * @model kind="package"
 *        annotation="http://www.eclipse.org/emf/2002/GenModel modelDirectory='/org.sheepy.lily.vulkan.nuklear.model/src/generated/java' editDirectory='/org.sheepy.lily.vulkan.nuklear.model.edit/src/generated/java' modelName='Nuklear' prefix='Nuklear' publicConstructors='true' complianceLevel='11.0' resource='XMI' childCreationExtenders='true' basePackage='org.sheepy.lily.vulkan.nuklear'"
 * @generated
 */
public interface NuklearPackage extends EPackage
{
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "model";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "org.sheepy.lily.vulkan.nuklear.model";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "model";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	NuklearPackage eINSTANCE = org.sheepy.lily.vulkan.nuklear.model.impl.NuklearPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.sheepy.lily.vulkan.nuklear.model.impl.NuklearPipelineImpl <em>Pipeline</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sheepy.lily.vulkan.nuklear.model.impl.NuklearPipelineImpl
	 * @see org.sheepy.lily.vulkan.nuklear.model.impl.NuklearPackageImpl#getNuklearPipeline()
	 * @generated
	 */
	int NUKLEAR_PIPELINE = 0;

	/**
	 * The feature id for the '<em><b>Content Objects</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUKLEAR_PIPELINE__CONTENT_OBJECTS = ProcessPackage.IPIPELINE__CONTENT_OBJECTS;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUKLEAR_PIPELINE__ENABLED = ProcessPackage.IPIPELINE__ENABLED;

	/**
	 * The feature id for the '<em><b>Stage</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUKLEAR_PIPELINE__STAGE = ProcessPackage.IPIPELINE__STAGE;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUKLEAR_PIPELINE__NAME = ProcessPackage.IPIPELINE__NAME;

	/**
	 * The feature id for the '<em><b>Index Buffer</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUKLEAR_PIPELINE__INDEX_BUFFER = ProcessPackage.IPIPELINE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Font</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUKLEAR_PIPELINE__FONT = ProcessPackage.IPIPELINE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Push Constant</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUKLEAR_PIPELINE__PUSH_CONSTANT = ProcessPackage.IPIPELINE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Subpass</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUKLEAR_PIPELINE__SUBPASS = ProcessPackage.IPIPELINE_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Pipeline</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUKLEAR_PIPELINE_FEATURE_COUNT = ProcessPackage.IPIPELINE_FEATURE_COUNT + 4;

	/**
	 * The operation id for the '<em>LInference Object</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUKLEAR_PIPELINE___LINFERENCE_OBJECT = ProcessPackage.IPIPELINE___LINFERENCE_OBJECT;

	/**
	 * The operation id for the '<em>Create Containment EList</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUKLEAR_PIPELINE___CREATE_CONTAINMENT_ELIST__ECLASS = ProcessPackage.IPIPELINE___CREATE_CONTAINMENT_ELIST__ECLASS;

	/**
	 * The operation id for the '<em>LContents</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUKLEAR_PIPELINE___LCONTENTS = ProcessPackage.IPIPELINE___LCONTENTS;

	/**
	 * The operation id for the '<em>LParent</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUKLEAR_PIPELINE___LPARENT = ProcessPackage.IPIPELINE___LPARENT;

	/**
	 * The operation id for the '<em>LAll Contents</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUKLEAR_PIPELINE___LALL_CONTENTS = ProcessPackage.IPIPELINE___LALL_CONTENTS;

	/**
	 * The number of operations of the '<em>Pipeline</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUKLEAR_PIPELINE_OPERATION_COUNT = ProcessPackage.IPIPELINE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.sheepy.lily.vulkan.nuklear.model.impl.NuklearConstantsImpl <em>Constants</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sheepy.lily.vulkan.nuklear.model.impl.NuklearConstantsImpl
	 * @see org.sheepy.lily.vulkan.nuklear.model.impl.NuklearPackageImpl#getNuklearConstants()
	 * @generated
	 */
	int NUKLEAR_CONSTANTS = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUKLEAR_CONSTANTS__NAME = ResourcePackage.CONSTANTS__NAME;

	/**
	 * The feature id for the '<em><b>Stage</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUKLEAR_CONSTANTS__STAGE = ResourcePackage.CONSTANTS__STAGE;

	/**
	 * The feature id for the '<em><b>Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUKLEAR_CONSTANTS__DATA = ResourcePackage.CONSTANTS__DATA;

	/**
	 * The number of structural features of the '<em>Constants</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUKLEAR_CONSTANTS_FEATURE_COUNT = ResourcePackage.CONSTANTS_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Constants</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUKLEAR_CONSTANTS_OPERATION_COUNT = ResourcePackage.CONSTANTS_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.sheepy.lily.vulkan.nuklear.model.impl.NuklearIndexBufferImpl <em>Index Buffer</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sheepy.lily.vulkan.nuklear.model.impl.NuklearIndexBufferImpl
	 * @see org.sheepy.lily.vulkan.nuklear.model.impl.NuklearPackageImpl#getNuklearIndexBuffer()
	 * @generated
	 */
	int NUKLEAR_INDEX_BUFFER = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUKLEAR_INDEX_BUFFER__NAME = ResourcePackage.INDEXED_BUFFER__NAME;

	/**
	 * The number of structural features of the '<em>Index Buffer</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUKLEAR_INDEX_BUFFER_FEATURE_COUNT = ResourcePackage.INDEXED_BUFFER_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Index Buffer</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUKLEAR_INDEX_BUFFER_OPERATION_COUNT = ResourcePackage.INDEXED_BUFFER_OPERATION_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link org.sheepy.lily.vulkan.nuklear.model.NuklearPipeline <em>Pipeline</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Pipeline</em>'.
	 * @see org.sheepy.lily.vulkan.nuklear.model.NuklearPipeline
	 * @generated
	 */
	EClass getNuklearPipeline();

	/**
	 * Returns the meta object for the containment reference '{@link org.sheepy.lily.vulkan.nuklear.model.NuklearPipeline#getIndexBuffer <em>Index Buffer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Index Buffer</em>'.
	 * @see org.sheepy.lily.vulkan.nuklear.model.NuklearPipeline#getIndexBuffer()
	 * @see #getNuklearPipeline()
	 * @generated
	 */
	EReference getNuklearPipeline_IndexBuffer();

	/**
	 * Returns the meta object for the containment reference '{@link org.sheepy.lily.vulkan.nuklear.model.NuklearPipeline#getFont <em>Font</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Font</em>'.
	 * @see org.sheepy.lily.vulkan.nuklear.model.NuklearPipeline#getFont()
	 * @see #getNuklearPipeline()
	 * @generated
	 */
	EReference getNuklearPipeline_Font();

	/**
	 * Returns the meta object for the containment reference '{@link org.sheepy.lily.vulkan.nuklear.model.NuklearPipeline#getPushConstant <em>Push Constant</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Push Constant</em>'.
	 * @see org.sheepy.lily.vulkan.nuklear.model.NuklearPipeline#getPushConstant()
	 * @see #getNuklearPipeline()
	 * @generated
	 */
	EReference getNuklearPipeline_PushConstant();

	/**
	 * Returns the meta object for the attribute '{@link org.sheepy.lily.vulkan.nuklear.model.NuklearPipeline#getSubpass <em>Subpass</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Subpass</em>'.
	 * @see org.sheepy.lily.vulkan.nuklear.model.NuklearPipeline#getSubpass()
	 * @see #getNuklearPipeline()
	 * @generated
	 */
	EAttribute getNuklearPipeline_Subpass();

	/**
	 * Returns the meta object for class '{@link org.sheepy.lily.vulkan.nuklear.model.NuklearConstants <em>Constants</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Constants</em>'.
	 * @see org.sheepy.lily.vulkan.nuklear.model.NuklearConstants
	 * @generated
	 */
	EClass getNuklearConstants();

	/**
	 * Returns the meta object for class '{@link org.sheepy.lily.vulkan.nuklear.model.NuklearIndexBuffer <em>Index Buffer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Index Buffer</em>'.
	 * @see org.sheepy.lily.vulkan.nuklear.model.NuklearIndexBuffer
	 * @generated
	 */
	EClass getNuklearIndexBuffer();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	NuklearFactory getNuklearFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals
	{
		/**
		 * The meta object literal for the '{@link org.sheepy.lily.vulkan.nuklear.model.impl.NuklearPipelineImpl <em>Pipeline</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sheepy.lily.vulkan.nuklear.model.impl.NuklearPipelineImpl
		 * @see org.sheepy.lily.vulkan.nuklear.model.impl.NuklearPackageImpl#getNuklearPipeline()
		 * @generated
		 */
		EClass NUKLEAR_PIPELINE = eINSTANCE.getNuklearPipeline();

		/**
		 * The meta object literal for the '<em><b>Index Buffer</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NUKLEAR_PIPELINE__INDEX_BUFFER = eINSTANCE.getNuklearPipeline_IndexBuffer();

		/**
		 * The meta object literal for the '<em><b>Font</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NUKLEAR_PIPELINE__FONT = eINSTANCE.getNuklearPipeline_Font();

		/**
		 * The meta object literal for the '<em><b>Push Constant</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NUKLEAR_PIPELINE__PUSH_CONSTANT = eINSTANCE.getNuklearPipeline_PushConstant();

		/**
		 * The meta object literal for the '<em><b>Subpass</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NUKLEAR_PIPELINE__SUBPASS = eINSTANCE.getNuklearPipeline_Subpass();

		/**
		 * The meta object literal for the '{@link org.sheepy.lily.vulkan.nuklear.model.impl.NuklearConstantsImpl <em>Constants</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sheepy.lily.vulkan.nuklear.model.impl.NuklearConstantsImpl
		 * @see org.sheepy.lily.vulkan.nuklear.model.impl.NuklearPackageImpl#getNuklearConstants()
		 * @generated
		 */
		EClass NUKLEAR_CONSTANTS = eINSTANCE.getNuklearConstants();

		/**
		 * The meta object literal for the '{@link org.sheepy.lily.vulkan.nuklear.model.impl.NuklearIndexBufferImpl <em>Index Buffer</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sheepy.lily.vulkan.nuklear.model.impl.NuklearIndexBufferImpl
		 * @see org.sheepy.lily.vulkan.nuklear.model.impl.NuklearPackageImpl#getNuklearIndexBuffer()
		 * @generated
		 */
		EClass NUKLEAR_INDEX_BUFFER = eINSTANCE.getNuklearIndexBuffer();

	}

} //NuklearPackage
