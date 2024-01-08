/**
 */
package pcdef.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.sirius.diagram.DiagramPackage;

import org.eclipse.sirius.diagram.description.DescriptionPackage;

import org.eclipse.sirius.viewpoint.ViewpointPackage;

import pcdef.PCDiagramDefinition;
import pcdef.PcdefFactory;
import pcdef.PcdefPackage;
import pcdef.RepresentationExtensionFeature;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class PcdefPackageImpl extends EPackageImpl implements PcdefPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass representationExtensionFeatureEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass pcDiagramDefinitionEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see pcdef.PcdefPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private PcdefPackageImpl() {
		super(eNS_URI, PcdefFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>This method is used to initialize {@link PcdefPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static PcdefPackage init() {
		if (isInited)
			return (PcdefPackage) EPackage.Registry.INSTANCE.getEPackage(PcdefPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredPcdefPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		PcdefPackageImpl thePcdefPackage = registeredPcdefPackage instanceof PcdefPackageImpl
				? (PcdefPackageImpl) registeredPcdefPackage
				: new PcdefPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		DiagramPackage.eINSTANCE.eClass();
		EcorePackage.eINSTANCE.eClass();
		ViewpointPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		thePcdefPackage.createPackageContents();

		// Initialize created meta-data
		thePcdefPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		thePcdefPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(PcdefPackage.eNS_URI, thePcdefPackage);
		return thePcdefPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getRepresentationExtensionFeature() {
		return representationExtensionFeatureEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getRepresentationExtensionFeature_Name() {
		return (EAttribute) representationExtensionFeatureEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getRepresentationExtensionFeature_ViewpointUri() {
		return (EAttribute) representationExtensionFeatureEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getRepresentationExtensionFeature_RepresentationName() {
		return (EAttribute) representationExtensionFeatureEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getPCDiagramDefinition() {
		return pcDiagramDefinitionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getPCDiagramDefinition_GraphicalElements() {
		return (EReference) pcDiagramDefinitionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public PcdefFactory getPcdefFactory() {
		return (PcdefFactory) getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated)
			return;
		isCreated = true;

		// Create classes and their features
		representationExtensionFeatureEClass = createEClass(REPRESENTATION_EXTENSION_FEATURE);
		createEAttribute(representationExtensionFeatureEClass, REPRESENTATION_EXTENSION_FEATURE__NAME);
		createEAttribute(representationExtensionFeatureEClass, REPRESENTATION_EXTENSION_FEATURE__VIEWPOINT_URI);
		createEAttribute(representationExtensionFeatureEClass, REPRESENTATION_EXTENSION_FEATURE__REPRESENTATION_NAME);

		pcDiagramDefinitionEClass = createEClass(PC_DIAGRAM_DEFINITION);
		createEReference(pcDiagramDefinitionEClass, PC_DIAGRAM_DEFINITION__GRAPHICAL_ELEMENTS);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized)
			return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		DescriptionPackage theDescriptionPackage = (DescriptionPackage) EPackage.Registry.INSTANCE
				.getEPackage(DescriptionPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		pcDiagramDefinitionEClass.getESuperTypes().add(this.getRepresentationExtensionFeature());

		// Initialize classes, features, and operations; add parameters
		initEClass(representationExtensionFeatureEClass, RepresentationExtensionFeature.class,
				"RepresentationExtensionFeature", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRepresentationExtensionFeature_Name(), ecorePackage.getEString(), "name", null, 1, 1,
				RepresentationExtensionFeature.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRepresentationExtensionFeature_ViewpointUri(), ecorePackage.getEString(), "viewpointUri",
				null, 1, 1, RepresentationExtensionFeature.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRepresentationExtensionFeature_RepresentationName(), ecorePackage.getEString(),
				"representationName", null, 1, 1, RepresentationExtensionFeature.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(pcDiagramDefinitionEClass, PCDiagramDefinition.class, "PCDiagramDefinition", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPCDiagramDefinition_GraphicalElements(), theDescriptionPackage.getDiagramElementMapping(),
				null, "graphicalElements", null, 1, -1, PCDiagramDefinition.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //PcdefPackageImpl
