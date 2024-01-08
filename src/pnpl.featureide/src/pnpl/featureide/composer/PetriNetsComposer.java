package pnpl.featureide.composer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;

import PetriNets.PTArc;
import PetriNets.PetriNetsFactory;
import PetriNets.Place;
import PetriNets.TPArc;
import PetriNets.Transition;
import de.ovgu.featureide.core.IFeatureProject;
import de.ovgu.featureide.core.builder.ComposerExtensionClass;
import de.ovgu.featureide.fm.core.ExtensionManager.NoSuchExtensionException;
import de.ovgu.featureide.fm.core.base.impl.ConfigFormatManager;
import de.ovgu.featureide.fm.core.configuration.Configuration;
import de.ovgu.featureide.fm.core.configuration.DefaultFormat;
import de.ovgu.featureide.fm.core.io.IPersistentFormat;
import de.ovgu.featureide.fm.core.io.manager.SimpleFileHandler;
import pnpl.featureide.helper.EMFHandler;
import solver.presenceConditions.ConditionParser;
import variability.PresenceCondition;
import variability.Variability;


public class PetriNetsComposer extends ComposerExtensionClass {
	private static final String petriExtension = ".petrinets";
	private static final String annotExtension = ".vrb";
	private File net, annot;
	private EMFHandler emh = null;
	private Resource pnResource, annResource;
	private Variability vRoot = null;
//	private PetriNet pn;
	
	public PetriNetsComposer() {
		System.out.println("[petri nets PLs] Initializing (constructor)");
		this.removeJavaNature();
	}
	
	public PetriNetsComposer(Variability vrb) {
		System.out.println("[petri nets PLs] Initializing (constructor)");
		this.removeJavaNature(); 
		this.vRoot = vrb;
	}
	
	private void findFiles()  {
		if (net == null) {
			IProject pr = this.featureProject.getProject();
			try {
				for (IResource res : pr.members()) {
					if (res.getName().endsWith(petriExtension)) {
						net = res.getLocation().toFile();
						System.out.println("[petri nets PLs] Processing file: "+(net == null ? "none" : this.net.getName()));
					}
					if (res.getName().endsWith(annotExtension)) {
						annot = res.getLocation().toFile();
						System.out.println("[petri nets PLs] Annotation file: "+(net == null ? "none" : this.annot.getName()));
					}
				}
			} catch (CoreException e) {				
				e.printStackTrace();
			}
		}
	}
	
	private void removeJavaNature() {
		if (this.featureProject == null) return;
		IProject pr = this.featureProject.getProject();
		System.out.println("[merlin] removing Java nature of project "+pr);
		try {
			if (pr.hasNature(JAVA_NATURE)) {
				IProjectDescription desc = pr.getDescription();
			    String[] prevNatures = desc.getNatureIds();
				String[] newNatures = new String[prevNatures.length - 1];
				int idx = 0;
				for (int i = 0; i<prevNatures.length; i++) {
					if (!JAVA_NATURE.equals(prevNatures[i])) newNatures[idx++] = prevNatures[i];
				}
			    desc.setNatureIds(newNatures);
			    pr.setDescription(desc, new NullProgressMonitor());
			}			
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}
	
	public boolean initialize(IFeatureProject project) {
		boolean ret = super.initialize(project);
		System.out.println("[petri nets PLs] initialize project");
		this.removeJavaNature();			
		return ret;
	}
	
	
	private void loadModels() {
		if (this.pnResource==null) {
			if (this.emh==null) this.emh = new EMFHandler();
			this.pnResource = this.emh.loadModel(this.net, PetriNetsFactory.eINSTANCE.getEPackage());
			this.annResource = this.emh.loadXtextModel(this.annot);			
			if (this.vRoot==null) this.vRoot = (Variability)this.annResource.getContents().get(0);
			EcoreUtil.resolveAll(vRoot);			
			//this.pn = (PetriNet)this.pnResource.getContents().get(0);
		}
	}
	
	public void performFullBuild(IFile config) {		
		System.out.println("[petri nets PLs] Full build"+config.getFullPath());
		this.removeJavaNature();
		this.findFiles();
	}
	
	@Override
	public void addCompiler(IProject project, String sourcePath, String configPath, String buildPath) {
		// NOP! (we do not want to compile Java!).
	}
	
	@Override
	public void buildConfiguration(IFolder folder, Configuration configuration, String cfgName) {
		this.findFiles();
		System.out.println("[petri nets PLs] Selected features = "+configuration.getSelectedFeatureNames());
		if (this.isInitialized())		
			super.buildConfiguration(folder, configuration, cfgName);
		else
			this.persistConfig(folder, configuration, cfgName);
		genModel(folder, configuration);
	}

	private void genModel(IFolder folder, Configuration config) {	
		this.loadModels();
		String fileName = this.net.getName();
		URI uri = URI.createFileURI(new File(folder.getLocation().toOSString()+File.separator+fileName).getAbsolutePath());
		
		Resource newr = this.pnResource.getResourceSet().createResource(uri);
		newr.getContents().addAll(EcoreUtil.copyAll(this.pnResource.getContents()));
		
		FeatureIDEProvider fip = new FeatureIDEProvider(config);
		if (this.emh==null) this.emh = new EMFHandler();
				
		/*for (PresenceCondition pc : vRoot.getPresencecondition()) {
			ConditionParser cp = new ConditionParser(pc.getExpression().toString(), fip);			
			System.out.println("[PC] "+pc.getExpression().toString()+"{"+config.getSelectedFeatureNames()+"} = "+cp.eval());
		}*/				
		
		List<EObject> toDelete = new ArrayList<>();
		TreeIterator<EObject> allContents = newr.getAllContents();
		while (allContents.hasNext()) {
			EObject obj = allContents.next();
			String name = getNameOf(obj);
			if (name == null) continue;
			PresenceCondition pc = vRoot.getPresencecondition(name);
			if (pc != null ) {
//				if (this.emh==null) this.emh = new EMFHandler();
				ConditionParser cp = new ConditionParser(this.emh.object2xtext(pc.getExpression()), fip);
				if (!cp.eval()) {
					toDelete.add(obj);
					System.out.println("[petri nets PLs] CFG " +config.getSelectedFeatureNames()+ "deleting "+obj);
				}
			}
		}		
		this.emh.removeAllPNObjects(newr, toDelete);
				
		try {
			newr.save(null);
		} catch (IOException e) {
			System.err.println("[petri nets PLs] Error saving file" + uri);
		}
		
		System.out.println("[petri nets PLs] saved model "+uri);
	}
	
	public static String getNameOf(EObject obj) {
		if      (obj instanceof Place) return ((Place)obj).getName();
		else if (obj instanceof Transition) return ((Transition)obj).getName();
		else if (obj instanceof PTArc) return ((PTArc)obj).getName();
		else if (obj instanceof TPArc) return ((TPArc)obj).getName();
		else return null;
	}

	private void persistConfig(IFolder folder, Configuration configuration, String cfgName) {
		IPersistentFormat<Configuration> format;
		try {
			if (!folder.exists()) {
				folder.create(true, true, null);
			}
			format = ConfigFormatManager.getInstance().getFormatById(DefaultFormat.ID);
			final IFile configurationFile = folder.getFile(cfgName + "." + format.getSuffix());
			SimpleFileHandler.save(Paths.get(configurationFile.getLocationURI()), configuration, format);
		} catch (NoSuchExtensionException | CoreException e) {
			System.err.println("[petri nets PLs] error saving configuration file to "+folder);
		}		
	}
	
	@Override
	public boolean hasSourceFolder() {
		return false;
	}
	
	@Override
	public boolean hasFeatureFolder() {
		return false;
	}
	
	@Override
	public boolean canGeneratInParallelJobs() {
		return false;
	}

	@Override
	public void performFullBuild(Path config) {
		// TODO Auto-generated method stub		
	}
}
