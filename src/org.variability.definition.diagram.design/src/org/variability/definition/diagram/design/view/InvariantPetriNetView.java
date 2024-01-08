package org.variability.definition.diagram.design.view;

//import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
//import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.dialogs.MessageDialog;
//import org.eclipse.sirius.business.api.dialect.DialectManager;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.diagram.description.Layer;
//import org.eclipse.sirius.diagram.tools.api.command.ChangeLayerActivationCommand;
import org.eclipse.sirius.diagram.ui.tools.api.editor.DDiagramEditor;
import org.eclipse.sirius.viewpoint.DRepresentation;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.variability.definition.diagram.design.generate.utils.InvariantUtils;
import org.variability.definition.diagram.design.generate.utils.VariabilityUtils;

//import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

public class InvariantPetriNetView extends ViewPart {

	private static InvariantPetriNetView singleInvariantView = null;
	private static String PETRINET_INVARIANT_LAYER_ID = "Petri-net-Invariant-Layer";
	private DDiagramEditor siriusEditor;
	private Table table; 
	private Button tInv;
	private Button pInv;
	private Map<String, List<String>> listOfInvariants;
	private boolean isRunningViewAnalysis = false;

	private int invariantIndex;

	//Initialization of the view
	public InvariantPetriNetView() {
		singleInvariantView = this;
		refresh();
	}

	public static InvariantPetriNetView getInstance() {
		return singleInvariantView;
	}	
	
	public boolean isRunningViewAnalysis() {
		return this.isRunningViewAnalysis;
	}

	private void refresh() {
		this.siriusEditor = VariabilityUtils.getActiveSiriusDiagram();
		this.listOfInvariants = new LinkedHashMap<String, List<String>>();
		this.invariantIndex = -1;
	}

	public Map<String, List<String>> getListOfInvariants() {
		return listOfInvariants;
	}

	public void setListOfInvariants(Map<String, List<String>> listOfInvariants) {
		this.listOfInvariants = listOfInvariants;
	}

	public int getInvariantIndex() {
		return invariantIndex;
	}

	public List<String> getCurrentInvariant(){
		int i=0;
		for(Map.Entry<String, List<String>> inv: getListOfInvariants().entrySet()) {
			if (i == this.invariantIndex) {
				List<String> values = inv.getValue();				
				return values;
			}
			i++;
		}
		return null;
	}

	@Override
	public void createPartControl(Composite parent) {
		Composite parentComposite = new Composite(parent, SWT.NONE);
		GridLayout parentLayout = new GridLayout(4, false);
		parentComposite.setLayout(parentLayout);
		GridData parentData = new GridData(GridData.FILL_BOTH);
		parentData.horizontalSpan = 4;
		parentComposite.setLayoutData(parentData);

		createInvariantOptions(parentComposite);
		createTable(parentComposite);
	}	

	private void createTable(Composite parent) {
		this.table = new Table(parent, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		GridData tableGridDataTable = new GridData(GridData.FILL_HORIZONTAL);
		tableGridDataTable.horizontalSpan = 4;
		tableGridDataTable.heightHint = 300;
		table.setLayoutData(tableGridDataTable);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		TableColumn tableColumnName = new TableColumn(table, SWT.NONE);
		tableColumnName.setText("Name");
		TableColumn tableColumnPath = new TableColumn(table, SWT.NONE);
		tableColumnPath.setText("Path");
		this.table.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				invariantIndex = table.getSelectionIndex();
				Layer petrinetInvlayer = getActivePetrinetInvLayer();
				if (petrinetInvlayer != null) {
					VariabilityUtils.refreshDiagram(siriusEditor);					
				} else {
					petrinetInvlayer = getAdditionalInvLayer();
					if (petrinetInvlayer != null) {
						VariabilityUtils.layerActivationCommand(petrinetInvlayer, siriusEditor);
					}
				}
			}
		});		
		fillTable();
	}

	public void fillTable() {
		table.removeAll();

		for (Map.Entry<String, List<String>> invariant : this.listOfInvariants.entrySet()) {
			TableItem item = new TableItem(this.table, SWT.NONE);
			item.setText(0,invariant.getKey());
			item.setText(1, formatPath(invariant.getValue()));
		}		
		packTable();

	}

	private void createInvariantOptions(Composite parentComposite) {		
		pInv = new Button(parentComposite, SWT.RADIO);
		pInv.setLayoutData(new GridData(GridData.FILL));
		pInv.setText("P-Inv");
		tInv = new Button(parentComposite, SWT.RADIO);
		tInv.setLayoutData(new GridData(GridData.FILL));
		tInv.setText("T-Inv");
		Button executeButton = new Button(parentComposite, SWT.PUSH);
		executeButton.setLayoutData(new GridData(GridData.FILL));
		executeButton.setText("Execute");		
		executeButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (pInv.getSelection() == false && tInv.getSelection() == false) {
					MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
							"Error", "One of these two options should be selected (P-Inv or T-Inv)");
					return;
				} else {
					executeAnalysis();
					fillTable();
				}
			}
		});

		Button removeLayerButton = new Button(parentComposite, SWT.PUSH);
		removeLayerButton.setLayoutData(new GridData(GridData.FILL));
		removeLayerButton.setText("Remove Layer P-Inv/T-Inv");
		removeLayerButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Layer petrinetInvlayer = getActivePetrinetInvLayer();
				if (petrinetInvlayer != null) {
					VariabilityUtils.layerActivationCommand(petrinetInvlayer, siriusEditor);					
				}
			}
		});
	}

	private String formatPath(List<String> listOfValues) {
		String formatPath = "";
		for (int i = 0; i < listOfValues.size(); i++) {
			if (i == 0)
				formatPath += listOfValues.get(i);
			else
				formatPath += ", " + listOfValues.get(i);
		}
		return formatPath;
	}

	private void packTable() {
		for (TableColumn tableColumn : this.table.getColumns()) {
			tableColumn.pack();
		}		
	}

	@Override
	public void setFocus() {

	}

	private void executeAnalysis() {
		IFile vrbFile = getVariabilityFile();
		if (vrbFile != null) {			
			InvariantUtils analysis = new InvariantUtils();			
			
			String type = "";
			if (this.pInv.getSelection() == true) {
				type = analysis.P_INVARIANT;
			} else if (this.tInv.getSelection() == true) {
				type = analysis.T_INVARIANT;
			}	
			
			isRunningViewAnalysis = true;
			analysis.execute(vrbFile, type);
			isRunningViewAnalysis = false;
		}		
	}

	private IFile getVariabilityFile() {
		if (this.siriusEditor == null) 
			this.siriusEditor = VariabilityUtils.getActiveSiriusDiagram();

		Collection<Resource> semanticResources = this.siriusEditor.getSession().getSemanticResources();
		for (Resource resource : semanticResources) {
			if (resource.getURI().fileExtension().equals("vrb")) {
				return ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(resource.getURI().toPlatformString(true)));				
			}
		}		
		return null;
	}

	@SuppressWarnings("unused")
	private Layer getPetrinetInvLayer() {
		DRepresentation representation = this.siriusEditor.getRepresentation();
		if (representation instanceof DDiagram) {
			DDiagram diagram = (DDiagram) representation;
			for(Layer layer: diagram.getDescription().getAdditionalLayers()) {
				if (layer.getName().equals(PETRINET_INVARIANT_LAYER_ID))
					return layer;
			}
		}		
		return null;
	}

	private Layer getActivePetrinetInvLayer() {
		DRepresentation representation = this.siriusEditor.getRepresentation();
		if (representation instanceof DDiagram) {
			DDiagram diagram = (DDiagram) representation;
			for(Layer layer: diagram.getActivatedLayers()) {
				if (layer.getName().equals(PETRINET_INVARIANT_LAYER_ID))
					return layer;
			}						
		}		
		return null;
	}

	private Layer getAdditionalInvLayer() {
		DRepresentation representation = this.siriusEditor.getRepresentation();
		if (representation instanceof DDiagram) {
			DDiagram diagram = (DDiagram) representation;
			for(Layer layer: diagram.getDescription().getAdditionalLayers()) {
				if (layer.getName().equals(PETRINET_INVARIANT_LAYER_ID))
					return layer;
			}
		}
		return null;
	}

}
