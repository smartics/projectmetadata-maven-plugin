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
package de.smartics.maven.plugin.projectmetadata;

import java.io.File;
import java.util.List;

/**
 * Locations to fetch project meta data descriptors.
 *
 * @author <a href="mailto:robert.reiner@smartics.de">Robert Reiner</a>
 * @version $Revision:591 $
 */
public class DescriptorSet
{
  // ********************************* Fields *********************************

  // --- constants ------------------------------------------------------------

  /**
   * The list of descriptor names to include from the standard folder.
   *
   * @parameter
   */
  private final List<String> standardDescriptorNames;

  /**
   * The list of descriptor names to include from other locations.
   *
   * @parameter
   */
  private final List<File> additionalDescriptorFiles;

  // --- members --------------------------------------------------------------

  // ****************************** Initializer *******************************

  // ****************************** Constructors ******************************

  /**
   * Default constructor.
   *
   * @param standardDescriptorNames the list of descriptor names to include from
   *          the standard folder.
   * @param additionalDescriptorFiles the list of descriptor names to include
   *          from other locations.
   */
  public DescriptorSet(final List<String> standardDescriptorNames,
      final List<File> additionalDescriptorFiles)
  {
    this.standardDescriptorNames = standardDescriptorNames;
    this.additionalDescriptorFiles = additionalDescriptorFiles;
  }

  // ****************************** Inner Classes *****************************

  // ********************************* Methods ********************************

  // --- init -----------------------------------------------------------------

  // --- get&set --------------------------------------------------------------

  /**
   * Returns the list of descriptor names to include from the standard folder.
   *
   * @return the list of descriptor names to include from the standard folder.
   */
  public List<String> getStandardDescriptorNames()
  {
    return standardDescriptorNames;
  }

  /**
   * Returns the list of descriptor names to include from other locations.
   *
   * @return the list of descriptor names to include from other locations.
   */
  public List<File> getAdditionalDescriptorFiles()
  {
    return additionalDescriptorFiles;
  }

  // --- business -------------------------------------------------------------

  // --- object basics --------------------------------------------------------

}
