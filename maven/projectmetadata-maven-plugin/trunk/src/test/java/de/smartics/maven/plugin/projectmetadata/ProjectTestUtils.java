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
package de.smartics.maven.plugin.projectmetadata;

import java.io.File;
import java.util.Arrays;

import de.smartics.util.test.io.FileTestUtils;

/**
 * Utilty functions for tests with descriptors.
 *
 * @author <a href="mailto:robert.reiner@smartics.de">Robert Reiner</a>
 * @version $Revision:591 $
 */
public final class ProjectTestUtils
{
  // ********************************* Fields *********************************

  // --- constants ------------------------------------------------------------

  // --- members --------------------------------------------------------------

  // ****************************** Initializer *******************************

  // ****************************** Constructors ******************************

  /**
   * Utility class pattern.
   */
  private ProjectTestUtils()
  {
  }

  // ****************************** Inner Classes *****************************

  // ********************************* Methods ********************************

  // --- init -----------------------------------------------------------------

  // --- get&set --------------------------------------------------------------

  // --- business -------------------------------------------------------------

  static MetaDataDescriptorReader createStandardReader(
      final String descriptorName)
  {
    return new MetaDataDescriptorReader(createStandardTestSet(descriptorName));
  }

  static DescriptorSet createStandardTestSet(final String descriptorName)
  {
    return new DescriptorSet(Arrays.asList(new String[]
    { descriptorName }), null);
  }

  static MetaDataDescriptorReader createAdditionalReader(final String basePath,
      final String descriptorName)
  {
    return new MetaDataDescriptorReader(createAdditionalTestSet(basePath,
        descriptorName));
  }

  static DescriptorSet createAdditionalTestSet(final String basePath,
      final String descriptorName)
  {
    final String resourceName = basePath + '/' + descriptorName + ".xml";
    final File descriptorFile = FileTestUtils.getFileFromResource(resourceName);
    return new DescriptorSet(null, Arrays.asList(new File[]
    { descriptorFile }));
  }

  static String normalizePath(final String path)
  {
    return new File(path).getPath();
  }

  // --- object basics --------------------------------------------------------

}
