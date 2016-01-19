package org.hokiegeek2.storm.extensions.io.client.config

class ClientConfig(host : String,port : Int) { 
  
  def getHost : String = {
    host
  }
  
  def getPort : Int = {
    port
  }
}