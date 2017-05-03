package com.achahal.jwt

import java.security.interfaces.{RSAPrivateKey, RSAPublicKey}
import java.security.spec.{PKCS8EncodedKeySpec, X509EncodedKeySpec}
import java.security.{KeyFactory, KeyPairGenerator}
import java.util
import java.util.Base64

import exceptions.JwtException
import org.apache.commons.codec.digest.DigestUtils

import scala.util.{Failure, Try}

trait RsaCipher {
  def decodePublicKey(publicKey: String): Try[RSAPublicKey] = Try {
    val data = Base64.getDecoder.decode(publicKey)
    val spec = new X509EncodedKeySpec(data)
    val fact = KeyFactory.getInstance("RSA")
    fact.generatePublic(spec).asInstanceOf[RSAPublicKey]
  } recoverWith {
    case ex => Failure(JwtException("Error while decoding Public Key string to object", ex))
  }

  def encodePublicKey(publicKey: RSAPublicKey): Try[String] = Try {
    val fact = KeyFactory.getInstance("RSA")
    val spec = fact.getKeySpec(publicKey, classOf[X509EncodedKeySpec])
    Base64.getEncoder().encodeToString(spec.getEncoded)
  } recoverWith {
    case ex => Failure(JwtException("Error while encoding Public Key object to string", ex))
  }

  def decodePrivateKey(key64: String): Try[RSAPrivateKey] = Try{
    val clear = Base64.getDecoder.decode(key64)
    val keySpec = new PKCS8EncodedKeySpec(clear)
    val fact = KeyFactory.getInstance("RSA")
    val priv = fact.generatePrivate(keySpec)
    util.Arrays.fill(clear, 0.toByte)
    priv.asInstanceOf[RSAPrivateKey]
  } recoverWith {
    case ex => Failure(JwtException("Error while decoding Private Key string to object", ex))
  }

  def encodePrivateKey(privateKey: RSAPrivateKey): Try[String] = Try{
    val fact = KeyFactory.getInstance("RSA")
    val spec = fact.getKeySpec(privateKey, classOf[PKCS8EncodedKeySpec])
    val packed = spec.getEncoded
    val key64 = Base64.getEncoder().encodeToString(packed)
    util.Arrays.fill(packed, 0.toByte)
    key64
  } recoverWith {
    case ex => Failure(JwtException("Error while encoding Private Key object to string", ex))
  }

  /**
    * Included in header of jwt, client can uniquely identify certificate with this thumbprint in his certificate store
    * public key or certificate is shared with client
    * @param publicKey
    * @return
    */
  def getCertificateThumbprint(publicKey: RSAPublicKey): String = {
    DigestUtils.sha1Hex(publicKey.getEncoded())
  }

  /**
    * Is used to generate new keys if needed.
    * @return
    */
  def generatePublicPrivateKeys: (RSAPrivateKey, RSAPublicKey) = {
    val keyGenerator = KeyPairGenerator.getInstance("RSA")
    keyGenerator.initialize(1024)
    val kp = keyGenerator.genKeyPair()
    val publicKey = kp.getPublic.asInstanceOf[RSAPublicKey]
    val privateKey = kp.getPrivate.asInstanceOf[RSAPrivateKey]

    (privateKey, publicKey)
  }
}
