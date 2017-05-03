package com.achahal.jwt

/**
  * Created by achahal on 04/05/2017.
  */
object TestConfigurationSettings {
  val validTestJwtToken = "eyJraWQiOiIxMjMiLCJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.CnsKICAiaXNzIjogImFjY291bnRzLmF1dG9zY291dDI0LmNvbSIsCiAgImF1ZCI6ICJyZWx5aW5ncGFydHkiLAogICJuYmYiOiAxNDg5NDE0ODQ0LAogICJleHAiOiAxNDg5NDE0OTA0LAogICJzdWIiOiAiMjI0MjIxMzJfMjI1NTUxODEiLAogICJjdHkiOiAiQiIsCiAgImlhdCI6ICIxNDg5NDE0ODQ1Igp9CiAgICAgIA.HvuUrWifL7KXg4KwUqMlJSUyMt2AaDK1HEWwGC7WxueUsUH8WanoEJjr81EkraeJcLGWM_izL-H3TgSoAe6MXUjDo79WbhZ0l6WqojKS9BZpkDYIiWCIBZ7V2kXPJwTSKO-CufuTgBXG8FrCaZp7visHNkF2Jz-YmP-wyrrA8JU"
  val publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCc+DZC3GNBSS/1IT++sfIsozgJfo2qR32TJIG8mSY7Ha+CKvtQu9QaZZ/JhuPou1U01n3aw6dnfePFFoO2dNenfbsi95FYwDwWZdcWX4zrVbtz+d4zul33aLl25P7Mr8xmNysQ5ziBUYmnXWNfTlmXXdoJ7jPDl9h4WTnQ+EAK9wIDAQAB"
  val privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJz4NkLcY0FJL/UhP76x8iyjOAl+japHfZMkgbyZJjsdr4Iq+1C71Bpln8mG4+i7VTTWfdrDp2d948UWg7Z016d9uyL3kVjAPBZl1xZfjOtVu3P53jO6XfdouXbk/syvzGY3KxDnOIFRiaddY19OWZdd2gnuM8OX2HhZOdD4QAr3AgMBAAECgYBPPFRXp9xKhmE5AhGldzniInoVANcOD4xqDFGZHE3aJYAE2yGFfWrx8D6aggbl4jp+s49QMNd+PGQPhu/x2EVLLokj7k5Gq3mVRFeMxv/td5o3eIsTcMvJlsaxgq2/Cwoc3NvShSgheVnRyXJpHm2bEVhWqBldNGCxJbZ+Xd2aEQJBAPUJc27KYxQbA2Idta0ropC9d/XN6fdlTF6UnV5rmmCKMVpsBGhflUAkwbeH6Ce79ubfVoewAvJlZ6SEAqWdWgsCQQCj/hM1XyZhJIUUD6rHi/Mr6iKP+/OuZLFFuh7F9TJ1UoUKHl/kTrnRspDXXZ671h7i8wY6c80rPw1AX+jgxhJFAkEAwvcnGoL7/HZV5c5/zG6IpaOrfC+/tjGqZyLWZ9cz+RZbmHeTtjw/M89LNy1y4ZKkLCSN495/KcDXi0XiGUYu6wJANcOgrIjLXmHIt3EDfnRlfuo99pKVq4EblU6VtDTVHB23vD+FFKayyQsP7WumFNL4QVOHoTZB94GuaIKJKNgHOQJAUhoN2S2lV0LCWFpYHS2pl0L9dPhEpGwSrYKQffPo2ZMA8EcZxf2bE/QReFunUeqpBToimhxRMdgXcT1vuDKTLQ=="


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
