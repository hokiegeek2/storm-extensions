package org.hokiegeek2.storm.extensions.io.client

import java.util.{Map => JMap}
import org.hokiegeek2.storm.extensions.io.client.config.ClientConfig

abstract class IOClient(config : ClientConfig) extends Serializable {

  def getConnection : Any;
  
  def returnConnection(conn : Any)
}