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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import de.smartics.maven.plugin.projectmetadata.bo.FilesDescriptor;
import de.smartics.maven.plugin.projectmetadata.bo.FilterDescriptor;
import de.smartics.maven.plugin.projectmetadata.bo.MetaDataDescriptor;

/**
 * Tests {@link MetaDataDescriptorReader}.
 * <p>
 * The test make sure that the XStream library is configured correctly.
 * </p>
 *
 * @author <a href="mailto:robert.reiner@smartics.de">Robert Reiner</a>
 * @version $Revision:591 $
 */
public class MetaDataDescriptorReaderTest
{
  // ********************************* Fields *********************************

  // --- constants ------------------------------------------------------------

  // --- members --------------------------------------------------------------

  // ****************************** Inner Classes *****************************

  // ********************************* Methods ********************************

  // --- prepare --------------------------------------------------------------

  // --- helper ---------------------------------------------------------------

  private static MetaDataDescriptorReader createReader(
      final String descriptorName)
  {
    final String basePath = "de/smartics/maven/plugin/projectmetadata";
    return ProjectTestUtils.createAdditionalReader(basePath, descriptorName);
  }

  private static void assertMinimal(final MetaDataDescriptor descriptor)
  {
    assertEquals("Minimal", descriptor.getName());
    final FilesDescriptor files = descriptor.getFiles();
    assertNotNull(files);
    assertEquals("/target/minimal", files.getProjectLocation());
    assertEquals("/minimal", files.getArchiveLocation());
    final FilterDescriptor filter = files.getFilter();
    assertNull(filter);
  }

  private static void assertFull(final MetaDataDescriptor descriptor)
  {
    assertEquals("Full", descriptor.getName());
    assertEquals("http://www.example.com/full", descriptor.getHomepage());
    assertEquals("A descriptor with all fields set.",
        descriptor.getDescription());
    assertEquals("category", descriptor.getCategory());
    assertEquals("subcategory", descriptor.getSubcategory());
    final FilesDescriptor files = descriptor.getFiles();
    assertNotNull(files);
    assertEquals("/target/full", files.getProjectLocation());
    assertEquals("/category/subcategory/full", files.getArchiveLocation());
    final FilterDescriptor filter = files.getFilter();
    assertNotNull(filter);

    final List<String> includes = filter.getIncludes();
    assertNotNull(includes);
    assertEquals(2, includes.size());
    assertEquals("*.xml", includes.get(0));
    assertEquals("*.csv", includes.get(1));

    final List<String> excludes = filter.getExcludes();
    assertNotNull(excludes);
    assertEquals(2, excludes.size());
    assertEquals("special.xml", excludes.get(0));
    assertEquals("index.csv", excludes.get(1));
  }

  // --- tests ----------------------------------------------------------------

  @Test
  public void emptyDescriptor() throws IOException
  {
    final MetaDataDescriptorReader uut = createReader("empty");

    final List<MetaDataDescriptor> result = uut.readMetaDataDescriptors();

    assertTrue("No descriptor expected.", result.isEmpty());
  }

  @Test
  public void minimalDescriptor() throws IOException
  {
    final MetaDataDescriptorReader uut = createReader("minimal");

    final List<MetaDataDescriptor> result = uut.readMetaDataDescriptors();

    assertEquals("One descriptor expected.", 1, result.size());
    final MetaDataDescriptor descriptor = result.get(0);
    assertMinimal(descriptor);
  }

  @Test
  public void fullDescriptor() throws IOException
  {
    final MetaDataDescriptorReader uut = createReader("full");

    final List<MetaDataDescriptor> result = uut.readMetaDataDescriptors();

    assertEquals("One descriptor expected.", 1, result.size());
    final MetaDataDescriptor descriptor = result.get(0);
    assertFull(descriptor);
  }

  @Test
  public void twoDescriptor() throws IOException
  {
    final MetaDataDescriptorReader uut = createReader("two");

    final List<MetaDataDescriptor> result = uut.readMetaDataDescriptors();

    assertEquals("Two descriptors expected.", 2, result.size());
    assertMinimal(result.get(0));
    assertFull(result.get(1));
  }
}
