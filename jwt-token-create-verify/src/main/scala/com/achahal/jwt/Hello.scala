package com.achahal.jwt

import com.achahal.jwt.TestConfigurationSettings._
import com.nimbusds.jose._

object Hello {
  def main(args: Array[String]): Unit = {
    println("-------------------Generating JWT token--------------\n")
    val token = Jwt.createJwtToken()
    println(s"JWT token created: $token")
    println
    println(s"Verify token result: ${Jwt.verifyJwtToken(token, publicKey)}")
    println("-------------------Parsing JWT token--------------\n")
    println(s"header:  ${JWSObject.parse(token).getHeader}")
    println
    println(s"payload:  ${JWSObject.parse(token).getPayload}")
    println
    println(s"signature:  ${JWSObject.parse(token).getSignature}")
    println
    println(JWSObject.parse(token).getPayload.toJSONObject.get("iss"))
  }
}




