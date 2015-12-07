package org.hokiegeek2.storm.extensions.scheduler

import backtype.storm.scheduler.IScheduler
import java.util.Collection
import backtype.storm.scheduler.Topologies
import backtype.storm.scheduler.SupervisorDetails
import backtype.storm.scheduler.WorkerSlot
import java.util.{List => JList, ArrayList => AList}
import backtype.storm.scheduler.TopologyDetails
import backtype.storm.scheduler.ExecutorDetails
import backtype.storm.scheduler.Cluster
import org.slf4j.LoggerFactory
import scala.collection.JavaConversions._
import util.control.Breaks._

class BaseCustomScheduler extends IScheduler {
   private final var log = LoggerFactory.getLogger(classOf[BaseCustomScheduler]);
  
    override def schedule(topologies : Topologies, cluster : Cluster) = {     
      for (topology <- topologies.getTopologies) {
        scheduleTopology(topology,cluster)
      }
    }

    protected def getSupervisors(cluster : Cluster) : Collection[SupervisorDetails] = {
      cluster.getSupervisors().values();
    }
    
    protected def getAvailableSlots(cluster : Cluster, supervisor : SupervisorDetails) : java.util.List[WorkerSlot] = {
      cluster.getAvailableSlots(supervisor)
    }
    
    protected def scheduleTopology(topology : TopologyDetails, cluster : Cluster) = {    
      if (cluster.needsScheduling(topology)) {
        //Get the topology executors that need to be assigned
        val componentExecutors : java.util.Map[String, java.util.List[ExecutorDetails]]  = cluster.getNeedsSchedulingComponentToExecutors(topology);
       
        val componentsToSchedule = componentExecutors.keySet().iterator
        
        for (supervisor <- getSupervisors(cluster)) {
          val slots : JList[WorkerSlot] = getAvailableSlots(cluster,supervisor)
                 
          breakable {for (slot <- slots) {  
            if (!componentsToSchedule.hasNext) break              
              val executors = componentsToSchedule.next
              cluster.assign(slot,topology.getId,componentExecutors.get(executors))
            }
          }
        }       
      }   
    }
    
    def prepare(conf : java.util.Map[_,_]) = {}
}