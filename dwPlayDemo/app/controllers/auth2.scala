package controllers

import com.mohiva.play.silhouette.api.{Environment, Silhouette}
import com.mohiva.play.silhouette.impl.authenticators.CookieAuthenticator
import models.User
import play.api.i18n.MessagesApi

/**
  * Created by achahal on 01/03/2017.
  */
class auth2  extends Silhouette[User,CookieAuthenticator]{
  override protected def env: Environment[User, CookieAuthenticator] = ???

  override def messagesApi: MessagesApi = ???
}
