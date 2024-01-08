/**
 */
package pcdef;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Representation Extension Feature</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link pcdef.RepresentationExtensionFeature#getName <em>Name</em>}</li>
 *   <li>{@link pcdef.RepresentationExtensionFeature#getViewpointUri <em>Viewpoint Uri</em>}</li>
 *   <li>{@link pcdef.RepresentationExtensionFeature#getRepresentationName <em>Representation Name</em>}</li>
 * </ul>
 *
 * @see pcdef.PcdefPackage#getRepresentationExtensionFeature()
 * @model abstract="true"
 * @generated
 */
public interface RepresentationExtensionFeature extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see pcdef.PcdefPackage#getRepresentationExtensionFeature_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link pcdef.RepresentationExtensionFeature#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Viewpoint Uri</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Viewpoint Uri</em>' attribute.
	 * @see #setViewpointUri(String)
	 * @see pcdef.PcdefPackage#getRepresentationExtensionFeature_ViewpointUri()
	 * @model required="true"
	 * @generated
	 */
	String getViewpointUri();

	/**
	 * Sets the value of the '{@link pcdef.RepresentationExtensionFeature#getViewpointUri <em>Viewpoint Uri</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Viewpoint Uri</em>' attribute.
	 * @see #getViewpointUri()
	 * @generated
	 */
	void setViewpointUri(String value);

	/**
	 * Returns the value of the '<em><b>Representation Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Representation Name</em>' attribute.
	 * @see #setRepresentationName(String)
	 * @see pcdef.PcdefPackage#getRepresentationExtensionFeature_RepresentationName()
	 * @model required="true"
	 * @generated
	 */
	String getRepresentationName();

	/**
	 * Sets the value of the '{@link pcdef.RepresentationExtensionFeature#getRepresentationName <em>Representation Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Representation Name</em>' attribute.
	 * @see #getRepresentationName()
	 * @generated
	 */
	void setRepresentationName(String value);

} // RepresentationExtensionFeature
