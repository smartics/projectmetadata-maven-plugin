/*
 * Copyright 2010-2011 smartics, Kronseder & Reiner GmbH
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
 * The filter is used to select files for inclusion.
 *
 * @author <a href="mailto:robert.reiner@smartics.de">Robert Reiner</a>
 * @version $Revision:591 $
 */
public class FilterDescriptor
{
  // ********************************* Fields *********************************

  // --- constants ------------------------------------------------------------

  // --- members --------------------------------------------------------------

  /**
   * The pattern to include files. Use the common <a
   * href="http://ant.apache.org/">Ant</a> patterns.
   */
  private List<String> includes;

  /**
   * The pattern to exclude files. Use the common <a
   * href="http://ant.apache.org/">Ant</a> patterns.
   */
  private List<String> excludes;

  // ****************************** Initializer *******************************

  // ****************************** Constructors ******************************

  // ****************************** Inner Classes *****************************

  // ********************************* Methods ********************************

  // --- init -----------------------------------------------------------------

  // --- get&set --------------------------------------------------------------

  /**
   * Returns the pattern to include files. Use the common <a
   * href="http://ant.apache.org/">Ant</a> patterns.
   *
   * @return the pattern to include files.
   */
  public List<String> getIncludes()
  {
    return includes;
  }

  /**
   * Returns the pattern to exclude files. Use the common <a
   * href="http://ant.apache.org/">Ant</a> patterns.
   *
   * @return the pattern to exclude files.
   */
  public List<String> getExcludes()
  {
    return excludes;
  }

  // --- business -------------------------------------------------------------

  // --- object basics --------------------------------------------------------

}
