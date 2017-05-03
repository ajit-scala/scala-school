package com.achahal.jwt

import org.scalatest.{Matchers, TryValues, WordSpec}

/**
  * Created by achahal on 03/05/2017.
  */
class RsaCipherSpec extends WordSpec with Matchers with RsaCipher with TryValues{

  val publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDINS0YlpY5zNFg4r+wHgd6WqMKcaaUmTRk0+T0kZeXoF+vVloxp5Ezh2vXzTOUHRVwNZ1lDyIVHpcmahl6I9E6A7a8+P9Nr5DQeFqFSy5bNKIcPh4v5BbxyrO59cAUlVAhXDMbgH61m7x/YRKi5ZMNmj9yz7F3Y1QvJFPyRJzOQwIDAQAB"

  "RsaCipherSpec" should {

    "decode string PublicKey to object" in {
      val  publicKeyObject = decodePublicKey(publicKey)
      publicKeyObject.success.value.getAlgorithm shouldBe "RSA"
    }

    "encode PublicKey object to string" in {
      val  publicKeyObject = decodePublicKey(publicKey).success.value
      encodePublicKey(publicKeyObject).success.value shouldBe publicKey
    }

    "decode string Private Key to object" in {
      import com.nimbusds.jose.util.X509CertUtils
      import java.security.cert.X509Certificate

      val (pr, pb) = generatePublicPrivateKeys
      val cert = X509CertUtils.parse(encodePrivateKey(pr).get)
      println("certnn---: " + cert)
    }
    "encode PrivateKey object to string" in {

    }

    "getCertificateThumbprint" in {
      val publicKeyObject = decodePublicKey(publicKey).success.value
      getCertificateThumbprint(publicKeyObject) shouldBe "de3eb4056f5a8b873e45ae6c70c9ee93680ab0dd"
    }
  }
}
