package util

import java.io.File
import java.net.URI

import scala.util.matching.Regex

object uri {

  implicit class RichURI(uri: URI) {

    private val file = new File(uri)

    def name: String = file.getName

    def isFile = file.isFile

    def isDirectory = file.isDirectory

    def exists: Boolean = file.exists()

    def nameWithoutSuffix: String = file.getName.substring(0, file.getName.lastIndexOf('.'))

    def suffix: Option[String] = name.split("\\.").lift(1)

    def asFile: File = file

    def list: List[URI] = file.listFiles().map(_.toURI).toList

    def listDirectories: List[URI] = list.filter(_.asFile.isDirectory)

    def listFiles: List[URI] = list.filter(_.asFile.isFile)

    def file(nameFilter: String => Boolean): Option[URI] = listFiles.find(uri => nameFilter(uri.name))

    def directory(nameFilter: String => Boolean): Option[URI] = listDirectories.find(uri => nameFilter(uri.name))

    private def listWithFilter(filter: (File) => Boolean): List[URI] = file.listFiles().withFilter(filter).map(_.toURI).toList

    def list(nameFilter: String => Boolean): List[URI] = listWithFilter((f: File) => nameFilter(f.getName))

    def list(regexFilter: Regex): List[URI] = listWithFilter((f: File) => regexFilter.findFirstMatchIn(f.getName).isDefined)

  }

}
