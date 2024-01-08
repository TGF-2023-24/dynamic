package org.variability.definition.diagram.design.generate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.sirius.diagram.description.AdditionalLayer;
import org.eclipse.sirius.diagram.description.DiagramExtensionDescription;
import org.eclipse.sirius.diagram.description.EdgeMapping;
import org.eclipse.sirius.viewpoint.description.JavaExtension;
import org.eclipse.sirius.viewpoint.description.RepresentationExtensionDescription;
import org.eclipse.sirius.viewpoint.description.Viewpoint;
import org.variability.definition.diagram.design.vp.ViewpointSpecificationProjectExtended;

import pcdef.PCDiagramDefinition;

public class RefineInitVaribilityDescription {
	
	public final static String LAYER_NAME = "PresenceCondition";
	public final static String EDGE_MAPPING_PC_ID = "PCtoDiagramElement";
	private static final String PRESENCE_CODITION_SERVICES_QUALIFIED = "PresenceConditionServices";
	
	private String projectName;
	private PCDiagramDefinition diagramDefinition;
	private Viewpoint vpBaseVariability;
	private Viewpoint vpToExtend;

	public RefineInitVaribilityDescription(String projectName, PCDiagramDefinition diagramDefinition, Viewpoint vpBaseVariability, Viewpoint vpToExtend) {
		this.projectName = projectName;
		this.diagramDefinition = diagramDefinition;
		this.vpBaseVariability = vpBaseVariability;
		this.vpToExtend = vpToExtend;		
	}

	public void doExtendInitDescription() {
		// update the odesign
		this.vpBaseVariability.getOwnedJavaExtensions().add(createJavaPresenceConditionExtension());
		updateAttributesViewpoint();
		updateDiagramExtension();
		updateTargetDiagramElements();
		// Save Viewpoint
		saveViewpoint();
	}
	
	private void saveViewpoint() {
		// Save the contents of the resource to the file system.
        final Map<String, String> options = new HashMap<String, String>();
        options.put(XMLResource.OPTION_ENCODING, ViewpointSpecificationProjectExtended.ENCODING_DEFAULT);
        try {
			this.vpBaseVariability.eResource().save(options);
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}

	private JavaExtension createJavaPresenceConditionExtension() {		
		JavaExtension presenceCondition = org.eclipse.sirius.viewpoint.description.impl.DescriptionFactoryImpl.eINSTANCE.createJavaExtension();
		presenceCondition.setQualifiedClassName(this.projectName + "." + PRESENCE_CODITION_SERVICES_QUALIFIED);
		return presenceCondition;		
	}
	
	private void updateAttributesViewpoint() {
		vpBaseVariability.setName(this.vpBaseVariability.getName() + " " + this.vpToExtend.getName());
		vpBaseVariability.setModelFileExtension(this.vpToExtend.getModelFileExtension());
	}
	
	//Update Diagram Extension Attributes
	private void updateDiagramExtension() {
		this.vpBaseVariability.getOwnedRepresentationExtensions().get(0).setName(this.diagramDefinition.getName());
		this.vpBaseVariability.getOwnedRepresentationExtensions().get(0).setViewpointURI(this.diagramDefinition.getViewpointUri());
		this.vpBaseVariability.getOwnedRepresentationExtensions().get(0).setRepresentationName(this.diagramDefinition.getRepresentationName());		
	}	
	
	private void updateTargetDiagramElements() {
		//get edge PC to TargetElement
		Iterator<RepresentationExtensionDescription> ownedRepresentations = this.vpBaseVariability.getOwnedRepresentationExtensions().iterator();
		while (ownedRepresentations.hasNext()) {
			RepresentationExtensionDescription representationExtensionDescription = (RepresentationExtensionDescription) ownedRepresentations
					.next();
			if (representationExtensionDescription.getName().equals(this.diagramDefinition.getName()) & 
					representationExtensionDescription instanceof DiagramExtensionDescription) {
				Iterator<AdditionalLayer> getLayers = ((DiagramExtensionDescription) representationExtensionDescription).getLayers().iterator();
				while (getLayers.hasNext()) {
					AdditionalLayer additionalLayer = (AdditionalLayer) getLayers.next();
					if (additionalLayer.getName().equals(LAYER_NAME)) {
						Iterator<EdgeMapping> itEdgeMappings = additionalLayer.getEdgeMappings().iterator();
						while (itEdgeMappings.hasNext()) {
							EdgeMapping edgeMapping = (EdgeMapping) itEdgeMappings.next();
							if (edgeMapping.getName().equals(EDGE_MAPPING_PC_ID)) {
								edgeMapping.getTargetMapping().addAll(diagramDefinition.getGraphicalElements());								
							}
						}
					}
				}
			}			
		}
	}

}
