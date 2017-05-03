package com.example

import java.security.interfaces.{RSAPrivateKey, RSAPublicKey}
import java.security.{KeyFactory, PrivateKey}
import java.security.spec.{PKCS8EncodedKeySpec, X509EncodedKeySpec}
import java.util
import java.util.Base64


import scala.util.{Failure, Try}

object RsaCipher {
  def decodePublicKey(publicKey: String): Try[RSAPublicKey] = Try{
    val data = Base64.getDecoder.decode(publicKey)
    val spec = new X509EncodedKeySpec(data)
    val fact = KeyFactory.getInstance("RSA")
    fact.generatePublic(spec).asInstanceOf[RSAPublicKey]
  } recoverWith {
    case ex => Failure(new Exception("Error while decoding Public Key string to object", ex))
  }
  def encodePublicKey(publicKey: RSAPublicKey): Try[String] = Try{
    val fact = KeyFactory.getInstance("RSA")
    val spec = fact.getKeySpec(publicKey, classOf[X509EncodedKeySpec])
    Base64.getEncoder().encodeToString(spec.getEncoded)
  } recoverWith {
    case ex => Failure(new Exception("Error while encoding Public Key object to string", ex))
  }

  def loadPrivateKey(key64: String): RSAPrivateKey = {
    val clear = Base64.getDecoder.decode(key64)
    val keySpec = new PKCS8EncodedKeySpec(clear)
    val fact = KeyFactory.getInstance("RSA")
    val priv = fact.generatePrivate(keySpec)
    util.Arrays.fill(clear, 0.toByte)
    priv.asInstanceOf[RSAPrivateKey]
  }

  def savePrivateKey(privateKey: RSAPrivateKey) = {
    val fact = KeyFactory.getInstance("RSA")
    val spec = fact.getKeySpec(privateKey, classOf[PKCS8EncodedKeySpec])
    val packed = spec.getEncoded
    val key64 = Base64.getEncoder().encodeToString(packed)
    util.Arrays.fill(packed, 0.toByte)
    key64
  }
}
