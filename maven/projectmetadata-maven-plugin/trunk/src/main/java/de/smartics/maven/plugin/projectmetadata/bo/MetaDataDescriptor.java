/*
 * Copyright 2010-2013 smartics, Kronseder & Reiner GmbH
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
 * A descriptor of a piece of project meta data. This descriptor is used to
 * fetch the relevant files from the project and to package them in the JAR
 * artifact.
 *
 * @author <a href="mailto:robert.reiner@smartics.de">Robert Reiner</a>
 * @version $Revision:591 $
 */
public class MetaDataDescriptor
{
  // ********************************* Fields *********************************

  // --- constants ------------------------------------------------------------

  // --- members --------------------------------------------------------------

  /**
   * The descriptive name of the meta data. This is usually the name of the tool
   * that generates the meta data.
   */
  private String name;

  /**
   * The URL to the home page of the tool that generated the meta data. This may
   * make it easier for consumers to find information about the meaning of the
   * meta data.
   */
  private String homepage;

  /**
   * Description to the tool and/or the meta data.
   */
  private String description;

  /**
   * The category the meta data belongs to.
   */
  private String category;

  /**
   * The subcategory the meta data belongs to.
   */
  private String subcategory;

  /**
   * The descriptor of the project files to include in the set of meta data.
   */
  private FilesDescriptor files;

  // ****************************** Initializer *******************************

  // ****************************** Constructors ******************************

  // ****************************** Inner Classes *****************************

  // ********************************* Methods ********************************

  // --- init -----------------------------------------------------------------

  // --- get&set --------------------------------------------------------------

  /**
   * Returns the descriptive name of the meta data. This is usually the name of
   * the tool that generates the meta data.
   *
   * @return the descriptive name of the meta data.
   */
  public String getName()
  {
    return name;
  }

  /**
   * Returns the URL to the home page of the tool that generated the meta data.
   * This may make it easier for consumers to find information about the meaning
   * of the meta data.
   *
   * @return the URL to the home page of the tool that generated the meta data.
   */
  public String getHomepage()
  {
    return homepage;
  }

  /**
   * Returns the value for description.
   * <p>
   * Description to the tool and/or the meta data.
   *
   * @return the value for description.
   */
  public String getDescription()
  {
    return description;
  }

  /**
   * Returns the category the meta data belongs to.
   *
   * @return the category the meta data belongs to.
   */
  public String getCategory()
  {
    return category;
  }

  /**
   * Returns the subcategory the meta data belongs to.
   *
   * @return the subcategory the meta data belongs to.
   */
  public String getSubcategory()
  {
    return subcategory;
  }

  /**
   * Returns the descriptor of the project files to include in the set of meta
   * data.
   *
   * @return the descriptor of the project files to include in the set of meta
   *         data.
   */
  public FilesDescriptor getFiles()
  {
    return files;
  }

  // --- business -------------------------------------------------------------

  // --- object basics --------------------------------------------------------

}
