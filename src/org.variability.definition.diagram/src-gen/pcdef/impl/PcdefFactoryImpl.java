/**
 */
package pcdef.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import pcdef.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class PcdefFactoryImpl extends EFactoryImpl implements PcdefFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static PcdefFactory init() {
		try {
			PcdefFactory thePcdefFactory = (PcdefFactory) EPackage.Registry.INSTANCE.getEFactory(PcdefPackage.eNS_URI);
			if (thePcdefFactory != null) {
				return thePcdefFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new PcdefFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PcdefFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
		case PcdefPackage.PC_DIAGRAM_DEFINITION:
			return createPCDiagramDefinition();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public PCDiagramDefinition createPCDiagramDefinition() {
		PCDiagramDefinitionImpl pcDiagramDefinition = new PCDiagramDefinitionImpl();
		return pcDiagramDefinition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public PcdefPackage getPcdefPackage() {
		return (PcdefPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static PcdefPackage getPackage() {
		return PcdefPackage.eINSTANCE;
	}

} //PcdefFactoryImpl
