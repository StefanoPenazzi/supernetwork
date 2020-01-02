/**
 * 
 */
package ch.ethz.matsim.supernetwork.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.population.Activity;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.PlanElement;

import ch.ethz.matsim.supernetwork.clustering.cluster.ClusterNetworkRegionImpl;
import ch.ethz.matsim.supernetwork.clustering.clusteringAlgorithms.ClusteringNetworkRegionAlgorithm;
import ch.ethz.matsim.supernetwork.clustering.clustersContainer.KDNode;
import ch.ethz.matsim.supernetwork.clustering.clustersContainer.KDTreeClustersContainer;



/**
 * @author stefanopenazzi
 *
 */
public class ClusteringActivities {

	public ClusteringActivities(Scenario scenario,String outputPath) {
		
		List<ClusterNetworkRegionImpl> regions;
		ClusteringNetworkRegionAlgorithm cn = new ClusteringNetworkRegionAlgorithm(scenario);
		regions = cn.getRegions();
		
		KDTreeClustersContainer container = new KDTreeClustersContainer(null,regions.size());
		for(ClusterNetworkRegionImpl reg: regions) {
			container.add(reg);
		}
		
		for(Person p: scenario.getPopulation().getPersons().values()) {
			if(p.getPlans().size() >= 1) {
			 List<PlanElement> le = p.getPlans().get(0).getPlanElements();
		     for(PlanElement pe: le) {
		    	 if ( !(pe instanceof Activity) ) continue;
		    	 KDNode kdn = container.nearestNeighbourSearch(((Activity)pe).getCoord());
		    	 kdn.getCluster().addActivity((Activity)pe);
		    	 //find the next activity 
		    	 int activityListIndex = le.indexOf(pe);
		    	 if(activityListIndex < le.size()-1) {
			    	 for(int j =  activityListIndex+1; j<le.size() ;++j ) {
			    		 if ( le.get(j) instanceof Activity) {
			    			 double dist = Math.pow(((Activity)pe).getCoord().getX()-((Activity)le.get(j)).getCoord().getX(),2) + 
			    					 Math.pow(((Activity)pe).getCoord().getY()-((Activity)le.get(j)).getCoord().getY(),2);
			    			 if(dist > ((ClusterNetworkRegionImpl)kdn.getCluster()).getNetworkRadius()) 
			    			 {
			    				 ((ClusterNetworkRegionImpl)kdn.getCluster()).setNetworkRadius(dist);
			    			 }
			    			 break;
			    		 }
			    	 }
		    	 }
		     }	
		   }
		}
		stat(outputPath,regions);
	}
	
    public void stat(String outputPath,List<ClusterNetworkRegionImpl> regions) {
		
		int nAct = 0;
		
		for(ClusterNetworkRegionImpl r: regions) {
			nAct += r.getActivities().size();
		}
		
		int[][] actFA = actFreqAnalysis(10,regions);
	    double[][] distFA = distFreqAnalysis(100,regions);
	    double[][] areaFA = areaFreqAnalysis(10000,regions);
	    double[] subnetRadius = subnetworkRadius(regions);
	    
	    File file = new File(outputPath);
	    FileWriter fr = null;
        BufferedWriter br = null;
        
        String results = "";
        results += "Tot number of regions: " + regions.size() +" \n";
        results += "Tot number of activities: " + nAct +" \n";
        results += " actFreqAnalysis \n";
        for(int i =0;i<actFA.length;++i) {
        	
        	results += " Range ( "+ actFA[i][0]+ " - "+ actFA[i][1]+ " ) -> "+ actFA[i][2] + "\n";
        	
        }
        results += " distFreqAnalysis \n";
        for(int i =0;i<distFA.length;++i) {
        	
        	results += " Range ( "+ distFA[i][0]+ " - "+ distFA[i][1]+ " ) -> "+ distFA[i][2] + " -> "+ distFA[i][3] +"\n";
        	
        }
        results += " areaFreqAnalysis \n";
        for(int i =0;i<areaFA.length;++i) {
        	
        	results += " Range ( "+ areaFA[i][0]+ " - "+ areaFA[i][1]+ " ) -> "+ areaFA[i][2] + "\n";
        	
        }
        results += " subnetworkRadius \n";
        for(int i =0;i<regions.size();++i) {
        	
        	results += " " + i + " -> " + subnetRadius[i] + "\n";
        	
        }
        
        try{
            fr = new FileWriter(file);
            br = new BufferedWriter(fr);
            br.write(results);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                br.close();
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
   
   public int[][] actFreqAnalysis(int range,List<ClusterNetworkRegionImpl> regions) {
	  
	   Collections.sort(regions, new Comparator<ClusterNetworkRegionImpl>(){
			@Override
			  public int compare(ClusterNetworkRegionImpl u1, ClusterNetworkRegionImpl u2) {
				int c;
				
			    c = u1.getActivities().size() == u2.getActivities().size()? 0 : u1.getActivities().size() < u2.getActivities().size() ? -1:1; 
			    return c;
			  }
		});
	   
	   int maxAct = regions.get(regions.size()-1).getActivities().size();
	   int minAct = 0;
	   if (range == 0 || range>maxAct) return null;
	   int classes = (int) (Math.ceil(maxAct/range) + 1);
	   int[][] res = new int[classes][3];
	   res[0][0] = 0;
	   res[0][1] = 0;
	   for(int i = 1;i<classes;i++) {
		   res[i][0] = i*range - (range-1);
		   res[i][1] = i*range;
	   }
	   
	   for(ClusterNetworkRegionImpl r:regions) {
		  int pos = (int) (Math.ceil(r.getActivities().size()/range));
		  res[pos][2] =  res[pos][2] + 1; 
	   }
	   
	   int regWithAct =0;
	   for(int i = 0;i<classes;i++) {
		   regWithAct += res[i][2];
	   }
	   
	   //print stat
		/*
		 * System.out.println("N. of regions with more than 1 act = "+ regWithAct);
		 * System.out.println("Freq analysis"); for(int i = 0;i<classes;i++) {
		 * System.out.println(" Range ( "+ res[i][0]+ " - "+ res[i][1]+ " ) -> "+
		 * res[i][2] ); }
		 */
	   return res;
   }
   
   public double[][] distFreqAnalysis(double range,List<ClusterNetworkRegionImpl> regions) {
		  
	   if (range == 0 || range> 4000) return null;
	   int classes = (int) (Math.ceil(4000/range) + 1);
	   double[][] res = new double[classes][4];
	   for(int i = 0;i<classes;i++) {
		   int j = i+1;
		   res[i][0] = j*range - (range-0.9);
		   res[i][1] = j*range;
	   }
	   for(ClusterNetworkRegionImpl r:regions) {
		   if(r.getActivities().size()>0) {
			   double avgDist =0;
			   for(Activity act:r.getActivities()){
				   avgDist += Math.sqrt( Math.pow((r.getCentroid().getX() - act.getCoord().getX() ),2) + Math.pow((r.getCentroid().getY() - act.getCoord().getY() ),2));
			   }
			   avgDist = avgDist/ r.getActivities().size();
			   int pos = (int) (Math.ceil(avgDist/range));
			   if(pos>classes-1) {
				   res[classes-1][2] =  res[classes-1][2] + 1; 
				   res[classes-1][3] =  res[classes-1][3] + r.getActivities().size();
			   }
			   else {
				   res[pos][2] =  res[pos][2] + 1; 
				   res[pos][3] =  res[pos][3] + r.getActivities().size(); 
			   }
		   }
	    }
	   
	   //print stat
		/*
		 * System.out.println("Average dist from centroid");
		 * System.out.println("Freq analysis"); for(int i = 0;i<classes;i++) {
		 * System.out.println(" Range ( "+ res[i][0]+ " - "+ res[i][1]+ " ) -> "+
		 * res[i][2] ); }
		 */
	   return res;
   }
   
   public double[][] areaFreqAnalysis(double range,List<ClusterNetworkRegionImpl> regions) {
		  
	   if (range == 0 || range> 1000000) return null;
	   int classes = (int) (Math.ceil(1000000/range) + 1);
	   double[][] res = new double[classes][3];
	   for(int i = 0;i<classes;i++) {
		   int j = i+1;
		   res[i][0] = j*range - (range-0.9);
		   res[i][1] = j*range;
	   }
	   for(ClusterNetworkRegionImpl r:regions) {
		   if(r.getActivities().size()>0) {
			   int pos = (int) (Math.ceil(r.getArea()/range));
			   if(pos>classes-1) {
				   res[classes-1][2] =  res[classes-1][2] + 1; 
			   }
			   else {
				   res[pos][2] =  res[pos][2] + 1; 
			   }
		   }
	    }
	   
	   //print stat
		/*
		 * System.out.println("Regions area"); System.out.println("Freq analysis");
		 * for(int i = 0;i<classes;i++) { System.out.println(" Range ( "+ res[i][0]+
		 * " - "+ res[i][1]+ " ) -> "+ res[i][2] ); }
		 */
	   return res;
   }
   
   public double[] subnetworkRadius(List<ClusterNetworkRegionImpl> regions) {
	   
	   double[] res = new double[regions.size()];
	   int k = 0;
	   for(ClusterNetworkRegionImpl r:regions) {
		   res[k] = Math.sqrt(r.getNetworkRadius());
		   k++;
	   }
	   return res;
   }
}
