package com.example

import java.security.{InvalidKeyException, KeyPairGenerator}
import java.security.interfaces.RSAPublicKey

import com.nimbusds.jose._
import com.nimbusds.jose.crypto.{RSASSASigner, RSASSAVerifier}

import scala.util.{Failure, Success, Try}

import JwtTestHelper._

object Hello {
  def main(args: Array[String]): Unit = {
    println("Hello, world!")
    val token = Jwt.createJwtToken()
    println(s"JWT token created: $token")
    println(s"Verfy token result: ${Jwt.verifyJwtToken(token, testPublicKey)}")
    println(s"header:  ${JWSObject.parse(token).getHeader}")
    println(s"payload:  ${JWSObject.parse(token).getPayload}")
    println(s"signature:  ${JWSObject.parse(token).getSignature}")
  }
}


object Jwt {
  def createJwtToken(): String = {
    //val keyGenerator = KeyPairGenerator.getInstance("RSA")
    //    keyGenerator.initialize(1024)
    //    val kp = keyGenerator.genKeyPair()
    //    val publicKey = kp.getPublic.asInstanceOf[RSAPublicKey]
    //    val privateKey = kp.getPrivate.asInstanceOf[RSAPrivateKey]
    val signer = new RSASSASigner(RsaCipher.loadPrivateKey(testPrivateKey))
    val jwsObject = new JWSObject(
      new JWSHeader.Builder(JWSAlgorithm.RS256).`type`(JOSEObjectType.JWT) /*.x509CertThumbprint()*/ .build(),
      new Payload(payLoad))
    jwsObject.sign(signer)
    val jwtString = jwsObject.serialize()

    jwtString
  }


  def verifyJwtToken(idToken: String, publicKey: String): Try[Boolean] = Try {
    val publicKeyObject: RSAPublicKey = RsaCipher.decodePublicKey(publicKey).get
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

object JwtTestHelper {
  val validTestJwtToken = "eyJraWQiOiIxMjMiLCJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.CnsKICAiaXNzIjogImFjY291bnRzLmF1dG9zY291dDI0LmNvbSIsCiAgImF1ZCI6ICJyZWx5aW5ncGFydHkiLAogICJuYmYiOiAxNDg5NDE0ODQ0LAogICJleHAiOiAxNDg5NDE0OTA0LAogICJzdWIiOiAiMjI0MjIxMzJfMjI1NTUxODEiLAogICJjdHkiOiAiQiIsCiAgImlhdCI6ICIxNDg5NDE0ODQ1Igp9CiAgICAgIA.HvuUrWifL7KXg4KwUqMlJSUyMt2AaDK1HEWwGC7WxueUsUH8WanoEJjr81EkraeJcLGWM_izL-H3TgSoAe6MXUjDo79WbhZ0l6WqojKS9BZpkDYIiWCIBZ7V2kXPJwTSKO-CufuTgBXG8FrCaZp7visHNkF2Jz-YmP-wyrrA8JU"
  val testPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCc+DZC3GNBSS/1IT++sfIsozgJfo2qR32TJIG8mSY7Ha+CKvtQu9QaZZ/JhuPou1U01n3aw6dnfePFFoO2dNenfbsi95FYwDwWZdcWX4zrVbtz+d4zul33aLl25P7Mr8xmNysQ5ziBUYmnXWNfTlmXXdoJ7jPDl9h4WTnQ+EAK9wIDAQAB"
  val testPrivateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJz4NkLcY0FJL/UhP76x8iyjOAl+japHfZMkgbyZJjsdr4Iq+1C71Bpln8mG4+i7VTTWfdrDp2d948UWg7Z016d9uyL3kVjAPBZl1xZfjOtVu3P53jO6XfdouXbk/syvzGY3KxDnOIFRiaddY19OWZdd2gnuM8OX2HhZOdD4QAr3AgMBAAECgYBPPFRXp9xKhmE5AhGldzniInoVANcOD4xqDFGZHE3aJYAE2yGFfWrx8D6aggbl4jp+s49QMNd+PGQPhu/x2EVLLokj7k5Gq3mVRFeMxv/td5o3eIsTcMvJlsaxgq2/Cwoc3NvShSgheVnRyXJpHm2bEVhWqBldNGCxJbZ+Xd2aEQJBAPUJc27KYxQbA2Idta0ropC9d/XN6fdlTF6UnV5rmmCKMVpsBGhflUAkwbeH6Ce79ubfVoewAvJlZ6SEAqWdWgsCQQCj/hM1XyZhJIUUD6rHi/Mr6iKP+/OuZLFFuh7F9TJ1UoUKHl/kTrnRspDXXZ671h7i8wY6c80rPw1AX+jgxhJFAkEAwvcnGoL7/HZV5c5/zG6IpaOrfC+/tjGqZyLWZ9cz+RZbmHeTtjw/M89LNy1y4ZKkLCSN495/KcDXi0XiGUYu6wJANcOgrIjLXmHIt3EDfnRlfuo99pKVq4EblU6VtDTVHB23vD+FFKayyQsP7WumFNL4QVOHoTZB94GuaIKJKNgHOQJAUhoN2S2lV0LCWFpYHS2pl0L9dPhEpGwSrYKQffPo2ZMA8EcZxf2bE/QReFunUeqpBToimhxRMdgXcT1vuDKTLQ=="


  val payLoad =
    """
      |{
      |  "iss": "identity.ajitchahal.de",
      |  "aud": "relyingparty",
      |  "nbf": 1489414844,
      |  "exp": 1489414904,
      |  "sub": "22422132_22555181",
      |  "tid": "x123",
      |  "iat": "344345324"
      |}
    """.stripMargin
}