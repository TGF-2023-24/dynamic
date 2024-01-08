package solver.features;

public interface IFeatureProvider {
	boolean isValidFeature(String name);
	IFeature getFeature(String token);
	boolean getFeatureValue(String name);
}
