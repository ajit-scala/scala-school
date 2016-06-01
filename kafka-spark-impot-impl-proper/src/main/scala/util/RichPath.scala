package util

import java.net.URI
import java.nio.file._
import java.util.concurrent.atomic.{ AtomicInteger, AtomicLong }
import java.util.concurrent.locks.ReentrantLock

import scala.collection.JavaConversions._
import scala.util.matching.Regex

object path {

  implicit class RichPath(path: Path) {

    def name: String = path.getFileName.toString

    def isFile = Files.isRegularFile(path)

    def isDirectory = Files.isDirectory(path)

    def exists: Boolean = Files.exists(path)

    def nameWithoutSuffix: String = name.substring(0, name.lastIndexOf('.'))

    def suffix: Option[String] = name.split("\\.").lift(1)

    def list: List[Path] = Files.list(path).iterator().toList

    def listDirectories: List[Path] = list.filter(Files.isDirectory(_))

    def listFiles: List[Path] = list.filter(Files.isRegularFile(_))

    def file(nameFilter: String => Boolean): Option[Path] = listFiles.find(uri => nameFilter(uri.name))

    def directory(nameFilter: String => Boolean): Option[Path] = listDirectories.find(uri => nameFilter(uri.name))

    def list(nameFilter: String => Boolean): List[Path] = list.filter(p => nameFilter(p.name))

    def list(regexFilter: Regex): List[Path] = list.filter(p => regexFilter.findFirstMatchIn(p.name).isDefined)

  }

  object ClassPathResource {

    private[this] val lock = new ReentrantLock()

    def byPath(name: String): Option[Path] = {
      val resource = Option(ClassPathResource.getClass.getClassLoader.getResource(name))
      resource map { r =>
        val uri = r.toURI
        if ("jar" == uri.getScheme) {
          try {
            lock.lock()
            FileSystems.getFileSystem(uri).getPath(name)
          } catch {
            case fsnf: FileSystemNotFoundException => {
              FileSystems.newFileSystem(uri, Map[String, Object]()).getPath(name)
            }
          } finally {
            lock.unlock()
          }
        } else Paths.get(uri)
      }
    }

    private val jarResourceName =
      """jar:.{1,}!\/?([\/a-zA-Z0-9._\-]+)\/?""".r

    def byUri(uri: URI): Option[Path] = uri.toString match {
      case jarResourceName(name) => byPath(name)
      case _ => None
    }

  }

}
