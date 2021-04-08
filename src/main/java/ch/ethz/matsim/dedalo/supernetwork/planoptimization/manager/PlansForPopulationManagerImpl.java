/**
 * 
 */
package ch.ethz.matsim.dedalo.supernetwork.planoptimization.manager;

import java.text.DecimalFormat;
import java.util.List;

import org.apache.log4j.Logger;
import org.matsim.api.core.v01.population.Activity;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.Plan;
import org.matsim.api.core.v01.population.PlanElement;
import org.matsim.api.core.v01.population.Population;
import org.matsim.core.controler.Controler;

import com.google.inject.Inject;

import ch.ethz.matsim.dedalo.supernetwork.planoptimization.containers.PlansForPopulationContainer;


/**
 * @author stefanopenazzi
 *
 */
public class PlansForPopulationManagerImpl implements PlansForPopulationManager{
	
	private final PlansForPopulationContainer plansForPopulationContainer;
	private final Population population;
	private final PlanManagerFactory planManagerFactory;
	private static final Logger log = Logger.getLogger(PlansForPopulationManagerImpl.class);
	private int count = 0;
	
	@Inject
	public PlansForPopulationManagerImpl(PlansForPopulationContainer plansForPopulationContainer,
			Population population,PlanManagerFactory planManagerFactory) {
		this.plansForPopulationContainer = plansForPopulationContainer;
		this.population = population;
		this.planManagerFactory = planManagerFactory;
	}

	@Override
	public void populationNewPlans() {
		//TODO
		//System.out.println("");
		long start = System.nanoTime();
		int noPath = 0;
		for (Person person : this.population.getPersons().values()) {
			//it is probably better to have this in the replanning strategy but the scoring becomes useless
			Plan check = plansForPopulationContainer.getPlanManagerForAgent(person.getId()).getNewPlan();
			/*
			 * if(check) { noPath++; }
			 */
		}
		long finish = System.nanoTime();
		long timeElapsed = finish - start;
		//System.out.printf(" exe time : %f", ((double)timeElapsed/1000000));
		//System.out.println("");
	}

	@Override
	public void init() {
		for (Person person : this.population.getPersons().values()) {
			PlanManager planManager = this.planManagerFactory.createPlanManager(person);
			this.plansForPopulationContainer.addPlanManager(person.getId(), planManager);
		}
	//System.out.println();
	}

	@Override
	public Plan personNewPlan(Person person) {
		//long start = System.nanoTime();
		
		PlanManager pm = plansForPopulationContainer.getPlanManagerForAgent(person.getId());
		if(pm == null) return null;
		Plan p = pm.getNewPlan();
//		
//		long fin = System.nanoTime();
//		long timeElapsed = fin - start;
//		
//		int nact = 0;
//		for (PlanElement pe: person.getSelectedPlan().getPlanElements()) {
//			if(pe instanceof Activity) {
//				if(!((Activity)pe).getType().contains("inter")) {
//				   nact++;
//				}
//			}
//		}
//		log.warn("Time to solve the graph agent id;"+ person.getId().toString() +";" + ((double)timeElapsed/1000000)+";"+nact );
		//System.out.printf(" exe time : %f", ((double)timeElapsed/1000000));
		//System.out.println("");
		return p;
	
	}

}
