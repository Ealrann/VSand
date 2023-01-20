/**
 */
package org.sheepy.vsand.model.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.joml.Vector2ic;
import org.sheepy.lily.core.model.application.impl.ApplicationImpl;
import org.sheepy.lily.vulkan.model.process.CompositeTask;
import org.sheepy.vsand.model.DrawCommand;
import org.sheepy.vsand.model.Material;
import org.sheepy.vsand.model.Materials;
import org.sheepy.vsand.model.Transformations;
import org.sheepy.vsand.model.VSandApplication;
import org.sheepy.vsand.model.VSandPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Application</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.sheepy.vsand.model.impl.VSandApplicationImpl#getMaterials <em>Materials</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.impl.VSandApplicationImpl#getTransformations <em>Transformations</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.impl.VSandApplicationImpl#getDrawQueue <em>Draw Queue</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.impl.VSandApplicationImpl#getMainMaterial <em>Main Material</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.impl.VSandApplicationImpl#getSecondaryMaterial <em>Secondary Material</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.impl.VSandApplicationImpl#isNextMode <em>Next Mode</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.impl.VSandApplicationImpl#isPaused <em>Paused</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.impl.VSandApplicationImpl#getSpeed <em>Speed</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.impl.VSandApplicationImpl#isForceClear <em>Force Clear</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.impl.VSandApplicationImpl#isShowSleepZones <em>Show Sleep Zones</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.impl.VSandApplicationImpl#getBrushSize <em>Brush Size</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.impl.VSandApplicationImpl#getBoardUpdateTask <em>Board Update Task</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.impl.VSandApplicationImpl#getVersion <em>Version</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.impl.VSandApplicationImpl#getSize <em>Size</em>}</li>
 * </ul>
 *
 * @generated
 */
public class VSandApplicationImpl extends ApplicationImpl implements VSandApplication
{
	/**
	 * The cached value of the '{@link #getMaterials() <em>Materials</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaterials()
	 * @generated
	 * @ordered
	 */
	protected Materials materials;

	/**
	 * The cached value of the '{@link #getTransformations() <em>Transformations</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTransformations()
	 * @generated
	 * @ordered
	 */
	protected Transformations transformations;

	/**
	 * The cached value of the '{@link #getDrawQueue() <em>Draw Queue</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDrawQueue()
	 * @generated
	 * @ordered
	 */
	protected EList<DrawCommand> drawQueue;

	/**
	 * The cached value of the '{@link #getMainMaterial() <em>Main Material</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMainMaterial()
	 * @generated
	 * @ordered
	 */
	protected Material mainMaterial;

	/**
	 * The cached value of the '{@link #getSecondaryMaterial() <em>Secondary Material</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSecondaryMaterial()
	 * @generated
	 * @ordered
	 */
	protected Material secondaryMaterial;

	/**
	 * The default value of the '{@link #isNextMode() <em>Next Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isNextMode()
	 * @generated
	 * @ordered
	 */
	protected static final boolean NEXT_MODE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isNextMode() <em>Next Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isNextMode()
	 * @generated
	 * @ordered
	 */
	protected boolean nextMode = NEXT_MODE_EDEFAULT;

	/**
	 * The default value of the '{@link #isPaused() <em>Paused</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPaused()
	 * @generated
	 * @ordered
	 */
	protected static final boolean PAUSED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isPaused() <em>Paused</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPaused()
	 * @generated
	 * @ordered
	 */
	protected boolean paused = PAUSED_EDEFAULT;

	/**
	 * The default value of the '{@link #getSpeed() <em>Speed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpeed()
	 * @generated
	 * @ordered
	 */
	protected static final int SPEED_EDEFAULT = 1;

	/**
	 * The cached value of the '{@link #getSpeed() <em>Speed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpeed()
	 * @generated
	 * @ordered
	 */
	protected int speed = SPEED_EDEFAULT;

	/**
	 * The default value of the '{@link #isForceClear() <em>Force Clear</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isForceClear()
	 * @generated
	 * @ordered
	 */
	protected static final boolean FORCE_CLEAR_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isForceClear() <em>Force Clear</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isForceClear()
	 * @generated
	 * @ordered
	 */
	protected boolean forceClear = FORCE_CLEAR_EDEFAULT;

	/**
	 * The default value of the '{@link #isShowSleepZones() <em>Show Sleep Zones</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isShowSleepZones()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SHOW_SLEEP_ZONES_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isShowSleepZones() <em>Show Sleep Zones</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isShowSleepZones()
	 * @generated
	 * @ordered
	 */
	protected boolean showSleepZones = SHOW_SLEEP_ZONES_EDEFAULT;

	/**
	 * The default value of the '{@link #getBrushSize() <em>Brush Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBrushSize()
	 * @generated
	 * @ordered
	 */
	protected static final int BRUSH_SIZE_EDEFAULT = 4;

	/**
	 * The cached value of the '{@link #getBrushSize() <em>Brush Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBrushSize()
	 * @generated
	 * @ordered
	 */
	protected int brushSize = BRUSH_SIZE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getBoardUpdateTask() <em>Board Update Task</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBoardUpdateTask()
	 * @generated
	 * @ordered
	 */
	protected CompositeTask boardUpdateTask;

	/**
	 * The default value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected static final String VERSION_EDEFAULT = "0.0.0";

	/**
	 * The cached value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected String version = VERSION_EDEFAULT;

	/**
	 * The default value of the '{@link #getSize() <em>Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSize()
	 * @generated
	 * @ordered
	 */
	protected static final Vector2ic SIZE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSize() <em>Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSize()
	 * @generated
	 * @ordered
	 */
	protected Vector2ic size = SIZE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected VSandApplicationImpl()
	{
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass()
	{
		return VSandPackage.Literals.VSAND_APPLICATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Materials getMaterials()
	{
		return materials;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMaterials(Materials newMaterials, NotificationChain msgs)
	{
		Materials oldMaterials = materials;
		materials = newMaterials;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, VSandPackage.VSAND_APPLICATION__MATERIALS, oldMaterials, newMaterials);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMaterials(Materials newMaterials)
	{
		if (newMaterials != materials)
		{
			NotificationChain msgs = null;
			if (materials != null)
				msgs = ((InternalEObject)materials).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - VSandPackage.VSAND_APPLICATION__MATERIALS, null, msgs);
			if (newMaterials != null)
				msgs = ((InternalEObject)newMaterials).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - VSandPackage.VSAND_APPLICATION__MATERIALS, null, msgs);
			msgs = basicSetMaterials(newMaterials, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, VSandPackage.VSAND_APPLICATION__MATERIALS, newMaterials, newMaterials));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Transformations getTransformations()
	{
		return transformations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTransformations(Transformations newTransformations, NotificationChain msgs)
	{
		Transformations oldTransformations = transformations;
		transformations = newTransformations;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, VSandPackage.VSAND_APPLICATION__TRANSFORMATIONS, oldTransformations, newTransformations);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTransformations(Transformations newTransformations)
	{
		if (newTransformations != transformations)
		{
			NotificationChain msgs = null;
			if (transformations != null)
				msgs = ((InternalEObject)transformations).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - VSandPackage.VSAND_APPLICATION__TRANSFORMATIONS, null, msgs);
			if (newTransformations != null)
				msgs = ((InternalEObject)newTransformations).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - VSandPackage.VSAND_APPLICATION__TRANSFORMATIONS, null, msgs);
			msgs = basicSetTransformations(newTransformations, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, VSandPackage.VSAND_APPLICATION__TRANSFORMATIONS, newTransformations, newTransformations));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<DrawCommand> getDrawQueue()
	{
		if (drawQueue == null)
		{
			drawQueue = new EObjectContainmentEList<>(DrawCommand.class, this, VSandPackage.VSAND_APPLICATION__DRAW_QUEUE);
		}
		return drawQueue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Material getMainMaterial()
	{
		if (mainMaterial != null && ((EObject)mainMaterial).eIsProxy())
		{
			InternalEObject oldMainMaterial = mainMaterial;
			mainMaterial = (Material)eResolveProxy(oldMainMaterial);
			if (mainMaterial != oldMainMaterial)
			{
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, VSandPackage.VSAND_APPLICATION__MAIN_MATERIAL, oldMainMaterial, mainMaterial));
			}
		}
		return mainMaterial;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Material basicGetMainMaterial()
	{
		return mainMaterial;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMainMaterial(Material newMainMaterial)
	{
		Material oldMainMaterial = mainMaterial;
		mainMaterial = newMainMaterial;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, VSandPackage.VSAND_APPLICATION__MAIN_MATERIAL, oldMainMaterial, mainMaterial));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Material getSecondaryMaterial()
	{
		if (secondaryMaterial != null && ((EObject)secondaryMaterial).eIsProxy())
		{
			InternalEObject oldSecondaryMaterial = secondaryMaterial;
			secondaryMaterial = (Material)eResolveProxy(oldSecondaryMaterial);
			if (secondaryMaterial != oldSecondaryMaterial)
			{
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, VSandPackage.VSAND_APPLICATION__SECONDARY_MATERIAL, oldSecondaryMaterial, secondaryMaterial));
			}
		}
		return secondaryMaterial;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Material basicGetSecondaryMaterial()
	{
		return secondaryMaterial;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSecondaryMaterial(Material newSecondaryMaterial)
	{
		Material oldSecondaryMaterial = secondaryMaterial;
		secondaryMaterial = newSecondaryMaterial;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, VSandPackage.VSAND_APPLICATION__SECONDARY_MATERIAL, oldSecondaryMaterial, secondaryMaterial));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isNextMode()
	{
		return nextMode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setNextMode(boolean newNextMode)
	{
		boolean oldNextMode = nextMode;
		nextMode = newNextMode;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, VSandPackage.VSAND_APPLICATION__NEXT_MODE, oldNextMode, nextMode));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isPaused()
	{
		return paused;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPaused(boolean newPaused)
	{
		boolean oldPaused = paused;
		paused = newPaused;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, VSandPackage.VSAND_APPLICATION__PAUSED, oldPaused, paused));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getSpeed()
	{
		return speed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSpeed(int newSpeed)
	{
		int oldSpeed = speed;
		speed = newSpeed;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, VSandPackage.VSAND_APPLICATION__SPEED, oldSpeed, speed));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isForceClear()
	{
		return forceClear;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setForceClear(boolean newForceClear)
	{
		boolean oldForceClear = forceClear;
		forceClear = newForceClear;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, VSandPackage.VSAND_APPLICATION__FORCE_CLEAR, oldForceClear, forceClear));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isShowSleepZones()
	{
		return showSleepZones;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setShowSleepZones(boolean newShowSleepZones)
	{
		boolean oldShowSleepZones = showSleepZones;
		showSleepZones = newShowSleepZones;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, VSandPackage.VSAND_APPLICATION__SHOW_SLEEP_ZONES, oldShowSleepZones, showSleepZones));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getBrushSize()
	{
		return brushSize;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setBrushSize(int newBrushSize)
	{
		int oldBrushSize = brushSize;
		brushSize = newBrushSize;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, VSandPackage.VSAND_APPLICATION__BRUSH_SIZE, oldBrushSize, brushSize));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CompositeTask getBoardUpdateTask()
	{
		if (boardUpdateTask != null && ((EObject)boardUpdateTask).eIsProxy())
		{
			InternalEObject oldBoardUpdateTask = boardUpdateTask;
			boardUpdateTask = (CompositeTask)eResolveProxy(oldBoardUpdateTask);
			if (boardUpdateTask != oldBoardUpdateTask)
			{
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, VSandPackage.VSAND_APPLICATION__BOARD_UPDATE_TASK, oldBoardUpdateTask, boardUpdateTask));
			}
		}
		return boardUpdateTask;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CompositeTask basicGetBoardUpdateTask()
	{
		return boardUpdateTask;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setBoardUpdateTask(CompositeTask newBoardUpdateTask)
	{
		CompositeTask oldBoardUpdateTask = boardUpdateTask;
		boardUpdateTask = newBoardUpdateTask;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, VSandPackage.VSAND_APPLICATION__BOARD_UPDATE_TASK, oldBoardUpdateTask, boardUpdateTask));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getVersion()
	{
		return version;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setVersion(String newVersion)
	{
		String oldVersion = version;
		version = newVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, VSandPackage.VSAND_APPLICATION__VERSION, oldVersion, version));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Vector2ic getSize()
	{
		return size;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSize(Vector2ic newSize)
	{
		Vector2ic oldSize = size;
		size = newSize;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, VSandPackage.VSAND_APPLICATION__SIZE, oldSize, size));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
	{
		switch (featureID)
		{
			case VSandPackage.VSAND_APPLICATION__MATERIALS:
				return basicSetMaterials(null, msgs);
			case VSandPackage.VSAND_APPLICATION__TRANSFORMATIONS:
				return basicSetTransformations(null, msgs);
			case VSandPackage.VSAND_APPLICATION__DRAW_QUEUE:
				return ((InternalEList<?>)getDrawQueue()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType)
	{
		switch (featureID)
		{
			case VSandPackage.VSAND_APPLICATION__MATERIALS:
				return getMaterials();
			case VSandPackage.VSAND_APPLICATION__TRANSFORMATIONS:
				return getTransformations();
			case VSandPackage.VSAND_APPLICATION__DRAW_QUEUE:
				return getDrawQueue();
			case VSandPackage.VSAND_APPLICATION__MAIN_MATERIAL:
				if (resolve) return getMainMaterial();
				return basicGetMainMaterial();
			case VSandPackage.VSAND_APPLICATION__SECONDARY_MATERIAL:
				if (resolve) return getSecondaryMaterial();
				return basicGetSecondaryMaterial();
			case VSandPackage.VSAND_APPLICATION__NEXT_MODE:
				return isNextMode();
			case VSandPackage.VSAND_APPLICATION__PAUSED:
				return isPaused();
			case VSandPackage.VSAND_APPLICATION__SPEED:
				return getSpeed();
			case VSandPackage.VSAND_APPLICATION__FORCE_CLEAR:
				return isForceClear();
			case VSandPackage.VSAND_APPLICATION__SHOW_SLEEP_ZONES:
				return isShowSleepZones();
			case VSandPackage.VSAND_APPLICATION__BRUSH_SIZE:
				return getBrushSize();
			case VSandPackage.VSAND_APPLICATION__BOARD_UPDATE_TASK:
				if (resolve) return getBoardUpdateTask();
				return basicGetBoardUpdateTask();
			case VSandPackage.VSAND_APPLICATION__VERSION:
				return getVersion();
			case VSandPackage.VSAND_APPLICATION__SIZE:
				return getSize();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue)
	{
		switch (featureID)
		{
			case VSandPackage.VSAND_APPLICATION__MATERIALS:
				setMaterials((Materials)newValue);
				return;
			case VSandPackage.VSAND_APPLICATION__TRANSFORMATIONS:
				setTransformations((Transformations)newValue);
				return;
			case VSandPackage.VSAND_APPLICATION__DRAW_QUEUE:
				getDrawQueue().clear();
				getDrawQueue().addAll((Collection<? extends DrawCommand>)newValue);
				return;
			case VSandPackage.VSAND_APPLICATION__MAIN_MATERIAL:
				setMainMaterial((Material)newValue);
				return;
			case VSandPackage.VSAND_APPLICATION__SECONDARY_MATERIAL:
				setSecondaryMaterial((Material)newValue);
				return;
			case VSandPackage.VSAND_APPLICATION__NEXT_MODE:
				setNextMode((Boolean)newValue);
				return;
			case VSandPackage.VSAND_APPLICATION__PAUSED:
				setPaused((Boolean)newValue);
				return;
			case VSandPackage.VSAND_APPLICATION__SPEED:
				setSpeed((Integer)newValue);
				return;
			case VSandPackage.VSAND_APPLICATION__FORCE_CLEAR:
				setForceClear((Boolean)newValue);
				return;
			case VSandPackage.VSAND_APPLICATION__SHOW_SLEEP_ZONES:
				setShowSleepZones((Boolean)newValue);
				return;
			case VSandPackage.VSAND_APPLICATION__BRUSH_SIZE:
				setBrushSize((Integer)newValue);
				return;
			case VSandPackage.VSAND_APPLICATION__BOARD_UPDATE_TASK:
				setBoardUpdateTask((CompositeTask)newValue);
				return;
			case VSandPackage.VSAND_APPLICATION__VERSION:
				setVersion((String)newValue);
				return;
			case VSandPackage.VSAND_APPLICATION__SIZE:
				setSize((Vector2ic)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID)
	{
		switch (featureID)
		{
			case VSandPackage.VSAND_APPLICATION__MATERIALS:
				setMaterials((Materials)null);
				return;
			case VSandPackage.VSAND_APPLICATION__TRANSFORMATIONS:
				setTransformations((Transformations)null);
				return;
			case VSandPackage.VSAND_APPLICATION__DRAW_QUEUE:
				getDrawQueue().clear();
				return;
			case VSandPackage.VSAND_APPLICATION__MAIN_MATERIAL:
				setMainMaterial((Material)null);
				return;
			case VSandPackage.VSAND_APPLICATION__SECONDARY_MATERIAL:
				setSecondaryMaterial((Material)null);
				return;
			case VSandPackage.VSAND_APPLICATION__NEXT_MODE:
				setNextMode(NEXT_MODE_EDEFAULT);
				return;
			case VSandPackage.VSAND_APPLICATION__PAUSED:
				setPaused(PAUSED_EDEFAULT);
				return;
			case VSandPackage.VSAND_APPLICATION__SPEED:
				setSpeed(SPEED_EDEFAULT);
				return;
			case VSandPackage.VSAND_APPLICATION__FORCE_CLEAR:
				setForceClear(FORCE_CLEAR_EDEFAULT);
				return;
			case VSandPackage.VSAND_APPLICATION__SHOW_SLEEP_ZONES:
				setShowSleepZones(SHOW_SLEEP_ZONES_EDEFAULT);
				return;
			case VSandPackage.VSAND_APPLICATION__BRUSH_SIZE:
				setBrushSize(BRUSH_SIZE_EDEFAULT);
				return;
			case VSandPackage.VSAND_APPLICATION__BOARD_UPDATE_TASK:
				setBoardUpdateTask((CompositeTask)null);
				return;
			case VSandPackage.VSAND_APPLICATION__VERSION:
				setVersion(VERSION_EDEFAULT);
				return;
			case VSandPackage.VSAND_APPLICATION__SIZE:
				setSize(SIZE_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID)
	{
		switch (featureID)
		{
			case VSandPackage.VSAND_APPLICATION__MATERIALS:
				return materials != null;
			case VSandPackage.VSAND_APPLICATION__TRANSFORMATIONS:
				return transformations != null;
			case VSandPackage.VSAND_APPLICATION__DRAW_QUEUE:
				return drawQueue != null && !drawQueue.isEmpty();
			case VSandPackage.VSAND_APPLICATION__MAIN_MATERIAL:
				return mainMaterial != null;
			case VSandPackage.VSAND_APPLICATION__SECONDARY_MATERIAL:
				return secondaryMaterial != null;
			case VSandPackage.VSAND_APPLICATION__NEXT_MODE:
				return nextMode != NEXT_MODE_EDEFAULT;
			case VSandPackage.VSAND_APPLICATION__PAUSED:
				return paused != PAUSED_EDEFAULT;
			case VSandPackage.VSAND_APPLICATION__SPEED:
				return speed != SPEED_EDEFAULT;
			case VSandPackage.VSAND_APPLICATION__FORCE_CLEAR:
				return forceClear != FORCE_CLEAR_EDEFAULT;
			case VSandPackage.VSAND_APPLICATION__SHOW_SLEEP_ZONES:
				return showSleepZones != SHOW_SLEEP_ZONES_EDEFAULT;
			case VSandPackage.VSAND_APPLICATION__BRUSH_SIZE:
				return brushSize != BRUSH_SIZE_EDEFAULT;
			case VSandPackage.VSAND_APPLICATION__BOARD_UPDATE_TASK:
				return boardUpdateTask != null;
			case VSandPackage.VSAND_APPLICATION__VERSION:
				return VERSION_EDEFAULT == null ? version != null : !VERSION_EDEFAULT.equals(version);
			case VSandPackage.VSAND_APPLICATION__SIZE:
				return SIZE_EDEFAULT == null ? size != null : !SIZE_EDEFAULT.equals(size);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString()
	{
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (nextMode: ");
		result.append(nextMode);
		result.append(", paused: ");
		result.append(paused);
		result.append(", speed: ");
		result.append(speed);
		result.append(", forceClear: ");
		result.append(forceClear);
		result.append(", showSleepZones: ");
		result.append(showSleepZones);
		result.append(", brushSize: ");
		result.append(brushSize);
		result.append(", version: ");
		result.append(version);
		result.append(", size: ");
		result.append(size);
		result.append(')');
		return result.toString();
	}

} //VSandApplicationImpl
