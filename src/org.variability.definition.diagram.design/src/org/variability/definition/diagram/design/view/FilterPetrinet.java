package org.variability.definition.diagram.design.view;

import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.diagram.description.filter.FilterDescription;
import org.eclipse.sirius.diagram.ui.tools.api.editor.DDiagramEditor;
import org.eclipse.sirius.viewpoint.DRepresentation;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.sat4j.specs.TimeoutException;
import org.variability.definition.diagram.design.generate.utils.VariabilityUtils;
import de.ovgu.featureide.fm.core.analysis.cnf.formula.FeatureModelFormula;
import de.ovgu.featureide.fm.core.configuration.Configuration;
import de.ovgu.featureide.fm.core.configuration.ConfigurationAnalyzer;
import de.ovgu.featureide.fm.core.configuration.Selection;
import de.ovgu.featureide.core.CorePlugin;
import de.ovgu.featureide.core.IFeatureProject;

public class FilterPetrinet extends ViewPart {
	
	private static FilterPetrinet singleFilterPetrinet = null;
	private DDiagramEditor siriusEditor;
	private IFeatureProject featureProject; 
	private Configuration configuration;
	private Composite parentComposite;
	
	//Actions
	private Action refresh;
		
	//Initialization of the View
	public FilterPetrinet() {		
		singleFilterPetrinet = this;	
		refresh();
	}	
	// static method to create instance of Singleton class 
    public static FilterPetrinet getInstance() { 
       return singleFilterPetrinet; 
    } 
    
    public void refresh() {
    	this.siriusEditor = VariabilityUtils.getActiveSiriusDiagram();  
    	if (this.getActiveSiriusDiagram() != null) {
	    	String projectName = this.getActiveSiriusDiagram().getSession().getSessionResource().getURI().segment(1);
	    	IProject iProject = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
	    	this.featureProject = CorePlugin.getFeatureProject(iProject);
	    	initConfiguration();
    	}
    }
    
    // Represents a configuration
    private void initConfiguration() {
		FeatureModelFormula featureModel = 
				this.getFeatureProject().getFeatureModelManager().getVariableFormula();		
		this.configuration = new Configuration(featureModel);		
		ConfigurationAnalyzer analyzer = new ConfigurationAnalyzer(
				getFeatureProject().getFeatureModelManager().getVariableFormula(), 
					configuration);
		try {
			List<List<String>> oneSolution = analyzer.getSolutions(1);
			updateConfiguration(oneSolution);			
		} catch (TimeoutException e) {
			e.printStackTrace();
		}		
	}
    
    private void updateConfiguration(List<List<String>> oneSolution) {
		if (oneSolution.size() >= 1) {
			Iterator<String> listOfSelectedFeatures = oneSolution.get(0).iterator();
			while (listOfSelectedFeatures.hasNext()) {
				String feature = (String) listOfSelectedFeatures.next();
				getConfiguration().setManual(feature, Selection.SELECTED);
			}
		}    	
	}
	public Configuration getConfiguration() {
    	return this.configuration;
    }
    
    public DDiagramEditor getActiveSiriusDiagram() {
    	return this.siriusEditor;
    }
    
    public IFeatureProject getFeatureProject() {
    	return this.featureProject;
    }
    
    public void createActions() {
    	this.refresh = new Action("Refresh") {
            public void run() { 
            	refresh();	
            	disposeAllChildren();
            	createViewControl(parentComposite);
            	parentComposite.layout();
            }

			private void disposeAllChildren() {
				Control[] children = parentComposite.getChildren();
				for (int i = 0; i < children.length; i++) {
					Control control = children[i];
					control.dispose();
				}
			}
    	};
    	refresh.setImageDescriptor(getImageDescriptor("refresh-icon.gif"));    	
    }
    
    /**
     * Create toolbar.
     */
    private void createToolbar() {
            IToolBarManager mgr = getViewSite().getActionBars().getToolBarManager();
            mgr.add(this.refresh);            
    }
    
	@Override
	public void createPartControl(Composite parent) {
		this.parentComposite = parent;
		createActions();
		createToolbar();
		createViewControl(parent);	
	}
	
	private void createViewControl(Composite parent) {
		if (this.getActiveSiriusDiagram() != null && this.getFeatureProject() != null) {		
			createFeatureTree(parent);     
			Composite secondComposite = new Composite(parent, SWT.NONE);
			GridLayout columnLayout = new GridLayout();
			columnLayout.numColumns = 1;
			secondComposite.setLayout(columnLayout);
			//Hide elements taking into account the feature configuration
			Button filterButton = new Button(secondComposite, SWT.PUSH);
			filterButton.setText("Filter Petri-elements using Configuration");
			Point size = filterButton.computeSize(SWT.DEFAULT, SWT.DEFAULT);
			filterButton.setSize(size);
			filterButton.addSelectionListener(new FilterButtonSelectionAdapter(filterButton
					,VariabilityUtils.FILTER_VARIABILITY_NAME));
			Device device = Display.getCurrent();
			filterButton.setBackground(new Color(device, 236,233,216));
			Button filterElementsWPCButton = new Button(secondComposite, SWT.PUSH);
			filterElementsWPCButton.setText("Hide Elements with attached PC");
			Point sizeFilterElementsWPCButton = filterElementsWPCButton.computeSize(SWT.DEFAULT, SWT.DEFAULT);
			filterElementsWPCButton.setSize(sizeFilterElementsWPCButton);
			filterElementsWPCButton.setBackground(new Color(device, 236,233,216));
			filterElementsWPCButton.addSelectionListener(new FilterButtonSelectionAdapter(filterElementsWPCButton
					,VariabilityUtils.FILTER_HIDE_ATTACHED_PC));
	     } else {
	    	 Text text = new Text(parent, SWT.BORDER);
		     text.setText("Cannot be found a Sirius Editor");
		}
	}
	
	private void createFeatureTree(Composite parent) {
		//Scrolled Composite
		ScrolledComposite sc = new ScrolledComposite(parent, SWT.V_SCROLL);
		sc.setLayout(new FillLayout());
		sc.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		//Create Tree
		Tree tree = new Tree(sc, SWT.VIRTUAL | SWT.BORDER | SWT.CHECK ); 
		tree.setHeaderVisible(false);
		tree.setLinesVisible(false);		
		//Create TreeViewer		
		TreeViewer treeViewer = new TreeViewer(tree);
		treeViewer.setUseHashlookup(true);
		GridData treedata = new GridData(SWT.FILL, SWT.FILL, true, true);
		tree.setLayoutData(treedata);
		tree.setLayout(new FillLayout());	
		tree.addListener(SWT.Selection, new FeatureSelectionListener());		
		//Column
		TreeViewerColumn featureColumn = new TreeViewerColumn(treeViewer, SWT.LEFT);
		featureColumn.getColumn().setWidth(500);
		fillWithFeatureValues(featureColumn, treeViewer);		
		sc.setExpandHorizontal(true);
		sc.setExpandVertical(true);
		sc.setAlwaysShowScrollBars(true);
		sc.setContent(tree);
		//Expand all elements
		treeViewer.getTree().setRedraw(false);
		treeViewer.expandAll();
		treeViewer.getTree().setRedraw(true);
		initializeFeature(treeViewer);		   
	}	
	
	private void initializeFeature(TreeViewer treeViewer) {
 		TreeItem[] treeItems = treeViewer.getTree().getItems();
 		if (treeItems.length == 1) {
			treeItems[0].setChecked(true);//Root of the feature is always true
			if (treeItems[0].getItems().length > 0) {
				initializeItems(treeItems[0].getItems());
			}
		}
	}
	
	private void initializeItems(TreeItem[] treeItems) {
		for (int i = 0; i < treeItems.length; i++) {
			if (configuration.getSelectedFeatureNames().contains(treeItems[i].getText()) == true) {
				treeItems[i].setChecked(true);
				if (treeItems[i].getItemCount() > 0)
					initializeItems(treeItems[i].getItems());
			}
		}			
	}
	private void fillWithFeatureValues(TreeViewerColumn featureColumn, TreeViewer treeViewer) {
		featureColumn.setLabelProvider(new CFeatureColumn());	
		featureColumn.setEditingSupport(new ESFeatureColumn(treeViewer));
		treeViewer.setContentProvider(new FeatureContentProvider());
		treeViewer.setInput(this.getFeatureProject().getFeatureModel().getStructure());
	}
	
	@Override
	public void setFocus() {
		
	}
	
	private class FilterButtonSelectionAdapter extends SelectionAdapter {
		
		private Button filterButton;
		private String filterId;
		
		public FilterButtonSelectionAdapter(Button filterButton, String filterId) {
			this.filterButton = filterButton;
			this.filterId = filterId;
		}

		@Override
		public void widgetSelected(SelectionEvent e) {
			if (siriusEditor != null) {
    			DRepresentation dRepresentation = siriusEditor.getRepresentation();
    			if (dRepresentation instanceof DDiagram) {
    				DDiagram siriusDiagram = (DDiagram) dRepresentation;
    				FilterDescription filterPNElements = VariabilityUtils.findifActiveFilterDescription(siriusDiagram, 
    						this.filterId);
    				boolean validConfiguration = checkValidConfiguration();
    				if (validConfiguration == false) {
    					MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
    	    					"Error", "This Feature Configuration is not valid");
    					return;
    				}
    				Device device = Display.getCurrent();
    				if (filterPNElements == null) {
    					VariabilityUtils.activateFilterPNHideElements(siriusDiagram, 
    							this.filterId);    					
    					this.filterButton.setBackground(new Color(device, 0, 200, 0));
    				} else {
    					VariabilityUtils.deActivateFilterPNHideElements(siriusDiagram, 
    							this.filterId); 
    					this.filterButton.setBackground(new Color(device, 236,233,216));
    				}
    			}
    		} else
    			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
    					"Error", "Could not be found a Sirius Diagram");			
		}

		private boolean checkValidConfiguration() {
			ConfigurationAnalyzer analyzer = new ConfigurationAnalyzer(
					getFeatureProject().getFeatureModelManager().getVariableFormula(), 
						configuration);
			return analyzer.canBeValid();
		}
	}	
	
	/**
     * Returns the image descriptor with the given relative path.
     */
    private ImageDescriptor getImageDescriptor(String relativePath) {
    	Bundle bundle = FrameworkUtil.getBundle(FilterPetrinet.class);
    	URL fullPathString = bundle.getEntry("icons/" + relativePath);
    	return ImageDescriptor.createFromURL(fullPathString);    	
    }
}
