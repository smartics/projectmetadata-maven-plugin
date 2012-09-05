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

import java.util.List;

/**
 * The project meta data descriptor.
 *
 * @author <a href="mailto:robert.reiner@smartics.de">Robert Reiner</a>
 * @version $Revision:591 $
 */
public class ProjectMetaDataDescriptors
{
  // ********************************* Fields *********************************

  // --- constants ------------------------------------------------------------

  // --- members --------------------------------------------------------------

  /**
   * The list of meta data descriptors to be included in the project meta data.
   */
  private List<MetaDataDescriptor> metaDataDescriptors;

  // ****************************** Initializer *******************************

  // ****************************** Constructors ******************************

  // ****************************** Inner Classes *****************************

  // ********************************* Methods ********************************

  // --- init -----------------------------------------------------------------

  // --- get&set --------------------------------------------------------------

  /**
   * Returns the list of meta data descriptors to be included in the project
   * meta data.
   *
   * @return the list of meta data descriptors to be included in the project
   *         meta data.
   */
  public List<MetaDataDescriptor> getMetaDataDescriptors()
  {
    return metaDataDescriptors;
  }

  // --- business -------------------------------------------------------------

  // --- object basics --------------------------------------------------------

}
