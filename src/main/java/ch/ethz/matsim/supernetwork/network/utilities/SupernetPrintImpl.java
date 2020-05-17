/**
 * 
 */
package ch.ethz.matsim.supernetwork.network.utilities;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.TreeSet;

import javax.inject.Inject;

import org.matsim.core.config.groups.ControlerConfigGroup;
import org.matsim.core.controler.OutputDirectoryHierarchy;
import org.matsim.core.utils.io.IOUtils;
import org.matsim.core.utils.io.UncheckedIOException;

import ch.ethz.matsim.supernetwork.cluster_analysis.cluster.Cluster;
import ch.ethz.matsim.supernetwork.cluster_analysis.cluster_element.Element;
import ch.ethz.matsim.supernetwork.cluster_analysis.cluster_element.ElementActivity;
import ch.ethz.matsim.supernetwork.network.Supernet;

/**
 * @author stefanopenazzi
 *
 */
public class SupernetPrintImpl implements SupernetPrint {

	public static final String FILENAME_SUPERNET = "supernetwork";
	
	private final Supernet supernet;
	final private BufferedWriter supernetOut ;
	private final ControlerConfigGroup controlerConfigGroup;
	final private String supernetFileName; 
	
	private int numOfClusters = 0;
	private int numOfActivities = 0;
	private double clustersActivitiesRate = 1;
	private int[] clustersActivitiesFreqAnalysis;
	private int[] clustersActivitiesAverageDistCentroidFreqAnalysis;
	
	@Inject
	SupernetPrintImpl(Supernet supernet,ControlerConfigGroup controlerConfigGroup,OutputDirectoryHierarchy controlerIO){
		this.supernet = supernet;
		this.controlerConfigGroup = controlerConfigGroup;
		this.supernetFileName = controlerIO.getOutputFilename( FILENAME_SUPERNET ) ;
		this.supernetOut = IOUtils.getBufferedWriter(this.supernetFileName + ".txt");
		
	}
	
	public void print() {
		
		init();
		
		try {
			this.supernetOut.write("Supernetwork clusters statistics \n");
			this.supernetOut.write(numOfClustersToString());
			this.supernetOut.write(numOfActivitiesToString());
			this.supernetOut.write(clustersActivitiesRateToString());
			this.supernetOut.write(clustersActivitiesFreqAnalysisToString());
			this.supernetOut.write(clustersActivitiesAverageDistCentroidFreqAnalysisToString());
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
		close();
		
	}
	
	public void init() {
		numOfClusters_();
		numOfActivities_();
		clustersActivitiesRate_();
		clustersActivitiesFreqAnalysis_(10);
		clustersActivitiesAverageDistCentroidFreqAnalysis_(100); 
	}
	
	public void numOfClusters_() {
		this.numOfClusters = supernet.getActivitiesClusterContainer().getClusters().size();	
	}
	
	public void numOfActivities_() {
		for(Cluster c: supernet.getActivitiesClusterContainer().getClusters()) {
			this.numOfActivities += c.getComponents().size();
		}
	}

	public void clustersActivitiesRate_() {
		clustersActivitiesRate = this.numOfClusters/this.numOfActivities;
	}
	
	public void clustersActivitiesFreqAnalysis_(int range) {
		clustersActivitiesFreqAnalysis = new int[this.numOfActivities/range];
		for(Cluster c: supernet.getActivitiesClusterContainer().getClusters()) {
			clustersActivitiesFreqAnalysis[(int)Math.floor(c.getComponents().size()/range)] += 1 ;
		}
	}
	
	public void clustersActivitiesAverageDistCentroidFreqAnalysis_(int range) {
		clustersActivitiesAverageDistCentroidFreqAnalysis = new int[10000/range];
		for(Cluster<? extends Element> c: supernet.getActivitiesClusterContainer().getClusters()) {
			double avgDist = 0;
			double XC = c.getCentroid().getX();
			double YC = c.getCentroid().getY();
			Iterator<? extends Element> iter = c.getComponents().iterator();
		    while (iter.hasNext()) {
		    	ElementActivity EA = (ElementActivity)iter.next();
		    	double XEA = EA.getActivity().getCoord().getX();
				double YEA = EA.getActivity().getCoord().getY();
				avgDist += Math.sqrt(Math.pow(XC - XEA,2) + 
						Math.pow(YC - YEA ,2));
		    	
		    }
		    avgDist = avgDist/c.getComponents().size();
			clustersActivitiesAverageDistCentroidFreqAnalysis[(int)Math.floor(avgDist/range)] += 1 ;
		}
	}
	
	public String numOfActivitiesToString() {
		String ss = "Number of activities = " + this.numOfActivities + "\n";
		return ss;
	}
	
	public String numOfClustersToString() {
		String ss = "Number of clusters = " + this.numOfClusters + "\n";
		return ss;
	}
	
	public String clustersActivitiesRateToString() {
		String ss = "Clusters//Activities = " + this.clustersActivitiesRate + "\n";
		return ss;
	}
	
	public String clustersActivitiesFreqAnalysisToString() {
		String ss = "Range;N_clusters \n";
		
		for(int i = 0; i < clustersActivitiesFreqAnalysis.length; ++i) {
			if(clustersActivitiesFreqAnalysis[i] > 0) {
			    ss += i + ";"+ clustersActivitiesFreqAnalysis[i] + "\n";
			}
		}
		return ss;
	}
	
	public String clustersActivitiesAverageDistCentroidFreqAnalysisToString() {
		String ss = "\n";
		ss += "clustersActivitiesAverageDistCentroidFreqAnalysis \n";
        ss += "Range;N_clusters \n";
		
		for(int i = 0; i < clustersActivitiesAverageDistCentroidFreqAnalysis.length; ++i) {
			if(clustersActivitiesAverageDistCentroidFreqAnalysis[i] > 0) {
			    ss += i + ";"+ clustersActivitiesAverageDistCentroidFreqAnalysis[i] + "\n";
			}
		}
		return ss;
	}
	
	public void close() {
		try {
			this.supernetOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
