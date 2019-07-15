/**
 */
package org.sheepy.vsand.model.impl;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EContentsEList;
import org.joml.Vector2ic;
import org.sheepy.lily.core.api.util.LTreeIterator;
import org.sheepy.lily.core.model.inference.IInferenceObject;
import org.sheepy.lily.core.model.presentation.IPositionElement;
import org.sheepy.lily.core.model.presentation.IUIElement;
import org.sheepy.lily.core.model.presentation.PresentationPackage;
import org.sheepy.lily.core.model.root.LObject;
import org.sheepy.lily.core.model.root.RootPackage;
import org.sheepy.lily.core.model.root.RootPackage.Literals;
import org.sheepy.lily.core.model.types.EHorizontalRelative;
import org.sheepy.lily.core.model.types.EVerticalRelative;
import org.sheepy.lily.core.model.types.TypesFactory;
import org.sheepy.lily.core.model.types.TypesPackage;
import org.sheepy.vsand.model.MaterialSelectorPanel;
import org.sheepy.vsand.model.VSandPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Material Selector Panel</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.sheepy.vsand.model.impl.MaterialSelectorPanelImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.impl.MaterialSelectorPanelImpl#getContentObjects <em>Content Objects</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.impl.MaterialSelectorPanelImpl#getPosition <em>Position</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.impl.MaterialSelectorPanelImpl#getVerticalRelative <em>Vertical Relative</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.impl.MaterialSelectorPanelImpl#getHorizontalRelative <em>Horizontal Relative</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.impl.MaterialSelectorPanelImpl#getLineHeight <em>Line Height</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.impl.MaterialSelectorPanelImpl#getPrimaryR <em>Primary R</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.impl.MaterialSelectorPanelImpl#getPrimaryG <em>Primary G</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.impl.MaterialSelectorPanelImpl#getPrimaryB <em>Primary B</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.impl.MaterialSelectorPanelImpl#getSecondaryR <em>Secondary R</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.impl.MaterialSelectorPanelImpl#getSecondaryG <em>Secondary G</em>}</li>
 *   <li>{@link org.sheepy.vsand.model.impl.MaterialSelectorPanelImpl#getSecondaryB <em>Secondary B</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MaterialSelectorPanelImpl extends MinimalEObjectImpl.Container implements MaterialSelectorPanel
{
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getContentObjects() <em>Content Objects</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContentObjects()
	 * @generated
	 * @ordered
	 */
	protected EList<LObject> contentObjects;

	/**
	 * The default value of the '{@link #getPosition() <em>Position</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPosition()
	 * @generated
	 * @ordered
	 */
	protected static final Vector2ic POSITION_EDEFAULT = (Vector2ic)TypesFactory.eINSTANCE.createFromString(TypesPackage.eINSTANCE.getVector2i(), "0;0");

	/**
	 * The cached value of the '{@link #getPosition() <em>Position</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPosition()
	 * @generated
	 * @ordered
	 */
	protected Vector2ic position = POSITION_EDEFAULT;

	/**
	 * The default value of the '{@link #getVerticalRelative() <em>Vertical Relative</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVerticalRelative()
	 * @generated
	 * @ordered
	 */
	protected static final EVerticalRelative VERTICAL_RELATIVE_EDEFAULT = EVerticalRelative.TOP;

	/**
	 * The cached value of the '{@link #getVerticalRelative() <em>Vertical Relative</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVerticalRelative()
	 * @generated
	 * @ordered
	 */
	protected EVerticalRelative verticalRelative = VERTICAL_RELATIVE_EDEFAULT;

	/**
	 * The default value of the '{@link #getHorizontalRelative() <em>Horizontal Relative</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHorizontalRelative()
	 * @generated
	 * @ordered
	 */
	protected static final EHorizontalRelative HORIZONTAL_RELATIVE_EDEFAULT = EHorizontalRelative.LEFT;

	/**
	 * The cached value of the '{@link #getHorizontalRelative() <em>Horizontal Relative</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHorizontalRelative()
	 * @generated
	 * @ordered
	 */
	protected EHorizontalRelative horizontalRelative = HORIZONTAL_RELATIVE_EDEFAULT;

	/**
	 * The default value of the '{@link #getLineHeight() <em>Line Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLineHeight()
	 * @generated
	 * @ordered
	 */
	protected static final int LINE_HEIGHT_EDEFAULT = 32;

	/**
	 * The cached value of the '{@link #getLineHeight() <em>Line Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLineHeight()
	 * @generated
	 * @ordered
	 */
	protected int lineHeight = LINE_HEIGHT_EDEFAULT;

	/**
	 * The default value of the '{@link #getPrimaryR() <em>Primary R</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPrimaryR()
	 * @generated
	 * @ordered
	 */
	protected static final int PRIMARY_R_EDEFAULT = 255;

	/**
	 * The cached value of the '{@link #getPrimaryR() <em>Primary R</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPrimaryR()
	 * @generated
	 * @ordered
	 */
	protected int primaryR = PRIMARY_R_EDEFAULT;

	/**
	 * The default value of the '{@link #getPrimaryG() <em>Primary G</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPrimaryG()
	 * @generated
	 * @ordered
	 */
	protected static final int PRIMARY_G_EDEFAULT = 50;

	/**
	 * The cached value of the '{@link #getPrimaryG() <em>Primary G</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPrimaryG()
	 * @generated
	 * @ordered
	 */
	protected int primaryG = PRIMARY_G_EDEFAULT;

	/**
	 * The default value of the '{@link #getPrimaryB() <em>Primary B</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPrimaryB()
	 * @generated
	 * @ordered
	 */
	protected static final int PRIMARY_B_EDEFAULT = 50;

	/**
	 * The cached value of the '{@link #getPrimaryB() <em>Primary B</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPrimaryB()
	 * @generated
	 * @ordered
	 */
	protected int primaryB = PRIMARY_B_EDEFAULT;

	/**
	 * The default value of the '{@link #getSecondaryR() <em>Secondary R</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSecondaryR()
	 * @generated
	 * @ordered
	 */
	protected static final int SECONDARY_R_EDEFAULT = 50;

	/**
	 * The cached value of the '{@link #getSecondaryR() <em>Secondary R</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSecondaryR()
	 * @generated
	 * @ordered
	 */
	protected int secondaryR = SECONDARY_R_EDEFAULT;

	/**
	 * The default value of the '{@link #getSecondaryG() <em>Secondary G</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSecondaryG()
	 * @generated
	 * @ordered
	 */
	protected static final int SECONDARY_G_EDEFAULT = 50;

	/**
	 * The cached value of the '{@link #getSecondaryG() <em>Secondary G</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSecondaryG()
	 * @generated
	 * @ordered
	 */
	protected int secondaryG = SECONDARY_G_EDEFAULT;

	/**
	 * The default value of the '{@link #getSecondaryB() <em>Secondary B</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSecondaryB()
	 * @generated
	 * @ordered
	 */
	protected static final int SECONDARY_B_EDEFAULT = 255;

	/**
	 * The cached value of the '{@link #getSecondaryB() <em>Secondary B</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSecondaryB()
	 * @generated
	 * @ordered
	 */
	protected int secondaryB = SECONDARY_B_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MaterialSelectorPanelImpl()
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
		return VSandPackage.Literals.MATERIAL_SELECTOR_PANEL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName()
	{
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setName(String newName)
	{
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, VSandPackage.MATERIAL_SELECTOR_PANEL__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<LObject> getContentObjects()
	{
		return contentObjects;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setContentObjects(EList<LObject> newContentObjects)
	{
		EList<LObject> oldContentObjects = contentObjects;
		contentObjects = newContentObjects;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, VSandPackage.MATERIAL_SELECTOR_PANEL__CONTENT_OBJECTS, oldContentObjects, contentObjects));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Vector2ic getPosition()
	{
		return position;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPosition(Vector2ic newPosition)
	{
		Vector2ic oldPosition = position;
		position = newPosition;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, VSandPackage.MATERIAL_SELECTOR_PANEL__POSITION, oldPosition, position));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EVerticalRelative getVerticalRelative()
	{
		return verticalRelative;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setVerticalRelative(EVerticalRelative newVerticalRelative)
	{
		EVerticalRelative oldVerticalRelative = verticalRelative;
		verticalRelative = newVerticalRelative == null ? VERTICAL_RELATIVE_EDEFAULT : newVerticalRelative;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, VSandPackage.MATERIAL_SELECTOR_PANEL__VERTICAL_RELATIVE, oldVerticalRelative, verticalRelative));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EHorizontalRelative getHorizontalRelative()
	{
		return horizontalRelative;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setHorizontalRelative(EHorizontalRelative newHorizontalRelative)
	{
		EHorizontalRelative oldHorizontalRelative = horizontalRelative;
		horizontalRelative = newHorizontalRelative == null ? HORIZONTAL_RELATIVE_EDEFAULT : newHorizontalRelative;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, VSandPackage.MATERIAL_SELECTOR_PANEL__HORIZONTAL_RELATIVE, oldHorizontalRelative, horizontalRelative));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getLineHeight()
	{
		return lineHeight;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setLineHeight(int newLineHeight)
	{
		int oldLineHeight = lineHeight;
		lineHeight = newLineHeight;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, VSandPackage.MATERIAL_SELECTOR_PANEL__LINE_HEIGHT, oldLineHeight, lineHeight));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getPrimaryR()
	{
		return primaryR;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPrimaryR(int newPrimaryR)
	{
		int oldPrimaryR = primaryR;
		primaryR = newPrimaryR;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, VSandPackage.MATERIAL_SELECTOR_PANEL__PRIMARY_R, oldPrimaryR, primaryR));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getPrimaryG()
	{
		return primaryG;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPrimaryG(int newPrimaryG)
	{
		int oldPrimaryG = primaryG;
		primaryG = newPrimaryG;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, VSandPackage.MATERIAL_SELECTOR_PANEL__PRIMARY_G, oldPrimaryG, primaryG));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getPrimaryB()
	{
		return primaryB;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPrimaryB(int newPrimaryB)
	{
		int oldPrimaryB = primaryB;
		primaryB = newPrimaryB;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, VSandPackage.MATERIAL_SELECTOR_PANEL__PRIMARY_B, oldPrimaryB, primaryB));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getSecondaryR()
	{
		return secondaryR;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSecondaryR(int newSecondaryR)
	{
		int oldSecondaryR = secondaryR;
		secondaryR = newSecondaryR;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, VSandPackage.MATERIAL_SELECTOR_PANEL__SECONDARY_R, oldSecondaryR, secondaryR));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getSecondaryG()
	{
		return secondaryG;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSecondaryG(int newSecondaryG)
	{
		int oldSecondaryG = secondaryG;
		secondaryG = newSecondaryG;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, VSandPackage.MATERIAL_SELECTOR_PANEL__SECONDARY_G, oldSecondaryG, secondaryG));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getSecondaryB()
	{
		return secondaryB;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSecondaryB(int newSecondaryB)
	{
		int oldSecondaryB = secondaryB;
		secondaryB = newSecondaryB;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, VSandPackage.MATERIAL_SELECTOR_PANEL__SECONDARY_B, oldSecondaryB, secondaryB));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public <T extends LObject> EList<T> createContainmentEList(final EClass targetEClass)
	{
		EList<T> res = null;
		final List<EStructuralFeature> unitRefs = new ArrayList<EStructuralFeature>();
		EList<EReference> _eAllContainments = this.eClass().getEAllContainments();
		for (final EReference ref : _eAllContainments)
		{
			EClassifier _eType = ref.getEType();
			boolean _isSuperTypeOf = targetEClass.isSuperTypeOf(((EClass) _eType));
			if (_isSuperTypeOf)
			{
				unitRefs.add(ref);
			}
		}
		boolean _isEmpty = unitRefs.isEmpty();
		if (_isEmpty)
		{
			res = ECollections.<T>emptyEList();
		}
		else
		{
			EContentsEList<T> _eContentsEList = new EContentsEList<T>(this, unitRefs);
			res = _eContentsEList;
		}
		return res;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<LObject> lContents()
	{
		EList<LObject> _xblockexpression = null;
		{
			EList<LObject> _contentObjects = this.getContentObjects();
			boolean _tripleEquals = (_contentObjects == null);
			if (_tripleEquals)
			{
				this.setContentObjects(this.<LObject>createContainmentEList(Literals.LOBJECT));
			}
			_xblockexpression = this.getContentObjects();
		}
		return _xblockexpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LObject lParent()
	{
		LObject _xifexpression = null;
		EObject _eContainer = this.eContainer();
		if ((_eContainer instanceof LObject))
		{
			EObject _eContainer_1 = this.eContainer();
			_xifexpression = ((LObject) _eContainer_1);
		}
		return _xifexpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LTreeIterator lAllContents()
	{
		return new LTreeIterator(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IInferenceObject lInferenceObject()
	{
		return this;
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
			case VSandPackage.MATERIAL_SELECTOR_PANEL__NAME:
				return getName();
			case VSandPackage.MATERIAL_SELECTOR_PANEL__CONTENT_OBJECTS:
				return getContentObjects();
			case VSandPackage.MATERIAL_SELECTOR_PANEL__POSITION:
				return getPosition();
			case VSandPackage.MATERIAL_SELECTOR_PANEL__VERTICAL_RELATIVE:
				return getVerticalRelative();
			case VSandPackage.MATERIAL_SELECTOR_PANEL__HORIZONTAL_RELATIVE:
				return getHorizontalRelative();
			case VSandPackage.MATERIAL_SELECTOR_PANEL__LINE_HEIGHT:
				return getLineHeight();
			case VSandPackage.MATERIAL_SELECTOR_PANEL__PRIMARY_R:
				return getPrimaryR();
			case VSandPackage.MATERIAL_SELECTOR_PANEL__PRIMARY_G:
				return getPrimaryG();
			case VSandPackage.MATERIAL_SELECTOR_PANEL__PRIMARY_B:
				return getPrimaryB();
			case VSandPackage.MATERIAL_SELECTOR_PANEL__SECONDARY_R:
				return getSecondaryR();
			case VSandPackage.MATERIAL_SELECTOR_PANEL__SECONDARY_G:
				return getSecondaryG();
			case VSandPackage.MATERIAL_SELECTOR_PANEL__SECONDARY_B:
				return getSecondaryB();
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
			case VSandPackage.MATERIAL_SELECTOR_PANEL__NAME:
				setName((String)newValue);
				return;
			case VSandPackage.MATERIAL_SELECTOR_PANEL__CONTENT_OBJECTS:
				setContentObjects((EList<LObject>)newValue);
				return;
			case VSandPackage.MATERIAL_SELECTOR_PANEL__POSITION:
				setPosition((Vector2ic)newValue);
				return;
			case VSandPackage.MATERIAL_SELECTOR_PANEL__VERTICAL_RELATIVE:
				setVerticalRelative((EVerticalRelative)newValue);
				return;
			case VSandPackage.MATERIAL_SELECTOR_PANEL__HORIZONTAL_RELATIVE:
				setHorizontalRelative((EHorizontalRelative)newValue);
				return;
			case VSandPackage.MATERIAL_SELECTOR_PANEL__LINE_HEIGHT:
				setLineHeight((Integer)newValue);
				return;
			case VSandPackage.MATERIAL_SELECTOR_PANEL__PRIMARY_R:
				setPrimaryR((Integer)newValue);
				return;
			case VSandPackage.MATERIAL_SELECTOR_PANEL__PRIMARY_G:
				setPrimaryG((Integer)newValue);
				return;
			case VSandPackage.MATERIAL_SELECTOR_PANEL__PRIMARY_B:
				setPrimaryB((Integer)newValue);
				return;
			case VSandPackage.MATERIAL_SELECTOR_PANEL__SECONDARY_R:
				setSecondaryR((Integer)newValue);
				return;
			case VSandPackage.MATERIAL_SELECTOR_PANEL__SECONDARY_G:
				setSecondaryG((Integer)newValue);
				return;
			case VSandPackage.MATERIAL_SELECTOR_PANEL__SECONDARY_B:
				setSecondaryB((Integer)newValue);
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
			case VSandPackage.MATERIAL_SELECTOR_PANEL__NAME:
				setName(NAME_EDEFAULT);
				return;
			case VSandPackage.MATERIAL_SELECTOR_PANEL__CONTENT_OBJECTS:
				setContentObjects((EList<LObject>)null);
				return;
			case VSandPackage.MATERIAL_SELECTOR_PANEL__POSITION:
				setPosition(POSITION_EDEFAULT);
				return;
			case VSandPackage.MATERIAL_SELECTOR_PANEL__VERTICAL_RELATIVE:
				setVerticalRelative(VERTICAL_RELATIVE_EDEFAULT);
				return;
			case VSandPackage.MATERIAL_SELECTOR_PANEL__HORIZONTAL_RELATIVE:
				setHorizontalRelative(HORIZONTAL_RELATIVE_EDEFAULT);
				return;
			case VSandPackage.MATERIAL_SELECTOR_PANEL__LINE_HEIGHT:
				setLineHeight(LINE_HEIGHT_EDEFAULT);
				return;
			case VSandPackage.MATERIAL_SELECTOR_PANEL__PRIMARY_R:
				setPrimaryR(PRIMARY_R_EDEFAULT);
				return;
			case VSandPackage.MATERIAL_SELECTOR_PANEL__PRIMARY_G:
				setPrimaryG(PRIMARY_G_EDEFAULT);
				return;
			case VSandPackage.MATERIAL_SELECTOR_PANEL__PRIMARY_B:
				setPrimaryB(PRIMARY_B_EDEFAULT);
				return;
			case VSandPackage.MATERIAL_SELECTOR_PANEL__SECONDARY_R:
				setSecondaryR(SECONDARY_R_EDEFAULT);
				return;
			case VSandPackage.MATERIAL_SELECTOR_PANEL__SECONDARY_G:
				setSecondaryG(SECONDARY_G_EDEFAULT);
				return;
			case VSandPackage.MATERIAL_SELECTOR_PANEL__SECONDARY_B:
				setSecondaryB(SECONDARY_B_EDEFAULT);
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
			case VSandPackage.MATERIAL_SELECTOR_PANEL__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case VSandPackage.MATERIAL_SELECTOR_PANEL__CONTENT_OBJECTS:
				return contentObjects != null;
			case VSandPackage.MATERIAL_SELECTOR_PANEL__POSITION:
				return POSITION_EDEFAULT == null ? position != null : !POSITION_EDEFAULT.equals(position);
			case VSandPackage.MATERIAL_SELECTOR_PANEL__VERTICAL_RELATIVE:
				return verticalRelative != VERTICAL_RELATIVE_EDEFAULT;
			case VSandPackage.MATERIAL_SELECTOR_PANEL__HORIZONTAL_RELATIVE:
				return horizontalRelative != HORIZONTAL_RELATIVE_EDEFAULT;
			case VSandPackage.MATERIAL_SELECTOR_PANEL__LINE_HEIGHT:
				return lineHeight != LINE_HEIGHT_EDEFAULT;
			case VSandPackage.MATERIAL_SELECTOR_PANEL__PRIMARY_R:
				return primaryR != PRIMARY_R_EDEFAULT;
			case VSandPackage.MATERIAL_SELECTOR_PANEL__PRIMARY_G:
				return primaryG != PRIMARY_G_EDEFAULT;
			case VSandPackage.MATERIAL_SELECTOR_PANEL__PRIMARY_B:
				return primaryB != PRIMARY_B_EDEFAULT;
			case VSandPackage.MATERIAL_SELECTOR_PANEL__SECONDARY_R:
				return secondaryR != SECONDARY_R_EDEFAULT;
			case VSandPackage.MATERIAL_SELECTOR_PANEL__SECONDARY_G:
				return secondaryG != SECONDARY_G_EDEFAULT;
			case VSandPackage.MATERIAL_SELECTOR_PANEL__SECONDARY_B:
				return secondaryB != SECONDARY_B_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass)
	{
		if (baseClass == IInferenceObject.class)
		{
			switch (derivedFeatureID)
			{
				default: return -1;
			}
		}
		if (baseClass == LObject.class)
		{
			switch (derivedFeatureID)
			{
				case VSandPackage.MATERIAL_SELECTOR_PANEL__CONTENT_OBJECTS: return RootPackage.LOBJECT__CONTENT_OBJECTS;
				default: return -1;
			}
		}
		if (baseClass == IUIElement.class)
		{
			switch (derivedFeatureID)
			{
				default: return -1;
			}
		}
		if (baseClass == IPositionElement.class)
		{
			switch (derivedFeatureID)
			{
				case VSandPackage.MATERIAL_SELECTOR_PANEL__POSITION: return PresentationPackage.IPOSITION_ELEMENT__POSITION;
				case VSandPackage.MATERIAL_SELECTOR_PANEL__VERTICAL_RELATIVE: return PresentationPackage.IPOSITION_ELEMENT__VERTICAL_RELATIVE;
				case VSandPackage.MATERIAL_SELECTOR_PANEL__HORIZONTAL_RELATIVE: return PresentationPackage.IPOSITION_ELEMENT__HORIZONTAL_RELATIVE;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass)
	{
		if (baseClass == IInferenceObject.class)
		{
			switch (baseFeatureID)
			{
				default: return -1;
			}
		}
		if (baseClass == LObject.class)
		{
			switch (baseFeatureID)
			{
				case RootPackage.LOBJECT__CONTENT_OBJECTS: return VSandPackage.MATERIAL_SELECTOR_PANEL__CONTENT_OBJECTS;
				default: return -1;
			}
		}
		if (baseClass == IUIElement.class)
		{
			switch (baseFeatureID)
			{
				default: return -1;
			}
		}
		if (baseClass == IPositionElement.class)
		{
			switch (baseFeatureID)
			{
				case PresentationPackage.IPOSITION_ELEMENT__POSITION: return VSandPackage.MATERIAL_SELECTOR_PANEL__POSITION;
				case PresentationPackage.IPOSITION_ELEMENT__VERTICAL_RELATIVE: return VSandPackage.MATERIAL_SELECTOR_PANEL__VERTICAL_RELATIVE;
				case PresentationPackage.IPOSITION_ELEMENT__HORIZONTAL_RELATIVE: return VSandPackage.MATERIAL_SELECTOR_PANEL__HORIZONTAL_RELATIVE;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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
		result.append(" (name: ");
		result.append(name);
		result.append(", contentObjects: ");
		result.append(contentObjects);
		result.append(", position: ");
		result.append(position);
		result.append(", verticalRelative: ");
		result.append(verticalRelative);
		result.append(", horizontalRelative: ");
		result.append(horizontalRelative);
		result.append(", lineHeight: ");
		result.append(lineHeight);
		result.append(", primaryR: ");
		result.append(primaryR);
		result.append(", primaryG: ");
		result.append(primaryG);
		result.append(", primaryB: ");
		result.append(primaryB);
		result.append(", secondaryR: ");
		result.append(secondaryR);
		result.append(", secondaryG: ");
		result.append(secondaryG);
		result.append(", secondaryB: ");
		result.append(secondaryB);
		result.append(')');
		return result.toString();
	}

} //MaterialSelectorPanelImpl
