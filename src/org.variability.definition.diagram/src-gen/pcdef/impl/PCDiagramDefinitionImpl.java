/**
 */
package pcdef.impl;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import org.eclipse.sirius.diagram.description.DiagramElementMapping;

import pcdef.PCDiagramDefinition;
import pcdef.PcdefPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>PC Diagram Definition</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link pcdef.impl.PCDiagramDefinitionImpl#getGraphicalElements <em>Graphical Elements</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PCDiagramDefinitionImpl extends RepresentationExtensionFeatureImpl implements PCDiagramDefinition {
	/**
	 * The cached value of the '{@link #getGraphicalElements() <em>Graphical Elements</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGraphicalElements()
	 * @generated
	 * @ordered
	 */
	protected EList<DiagramElementMapping> graphicalElements;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PCDiagramDefinitionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PcdefPackage.Literals.PC_DIAGRAM_DEFINITION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<DiagramElementMapping> getGraphicalElements() {
		if (graphicalElements == null) {
			graphicalElements = new EObjectResolvingEList<DiagramElementMapping>(DiagramElementMapping.class, this,
					PcdefPackage.PC_DIAGRAM_DEFINITION__GRAPHICAL_ELEMENTS);
		}
		return graphicalElements;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case PcdefPackage.PC_DIAGRAM_DEFINITION__GRAPHICAL_ELEMENTS:
			return getGraphicalElements();
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
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case PcdefPackage.PC_DIAGRAM_DEFINITION__GRAPHICAL_ELEMENTS:
			getGraphicalElements().clear();
			getGraphicalElements().addAll((Collection<? extends DiagramElementMapping>) newValue);
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
		case PcdefPackage.PC_DIAGRAM_DEFINITION__GRAPHICAL_ELEMENTS:
			getGraphicalElements().clear();
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
		case PcdefPackage.PC_DIAGRAM_DEFINITION__GRAPHICAL_ELEMENTS:
			return graphicalElements != null && !graphicalElements.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //PCDiagramDefinitionImpl
