/*
 * Copyright 2010-2012 smartics, Kronseder & Reiner GmbH
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
package de.smartics.maven.plugin.projectmetadata.bo;

/**
 * The descriptor of the project files to include in the set of meta data.
 *
 * @author <a href="mailto:robert.reiner@smartics.de">Robert Reiner</a>
 * @version $Revision:591 $
 */
public class FilesDescriptor
{
  // ********************************* Fields *********************************

  // --- constants ------------------------------------------------------------

  // --- members --------------------------------------------------------------

  /**
   * The location of the files in the project layout. This may either be a file
   * or directory and is relative to the project's base directory.
   */
  private String projectLocation;

  /**
   * The location where the file(s) will be written to. This is always a
   * directory in the archive.
   */
  private String archiveLocation;

  /**
   * The filter used to select from the project location, if this location is a
   * directory. Otherwise the values here are ignored.
   */
  private FilterDescriptor filter;

  // ****************************** Initializer *******************************

  // ****************************** Constructors ******************************

  // ****************************** Inner Classes *****************************

  // ********************************* Methods ********************************

  // --- init -----------------------------------------------------------------

  // --- get&set --------------------------------------------------------------

  /**
   * Returns the location of the files in the project layout. This may either be
   * a file or directory and is relative to the project's base directory.
   *
   * @return the location of the files in the project layout.
   */
  public String getProjectLocation()
  {
    return projectLocation;
  }

  /**
   * Returns the location where the file(s) will be written to. This is always a
   * directory in the archive.
   *
   * @return the location where the file(s) will be written to.
   */
  public String getArchiveLocation()
  {
    return archiveLocation;
  }

  /**
   * Returns the filter used to select from the project location, if this
   * location is a directory. Otherwise the values here are ignored.
   *
   * @return the filter used to select from the project location, if this
   *         location is a directory.
   */
  public FilterDescriptor getFilter()
  {
    return filter;
  }

  // --- business -------------------------------------------------------------

  // --- object basics --------------------------------------------------------

}
