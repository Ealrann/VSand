/**
 */
package org.sheepy.vsand.model;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.sheepy.lily.core.model.application.ApplicationPackage;
import org.sheepy.lily.core.model.presentation.PresentationPackage;

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
 * @see org.sheepy.vsand.model.VSandFactory
 * @model kind="package"
 * @generated
 */
public interface VSandPackage extends EPackage
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
	String eNS_URI = "org.sheepy.vsand.model";

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
	VSandPackage eINSTANCE = org.sheepy.vsand.model.impl.VSandPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.sheepy.vsand.model.impl.VSandApplicationImpl <em>Application</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sheepy.vsand.model.impl.VSandApplicationImpl
	 * @see org.sheepy.vsand.model.impl.VSandPackageImpl#getVSandApplication()
	 * @generated
	 */
	int VSAND_APPLICATION = 0;

	/**
	 * The feature id for the '<em><b>Content Objects</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VSAND_APPLICATION__CONTENT_OBJECTS = ApplicationPackage.APPLICATION__CONTENT_OBJECTS;

	/**
	 * The feature id for the '<em><b>Views</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VSAND_APPLICATION__VIEWS = ApplicationPackage.APPLICATION__VIEWS;

	/**
	 * The feature id for the '<em><b>Engines</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VSAND_APPLICATION__ENGINES = ApplicationPackage.APPLICATION__ENGINES;

	/**
	 * The feature id for the '<em><b>Run</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VSAND_APPLICATION__RUN = ApplicationPackage.APPLICATION__RUN;

	/**
	 * The feature id for the '<em><b>Fullscreen</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VSAND_APPLICATION__FULLSCREEN = ApplicationPackage.APPLICATION__FULLSCREEN;

	/**
	 * The feature id for the '<em><b>Resizeable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VSAND_APPLICATION__RESIZEABLE = ApplicationPackage.APPLICATION__RESIZEABLE;

	/**
	 * The feature id for the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VSAND_APPLICATION__TITLE = ApplicationPackage.APPLICATION__TITLE;

	/**
	 * The feature id for the '<em><b>Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VSAND_APPLICATION__SIZE = ApplicationPackage.APPLICATION__SIZE;

	/**
	 * The feature id for the '<em><b>Current View</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VSAND_APPLICATION__CURRENT_VIEW = ApplicationPackage.APPLICATION__CURRENT_VIEW;

	/**
	 * The feature id for the '<em><b>Cadence In Hz</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VSAND_APPLICATION__CADENCE_IN_HZ = ApplicationPackage.APPLICATION__CADENCE_IN_HZ;

	/**
	 * The feature id for the '<em><b>Materials</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VSAND_APPLICATION__MATERIALS = ApplicationPackage.APPLICATION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Transformations</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VSAND_APPLICATION__TRANSFORMATIONS = ApplicationPackage.APPLICATION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Main Material</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VSAND_APPLICATION__MAIN_MATERIAL = ApplicationPackage.APPLICATION_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Secondary Material</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VSAND_APPLICATION__SECONDARY_MATERIAL = ApplicationPackage.APPLICATION_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Next Mode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VSAND_APPLICATION__NEXT_MODE = ApplicationPackage.APPLICATION_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Force Clear</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VSAND_APPLICATION__FORCE_CLEAR = ApplicationPackage.APPLICATION_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Show Sleep Zones</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VSAND_APPLICATION__SHOW_SLEEP_ZONES = ApplicationPackage.APPLICATION_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Brush Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VSAND_APPLICATION__BRUSH_SIZE = ApplicationPackage.APPLICATION_FEATURE_COUNT + 7;

	/**
	 * The number of structural features of the '<em>Application</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VSAND_APPLICATION_FEATURE_COUNT = ApplicationPackage.APPLICATION_FEATURE_COUNT + 8;

	/**
	 * The operation id for the '<em>LInference Object</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VSAND_APPLICATION___LINFERENCE_OBJECT = ApplicationPackage.APPLICATION___LINFERENCE_OBJECT;

	/**
	 * The operation id for the '<em>Create Containment EList</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VSAND_APPLICATION___CREATE_CONTAINMENT_ELIST__ECLASS = ApplicationPackage.APPLICATION___CREATE_CONTAINMENT_ELIST__ECLASS;

	/**
	 * The operation id for the '<em>LContents</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VSAND_APPLICATION___LCONTENTS = ApplicationPackage.APPLICATION___LCONTENTS;

	/**
	 * The operation id for the '<em>LParent</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VSAND_APPLICATION___LPARENT = ApplicationPackage.APPLICATION___LPARENT;

	/**
	 * The operation id for the '<em>LAll Contents</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VSAND_APPLICATION___LALL_CONTENTS = ApplicationPackage.APPLICATION___LALL_CONTENTS;

	/**
	 * The number of operations of the '<em>Application</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VSAND_APPLICATION_OPERATION_COUNT = ApplicationPackage.APPLICATION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.sheepy.vsand.model.impl.MaterialsImpl <em>Materials</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sheepy.vsand.model.impl.MaterialsImpl
	 * @see org.sheepy.vsand.model.impl.VSandPackageImpl#getMaterials()
	 * @generated
	 */
	int MATERIALS = 1;

	/**
	 * The feature id for the '<em><b>Materials</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATERIALS__MATERIALS = 0;

	/**
	 * The number of structural features of the '<em>Materials</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATERIALS_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Materials</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATERIALS_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.sheepy.vsand.model.impl.MaterialImpl <em>Material</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sheepy.vsand.model.impl.MaterialImpl
	 * @see org.sheepy.vsand.model.impl.VSandPackageImpl#getMaterial()
	 * @generated
	 */
	int MATERIAL = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATERIAL__NAME = 0;

	/**
	 * The feature id for the '<em><b>Is Static</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATERIAL__IS_STATIC = 1;

	/**
	 * The feature id for the '<em><b>Density</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATERIAL__DENSITY = 2;

	/**
	 * The feature id for the '<em><b>Runoff</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATERIAL__RUNOFF = 3;

	/**
	 * The feature id for the '<em><b>R</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATERIAL__R = 4;

	/**
	 * The feature id for the '<em><b>G</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATERIAL__G = 5;

	/**
	 * The feature id for the '<em><b>B</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATERIAL__B = 6;

	/**
	 * The feature id for the '<em><b>User Friendly</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATERIAL__USER_FRIENDLY = 7;

	/**
	 * The number of structural features of the '<em>Material</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATERIAL_FEATURE_COUNT = 8;

	/**
	 * The number of operations of the '<em>Material</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATERIAL_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.sheepy.vsand.model.impl.TransformationsImpl <em>Transformations</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sheepy.vsand.model.impl.TransformationsImpl
	 * @see org.sheepy.vsand.model.impl.VSandPackageImpl#getTransformations()
	 * @generated
	 */
	int TRANSFORMATIONS = 3;

	/**
	 * The feature id for the '<em><b>Transformations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSFORMATIONS__TRANSFORMATIONS = 0;

	/**
	 * The number of structural features of the '<em>Transformations</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSFORMATIONS_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Transformations</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSFORMATIONS_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.sheepy.vsand.model.impl.TransformationImpl <em>Transformation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sheepy.vsand.model.impl.TransformationImpl
	 * @see org.sheepy.vsand.model.impl.VSandPackageImpl#getTransformation()
	 * @generated
	 */
	int TRANSFORMATION = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSFORMATION__NAME = 0;

	/**
	 * The feature id for the '<em><b>Reactant</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSFORMATION__REACTANT = 1;

	/**
	 * The feature id for the '<em><b>Catalyst</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSFORMATION__CATALYST = 2;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSFORMATION__TARGET = 3;

	/**
	 * The feature id for the '<em><b>Probability</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSFORMATION__PROBABILITY = 4;

	/**
	 * The feature id for the '<em><b>Propagation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSFORMATION__PROPAGATION = 5;

	/**
	 * The feature id for the '<em><b>Is Static Transformation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSFORMATION__IS_STATIC_TRANSFORMATION = 6;

	/**
	 * The number of structural features of the '<em>Transformation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSFORMATION_FEATURE_COUNT = 7;

	/**
	 * The number of operations of the '<em>Transformation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSFORMATION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.sheepy.vsand.model.impl.MaterialSelectorPanelImpl <em>Material Selector Panel</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.sheepy.vsand.model.impl.MaterialSelectorPanelImpl
	 * @see org.sheepy.vsand.model.impl.VSandPackageImpl#getMaterialSelectorPanel()
	 * @generated
	 */
	int MATERIAL_SELECTOR_PANEL = 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATERIAL_SELECTOR_PANEL__NAME = PresentationPackage.IPANEL__NAME;

	/**
	 * The feature id for the '<em><b>Content Objects</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATERIAL_SELECTOR_PANEL__CONTENT_OBJECTS = PresentationPackage.IPANEL__CONTENT_OBJECTS;

	/**
	 * The feature id for the '<em><b>Position</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATERIAL_SELECTOR_PANEL__POSITION = PresentationPackage.IPANEL__POSITION;

	/**
	 * The feature id for the '<em><b>Vertical Relative</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATERIAL_SELECTOR_PANEL__VERTICAL_RELATIVE = PresentationPackage.IPANEL__VERTICAL_RELATIVE;

	/**
	 * The feature id for the '<em><b>Horizontal Relative</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATERIAL_SELECTOR_PANEL__HORIZONTAL_RELATIVE = PresentationPackage.IPANEL__HORIZONTAL_RELATIVE;

	/**
	 * The feature id for the '<em><b>Line Height</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATERIAL_SELECTOR_PANEL__LINE_HEIGHT = PresentationPackage.IPANEL_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Primary R</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATERIAL_SELECTOR_PANEL__PRIMARY_R = PresentationPackage.IPANEL_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Primary G</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATERIAL_SELECTOR_PANEL__PRIMARY_G = PresentationPackage.IPANEL_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Primary B</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATERIAL_SELECTOR_PANEL__PRIMARY_B = PresentationPackage.IPANEL_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Secondary R</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATERIAL_SELECTOR_PANEL__SECONDARY_R = PresentationPackage.IPANEL_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Secondary G</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATERIAL_SELECTOR_PANEL__SECONDARY_G = PresentationPackage.IPANEL_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Secondary B</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATERIAL_SELECTOR_PANEL__SECONDARY_B = PresentationPackage.IPANEL_FEATURE_COUNT + 6;

	/**
	 * The number of structural features of the '<em>Material Selector Panel</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATERIAL_SELECTOR_PANEL_FEATURE_COUNT = PresentationPackage.IPANEL_FEATURE_COUNT + 7;

	/**
	 * The operation id for the '<em>LInference Object</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATERIAL_SELECTOR_PANEL___LINFERENCE_OBJECT = PresentationPackage.IPANEL___LINFERENCE_OBJECT;

	/**
	 * The operation id for the '<em>Create Containment EList</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATERIAL_SELECTOR_PANEL___CREATE_CONTAINMENT_ELIST__ECLASS = PresentationPackage.IPANEL___CREATE_CONTAINMENT_ELIST__ECLASS;

	/**
	 * The operation id for the '<em>LContents</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATERIAL_SELECTOR_PANEL___LCONTENTS = PresentationPackage.IPANEL___LCONTENTS;

	/**
	 * The operation id for the '<em>LParent</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATERIAL_SELECTOR_PANEL___LPARENT = PresentationPackage.IPANEL___LPARENT;

	/**
	 * The operation id for the '<em>LAll Contents</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATERIAL_SELECTOR_PANEL___LALL_CONTENTS = PresentationPackage.IPANEL___LALL_CONTENTS;

	/**
	 * The number of operations of the '<em>Material Selector Panel</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATERIAL_SELECTOR_PANEL_OPERATION_COUNT = PresentationPackage.IPANEL_OPERATION_COUNT + 0;

	/**
	 * Returns the meta object for class '{@link org.sheepy.vsand.model.VSandApplication <em>Application</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Application</em>'.
	 * @see org.sheepy.vsand.model.VSandApplication
	 * @generated
	 */
	EClass getVSandApplication();

	/**
	 * Returns the meta object for the containment reference '{@link org.sheepy.vsand.model.VSandApplication#getMaterials <em>Materials</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Materials</em>'.
	 * @see org.sheepy.vsand.model.VSandApplication#getMaterials()
	 * @see #getVSandApplication()
	 * @generated
	 */
	EReference getVSandApplication_Materials();

	/**
	 * Returns the meta object for the containment reference '{@link org.sheepy.vsand.model.VSandApplication#getTransformations <em>Transformations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Transformations</em>'.
	 * @see org.sheepy.vsand.model.VSandApplication#getTransformations()
	 * @see #getVSandApplication()
	 * @generated
	 */
	EReference getVSandApplication_Transformations();

	/**
	 * Returns the meta object for the reference '{@link org.sheepy.vsand.model.VSandApplication#getMainMaterial <em>Main Material</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Main Material</em>'.
	 * @see org.sheepy.vsand.model.VSandApplication#getMainMaterial()
	 * @see #getVSandApplication()
	 * @generated
	 */
	EReference getVSandApplication_MainMaterial();

	/**
	 * Returns the meta object for the reference '{@link org.sheepy.vsand.model.VSandApplication#getSecondaryMaterial <em>Secondary Material</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Secondary Material</em>'.
	 * @see org.sheepy.vsand.model.VSandApplication#getSecondaryMaterial()
	 * @see #getVSandApplication()
	 * @generated
	 */
	EReference getVSandApplication_SecondaryMaterial();

	/**
	 * Returns the meta object for the attribute '{@link org.sheepy.vsand.model.VSandApplication#isNextMode <em>Next Mode</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Next Mode</em>'.
	 * @see org.sheepy.vsand.model.VSandApplication#isNextMode()
	 * @see #getVSandApplication()
	 * @generated
	 */
	EAttribute getVSandApplication_NextMode();

	/**
	 * Returns the meta object for the attribute '{@link org.sheepy.vsand.model.VSandApplication#isForceClear <em>Force Clear</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Force Clear</em>'.
	 * @see org.sheepy.vsand.model.VSandApplication#isForceClear()
	 * @see #getVSandApplication()
	 * @generated
	 */
	EAttribute getVSandApplication_ForceClear();

	/**
	 * Returns the meta object for the attribute '{@link org.sheepy.vsand.model.VSandApplication#isShowSleepZones <em>Show Sleep Zones</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Show Sleep Zones</em>'.
	 * @see org.sheepy.vsand.model.VSandApplication#isShowSleepZones()
	 * @see #getVSandApplication()
	 * @generated
	 */
	EAttribute getVSandApplication_ShowSleepZones();

	/**
	 * Returns the meta object for the attribute '{@link org.sheepy.vsand.model.VSandApplication#getBrushSize <em>Brush Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Brush Size</em>'.
	 * @see org.sheepy.vsand.model.VSandApplication#getBrushSize()
	 * @see #getVSandApplication()
	 * @generated
	 */
	EAttribute getVSandApplication_BrushSize();

	/**
	 * Returns the meta object for class '{@link org.sheepy.vsand.model.Materials <em>Materials</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Materials</em>'.
	 * @see org.sheepy.vsand.model.Materials
	 * @generated
	 */
	EClass getMaterials();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sheepy.vsand.model.Materials#getMaterials <em>Materials</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Materials</em>'.
	 * @see org.sheepy.vsand.model.Materials#getMaterials()
	 * @see #getMaterials()
	 * @generated
	 */
	EReference getMaterials_Materials();

	/**
	 * Returns the meta object for class '{@link org.sheepy.vsand.model.Material <em>Material</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Material</em>'.
	 * @see org.sheepy.vsand.model.Material
	 * @generated
	 */
	EClass getMaterial();

	/**
	 * Returns the meta object for the attribute '{@link org.sheepy.vsand.model.Material#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.sheepy.vsand.model.Material#getName()
	 * @see #getMaterial()
	 * @generated
	 */
	EAttribute getMaterial_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.sheepy.vsand.model.Material#isIsStatic <em>Is Static</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Static</em>'.
	 * @see org.sheepy.vsand.model.Material#isIsStatic()
	 * @see #getMaterial()
	 * @generated
	 */
	EAttribute getMaterial_IsStatic();

	/**
	 * Returns the meta object for the attribute '{@link org.sheepy.vsand.model.Material#getDensity <em>Density</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Density</em>'.
	 * @see org.sheepy.vsand.model.Material#getDensity()
	 * @see #getMaterial()
	 * @generated
	 */
	EAttribute getMaterial_Density();

	/**
	 * Returns the meta object for the attribute '{@link org.sheepy.vsand.model.Material#getRunoff <em>Runoff</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Runoff</em>'.
	 * @see org.sheepy.vsand.model.Material#getRunoff()
	 * @see #getMaterial()
	 * @generated
	 */
	EAttribute getMaterial_Runoff();

	/**
	 * Returns the meta object for the attribute '{@link org.sheepy.vsand.model.Material#getR <em>R</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>R</em>'.
	 * @see org.sheepy.vsand.model.Material#getR()
	 * @see #getMaterial()
	 * @generated
	 */
	EAttribute getMaterial_R();

	/**
	 * Returns the meta object for the attribute '{@link org.sheepy.vsand.model.Material#getG <em>G</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>G</em>'.
	 * @see org.sheepy.vsand.model.Material#getG()
	 * @see #getMaterial()
	 * @generated
	 */
	EAttribute getMaterial_G();

	/**
	 * Returns the meta object for the attribute '{@link org.sheepy.vsand.model.Material#getB <em>B</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>B</em>'.
	 * @see org.sheepy.vsand.model.Material#getB()
	 * @see #getMaterial()
	 * @generated
	 */
	EAttribute getMaterial_B();

	/**
	 * Returns the meta object for the attribute '{@link org.sheepy.vsand.model.Material#isUserFriendly <em>User Friendly</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>User Friendly</em>'.
	 * @see org.sheepy.vsand.model.Material#isUserFriendly()
	 * @see #getMaterial()
	 * @generated
	 */
	EAttribute getMaterial_UserFriendly();

	/**
	 * Returns the meta object for class '{@link org.sheepy.vsand.model.Transformations <em>Transformations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Transformations</em>'.
	 * @see org.sheepy.vsand.model.Transformations
	 * @generated
	 */
	EClass getTransformations();

	/**
	 * Returns the meta object for the containment reference list '{@link org.sheepy.vsand.model.Transformations#getTransformations <em>Transformations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Transformations</em>'.
	 * @see org.sheepy.vsand.model.Transformations#getTransformations()
	 * @see #getTransformations()
	 * @generated
	 */
	EReference getTransformations_Transformations();

	/**
	 * Returns the meta object for class '{@link org.sheepy.vsand.model.Transformation <em>Transformation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Transformation</em>'.
	 * @see org.sheepy.vsand.model.Transformation
	 * @generated
	 */
	EClass getTransformation();

	/**
	 * Returns the meta object for the attribute '{@link org.sheepy.vsand.model.Transformation#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.sheepy.vsand.model.Transformation#getName()
	 * @see #getTransformation()
	 * @generated
	 */
	EAttribute getTransformation_Name();

	/**
	 * Returns the meta object for the reference '{@link org.sheepy.vsand.model.Transformation#getReactant <em>Reactant</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Reactant</em>'.
	 * @see org.sheepy.vsand.model.Transformation#getReactant()
	 * @see #getTransformation()
	 * @generated
	 */
	EReference getTransformation_Reactant();

	/**
	 * Returns the meta object for the reference '{@link org.sheepy.vsand.model.Transformation#getCatalyst <em>Catalyst</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Catalyst</em>'.
	 * @see org.sheepy.vsand.model.Transformation#getCatalyst()
	 * @see #getTransformation()
	 * @generated
	 */
	EReference getTransformation_Catalyst();

	/**
	 * Returns the meta object for the reference '{@link org.sheepy.vsand.model.Transformation#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see org.sheepy.vsand.model.Transformation#getTarget()
	 * @see #getTransformation()
	 * @generated
	 */
	EReference getTransformation_Target();

	/**
	 * Returns the meta object for the attribute '{@link org.sheepy.vsand.model.Transformation#getProbability <em>Probability</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Probability</em>'.
	 * @see org.sheepy.vsand.model.Transformation#getProbability()
	 * @see #getTransformation()
	 * @generated
	 */
	EAttribute getTransformation_Probability();

	/**
	 * Returns the meta object for the attribute '{@link org.sheepy.vsand.model.Transformation#getPropagation <em>Propagation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Propagation</em>'.
	 * @see org.sheepy.vsand.model.Transformation#getPropagation()
	 * @see #getTransformation()
	 * @generated
	 */
	EAttribute getTransformation_Propagation();

	/**
	 * Returns the meta object for the attribute '{@link org.sheepy.vsand.model.Transformation#isIsStaticTransformation <em>Is Static Transformation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Static Transformation</em>'.
	 * @see org.sheepy.vsand.model.Transformation#isIsStaticTransformation()
	 * @see #getTransformation()
	 * @generated
	 */
	EAttribute getTransformation_IsStaticTransformation();

	/**
	 * Returns the meta object for class '{@link org.sheepy.vsand.model.MaterialSelectorPanel <em>Material Selector Panel</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Material Selector Panel</em>'.
	 * @see org.sheepy.vsand.model.MaterialSelectorPanel
	 * @generated
	 */
	EClass getMaterialSelectorPanel();

	/**
	 * Returns the meta object for the attribute '{@link org.sheepy.vsand.model.MaterialSelectorPanel#getLineHeight <em>Line Height</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Line Height</em>'.
	 * @see org.sheepy.vsand.model.MaterialSelectorPanel#getLineHeight()
	 * @see #getMaterialSelectorPanel()
	 * @generated
	 */
	EAttribute getMaterialSelectorPanel_LineHeight();

	/**
	 * Returns the meta object for the attribute '{@link org.sheepy.vsand.model.MaterialSelectorPanel#getPrimaryR <em>Primary R</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Primary R</em>'.
	 * @see org.sheepy.vsand.model.MaterialSelectorPanel#getPrimaryR()
	 * @see #getMaterialSelectorPanel()
	 * @generated
	 */
	EAttribute getMaterialSelectorPanel_PrimaryR();

	/**
	 * Returns the meta object for the attribute '{@link org.sheepy.vsand.model.MaterialSelectorPanel#getPrimaryG <em>Primary G</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Primary G</em>'.
	 * @see org.sheepy.vsand.model.MaterialSelectorPanel#getPrimaryG()
	 * @see #getMaterialSelectorPanel()
	 * @generated
	 */
	EAttribute getMaterialSelectorPanel_PrimaryG();

	/**
	 * Returns the meta object for the attribute '{@link org.sheepy.vsand.model.MaterialSelectorPanel#getPrimaryB <em>Primary B</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Primary B</em>'.
	 * @see org.sheepy.vsand.model.MaterialSelectorPanel#getPrimaryB()
	 * @see #getMaterialSelectorPanel()
	 * @generated
	 */
	EAttribute getMaterialSelectorPanel_PrimaryB();

	/**
	 * Returns the meta object for the attribute '{@link org.sheepy.vsand.model.MaterialSelectorPanel#getSecondaryR <em>Secondary R</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Secondary R</em>'.
	 * @see org.sheepy.vsand.model.MaterialSelectorPanel#getSecondaryR()
	 * @see #getMaterialSelectorPanel()
	 * @generated
	 */
	EAttribute getMaterialSelectorPanel_SecondaryR();

	/**
	 * Returns the meta object for the attribute '{@link org.sheepy.vsand.model.MaterialSelectorPanel#getSecondaryG <em>Secondary G</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Secondary G</em>'.
	 * @see org.sheepy.vsand.model.MaterialSelectorPanel#getSecondaryG()
	 * @see #getMaterialSelectorPanel()
	 * @generated
	 */
	EAttribute getMaterialSelectorPanel_SecondaryG();

	/**
	 * Returns the meta object for the attribute '{@link org.sheepy.vsand.model.MaterialSelectorPanel#getSecondaryB <em>Secondary B</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Secondary B</em>'.
	 * @see org.sheepy.vsand.model.MaterialSelectorPanel#getSecondaryB()
	 * @see #getMaterialSelectorPanel()
	 * @generated
	 */
	EAttribute getMaterialSelectorPanel_SecondaryB();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	VSandFactory getVSandFactory();

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
		 * The meta object literal for the '{@link org.sheepy.vsand.model.impl.VSandApplicationImpl <em>Application</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sheepy.vsand.model.impl.VSandApplicationImpl
		 * @see org.sheepy.vsand.model.impl.VSandPackageImpl#getVSandApplication()
		 * @generated
		 */
		EClass VSAND_APPLICATION = eINSTANCE.getVSandApplication();

		/**
		 * The meta object literal for the '<em><b>Materials</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VSAND_APPLICATION__MATERIALS = eINSTANCE.getVSandApplication_Materials();

		/**
		 * The meta object literal for the '<em><b>Transformations</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VSAND_APPLICATION__TRANSFORMATIONS = eINSTANCE.getVSandApplication_Transformations();

		/**
		 * The meta object literal for the '<em><b>Main Material</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VSAND_APPLICATION__MAIN_MATERIAL = eINSTANCE.getVSandApplication_MainMaterial();

		/**
		 * The meta object literal for the '<em><b>Secondary Material</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VSAND_APPLICATION__SECONDARY_MATERIAL = eINSTANCE.getVSandApplication_SecondaryMaterial();

		/**
		 * The meta object literal for the '<em><b>Next Mode</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VSAND_APPLICATION__NEXT_MODE = eINSTANCE.getVSandApplication_NextMode();

		/**
		 * The meta object literal for the '<em><b>Force Clear</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VSAND_APPLICATION__FORCE_CLEAR = eINSTANCE.getVSandApplication_ForceClear();

		/**
		 * The meta object literal for the '<em><b>Show Sleep Zones</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VSAND_APPLICATION__SHOW_SLEEP_ZONES = eINSTANCE.getVSandApplication_ShowSleepZones();

		/**
		 * The meta object literal for the '<em><b>Brush Size</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VSAND_APPLICATION__BRUSH_SIZE = eINSTANCE.getVSandApplication_BrushSize();

		/**
		 * The meta object literal for the '{@link org.sheepy.vsand.model.impl.MaterialsImpl <em>Materials</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sheepy.vsand.model.impl.MaterialsImpl
		 * @see org.sheepy.vsand.model.impl.VSandPackageImpl#getMaterials()
		 * @generated
		 */
		EClass MATERIALS = eINSTANCE.getMaterials();

		/**
		 * The meta object literal for the '<em><b>Materials</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MATERIALS__MATERIALS = eINSTANCE.getMaterials_Materials();

		/**
		 * The meta object literal for the '{@link org.sheepy.vsand.model.impl.MaterialImpl <em>Material</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sheepy.vsand.model.impl.MaterialImpl
		 * @see org.sheepy.vsand.model.impl.VSandPackageImpl#getMaterial()
		 * @generated
		 */
		EClass MATERIAL = eINSTANCE.getMaterial();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MATERIAL__NAME = eINSTANCE.getMaterial_Name();

		/**
		 * The meta object literal for the '<em><b>Is Static</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MATERIAL__IS_STATIC = eINSTANCE.getMaterial_IsStatic();

		/**
		 * The meta object literal for the '<em><b>Density</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MATERIAL__DENSITY = eINSTANCE.getMaterial_Density();

		/**
		 * The meta object literal for the '<em><b>Runoff</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MATERIAL__RUNOFF = eINSTANCE.getMaterial_Runoff();

		/**
		 * The meta object literal for the '<em><b>R</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MATERIAL__R = eINSTANCE.getMaterial_R();

		/**
		 * The meta object literal for the '<em><b>G</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MATERIAL__G = eINSTANCE.getMaterial_G();

		/**
		 * The meta object literal for the '<em><b>B</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MATERIAL__B = eINSTANCE.getMaterial_B();

		/**
		 * The meta object literal for the '<em><b>User Friendly</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MATERIAL__USER_FRIENDLY = eINSTANCE.getMaterial_UserFriendly();

		/**
		 * The meta object literal for the '{@link org.sheepy.vsand.model.impl.TransformationsImpl <em>Transformations</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sheepy.vsand.model.impl.TransformationsImpl
		 * @see org.sheepy.vsand.model.impl.VSandPackageImpl#getTransformations()
		 * @generated
		 */
		EClass TRANSFORMATIONS = eINSTANCE.getTransformations();

		/**
		 * The meta object literal for the '<em><b>Transformations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRANSFORMATIONS__TRANSFORMATIONS = eINSTANCE.getTransformations_Transformations();

		/**
		 * The meta object literal for the '{@link org.sheepy.vsand.model.impl.TransformationImpl <em>Transformation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sheepy.vsand.model.impl.TransformationImpl
		 * @see org.sheepy.vsand.model.impl.VSandPackageImpl#getTransformation()
		 * @generated
		 */
		EClass TRANSFORMATION = eINSTANCE.getTransformation();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRANSFORMATION__NAME = eINSTANCE.getTransformation_Name();

		/**
		 * The meta object literal for the '<em><b>Reactant</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRANSFORMATION__REACTANT = eINSTANCE.getTransformation_Reactant();

		/**
		 * The meta object literal for the '<em><b>Catalyst</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRANSFORMATION__CATALYST = eINSTANCE.getTransformation_Catalyst();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRANSFORMATION__TARGET = eINSTANCE.getTransformation_Target();

		/**
		 * The meta object literal for the '<em><b>Probability</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRANSFORMATION__PROBABILITY = eINSTANCE.getTransformation_Probability();

		/**
		 * The meta object literal for the '<em><b>Propagation</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRANSFORMATION__PROPAGATION = eINSTANCE.getTransformation_Propagation();

		/**
		 * The meta object literal for the '<em><b>Is Static Transformation</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRANSFORMATION__IS_STATIC_TRANSFORMATION = eINSTANCE.getTransformation_IsStaticTransformation();

		/**
		 * The meta object literal for the '{@link org.sheepy.vsand.model.impl.MaterialSelectorPanelImpl <em>Material Selector Panel</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.sheepy.vsand.model.impl.MaterialSelectorPanelImpl
		 * @see org.sheepy.vsand.model.impl.VSandPackageImpl#getMaterialSelectorPanel()
		 * @generated
		 */
		EClass MATERIAL_SELECTOR_PANEL = eINSTANCE.getMaterialSelectorPanel();

		/**
		 * The meta object literal for the '<em><b>Line Height</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MATERIAL_SELECTOR_PANEL__LINE_HEIGHT = eINSTANCE.getMaterialSelectorPanel_LineHeight();

		/**
		 * The meta object literal for the '<em><b>Primary R</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MATERIAL_SELECTOR_PANEL__PRIMARY_R = eINSTANCE.getMaterialSelectorPanel_PrimaryR();

		/**
		 * The meta object literal for the '<em><b>Primary G</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MATERIAL_SELECTOR_PANEL__PRIMARY_G = eINSTANCE.getMaterialSelectorPanel_PrimaryG();

		/**
		 * The meta object literal for the '<em><b>Primary B</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MATERIAL_SELECTOR_PANEL__PRIMARY_B = eINSTANCE.getMaterialSelectorPanel_PrimaryB();

		/**
		 * The meta object literal for the '<em><b>Secondary R</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MATERIAL_SELECTOR_PANEL__SECONDARY_R = eINSTANCE.getMaterialSelectorPanel_SecondaryR();

		/**
		 * The meta object literal for the '<em><b>Secondary G</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MATERIAL_SELECTOR_PANEL__SECONDARY_G = eINSTANCE.getMaterialSelectorPanel_SecondaryG();

		/**
		 * The meta object literal for the '<em><b>Secondary B</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MATERIAL_SELECTOR_PANEL__SECONDARY_B = eINSTANCE.getMaterialSelectorPanel_SecondaryB();

	}

} //VSandPackage
