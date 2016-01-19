package org.hokiegeek2.storm.extensions.io.client

import org.hokiegeek2.storm.extensions.io.client.config.ClientConfig
import org.hokiegeek2.storm.extensions.io.client.config.RedisClientConfig
import redis.clients.jedis.JedisPool
import redis.clients.jedis.JedisPoolConfig
import redis.clients.jedis.Jedis

class RedisClient(config : ClientConfig) extends IOClient(config) {
  
  val pool : JedisPool = setJedisPool(config.asInstanceOf[RedisClientConfig])
  
  private def setJedisPool(config : RedisClientConfig) : JedisPool = {
    val pConfig = new JedisPoolConfig
    pConfig.setTestOnBorrow(config.testOnBorrow)
    pConfig.setTestOnCreate(config.testOnCreate)
    pConfig.setTestWhileIdle(config.testWhileIdle)
    pConfig.setTestOnReturn(config.testOnReturn)
    pConfig.setMaxTotal(config.maxTotal)
    
    new JedisPool(pConfig,config.getHost,config.getPort)
  }
  
  override def getConnection : Jedis = {
    pool.getResource
  }
  
  override def returnConnection(conn : Any) : Unit = {
    pool.returnResourceObject(conn.asInstanceOf[Jedis])
  }
}

object RedisClient {
  def apply(config : ClientConfig) : RedisClient = {
    new RedisClient(config)
  }
}