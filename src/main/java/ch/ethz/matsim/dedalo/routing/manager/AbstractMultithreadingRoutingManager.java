/**
 * 
 */
package ch.ethz.matsim.dedalo.routing.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import org.apache.log4j.Logger;
import org.javatuples.Pair;
import org.matsim.core.router.util.LeastCostPathCalculator.Path;
import com.google.common.collect.Iterables;

import ch.ethz.matsim.dedalo.routing.manager.updatealgorithms.UpdateAlgorithmOutput;
import ch.ethz.matsim.dedalo.routing.router.cluster.ClusterRoutingModule;

/**
 * @author stefanopenazzi
 *
 */
public abstract class AbstractMultithreadingRoutingManager implements RoutingManager {

	private int count = 0;
	private final int numOfThreads;
//
//	private final AtomicReference<Throwable> hadException = new AtomicReference<>(null);
//	private final ExceptionHandler exceptionHandler = new ExceptionHandler(this.hadException);
	
	private List<Future<List<Pair<PathTimeKey,Path>>>> resultList;
	private List<List<UpdateAlgorithmOutput>> uaoLists;
	private List<RoutingThread> routingThreadList;

	static final private Logger log = Logger.getLogger( AbstractMultithreadingRoutingManager.class);

	public  AbstractMultithreadingRoutingManager(int numOfThreads) {
		this.numOfThreads = numOfThreads;
	}
	
	@Override
	public final void addRequest(final UpdateAlgorithmOutput uao) {
		this.uaoLists.get(this.count % this.numOfThreads).add(uao);
		this.count++;
	}
	
	public abstract ClusterRoutingModule  getSupernetworkRoutingModule(); 
	
	@Override
	public List<Pair<PathTimeKey,Path>> run(){
		
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(this.numOfThreads);
		List<Pair<PathTimeKey,Path>> res = new ArrayList<Pair<PathTimeKey,Path>>();
		for(int j = 0;j< numOfThreads;j++) {
			routingThreadList.get(j).setUpdateList(uaoLists.get(j));
		}
		long startTime = System.nanoTime();
		try {
			resultList = executor.invokeAll(routingThreadList);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		long endTime = System.nanoTime();
		long dur = (endTime - startTime)/1000000;
		for(Future<List<Pair<PathTimeKey,Path>>> future : resultList)
        {
			 try
             {
				 res.addAll(future.get());
				 //future.isDone();
             } 
             catch (InterruptedException | ExecutionException e) 
             {
                 e.printStackTrace();
             }
        }
          //shut down the executor service now
		  this.count = 0;
          executor.shutdown();
          return res;
	}

	@Override
	public void init() {
		
//		if (this.threads != null) {
//			throw new RuntimeException("threads are already initialized");
//		}
//
		//Counter counter = null;
		this.uaoLists = new ArrayList<List<UpdateAlgorithmOutput>>();
		for(int j = 0;j<this.numOfThreads;++j) {
			this.uaoLists.add(new ArrayList<UpdateAlgorithmOutput>());
		}
		routingThreadList = new ArrayList<>();
		for(int j = 0;j<this.numOfThreads;++j) {
			this.routingThreadList.add(new RoutingThread(getSupernetworkRoutingModule()));
		}
		
	    resultList = new ArrayList<Future<List<Pair<PathTimeKey,Path>>>>();
		
//		this.hadException.set(null);
//		this.threads = new Thread[this.numOfThreads];
//		this.routingThreads = new RoutingThread[this.numOfThreads];
//
//		Counter counter = null;
//		// setup thread
//		for (int i = 0; i < this.numOfThreads; i++) {
//			SupernetworkRoutingModule sr = getSupernetworkRoutingModule();
//			if (i == 0) {
//				counter = new Counter("[ ] handled plan # ");
//			}
//			RoutingThread routingThread = new RoutingThread(counter, sr);
//			Thread thread = new Thread(routingThread, this.name + "." + i);
//			thread.setUncaughtExceptionHandler(this.exceptionHandler);
//			this.threads[i] = thread;
//			this.routingThreads[i] = routingThread;
//		}
		
		
	}

	/* package (for a test) */ final int getNumOfThreads() {
		return numOfThreads;
	}

	private class RoutingThread implements Callable<List<Pair<PathTimeKey,Path>>> {

		private List<UpdateAlgorithmOutput> updateList = new ArrayList<>();
		private ClusterRoutingModule superNetworkRoutingModule;
		

		public RoutingThread(ClusterRoutingModule superNetworkRoutingModule) {
			this.superNetworkRoutingModule = superNetworkRoutingModule;
			
		}

		public void setUpdateList(List<UpdateAlgorithmOutput> updateList) {
			this.updateList = updateList;
		}

		@Override
		public List<Pair<PathTimeKey,Path>> call() throws Exception {
			
			List<Pair<PathTimeKey,Path>> res = new ArrayList<Pair<PathTimeKey,Path>> ();
			for (UpdateAlgorithmOutput uao : this.updateList) {
				Path [] paths= this.superNetworkRoutingModule.calcTree(uao.getSupernode().getNode(),uao.getToNodes(),uao.getTime());
				for(Path p: paths) {
					if(p != null && p.links.size()>1) {
						PathTimeKey d = new PathTimeKey(p.nodes.get(0),uao.getTime(),Iterables.getLast(p.nodes));
						res.add(new Pair<PathTimeKey,Path>(d,p));
					}
				}
				//System.out.println("Result : "+count );
			}
			
			return res;
		}
	}
}
