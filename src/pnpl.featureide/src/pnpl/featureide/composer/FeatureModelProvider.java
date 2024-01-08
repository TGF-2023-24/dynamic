package pnpl.featureide.composer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;

import de.ovgu.featureide.fm.core.ExtensionManager.NoSuchExtensionException;
import de.ovgu.featureide.fm.core.base.IConstraint;
import de.ovgu.featureide.fm.core.base.IFeature;
import de.ovgu.featureide.fm.core.base.IFeatureModel;
import de.ovgu.featureide.fm.core.base.impl.FMFactoryManager;
import de.ovgu.featureide.fm.core.base.impl.FMFormatManager;
import de.ovgu.featureide.fm.core.functional.Functional;
import de.ovgu.featureide.fm.core.io.IFeatureModelFormat;
import de.ovgu.featureide.fm.core.io.manager.SimpleFileHandler;
import solver.features.IFeatureProvider;
import solver.features.impl.Feature;

/**
 * A feature provider that obtains the feature values directly from the XML file
 * @author Juan de Lara
 *
 */
public class FeatureModelProvider implements IFeatureProvider {
	private IFile featureModelFile;
	private IFeatureModel fm;
	
	public FeatureModelProvider() { }
	
	public FeatureModelProvider(IFile inputFile) {
		this.featureModelFile = inputFile;
		this.createFeatureModel();
	}
	
	public IFeatureModel getFeatureModel() {
		return this.fm;
	}
	
	public List<IFeature> getFeaturesInConstraints() {
		List<IFeature> features = new ArrayList<>();
		for (IConstraint ic : this.fm.getConstraints()) {
			features.addAll(ic.getContainedFeatures());
		}
		return features;
	}
	
	private void createFeatureModel() {
		String contents = this.readFile(this.featureModelFile).toString();
		final IFeatureModelFormat format = (IFeatureModelFormat) FMFormatManager.getInstance().getFormatByContent(contents, this.featureModelFile.getLocation().toOSString());
		try {
			//fm = FMFactoryManager.getFactory(this.featureModelFile.getLocation().toString(), format).createFeatureModel();
			fm = FMFactoryManager.getInstance().getFactory(this.featureModelFile.getLocation().toFile().toPath(), format).create();
		} catch (NoSuchExtensionException e) {
			//fm = FMFactoryManager.getDefaultFactory().createFeatureModel();
			try {
				fm = FMFactoryManager.getInstance().getFactory(format).create();
			} catch (NoSuchExtensionException e1) {
				// TODO Auto-generated catch block
				System.err.println(e1);
			}
		}
		fm.setSourceFile(this.featureModelFile.getLocation().toFile().toPath());
		try {
			SimpleFileHandler.load(this.featureModelFile.getContents(), fm, format);
		} catch (final CoreException e) {
			System.err.println(e);
		}
	}
	
	private StringBuilder readFile(IFile inputFile) {
		StringBuilder sb = new StringBuilder();
		if (inputFile==null) return sb;
		try (BufferedReader br = new BufferedReader(new FileReader(inputFile.getLocation().toFile()))){
		    String line = br.readLine();

		    while (line != null) {
		        sb.append(line);
		        line = br.readLine();
		    }
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return sb;
	}
	
	@Override
	public boolean isValidFeature(String s) {
		if (this.featureModelFile==null) return false;
		for (IFeature feat : this.fm.getFeatures()) {
			if (feat.getName().equals(s)) return true;
		}
		return false;
	}
	
	@Override
	public solver.features.IFeature getFeature(String name) {
		if (this.fm==null) return null; 
		IFeature feature = this.fm.getFeature(name);
		if (feature!=null) return new Feature(name);	// we might probably like to return the IFeature from FeatureIDE
		return null;
	}

/*	@Override
	public boolean isValidFeature(String token) {
		if (this.featureModelFile==null) return false;
		File file = this.featureModelFile.getRawLocation().makeAbsolute().toFile();
		// TODO: Cache this
		List<String> features = this.getFeatures(file);
		
		for (String s : features) {
			if (s.toUpperCase().equals(token.toUpperCase())) return true;
		}
		
		return false;
	}
	
	private List<String> getFeatures(File file) {
	    StringBuilder sb = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(file))){
		    String line = br.readLine();

		    while (line != null) {
		        sb.append(line);
		        line = br.readLine();
		    }
		    String everything = sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return this.getFeatures(sb);
	}
	
	private List<String> getFeatures(StringBuilder sb) {
		Set<String> features = new HashSet<String>();
		
		String fm = sb.toString();
		
		int idx = 0;
		while (idx >= 0) {
			int occur = fm.indexOf("name=\"", idx);	// TODO: improve robustness... this should be a regular expression
			if (occur >= 0) {
				String featureName = fm.substring(occur+6);	// TODO: improve robustness
				int occur2 = featureName.indexOf("\"");
				featureName = featureName.substring(0, occur2);
				features.add(featureName);
				idx = occur+1;
			} else { 
				idx = occur;
			}
		}
		
		return new ArrayList<String>(features);
	}*/

	@Override
	public boolean getFeatureValue(String s) {
		return false;
	}

	public void setFeatureModelFile(IFile f) {
		this.featureModelFile = f;
		this.createFeatureModel();
	}

	public IFile getFeatureModelFile() {
		return this.featureModelFile;
	}

	public List<IFeature> getFeatures() {
		return this.featureModelFile==null && this.fm==null? 
				Collections.emptyList() :
				Functional.toList(this.fm.getFeatures());
	}
}
