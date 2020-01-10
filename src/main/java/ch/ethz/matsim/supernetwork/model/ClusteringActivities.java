/**
 * 
 */
package ch.ethz.matsim.supernetwork.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.population.Activity;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.PlanElement;

import ch.ethz.matsim.supernetwork.clustering.cluster.Cluster;
import ch.ethz.matsim.supernetwork.clustering.cluster.ClusterDefaultImpl;
import ch.ethz.matsim.supernetwork.clustering.cluster.ClusterNetworkRegionImpl;
import ch.ethz.matsim.supernetwork.clustering.clusteringAlgorithms.ClusteringNetworkRegionAlgorithm;
import ch.ethz.matsim.supernetwork.clustering.clusteringAlgorithms.AgglomerativeHierarchical.HierarchicalClusteringActivities;
import ch.ethz.matsim.supernetwork.clustering.clustersContainer.KDNode;
import ch.ethz.matsim.supernetwork.clustering.clustersContainer.KDTreeClustersContainer;



/**
 * @author stefanopenazzi
 *
 */
public class ClusteringActivities {

	public ClusteringActivities(Scenario scenario,String outputPath,double cut) {
		
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
			    		 
			    			 ((ClusterNetworkRegionImpl)kdn.getCluster()).addRadius(Math.sqrt(dist));
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
		
		List<Cluster> clusters = new ArrayList();
		
		for(ClusterNetworkRegionImpl cnr: regions) {
			
			clusters.add(cnr);
			
		}
		csvStatRegions(outputPath,clusters);
		
	}
	
    public void stat(String outputPath,List<Cluster> clusters) {
		
		int nAct = 0;
		
		for(Cluster r: clusters) {
			nAct += r.getActivities().size();
		}
		
		int[][] actFA = actFreqAnalysis(10,clusters);
	    double[][] distFA = distFreqAnalysis(100,clusters);
	    double[][] areaFA = areaFreqAnalysis(10000,clusters);
	    double[] subnetRadius = subnetworkRadius(clusters);
	    int[][] snrFA = subnetworkRadiusFreqAnalysis(5000,clusters);
	    
	    File file = new File(outputPath);
	    FileWriter fr = null;
        BufferedWriter br = null;
        
        String results = "";
        results += "Tot number of regions: " + clusters.size() +" \n";
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
        for(int i =0;i<clusters.size();++i) {
        	
        	results += " " + i + " -> " + subnetRadius[i] + "\n";
        	
        }
        results += " subnetworkRadiusFreqAnalysis \n";
        for(int i =0;i<clusters.size();++i) {
        	results += " " + i + " -> ";
        	for(int j=0; j< snrFA[0].length;++j) {
        		results +=  snrFA[i][j] + " - ";
        	}
        	results += "\n";
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
    
    public void csvStatRegions(String outputPath,List<Cluster> clusters) {
		
    	File file = null;// new File(outputPath);
    	FileWriter fr = null;
        BufferedWriter br = null;
        String results = "";
        
    	// general stat
    	int nAct = 0;
		for(Cluster r: clusters) {
			nAct += r.getActivities().size();
		}
        results += "Tot number of regions: " + clusters.size() +" \n";
        results += "Tot number of activities: " + nAct +" \n";
        try{
        	file = new File(outputPath + "/generalStat.txt");
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
        results = "";
        
	    //activities freq analysis
        int[][] actFA = actFreqAnalysis(10,clusters);
        results += "range;n act \n";
        for(int i =0;i<actFA.length;++i) {
        	
        	results += "("+ actFA[i][0]+ "-"+ actFA[i][1]+ ");"+ actFA[i][2] + "\n";
        	
        }
        try{
        	file = new File(outputPath + "/activitiesFreqAnalysis.txt");
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
        results = "";
	    
        //distances freq analysis
        double[][] distFA = distFreqAnalysis(100,clusters);
        results += "range;n regions;n activities \n";
        for(int i =0;i<distFA.length;++i) {
        	
        	results += "("+ distFA[i][0]+ "-"+ distFA[i][1]+ ");"+ distFA[i][2] + ";"+ distFA[i][3] +"\n";
        	
        }
        try{
        	file = new File(outputPath + "/distancesFreqAnalysis.txt");
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
        results = "";
        
        //areas freq analysis
        double[][] areaFA = areaFreqAnalysis(10000,clusters);
        results += "range;area \n";
        for(int i =0;i<areaFA.length;++i) {
        	
        	results += "("+ areaFA[i][0]+ "-"+ areaFA[i][1]+ ");"+ areaFA[i][2] + "\n";
        	
        }
        try{
        	file = new File(outputPath + "/areasFreqAnalysis.txt");
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
        results = "";
        
        //subnetwork radius
        double[] subnetRadius = subnetworkRadius(clusters);
        results += "region id;max radius \n";
        for(int i =0;i<clusters.size();++i) {
        	
        	results += i + ";" + subnetRadius[i] + "\n";
        	
        }
        try{
        	file = new File(outputPath + "/maxRadiusSubnetwork.txt");
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
        results = "";
        
        //subnetwork radius freq analysis
        double rangeRadius = 5000;
	    int[][] snrFA = subnetworkRadiusFreqAnalysis(rangeRadius,clusters);
        results += "region id; \n";
        for(int j=0; j< snrFA[0].length;++j) {
    		results += j*rangeRadius + "-" + (j+1)*rangeRadius + ";" ;
    	}
        results += "\n";
        for(int i =0;i<clusters.size();++i) {
        	results += i + ";";
        	for(int j=0; j< snrFA[0].length;++j) {
        		results +=  snrFA[i][j] + ";";
        	}
        	results += "\n";
        }
        
        try{
            fr = new FileWriter(outputPath + "/subnetworkRadiusFreqAnalysis");
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
        results = "";
        
        //regions data
        results += "region id;n activities;area;coordX;coordY \n";
        for(int i =0;i<clusters.size();++i) {
        	results += i + ";"+ clusters.get(i).getActivities().size()+";"+((ClusterNetworkRegionImpl)clusters.get(i)).getArea()+";"+clusters.get(i).getCentroid().getX()+";"+clusters.get(i).getCentroid().getY()+"\n";
        }
        try{
            fr = new FileWriter(outputPath + "/clusterAnalysis");
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
        results = "";
    }
    
   public void csvStat(String outputPath,List<Cluster> clusters) {
		
    	File file = null;// new File(outputPath);
    	FileWriter fr = null;
        BufferedWriter br = null;
        String results = "";
        
    	// general stat
    	int nAct = 0;
		for(Cluster r: clusters) {
			nAct += r.getActivities().size();
		}
        results += "Tot number of clusters: " + clusters.size() +" \n";
        results += "Tot number of activities: " + nAct +" \n";
        try{
        	file = new File(outputPath + "/generalStat.txt");
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
        results = "";
        
	    //activities freq analysis
        int[][] actFA = actFreqAnalysis(10,clusters);
        results += "range;n act \n";
        for(int i =0;i<actFA.length;++i) {
        	
        	results += "("+ actFA[i][0]+ "-"+ actFA[i][1]+ ");"+ actFA[i][2] + "\n";
        	
        }
        try{
        	file = new File(outputPath + "/activitiesFreqAnalysis.txt");
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
        results = "";
	    
        //distances freq analysis
        double[][] distFA = distFreqAnalysis(100,clusters);
        results += "range;n clusters;n activities \n";
        for(int i =0;i<distFA.length;++i) {
        	
        	results += "("+ distFA[i][0]+ "-"+ distFA[i][1]+ ");"+ distFA[i][2] + ";"+ distFA[i][3] +"\n";
        	
        }
        try{
        	file = new File(outputPath + "/distancesFreqAnalysis.txt");
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
        results = "";
        
        //subnetwork radius
        double[] subnetRadius = subnetworkRadius(clusters);
        results += "region id;max radius \n";
        for(int i =0;i<clusters.size();++i) {
        	
        	results += i + ";" + subnetRadius[i] + "\n";
        	
        }
        try{
        	file = new File(outputPath + "/maxRadiusSubnetwork.txt");
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
        results = "";
        
        //subnetwork radius freq analysis
        double rangeRadius = 5000;
	    int[][] snrFA = subnetworkRadiusFreqAnalysis(rangeRadius,clusters);
        results += "region id; \n";
        for(int j=0; j< snrFA[0].length;++j) {
    		results += j*rangeRadius + "-" + (j+1)*rangeRadius + ";" ;
    	}
        results += "\n";
        for(int i =0;i<clusters.size();++i) {
        	results += i + ";";
        	for(int j=0; j< snrFA[0].length;++j) {
        		results +=  snrFA[i][j] + ";";
        	}
        	results += "\n";
        }
        
        try{
            fr = new FileWriter(outputPath + "/subnetworkRadiusFreqAnalysis");
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
        results = "";
        
        //regions data
        results += "cluster id;n activities;coordX;coordY \n";
        for(int i =0;i<clusters.size();++i) {
        	results += i + ";"+ clusters.get(i).getActivities().size()+";"+clusters.get(i).getCentroid().getX()+";"+clusters.get(i).getCentroid().getY()+"\n";
        }
        try{
            fr = new FileWriter(outputPath + "/clusterAnalysis");
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
        results = "";
    }
   
   public int[][] actFreqAnalysis(int range,List<Cluster> clusters) {
	  
	   Collections.sort(clusters, new Comparator<Cluster>(){
			@Override
			  public int compare(Cluster u1, Cluster u2) {
				int c;
				
			    c = u1.getActivities().size() == u2.getActivities().size()? 0 : u1.getActivities().size() < u2.getActivities().size() ? -1:1; 
			    return c;
			  }
		});
	   
	   int maxAct = clusters.get(clusters.size()-1).getActivities().size();
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
	   
	   for(Cluster r:clusters) {
		  int pos = (int) (Math.ceil(r.getActivities().size()/range));
		  res[pos][2] =  res[pos][2] + 1; 
	   }
	   
	   int regWithAct =0;
	   for(int i = 0;i<classes;i++) {
		   regWithAct += res[i][2];
	   }
	   return res;
   }
   
   public double[][] distFreqAnalysis(double range,List<Cluster> clusters) {
		  
	   if (range == 0 || range> 4000) return null;
	   int classes = (int) (Math.ceil(4000/range) + 1);
	   double[][] res = new double[classes][4];
	   for(int i = 0;i<classes;i++) {
		   int j = i+1;
		   res[i][0] = j*range - (range-0.9);
		   res[i][1] = j*range;
	   }
	   for(Cluster r:clusters) {
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
	   return res;
   }
   
   public double[][] areaFreqAnalysis(double range,List<Cluster> clusters) {
		  
	   if (range == 0 || range> 1000000) return null;
	   int classes = (int) (Math.ceil(1000000/range) + 1);
	   double[][] res = new double[classes][3];
	   for(int i = 0;i<classes;i++) {
		   int j = i+1;
		   res[i][0] = j*range - (range-0.9);
		   res[i][1] = j*range;
	   }
	   for(Cluster r:clusters) {
		   if(r.getActivities().size()>0) {
			   int pos = (int) (Math.ceil(((ClusterNetworkRegionImpl)r).getArea()/range));
			   if(pos>classes-1) {
				   res[classes-1][2] =  res[classes-1][2] + 1; 
			   }
			   else {
				   res[pos][2] =  res[pos][2] + 1; 
			   }
		   }
	    }
	   return res;
   }
   
   public double[] subnetworkRadius(List<Cluster> clusters) {
	   
	   double[] res = new double[clusters.size()];
	   int k = 0;
	   for(Cluster r:clusters) {
		   res[k] = Math.sqrt(r.getNetworkRadius());
		   k++;
	   }
	   return res;
   }
   
   public int[][] subnetworkRadiusFreqAnalysis(double range,List<Cluster> clusters) {
	   
	   int classes = (int) (Math.ceil(100000/range) + 1);
	   int[][] res = new int[clusters.size()][classes];
	   
	   int k = 0;
	   for(Cluster r:clusters) {
		   for(Double rad: r.getNetworkRadiusArray()) {
			   int pos = (int) (rad/range);
			   if(pos>classes-1) {
				   res[k][classes-1] =  res[k][classes-1] + 1; 
			   }
			   else {
				   res[k][pos] =  res[k][pos] + 1; 
			   }
		   }
		   ++k;
	   }
	   return res;
   }

}
