/**
 */
package pcdef.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import pcdef.PcdefPackage;
import pcdef.RepresentationExtensionFeature;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Representation Extension Feature</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link pcdef.impl.RepresentationExtensionFeatureImpl#getName <em>Name</em>}</li>
 *   <li>{@link pcdef.impl.RepresentationExtensionFeatureImpl#getViewpointUri <em>Viewpoint Uri</em>}</li>
 *   <li>{@link pcdef.impl.RepresentationExtensionFeatureImpl#getRepresentationName <em>Representation Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class RepresentationExtensionFeatureImpl extends MinimalEObjectImpl.Container
		implements RepresentationExtensionFeature {
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
	 * The default value of the '{@link #getViewpointUri() <em>Viewpoint Uri</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getViewpointUri()
	 * @generated
	 * @ordered
	 */
	protected static final String VIEWPOINT_URI_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getViewpointUri() <em>Viewpoint Uri</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getViewpointUri()
	 * @generated
	 * @ordered
	 */
	protected String viewpointUri = VIEWPOINT_URI_EDEFAULT;

	/**
	 * The default value of the '{@link #getRepresentationName() <em>Representation Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRepresentationName()
	 * @generated
	 * @ordered
	 */
	protected static final String REPRESENTATION_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRepresentationName() <em>Representation Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRepresentationName()
	 * @generated
	 * @ordered
	 */
	protected String representationName = REPRESENTATION_NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RepresentationExtensionFeatureImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PcdefPackage.Literals.REPRESENTATION_EXTENSION_FEATURE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PcdefPackage.REPRESENTATION_EXTENSION_FEATURE__NAME,
					oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getViewpointUri() {
		return viewpointUri;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setViewpointUri(String newViewpointUri) {
		String oldViewpointUri = viewpointUri;
		viewpointUri = newViewpointUri;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					PcdefPackage.REPRESENTATION_EXTENSION_FEATURE__VIEWPOINT_URI, oldViewpointUri, viewpointUri));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getRepresentationName() {
		return representationName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRepresentationName(String newRepresentationName) {
		String oldRepresentationName = representationName;
		representationName = newRepresentationName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					PcdefPackage.REPRESENTATION_EXTENSION_FEATURE__REPRESENTATION_NAME, oldRepresentationName,
					representationName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case PcdefPackage.REPRESENTATION_EXTENSION_FEATURE__NAME:
			return getName();
		case PcdefPackage.REPRESENTATION_EXTENSION_FEATURE__VIEWPOINT_URI:
			return getViewpointUri();
		case PcdefPackage.REPRESENTATION_EXTENSION_FEATURE__REPRESENTATION_NAME:
			return getRepresentationName();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case PcdefPackage.REPRESENTATION_EXTENSION_FEATURE__NAME:
			setName((String) newValue);
			return;
		case PcdefPackage.REPRESENTATION_EXTENSION_FEATURE__VIEWPOINT_URI:
			setViewpointUri((String) newValue);
			return;
		case PcdefPackage.REPRESENTATION_EXTENSION_FEATURE__REPRESENTATION_NAME:
			setRepresentationName((String) newValue);
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
	public void eUnset(int featureID) {
		switch (featureID) {
		case PcdefPackage.REPRESENTATION_EXTENSION_FEATURE__NAME:
			setName(NAME_EDEFAULT);
			return;
		case PcdefPackage.REPRESENTATION_EXTENSION_FEATURE__VIEWPOINT_URI:
			setViewpointUri(VIEWPOINT_URI_EDEFAULT);
			return;
		case PcdefPackage.REPRESENTATION_EXTENSION_FEATURE__REPRESENTATION_NAME:
			setRepresentationName(REPRESENTATION_NAME_EDEFAULT);
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
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case PcdefPackage.REPRESENTATION_EXTENSION_FEATURE__NAME:
			return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
		case PcdefPackage.REPRESENTATION_EXTENSION_FEATURE__VIEWPOINT_URI:
			return VIEWPOINT_URI_EDEFAULT == null ? viewpointUri != null : !VIEWPOINT_URI_EDEFAULT.equals(viewpointUri);
		case PcdefPackage.REPRESENTATION_EXTENSION_FEATURE__REPRESENTATION_NAME:
			return REPRESENTATION_NAME_EDEFAULT == null ? representationName != null
					: !REPRESENTATION_NAME_EDEFAULT.equals(representationName);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(", viewpointUri: ");
		result.append(viewpointUri);
		result.append(", representationName: ");
		result.append(representationName);
		result.append(')');
		return result.toString();
	}

} //RepresentationExtensionFeatureImpl
