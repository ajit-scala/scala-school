package com.achahal.jwt

import java.security.InvalidKeyException
import java.security.interfaces.RSAPublicKey

import com.achahal.jwt.TestConfigurationSettings.{payLoad, privateKey}
import com.nimbusds.jose._
import com.nimbusds.jose.crypto.RSASSASigner

import scala.util.{Failure, Try}

/**
  * Created by achahal on 04/05/2017.
  */
object Jwt extends RsaCipher {
  def createJwtToken(): String = {
    val signer = new RSASSASigner(decodePrivateKey(privateKey).get)
    val jwsObject = new JWSObject(
      new JWSHeader.Builder(JWSAlgorithm.RS256).`type`(JOSEObjectType.JWT)/* .x509CertThumbprint()*/ .build(),
      new Payload(payLoad))
    jwsObject.sign(signer)
    val jwtString = jwsObject.serialize()

    jwtString
  }


  def verifyJwtToken(idToken: String, publicKey: String): Try[Boolean] = Try {
    val publicKeyObject: RSAPublicKey = decodePublicKey(publicKey).get
    import com.nimbusds.jose.crypto.RSASSAVerifier

    val verifier = new RSASSAVerifier(publicKeyObject)

    val jwObject = JWSObject.parse(idToken)
    jwObject.verify(verifier) match {
      case true => true
      case _ => throw new InvalidKeyException("Can not verify jwt token")
    }

  } recoverWith {
    case ex: InvalidKeyException => Failure(ex)
    case ex: Exception => Failure(new Exception("Error while verifying jwt token string", ex))
  }
}
