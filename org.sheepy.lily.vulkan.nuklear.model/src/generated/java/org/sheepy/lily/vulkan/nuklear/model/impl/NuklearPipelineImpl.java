/**
 */
package org.sheepy.lily.vulkan.nuklear.model.impl;

import java.lang.reflect.InvocationTargetException;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EContentsEList;

import org.sheepy.lily.core.api.util.LTreeIterator;

import org.sheepy.lily.core.model.inference.IInferenceObject;

import org.sheepy.lily.core.model.root.LObject;

import org.sheepy.lily.core.model.root.RootPackage.Literals;

import org.sheepy.lily.core.model.types.LNamedElement;
import org.sheepy.lily.core.model.types.TypesPackage;

import org.sheepy.lily.vulkan.model.enumeration.ECommandStage;

import org.sheepy.lily.vulkan.model.process.graphic.IGUIPipeline;
import org.sheepy.lily.vulkan.model.process.graphic.IGraphicsPipeline;

import org.sheepy.lily.vulkan.model.resource.Font;

import org.sheepy.lily.vulkan.nuklear.model.NuklearConstants;
import org.sheepy.lily.vulkan.nuklear.model.NuklearIndexBuffer;
import org.sheepy.lily.vulkan.nuklear.model.NuklearPackage;
import org.sheepy.lily.vulkan.nuklear.model.NuklearPipeline;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Pipeline</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.sheepy.lily.vulkan.nuklear.model.impl.NuklearPipelineImpl#getContentObjects <em>Content Objects</em>}</li>
 *   <li>{@link org.sheepy.lily.vulkan.nuklear.model.impl.NuklearPipelineImpl#isEnabled <em>Enabled</em>}</li>
 *   <li>{@link org.sheepy.lily.vulkan.nuklear.model.impl.NuklearPipelineImpl#getStage <em>Stage</em>}</li>
 *   <li>{@link org.sheepy.lily.vulkan.nuklear.model.impl.NuklearPipelineImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.sheepy.lily.vulkan.nuklear.model.impl.NuklearPipelineImpl#getIndexBuffer <em>Index Buffer</em>}</li>
 *   <li>{@link org.sheepy.lily.vulkan.nuklear.model.impl.NuklearPipelineImpl#getFont <em>Font</em>}</li>
 *   <li>{@link org.sheepy.lily.vulkan.nuklear.model.impl.NuklearPipelineImpl#getPushConstant <em>Push Constant</em>}</li>
 *   <li>{@link org.sheepy.lily.vulkan.nuklear.model.impl.NuklearPipelineImpl#getSubpass <em>Subpass</em>}</li>
 * </ul>
 *
 * @generated
 */
public class NuklearPipelineImpl extends MinimalEObjectImpl.Container implements NuklearPipeline
{
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
	 * The default value of the '{@link #isEnabled() <em>Enabled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isEnabled()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ENABLED_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isEnabled() <em>Enabled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isEnabled()
	 * @generated
	 * @ordered
	 */
	protected boolean enabled = ENABLED_EDEFAULT;

	/**
	 * The default value of the '{@link #getStage() <em>Stage</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStage()
	 * @generated
	 * @ordered
	 */
	protected static final ECommandStage STAGE_EDEFAULT = ECommandStage.RENDER;

	/**
	 * The cached value of the '{@link #getStage() <em>Stage</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStage()
	 * @generated
	 * @ordered
	 */
	protected ECommandStage stage = STAGE_EDEFAULT;

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
	 * The cached value of the '{@link #getIndexBuffer() <em>Index Buffer</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIndexBuffer()
	 * @generated
	 * @ordered
	 */
	protected NuklearIndexBuffer indexBuffer;

	/**
	 * The cached value of the '{@link #getFont() <em>Font</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFont()
	 * @generated
	 * @ordered
	 */
	protected Font font;

	/**
	 * The cached value of the '{@link #getPushConstant() <em>Push Constant</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPushConstant()
	 * @generated
	 * @ordered
	 */
	protected NuklearConstants pushConstant;

	/**
	 * The default value of the '{@link #getSubpass() <em>Subpass</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubpass()
	 * @generated
	 * @ordered
	 */
	protected static final int SUBPASS_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getSubpass() <em>Subpass</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubpass()
	 * @generated
	 * @ordered
	 */
	protected int subpass = SUBPASS_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NuklearPipelineImpl()
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
		return NuklearPackage.Literals.NUKLEAR_PIPELINE;
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
			eNotify(new ENotificationImpl(this, Notification.SET, NuklearPackage.NUKLEAR_PIPELINE__CONTENT_OBJECTS, oldContentObjects, contentObjects));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isEnabled()
	{
		return enabled;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setEnabled(boolean newEnabled)
	{
		boolean oldEnabled = enabled;
		enabled = newEnabled;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, NuklearPackage.NUKLEAR_PIPELINE__ENABLED, oldEnabled, enabled));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ECommandStage getStage()
	{
		return stage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setStage(ECommandStage newStage)
	{
		ECommandStage oldStage = stage;
		stage = newStage == null ? STAGE_EDEFAULT : newStage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, NuklearPackage.NUKLEAR_PIPELINE__STAGE, oldStage, stage));
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
			eNotify(new ENotificationImpl(this, Notification.SET, NuklearPackage.NUKLEAR_PIPELINE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NuklearIndexBuffer getIndexBuffer()
	{
		return indexBuffer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetIndexBuffer(NuklearIndexBuffer newIndexBuffer, NotificationChain msgs)
	{
		NuklearIndexBuffer oldIndexBuffer = indexBuffer;
		indexBuffer = newIndexBuffer;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, NuklearPackage.NUKLEAR_PIPELINE__INDEX_BUFFER, oldIndexBuffer, newIndexBuffer);
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
	public void setIndexBuffer(NuklearIndexBuffer newIndexBuffer)
	{
		if (newIndexBuffer != indexBuffer)
		{
			NotificationChain msgs = null;
			if (indexBuffer != null)
				msgs = ((InternalEObject)indexBuffer).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - NuklearPackage.NUKLEAR_PIPELINE__INDEX_BUFFER, null, msgs);
			if (newIndexBuffer != null)
				msgs = ((InternalEObject)newIndexBuffer).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - NuklearPackage.NUKLEAR_PIPELINE__INDEX_BUFFER, null, msgs);
			msgs = basicSetIndexBuffer(newIndexBuffer, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, NuklearPackage.NUKLEAR_PIPELINE__INDEX_BUFFER, newIndexBuffer, newIndexBuffer));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Font getFont()
	{
		return font;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFont(Font newFont, NotificationChain msgs)
	{
		Font oldFont = font;
		font = newFont;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, NuklearPackage.NUKLEAR_PIPELINE__FONT, oldFont, newFont);
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
	public void setFont(Font newFont)
	{
		if (newFont != font)
		{
			NotificationChain msgs = null;
			if (font != null)
				msgs = ((InternalEObject)font).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - NuklearPackage.NUKLEAR_PIPELINE__FONT, null, msgs);
			if (newFont != null)
				msgs = ((InternalEObject)newFont).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - NuklearPackage.NUKLEAR_PIPELINE__FONT, null, msgs);
			msgs = basicSetFont(newFont, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, NuklearPackage.NUKLEAR_PIPELINE__FONT, newFont, newFont));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NuklearConstants getPushConstant()
	{
		return pushConstant;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPushConstant(NuklearConstants newPushConstant, NotificationChain msgs)
	{
		NuklearConstants oldPushConstant = pushConstant;
		pushConstant = newPushConstant;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, NuklearPackage.NUKLEAR_PIPELINE__PUSH_CONSTANT, oldPushConstant, newPushConstant);
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
	public void setPushConstant(NuklearConstants newPushConstant)
	{
		if (newPushConstant != pushConstant)
		{
			NotificationChain msgs = null;
			if (pushConstant != null)
				msgs = ((InternalEObject)pushConstant).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - NuklearPackage.NUKLEAR_PIPELINE__PUSH_CONSTANT, null, msgs);
			if (newPushConstant != null)
				msgs = ((InternalEObject)newPushConstant).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - NuklearPackage.NUKLEAR_PIPELINE__PUSH_CONSTANT, null, msgs);
			msgs = basicSetPushConstant(newPushConstant, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, NuklearPackage.NUKLEAR_PIPELINE__PUSH_CONSTANT, newPushConstant, newPushConstant));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getSubpass()
	{
		return subpass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSubpass(int newSubpass)
	{
		int oldSubpass = subpass;
		subpass = newSubpass;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, NuklearPackage.NUKLEAR_PIPELINE__SUBPASS, oldSubpass, subpass));
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
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
	{
		switch (featureID)
		{
			case NuklearPackage.NUKLEAR_PIPELINE__INDEX_BUFFER:
				return basicSetIndexBuffer(null, msgs);
			case NuklearPackage.NUKLEAR_PIPELINE__FONT:
				return basicSetFont(null, msgs);
			case NuklearPackage.NUKLEAR_PIPELINE__PUSH_CONSTANT:
				return basicSetPushConstant(null, msgs);
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
			case NuklearPackage.NUKLEAR_PIPELINE__CONTENT_OBJECTS:
				return getContentObjects();
			case NuklearPackage.NUKLEAR_PIPELINE__ENABLED:
				return isEnabled();
			case NuklearPackage.NUKLEAR_PIPELINE__STAGE:
				return getStage();
			case NuklearPackage.NUKLEAR_PIPELINE__NAME:
				return getName();
			case NuklearPackage.NUKLEAR_PIPELINE__INDEX_BUFFER:
				return getIndexBuffer();
			case NuklearPackage.NUKLEAR_PIPELINE__FONT:
				return getFont();
			case NuklearPackage.NUKLEAR_PIPELINE__PUSH_CONSTANT:
				return getPushConstant();
			case NuklearPackage.NUKLEAR_PIPELINE__SUBPASS:
				return getSubpass();
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
			case NuklearPackage.NUKLEAR_PIPELINE__CONTENT_OBJECTS:
				setContentObjects((EList<LObject>)newValue);
				return;
			case NuklearPackage.NUKLEAR_PIPELINE__ENABLED:
				setEnabled((Boolean)newValue);
				return;
			case NuklearPackage.NUKLEAR_PIPELINE__STAGE:
				setStage((ECommandStage)newValue);
				return;
			case NuklearPackage.NUKLEAR_PIPELINE__NAME:
				setName((String)newValue);
				return;
			case NuklearPackage.NUKLEAR_PIPELINE__INDEX_BUFFER:
				setIndexBuffer((NuklearIndexBuffer)newValue);
				return;
			case NuklearPackage.NUKLEAR_PIPELINE__FONT:
				setFont((Font)newValue);
				return;
			case NuklearPackage.NUKLEAR_PIPELINE__PUSH_CONSTANT:
				setPushConstant((NuklearConstants)newValue);
				return;
			case NuklearPackage.NUKLEAR_PIPELINE__SUBPASS:
				setSubpass((Integer)newValue);
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
			case NuklearPackage.NUKLEAR_PIPELINE__CONTENT_OBJECTS:
				setContentObjects((EList<LObject>)null);
				return;
			case NuklearPackage.NUKLEAR_PIPELINE__ENABLED:
				setEnabled(ENABLED_EDEFAULT);
				return;
			case NuklearPackage.NUKLEAR_PIPELINE__STAGE:
				setStage(STAGE_EDEFAULT);
				return;
			case NuklearPackage.NUKLEAR_PIPELINE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case NuklearPackage.NUKLEAR_PIPELINE__INDEX_BUFFER:
				setIndexBuffer((NuklearIndexBuffer)null);
				return;
			case NuklearPackage.NUKLEAR_PIPELINE__FONT:
				setFont((Font)null);
				return;
			case NuklearPackage.NUKLEAR_PIPELINE__PUSH_CONSTANT:
				setPushConstant((NuklearConstants)null);
				return;
			case NuklearPackage.NUKLEAR_PIPELINE__SUBPASS:
				setSubpass(SUBPASS_EDEFAULT);
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
			case NuklearPackage.NUKLEAR_PIPELINE__CONTENT_OBJECTS:
				return contentObjects != null;
			case NuklearPackage.NUKLEAR_PIPELINE__ENABLED:
				return enabled != ENABLED_EDEFAULT;
			case NuklearPackage.NUKLEAR_PIPELINE__STAGE:
				return stage != STAGE_EDEFAULT;
			case NuklearPackage.NUKLEAR_PIPELINE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case NuklearPackage.NUKLEAR_PIPELINE__INDEX_BUFFER:
				return indexBuffer != null;
			case NuklearPackage.NUKLEAR_PIPELINE__FONT:
				return font != null;
			case NuklearPackage.NUKLEAR_PIPELINE__PUSH_CONSTANT:
				return pushConstant != null;
			case NuklearPackage.NUKLEAR_PIPELINE__SUBPASS:
				return subpass != SUBPASS_EDEFAULT;
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
		if (baseClass == LNamedElement.class)
		{
			switch (derivedFeatureID)
			{
				case NuklearPackage.NUKLEAR_PIPELINE__NAME: return TypesPackage.LNAMED_ELEMENT__NAME;
				default: return -1;
			}
		}
		if (baseClass == IGraphicsPipeline.class)
		{
			switch (derivedFeatureID)
			{
				default: return -1;
			}
		}
		if (baseClass == IGUIPipeline.class)
		{
			switch (derivedFeatureID)
			{
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
		if (baseClass == LNamedElement.class)
		{
			switch (baseFeatureID)
			{
				case TypesPackage.LNAMED_ELEMENT__NAME: return NuklearPackage.NUKLEAR_PIPELINE__NAME;
				default: return -1;
			}
		}
		if (baseClass == IGraphicsPipeline.class)
		{
			switch (baseFeatureID)
			{
				default: return -1;
			}
		}
		if (baseClass == IGUIPipeline.class)
		{
			switch (baseFeatureID)
			{
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
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException
	{
		switch (operationID)
		{
			case NuklearPackage.NUKLEAR_PIPELINE___CREATE_CONTAINMENT_ELIST__ECLASS:
				return createContainmentEList((EClass)arguments.get(0));
			case NuklearPackage.NUKLEAR_PIPELINE___LCONTENTS:
				return lContents();
			case NuklearPackage.NUKLEAR_PIPELINE___LPARENT:
				return lParent();
			case NuklearPackage.NUKLEAR_PIPELINE___LALL_CONTENTS:
				return lAllContents();
			case NuklearPackage.NUKLEAR_PIPELINE___LINFERENCE_OBJECT:
				return lInferenceObject();
		}
		return super.eInvoke(operationID, arguments);
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
		result.append(" (contentObjects: ");
		result.append(contentObjects);
		result.append(", enabled: ");
		result.append(enabled);
		result.append(", stage: ");
		result.append(stage);
		result.append(", name: ");
		result.append(name);
		result.append(", subpass: ");
		result.append(subpass);
		result.append(')');
		return result.toString();
	}

} //NuklearPipelineImpl
