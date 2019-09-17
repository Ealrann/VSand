/**
 */
package org.sheepy.vsand.model;

import org.eclipse.emf.common.util.EList;
import org.sheepy.lily.core.model.application.Application;
import org.sheepy.lily.vulkan.model.process.CompositeTask;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Application</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.sheepy.vsand.model.VSandApplication#getMaterials <em>Materials</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.VSandApplication#getTransformations <em>Transformations</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.VSandApplication#getDrawQueue <em>Draw Queue</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.VSandApplication#getMainMaterial <em>Main Material</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.VSandApplication#getSecondaryMaterial <em>Secondary Material</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.VSandApplication#isNextMode <em>Next Mode</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.VSandApplication#isPaused <em>Paused</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.VSandApplication#getSpeed <em>Speed</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.VSandApplication#isForceClear <em>Force Clear</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.VSandApplication#isShowSleepZones <em>Show Sleep Zones</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.VSandApplication#getBrushSize <em>Brush Size</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.VSandApplication#getBoardUpdateTask <em>Board Update Task</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.VSandApplication#getVersion <em>Version</em>}</li>
 * </ul>
 *
 * @see org.sheepy.vsand.model.VSandPackage#getVSandApplication()
 * @model
 * @generated
 */
public interface VSandApplication extends Application
{

	/**
	 * Returns the value of the '<em><b>Materials</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Materials</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Materials</em>' containment reference.
	 * @see #setMaterials(Materials)
	 * @see org.sheepy.vsand.model.VSandPackage#getVSandApplication_Materials()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Materials getMaterials();

	/**
	 * Sets the value of the '{@link org.sheepy.vsand.model.VSandApplication#getMaterials <em>Materials</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Materials</em>' containment reference.
	 * @see #getMaterials()
	 * @generated
	 */
	void setMaterials(Materials value);

	/**
	 * Returns the value of the '<em><b>Transformations</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Transformations</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Transformations</em>' containment reference.
	 * @see #setTransformations(Transformations)
	 * @see org.sheepy.vsand.model.VSandPackage#getVSandApplication_Transformations()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Transformations getTransformations();

	/**
	 * Sets the value of the '{@link org.sheepy.vsand.model.VSandApplication#getTransformations <em>Transformations</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Transformations</em>' containment reference.
	 * @see #getTransformations()
	 * @generated
	 */
	void setTransformations(Transformations value);

	/**
	 * Returns the value of the '<em><b>Draw Queue</b></em>' containment reference list.
	 * The list contents are of type {@link org.sheepy.vsand.model.DrawCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Draw Queue</em>' containment reference list.
	 * @see org.sheepy.vsand.model.VSandPackage#getVSandApplication_DrawQueue()
	 * @model containment="true"
	 * @generated
	 */
	EList<DrawCommand> getDrawQueue();

	/**
	 * Returns the value of the '<em><b>Main Material</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Main Material</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Main Material</em>' reference.
	 * @see #setMainMaterial(Material)
	 * @see org.sheepy.vsand.model.VSandPackage#getVSandApplication_MainMaterial()
	 * @model
	 * @generated
	 */
	Material getMainMaterial();

	/**
	 * Sets the value of the '{@link org.sheepy.vsand.model.VSandApplication#getMainMaterial <em>Main Material</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Main Material</em>' reference.
	 * @see #getMainMaterial()
	 * @generated
	 */
	void setMainMaterial(Material value);

	/**
	 * Returns the value of the '<em><b>Secondary Material</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Secondary Material</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Secondary Material</em>' reference.
	 * @see #setSecondaryMaterial(Material)
	 * @see org.sheepy.vsand.model.VSandPackage#getVSandApplication_SecondaryMaterial()
	 * @model
	 * @generated
	 */
	Material getSecondaryMaterial();

	/**
	 * Sets the value of the '{@link org.sheepy.vsand.model.VSandApplication#getSecondaryMaterial <em>Secondary Material</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Secondary Material</em>' reference.
	 * @see #getSecondaryMaterial()
	 * @generated
	 */
	void setSecondaryMaterial(Material value);

	/**
	 * Returns the value of the '<em><b>Next Mode</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Next Mode</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Next Mode</em>' attribute.
	 * @see #setNextMode(boolean)
	 * @see org.sheepy.vsand.model.VSandPackage#getVSandApplication_NextMode()
	 * @model default="false" unique="false"
	 * @generated
	 */
	boolean isNextMode();

	/**
	 * Sets the value of the '{@link org.sheepy.vsand.model.VSandApplication#isNextMode <em>Next Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Next Mode</em>' attribute.
	 * @see #isNextMode()
	 * @generated
	 */
	void setNextMode(boolean value);

	/**
	 * Returns the value of the '<em><b>Paused</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Paused</em>' attribute.
	 * @see #setPaused(boolean)
	 * @see org.sheepy.vsand.model.VSandPackage#getVSandApplication_Paused()
	 * @model default="false" unique="false"
	 * @generated
	 */
	boolean isPaused();

	/**
	 * Sets the value of the '{@link org.sheepy.vsand.model.VSandApplication#isPaused <em>Paused</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Paused</em>' attribute.
	 * @see #isPaused()
	 * @generated
	 */
	void setPaused(boolean value);

	/**
	 * Returns the value of the '<em><b>Speed</b></em>' attribute.
	 * The default value is <code>"1"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Speed</em>' attribute.
	 * @see #setSpeed(int)
	 * @see org.sheepy.vsand.model.VSandPackage#getVSandApplication_Speed()
	 * @model default="1" unique="false"
	 * @generated
	 */
	int getSpeed();

	/**
	 * Sets the value of the '{@link org.sheepy.vsand.model.VSandApplication#getSpeed <em>Speed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Speed</em>' attribute.
	 * @see #getSpeed()
	 * @generated
	 */
	void setSpeed(int value);

	/**
	 * Returns the value of the '<em><b>Force Clear</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Force Clear</em>' attribute.
	 * @see #setForceClear(boolean)
	 * @see org.sheepy.vsand.model.VSandPackage#getVSandApplication_ForceClear()
	 * @model default="false" unique="false"
	 * @generated
	 */
	boolean isForceClear();

	/**
	 * Sets the value of the '{@link org.sheepy.vsand.model.VSandApplication#isForceClear <em>Force Clear</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Force Clear</em>' attribute.
	 * @see #isForceClear()
	 * @generated
	 */
	void setForceClear(boolean value);

	/**
	 * Returns the value of the '<em><b>Show Sleep Zones</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Show Sleep Zones</em>' attribute.
	 * @see #setShowSleepZones(boolean)
	 * @see org.sheepy.vsand.model.VSandPackage#getVSandApplication_ShowSleepZones()
	 * @model default="false" unique="false"
	 * @generated
	 */
	boolean isShowSleepZones();

	/**
	 * Sets the value of the '{@link org.sheepy.vsand.model.VSandApplication#isShowSleepZones <em>Show Sleep Zones</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Show Sleep Zones</em>' attribute.
	 * @see #isShowSleepZones()
	 * @generated
	 */
	void setShowSleepZones(boolean value);

	/**
	 * Returns the value of the '<em><b>Brush Size</b></em>' attribute.
	 * The default value is <code>"4"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Brush Size</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Brush Size</em>' attribute.
	 * @see #setBrushSize(int)
	 * @see org.sheepy.vsand.model.VSandPackage#getVSandApplication_BrushSize()
	 * @model default="4" unique="false"
	 * @generated
	 */
	int getBrushSize();

	/**
	 * Sets the value of the '{@link org.sheepy.vsand.model.VSandApplication#getBrushSize <em>Brush Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Brush Size</em>' attribute.
	 * @see #getBrushSize()
	 * @generated
	 */
	void setBrushSize(int value);

	/**
	 * Returns the value of the '<em><b>Board Update Task</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Board Update Task</em>' reference.
	 * @see #setBoardUpdateTask(CompositeTask)
	 * @see org.sheepy.vsand.model.VSandPackage#getVSandApplication_BoardUpdateTask()
	 * @model
	 * @generated
	 */
	CompositeTask getBoardUpdateTask();

	/**
	 * Sets the value of the '{@link org.sheepy.vsand.model.VSandApplication#getBoardUpdateTask <em>Board Update Task</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Board Update Task</em>' reference.
	 * @see #getBoardUpdateTask()
	 * @generated
	 */
	void setBoardUpdateTask(CompositeTask value);

	/**
	 * Returns the value of the '<em><b>Version</b></em>' attribute.
	 * The default value is <code>"0.0.0"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Version</em>' attribute.
	 * @see #setVersion(String)
	 * @see org.sheepy.vsand.model.VSandPackage#getVSandApplication_Version()
	 * @model default="0.0.0" required="true"
	 * @generated
	 */
	String getVersion();

	/**
	 * Sets the value of the '{@link org.sheepy.vsand.model.VSandApplication#getVersion <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Version</em>' attribute.
	 * @see #getVersion()
	 * @generated
	 */
	void setVersion(String value);
} // VSandApplication
