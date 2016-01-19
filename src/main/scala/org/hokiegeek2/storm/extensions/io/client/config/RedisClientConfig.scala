package org.hokiegeek2.storm.extensions.io.client.config

import java.util.{Map => JMap}

class RedisClientConfig(host : String,port : Int, poolParams : JMap[String,String]) extends ClientConfig(host,port) {
  
  var testOnCreate  : Boolean = false
  
  var testOnBorrow  : Boolean = false
  
  var testOnReturn  : Boolean = false
  
  var testWhileIdle : Boolean = false
  
  var maxTotal : Int = 1
  
  private def setPoolParams(poolParams : JMap[String,String]) = {

  }
}