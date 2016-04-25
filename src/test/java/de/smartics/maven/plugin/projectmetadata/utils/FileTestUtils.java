/*
 * Copyright 2010-2016 smartics, Kronseder & Reiner GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.smartics.maven.plugin.projectmetadata.utils;

import org.apache.maven.shared.utils.io.IOUtil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * Utilities to make unit tests with files easier.
 *
 * @author <a href="mailto:robert.reiner@smartics.de">Robert Reiner</a>
 * @version $Revision:591 $
 */
public final class FileTestUtils
{
  // ********************************* Fields *********************************

  // --- constants ------------------------------------------------------------

  // --- members --------------------------------------------------------------

  // ****************************** Initializer *******************************

  // ****************************** Constructors ******************************

  /**
   * Utility class pattern.
   */
  private FileTestUtils()
  {
  }

  // ****************************** Inner Classes *****************************

  // ********************************* Methods ********************************

  // --- init -----------------------------------------------------------------

  // --- get&set --------------------------------------------------------------

  // --- business -------------------------------------------------------------

  /**
   * Returns the specified file from the class path.
   *
   * @param resourceName name of the resource (file or directory) in the class
   *          path.
   * @return the file representation of the resource or <code>null</code> if the
   *         resource does not exist, is not accessible via the
   *         <code>file:/</code> protocol or due to missing privileges.
   */
  public static File getFileFromResource(final String resourceName)
  {
    final ClassLoader classLoader = FileTestUtils.class.getClassLoader(); // NOPMD
    return getFileFromResource(classLoader, resourceName);
  }

  /**
   * Returns the specified file from the specified class loader.
   *
   * @param classLoader the class loader to load the resource from.
   * @param resourceName name of the resource (file or directory) in the class
   *          path.
   * @return the file representation of the resource or <code>null</code> if the
   *         resource does not exist, is not accessible via the
   *         <code>file:/</code> protocol or due to missing privileges.
   */
  public static File getFileFromResource(final ClassLoader classLoader,
      final String resourceName)
  {
    final URL url = classLoader.getResource(resourceName);
    final File file = getFileFrom(url);
    return file;
  }

  /**
   * Returns the specified file from the class loader associated with the given
   * type.
   *
   * @param type the type to refer to the class loader to load the resource
   *          from. The resource is relative to this type.
   * @param resourceName name of the resource (file or directory) in the class
   *          path.
   * @return the file representation of the resource or <code>null</code> if the
   *         resource does not exist, is not accessible via the
   *         <code>file:/</code> protocol or due to missing privileges.
   */
  public static File getFileFromResource(final Class<?> type,
      final String resourceName)
  {
    return getFileFromResource(type.getClassLoader(), resourceName);
  }

  /**
   * Returns the specified file from a resource relative to the given type.
   *
   * @param type the type to refer to the class loader to load the resource
   *          from. The resource is relative to this type.
   * @param relativeResourceName name of the resource (file or directory) in the
   *          class path, relative to the passed in <code>type</code>.
   * @return the file representation of the resource or <code>null</code> if the
   *         resource does not exist, is not accessible via the
   *         <code>file:/</code> protocol or due to missing privileges.
   */
  public static File getFileFromRelativeResource(final Class<?> type,
      final String relativeResourceName)
  {
    final URL url = type.getResource(relativeResourceName);
    final File file = getFileFrom(url);
    return file;
  }

  private static File getFileFrom(final URL url)
  {
    if (url != null)
    {
      final String resourceAsString = url.toExternalForm();

      if (resourceAsString.startsWith("file:/"))
      {
        final int startIndex = isWindows() ? 6 : 5;
        final String fileName = resourceAsString.substring(startIndex);
        final File file = new File(fileName);
        return file;
      }
    }
    return null;
  }

  private static boolean isWindows()
  {
    return File.separatorChar == '\\';
  }

  /**
   * Returns a properties object backed by the specified file from a resource
   * relative to the given type with the simple name of the resource with an
   * ending <code>.properties</code> .
   *
   * @param clazz the type to refer to the class loader to load the resource
   *          from. The resource is relative to this type and has its simple
   *          name.
   * @return the properties object containing the properties or empty if the
   *         resource does not exist or is empty.
   */
  public static Properties getPropertiesFromRelativePropertiesFileForClass(
      final Class<?> clazz)
  {

    final InputStream is =
        clazz.getResourceAsStream(clazz.getSimpleName().concat(".properties"));
    final BufferedInputStream buffer = new BufferedInputStream(is);
    final Properties props = new Properties();
    try
    {
      props.load(buffer);
    }
    catch (final IOException ioe)
    {
      // Do nohing
    }
    finally
    {
      IOUtil.close(buffer);
    }
    return props;
  }
  // --- object basics --------------------------------------------------------

}
