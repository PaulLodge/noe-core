package noe.tomcat.configure

import noe.common.utils.Cmd
import noe.common.utils.JBFile
import noe.common.utils.Library
import noe.common.utils.Platform
import noe.server.Tomcat;

/**
 * Abstraction for JMX remote acccess file
 *
 * @link http://tomcat.apache.org/tomcat-7.0-doc/monitoring.html#Enabling_JMX_Remote
 */
class JmxRemoteAccessFileTomcat {

  enum Access { readonly, readwrite }

  final private File path
  private Map<String, Access> roleAccess

  Tomcat tomcat


  JmxRemoteAccessFileTomcat(Tomcat tomcat, File path, Map<String, Access> roleAccess) {
    this.path = path
    this.roleAccess = roleAccess
    this.tomcat = tomcat
  }

  /**
   * Remove file if exists and create new.
   */
  void reCreate() {
    String nl = new Platform().nl
    path.delete()

    def content = ""
    roleAccess.each {String role, Access access ->
      content += "${role} ${access.toString()}"
      content += "${nl}"
    }

    JBFile.createFile(path, content)

    JmxTomcatUtils.prepareAccessRights(tomcat, path)
  }

  File getPath() {
    return path
  }
}
